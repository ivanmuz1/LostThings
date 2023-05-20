package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CategoryActivity extends AppCompatActivity {

    private Button phone;
    private Button animals;
    private Button document;
    private Button other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        phone = (Button)findViewById(R.id.phone);
        animals =  (Button)findViewById(R.id.animals);
        document =  (Button)findViewById(R.id.documents);
        other = (Button)findViewById(R.id.other);


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phone = new Intent(CategoryActivity.this, AddNewLostThing.class);
                startActivity(phone);

            }
        });


    }


}