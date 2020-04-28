package com.example.mychild2.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mychild2.Fragment.Play_AreaActivity;
import com.example.mychild2.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Main2Activity extends AppCompatActivity {

    private BarChart barChart; // 차트
    private Button btn_comment; // 코멘트 버튼
    private TextView tv_child_info, tv_today_class; // 아이 정보
    private ArrayList NoOfEmp = new ArrayList(); // 차트 데이터
    private ArrayList area = new ArrayList(); // 성향

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv_child_info = (TextView)findViewById(R.id.tv_child_info);
        tv_today_class = (TextView)findViewById(R.id.tv_today_class);

        barChart = (BarChart)findViewById(R.id.barchart);

        Intent intent = getIntent();

        final String chartName = intent.getStringExtra("paramChartName");
        final String chartClass = intent.getStringExtra("paramChartClass");
        final String ChildNumber = intent.getStringExtra("paramChildNumber");

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        String time = format.format(date);

        tv_child_info.setText(time+ " " + chartClass + " " + chartName);

        NoOfEmp.add(new BarEntry(23f, 0));
        NoOfEmp.add(new BarEntry(72f, 1));
        NoOfEmp.add(new BarEntry(35f, 2));
        NoOfEmp.add(new BarEntry(66f, 3));


        area.add("수조작 영역");
        area.add("과학 영역");
        area.add("쌓기 영역");
        area.add("음률 영역");

        BarDataSet bar_dataset = new BarDataSet(NoOfEmp, "놀이 영역");
        barChart.animateY(5000);
        BarData data = new BarData(area, bar_dataset);      // MPAndroidChart v3.X 오류 발생
        bar_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                int index = e.getXIndex();
                Log.i("barchart","index is" + index);
                Intent intent = new Intent(getApplicationContext(), Play_AreaActivity.class);

                intent.putExtra("fragment_Index", index);

                startActivity(intent);

            }

            @Override
            public void onNothingSelected() {

            }
        });

        tv_today_class.setText("");


        btn_comment = (Button)findViewById(R.id.btn_comment);
        //클릭 시 Main3Activity 화면으로 넘어가기
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                intent.putExtra("paramChartName", chartName);
                intent.putExtra("paramChartClass", chartClass);
                intent.putExtra("paramChildNumber", ChildNumber);
                startActivity(intent);
            }
        });
    }

}
