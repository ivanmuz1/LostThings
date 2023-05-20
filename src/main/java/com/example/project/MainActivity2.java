package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {
    private EditText message;
    private EditText userName;
    private ImageButton sendMessage;
    private String Message, saveCurrentDate, saveCurrentTime, productKey;
    private TextView inMessage;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        message = findViewById(R.id.messageEditTxt);
        sendMessage = findViewById(R.id.sendBtn);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Chat");

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateNewMessage();
            }

        });

        recyclerView = findViewById(R.id.Recycler_chat_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<chatItems> options = new FirebaseRecyclerOptions.Builder<chatItems>().setQuery(ProductsRef, chatItems.class).build();
        FirebaseRecyclerAdapter<chatItems, ViewChat> adapter = new FirebaseRecyclerAdapter<chatItems, ViewChat>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewChat holder, int position, @NonNull chatItems model) {
                holder.messageTxt.setText(model.getMessage());
            }

            @NonNull
            @Override
            public ViewChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_items,parent,false);
                ViewChat holder = new ViewChat(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    private void ValidateNewMessage() {

        Message = message.getText().toString();
        SendToFirebase();
    }

    private void SendToFirebase() {
        Calendar calendar  = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd.MM.yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productKey = saveCurrentDate + saveCurrentTime;

        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",productKey);
        productMap.put("data",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("message",Message);

        ProductsRef.child(productKey).updateChildren(productMap);
    }

}