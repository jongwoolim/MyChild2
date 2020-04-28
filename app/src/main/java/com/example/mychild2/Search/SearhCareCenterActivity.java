package com.example.mychild2.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mychild2.R;
import com.example.mychild2.Domain.Item.item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SearhCareCenterActivity extends AppCompatActivity {

    private ListView list_api; // 어린이집 정보 뿌려주기
    private SearchView search_view;
    private ArrayList<item> vItem = new ArrayList<>(); // 어린이집 정보
    private ArrayList<item> items = new ArrayList<>(); // 어린이집 검색 정보
    private List<String> data = new ArrayList<>(); // 리스트에 담을 내용
    private ArrayList<String> arrayList = new ArrayList<>(); // 검색된 정보 리스트에 담을 내용
    private ArrayAdapter<String> adapter;
    private item i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_care_center);

        list_api = (ListView) findViewById(R.id.list_api);
        search_view = (SearchView) findViewById(R.id.search_view);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        list_api.setAdapter(adapter);

        String loadXmlString = loadXml();

        XmlParsing(loadXmlString);

        items.addAll(vItem);
        arrayList.addAll(data);

       search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {

               String filterText = newText;
               search(filterText);

               list_api.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Intent intent = new Intent();
                       intent.putExtra("CrName", vItem.get(position).getCrname());
                       intent.putExtra("CrAddr", vItem.get(position).getCraddr());
                       setResult(RESULT_OK, intent);
                       finish();
                   }
               });
               Toast.makeText(SearhCareCenterActivity.this, newText, Toast.LENGTH_SHORT).show();
               return true;
           }
       });


        list_api.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("CrName", vItem.get(position).getCrname());
                intent.putExtra("CrAddr", vItem.get(position).getCraddr());
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }


    //어린이집 검색
    private void search(String filterText) {

        vItem.clear();
        data.clear();

        if(filterText.length() == 0){
            data.addAll(arrayList);
            vItem.addAll(items);

        }else{
            for(int i=0; i<arrayList.size(); i++){
                if(arrayList.get(i).contains(filterText)){
                    data.add(arrayList.get(i));
                    vItem.add(items.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    // XML 파싱
    private void XmlParsing(String loadXmlString) {

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(loadXmlString));

            Log.i("===parser======","loaded!!!!");

            int eventType = parser.getEventType();
            String tagName = "";

            while(eventType != XmlPullParser.END_DOCUMENT){

                switch(eventType){
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if(parser.getName().equals("item")){
                            i = new item();
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            vItem.add(i);
                            data.add("시설코드 : " + i.getStcode() +"\n보육시설명 : "+
                                    i.getCrname()+"\n전화번호 : "+ i.getCrtel()+"\n팩스 :"+i.getCrfax()+"\n주소 : "+ i.getCraddr()+
                                    "\n정원 : "+ i.getCrcapat()+"\n"+"\n");
                            i=null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch (tagName){
                            case "stcode":
                                i.setStcode(parser.getText());
                                break;
                            case "crname":
                                i.setCrname(parser.getText());
                                break;
                            case "crtel":
                                i.setCrtel(parser.getText());
                                break;
                            case "crfax":
                                i.setCrfax(parser.getText());
                                break;
                            case "craddr":
                                i.setCraddr(parser.getText());
                                break;
                            case "crcapat":
                                i.setCrcapat(parser.getText());
                                break;
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("======List is ",vItem.size() +" values===============");
    }

    // XML load
    private String loadXml() {
        AssetManager assetManager = getResources().getAssets();
        AssetManager.AssetInputStream ais = null;

        try{
            ais = (AssetManager.AssetInputStream)assetManager.open("item.xml");

        }catch (Exception e){
            e.printStackTrace();

        }
        Log.d("TAG","loaded!!");

        String line = "";
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(ais));

        try{
            while((line = br.readLine())!= null){
                sb.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("==========XML=========",sb.toString());
        return sb.toString();
    }


}
