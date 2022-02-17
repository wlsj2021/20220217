package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.bean.todo.DeleteTodo;
import com.wlsj2021.myapplication.bean.todo.FinishTodo;
import com.wlsj2021.myapplication.bean.todo.Todo;
import com.wlsj2021.myapplication.contract.todo.Contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/02/22
 * Time: 21:47
 */
public class TodoModel extends BaseModel implements Contract.ITodoModel {
    public TodoModel() {
        setCookies(false);
    }
    @Override
    public Observable<List<Todo>> loadTodo(int pageNum) {
        Observable<List<Todo>> loadFromNet = loadTodoFromNet(pageNum);
        return loadFromNet;
    }

    private Observable<List<Todo>> loadTodoFromNet(int pageNum) {
        return mApiServer.loadTodoData(pageNum).filter(todoData -> todoData.getErrorCode() == Constant.SUCCESS)
                .map(todoData -> {
                    List<Todo> todoList = new ArrayList<>();
                    todoData.getData().getDatas().stream().forEach(datasBean -> {
                        Todo todo = new Todo();
                        todo.id = datasBean.getId();
                        todo.title = datasBean.getTitle();
                        todo.content = datasBean.getContent();
                        todo.dateStr = datasBean.getDateStr();
                        todo.priority = datasBean.getPriority();
                        todo.status = datasBean.getStatus();
                        todo.date = datasBean.getDate();
                        todo.type = datasBean.getType();
                        todo.completeDate = datasBean.getCompleteDate();
                        todoList.add(todo);
                    });
                    return todoList;
                });
    }

    @Override
    public Observable<List<Todo>> refreshTodo(int pageNum) {
        return loadTodoFromNet(pageNum);
    }

    @Override
    public Observable<DeleteTodo> deleteTodo(int id) {
        return mApiServer.deleteTodo(id);
    }

    @Override
    public Observable<FinishTodo> finishTodo(int id, int status) {
        return mApiServer.finishTodo(id, status);
    }
}
