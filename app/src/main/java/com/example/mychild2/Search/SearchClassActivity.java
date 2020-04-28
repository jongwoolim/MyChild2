package com.example.mychild2.Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.mychild2.R;

import java.util.ArrayList;
import java.util.List;

public class SearchClassActivity extends AppCompatActivity {

    public static final int CLASS_EMPTY_RESULT_OK = 9999;
    private ListView list_class;
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class);

     list_class = (ListView)findViewById(R.id.list_class);

     Intent intent = getIntent();
     String careCenterText = intent.getStringExtra("careCenterText");

     adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

     if(careCenterText.equals("남서울대학교 부설어린이집")){

         data.add("A");
         data.add("B");
         data.add("C");

     }else if (careCenterText.equals("삼성어린이집")){

         data.add("새싹반");
         data.add("장미반");
         data.add("민들레반");
         data.add("해바라기반");
     }else if(careCenterText.equals("")){
         Intent intent1 = new Intent();
         setResult(CLASS_EMPTY_RESULT_OK, intent1);
         finish();
     }
     list_class.setAdapter(adapter);


     list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra("Class", data.get(position));
            setResult(RESULT_OK, intent);
            finish();
         }
     });


    }


}
