package com.example.mychild2.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mychild2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchChildActivity extends AppCompatActivity {

    public static final int CHILD_EMPTY_RESULT_OK = 8888;
    private ListView list_child;
    private List<String> data = new ArrayList<>();
    private ArrayAdapter adapter;
    private DatabaseReference databaseReference;
    private Map<Integer, String> ChildName = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__child);

        list_child = (ListView)findViewById(R.id.list_child);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

        Intent intent = getIntent();
        String careCenter = intent.getStringExtra("careCenter");
        String Class = intent.getStringExtra("Class");

        if(careCenter.equals("") || Class.equals("")){
            Intent intent1 = new Intent();
            setResult(CHILD_EMPTY_RESULT_OK, intent1);
            finish();
        }else {
            databaseReference.child("Child").child(Class.substring(0, 1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {

                        String Name = nameSnapshot.child("ch_name").getValue(String.class);
                        String Number = nameSnapshot.child("ch_number").getValue(String.class);
                        Log.i("nameTAG", "Value is " + Name);
                        Log.i("numTAG", "Value is " + Number);

                        ChildName.put(Integer.parseInt(Number), Name);
                        data.add(Name);
                    }

                    //key 확인
                    Set<Integer> keys = ChildName.keySet();
                    for(int key : keys){
                        Log.i("keyTAG","==========="+ key);
                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        list_child.setAdapter(adapter);



        list_child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int NumberKey = 0;
                Set<Integer> keys = ChildName.keySet();
                for(int key : keys){
                    if(key == (position+1)){
                        NumberKey = key;
                        Log.i("abcdefg","==========================="+NumberKey);
                    }
                }
                String Number_Key = String.valueOf(NumberKey);
                Intent intent = new Intent();
                intent.putExtra("ChildName", data.get(position));
                intent.putExtra("ChildNumber", Number_Key);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
