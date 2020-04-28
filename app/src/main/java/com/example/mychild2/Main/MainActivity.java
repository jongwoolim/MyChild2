package com.example.mychild2.Main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mychild2.Domain.child.Child;
import com.example.mychild2.LoginActivity;
import com.example.mychild2.R;
import com.example.mychild2.Search.SearchChildActivity;
import com.example.mychild2.Search.SearchClassActivity;
import com.example.mychild2.Search.SearhCareCenterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

import static com.example.mychild2.Search.SearchChildActivity.CHILD_EMPTY_RESULT_OK;
import static com.example.mychild2.Search.SearchClassActivity.CLASS_EMPTY_RESULT_OK;

public class MainActivity extends AppCompatActivity  {

    private static final int CENTER_REQUEST_CODE = 1000; // SearchCareCenterActivity 요청 코드
    private static final int CLASS_REQUEST_CODE = 2000; // SearchClassActivity 요청 코드
    private static final int CHILD_REQUEST_CODE = 3000; // SearchChildActivity 요청 코드
    private long backTime = 0; // 뒤로가기 버튼 시간 초기화

    private Button btn_barchart; // 차트 보러가기 버튼
    private ImageButton search_care_center, search_class, search_child; // 어린이집, 반, 이름 검색버튼
    private TextView tv__care_center, tv__care_center_addr, tv_class, tv__name; // 어린이집,주소 , 반, 이름 텍스트뷰

    private DatabaseReference databaseReference;

    private String care_Center_text; // 어린이집
    private String care_Class_text; // 반
    private String child_Name_text; // 이름
    private String child_Number; // 번호
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //해쉬 값 가져오기
        mContext = getApplicationContext();
        getHashKey(mContext);



  //데이터베이스 쓰기
        /*databaseReference = FirebaseDatabase.getInstance().getReference();
        writeNewChild("D","1","김미모","6살","1","충청남도 천안시 서북구 000-000",
                "010-4523-6232","이 아이는 매우 활발하며 뛰어노는 것을 좋아합니다.");

        writeNewChild("D","2","박미모","4살","2","경기도 안산시 상록구 ",
                "010-46324-5732","이 아이는 조용하고 소극적이며 매우 착합니다.");

        writeNewChild("D","3","이미모","5살","3","경기도 안양시 동안구 비산2동 ",
                "010-5232-6623","이 아이는 과학 영역에 관심이 많습니다");

        writeNewChild("D","4","최미모","4살","4","경기도 용인시 처인구 ",
                "010-7434-9785","피아노를 매우 잘 칩니다");

        writeNewChild("D","5","임미모","4살","5","경기도 안양시 만안구 ",
                "010-5323-7643","이 아이는 먹는 것을 좋아하고 호기심이 많은 아이입니다.");*/


        // 텍스트뷰 ID 찾기
        tv__care_center = (TextView)findViewById(R.id.tv__care_center);
        tv__care_center_addr = (TextView)findViewById(R.id.tv__care_center_addr);
        tv_class = (TextView)findViewById(R.id.tv__class);
        tv__name = (TextView)findViewById(R.id.tv__name);

        //현재 사용자가 null이면 로그인 화면 넘어가기
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startLoginAct();
        }


        btn_barchart = (Button)findViewById(R.id.btn_barchart);
        //차트 보러가기
            btn_barchart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tv__care_center.getText().toString().equals("") || tv_class.getText().toString().equals("") || tv__name.getText().toString().equals("")){

                        Toast.makeText(MainActivity.this, "어린이집과 반, 이름을 선택해 주세요", Toast.LENGTH_SHORT).show();

                    }else{

                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);

                        care_Class_text = tv_class.getText().toString();
                        child_Name_text = tv__name.getText().toString();

                        intent.putExtra("paramChartClass", care_Class_text);
                        intent.putExtra("paramChartName", child_Name_text);
                        intent.putExtra("paramChildNumber", child_Number);

                        startActivity(intent);

                    }

                }
            });

       

        search_care_center = (ImageButton)findViewById(R.id.search_care_center);
        //어린이집 검색
        search_care_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SearhCareCenterActivity.class);
                startActivityForResult(intent, CENTER_REQUEST_CODE);
            }
        });

        search_class = (ImageButton)findViewById(R.id.search_class);
        //반 이름 검색
        search_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SearchClassActivity.class);

                care_Center_text = tv__care_center.getText().toString();
                intent.putExtra("careCenterText",care_Center_text);

                startActivityForResult(intent, CLASS_REQUEST_CODE);
            }
        });

        search_child = (ImageButton)findViewById(R.id.search_child);
        //아이 이름 검색
        search_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchChildActivity.class);

                care_Center_text = tv__care_center.getText().toString();
                care_Class_text = tv_class.getText().toString();

                intent.putExtra("careCenter", care_Center_text);
                intent.putExtra("Class", care_Class_text);

                startActivityForResult(intent, CHILD_REQUEST_CODE);

            }
        });



    }

    // 데이터베이스 쓰기
    public void writeNewChild(String CH_class, String CH_id, String CH_name, String CH_age,
                              String CH_number, String CH_addr, String CH_phone, String CH_comment){

        Child child = new Child(CH_class, CH_name, CH_age, CH_number, CH_addr, CH_phone, CH_comment);

        databaseReference.child("Child").child(CH_class).child(CH_id).setValue(child);
    }


    //2초 안에 두 번 눌러야 메인화면 종료 && 로그아웃
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backTime;
        if(gapTime >=0 && gapTime <=2000){
            signOut();
            super.onBackPressed();
        }else{
            backTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }

    //로그인 화면 이동
    private void startLoginAct(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //로그아웃
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

//어린이집 이름,주소,반, 이름 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //어린이집과 주소 데이터 가져오기
        if(requestCode == CENTER_REQUEST_CODE && resultCode == RESULT_OK) {

            Toast.makeText(getApplicationContext(),"선택 완료",Toast.LENGTH_SHORT).show();
            String care_center = data.getStringExtra("CrName");
            String care_addr = data.getStringExtra("CrAddr");

            tv__care_center.setText(care_center);
            tv__care_center_addr.setText(care_addr);

        }//반 이름 데이터 가져오기
        else if(requestCode == CLASS_REQUEST_CODE && resultCode == RESULT_OK){

            Toast.makeText(getApplicationContext(), "선택 완료", Toast.LENGTH_SHORT).show();

            String Search_class = data.getStringExtra("Class");
            tv_class.setText(Search_class+"반");

        }else if(requestCode == CLASS_REQUEST_CODE && resultCode == CLASS_EMPTY_RESULT_OK){

            Toast.makeText(getApplicationContext(), "어린이집을 선택해 주세요", Toast.LENGTH_SHORT).show();

        }//아이 이름 가져오기
        else if(requestCode == CHILD_REQUEST_CODE && resultCode == RESULT_OK){

            Toast.makeText(this, "선택 완료", Toast.LENGTH_SHORT).show();
            String ChildName = data.getStringExtra("ChildName");
            String ChildNumber =  data.getStringExtra("ChildNumber");
            tv__name.setText(ChildName + " 어린이");
            child_Number = ChildNumber;
            Log.i("Key_TAG","==============="+ChildNumber);

        }else if(requestCode == CHILD_REQUEST_CODE && resultCode == CHILD_EMPTY_RESULT_OK){

            Toast.makeText(this, "어린이집과 반을 선택해 주세요", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(this, "선택 실패", Toast.LENGTH_SHORT).show();
        }






    }


    //해쉬 키 함수
    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }
}
