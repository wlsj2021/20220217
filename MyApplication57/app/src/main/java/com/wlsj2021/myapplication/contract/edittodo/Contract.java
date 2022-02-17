package com.wlsj2021.myapplication.contract.edittodo;

import com.wlsj2021.myapplication.base.interfaces.IBaseView;
import com.wlsj2021.myapplication.bean.todo.DeleteTodo;
import com.wlsj2021.myapplication.bean.todo.FinishTodo;
import com.wlsj2021.myapplication.bean.todo.Todo;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/02/22
 * Time: 21:25
 */
public class Contract {
    public interface IEditTodoModel {
        /**
         * 添加一个Todo
         *
         * @param title
         * @param content
         * @param date
         * @param type
         * @param priority
         * @return
         */
        Observable<Todo> addTodo(String title, String content, String date, int type, int priority);

        /**
         * 更新一个Todo
         *
         * @param title
         * @param content
         * @param date
         * @param type
         * @param priority
         * @param id
         * @return
         */
        Observable<Todo> updateTodo(int id, String title, String content, String date, int type, int priority);
    }

    public interface IEditTodoView extends IBaseView {

        void onAddTodo(Todo todo);

        void onUpdateTodo(Todo todo);
    }

    public interface IEditTodoPresenter {
        void addTodo(String title, String content, String date, int type, int priority);

        void updateTodo(int id, String title, String content, String date, int type, int priority);
    }
}
