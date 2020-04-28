package com.example.mychild2.Main;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mychild2.Domain.child.Child;
import com.example.mychild2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Main3Activity extends AppCompatActivity {

    private TextView tv_comment;
    private TextView tv_name;
    private TextView tv_class;
    private TextView tv_age;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv_comment = (TextView)findViewById(R.id.main3_tv_comment);
        tv_name = (TextView)findViewById(R.id.main3_tv_name);
        tv_class = (TextView)findViewById(R.id.main3_tv_class);
        tv_age = (TextView)findViewById(R.id.main3_tv_age);

        Intent intent = getIntent();

        String paramChartName = intent.getStringExtra("paramChartName");
        String paramChartClass = intent.getStringExtra("paramChartClass");
        String paramChildNumber = intent.getStringExtra("paramChildNumber");

        Log.i("paramChildNumber","=========================="+paramChildNumber);
        tv_name.setText(paramChartName);
        tv_class.setText(paramChartClass);

        //코멘트
        getValue(paramChartClass, paramChildNumber);

    }

    // 데이터베이스 읽기
    public void getValue(String paramChartClass, String paramChildNumber){

        reference.child("Child").child(paramChartClass.substring(0,1)).child(paramChildNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Child child = dataSnapshot.getValue(Child.class);
                tv_comment.setText(child.getCH_comment());
                tv_age.setText(child.getCH_age());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
