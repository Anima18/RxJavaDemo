package com.example.jianjianhong.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.internal.util.UtilityFunctions;

public class MainActivity extends AppCompatActivity {

    private ListView dataLv;
    private List<ActivityClass> activityClassList;

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        dataLv = (ListView)findViewById(R.id.data_lv);

        ListDataAdapter adapter = new ListDataAdapter(this, getActivityClassList());
        dataLv.setAdapter(adapter);
        dataLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityClass ac = activityClassList.get(position);
                if(ac != null && ac.getActivityClass() != null) {
                    Intent intent = new Intent(MainActivity.this, ac.getActivityClass());
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "没有实现", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public List<ActivityClass> getActivityClassList() {
        activityClassList = new ArrayList<>();

        activityClassList.add(new ActivityClass("Create operators", CreateOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Transform operators", TransformOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Filter operators", FilterOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Combining operators", CombinOperatorsActivity.class));
        activityClassList.add(new ActivityClass("ErrorHandling operators", ErrorHandlingOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Utility operators", UitilityOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Conditional and Boolean Operators", ConditionalAndBooleanOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Average operators", AverageOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Async operators", AsyncOperatorsActivity.class));
        activityClassList.add(new ActivityClass("Connectable operators", ConnectableOperatorsActivity.class));

        return activityClassList;
    }
}
