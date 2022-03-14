package com.wlsj2022.recyclerviewim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private Button bt_send;

    private EditText et_context;

    private MessageAdapter adapter;

    private List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_test);
        bt_send = findViewById(R.id.bt_send);
        et_context = findViewById(R.id.et_context);

        initdata();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MessageAdapter(MainActivity.this, messages);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initdata() {
        Message message1 = new Message("说什么呢，我也不知的", Message.TYPE_RIGHT);
        Message message2 = new Message("说什么呢，我也不知的", 2);
        Message message3 = new Message("说什么呢，我也不知的", 2);
        Message message4 = new Message("说什么呢，我也不知的", Message.TYPE_LEFT);
        Message message5 = new Message("说什么呢，我也不知的", 1);
        Message message6 = new Message("说什么呢，我也不知的", 2);
        Message message7 = new Message("说什么呢，我也不知的", 1);
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);
        messages.add(message6);
        messages.add(message7);


    }


    public void send(View view) {

        switch (view.getId()) {
            case R.id.bt_send:
                if (!et_context.getText().equals("") && et_context.getText() != null) {
                    Message message = new Message(et_context.getText().toString(), Message.TYPE_RIGHT);
                    messages.add(message);
                    adapter.notifyItemInserted(messages.size() - 1);//有新消息时刷新recycleview中的显示
                    recyclerView.scrollToPosition(messages.size() - 1);//将recycleview定位到最后一行
                    et_context.setText("");
                }

                break;
            default:
                break;
        }
    }
}