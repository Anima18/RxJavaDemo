package com.example.jianjianhong.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * Created by jianjianhong on 2016/5/30.
 */
public class TransformOperatorsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "TransformActivity";

    private Button mapBt;
    private Button flatMapBt;
    private Button groupByBt;
    private Button bufferBt;
    private Button scanBt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transform_operators);
        initView();
        initEvent();
    }

    private void initView() {
        mapBt = (Button)findViewById(R.id.tpAct_map);
        flatMapBt = (Button)findViewById(R.id.tpAct_flatMap);
        groupByBt = (Button)findViewById(R.id.tpAct_groupBy);
        bufferBt = (Button)findViewById(R.id.tpAct_buffer);
        scanBt = (Button)findViewById(R.id.tpAct_scan);
    }

    private void initEvent() {
        mapBt.setOnClickListener(this);
        flatMapBt.setOnClickListener(this);
        groupByBt.setOnClickListener(this);
        bufferBt.setOnClickListener(this);
        scanBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tpAct_map:
                map();
                break;
            case R.id.tpAct_flatMap:
                flatMap();
                break;
            case R.id.tpAct_groupBy:
                groupBy();
                break;
            case R.id.tpAct_buffer:
                buffer();
                break;
            case R.id.tpAct_scan:
                scan();
                break;
        }
    }

    private void map() {
        Log.d(TAG, "===========map===========");
        String[] array = {"Robert", "Joy", "Denny"};
        Observable.from(array)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "1";
                    }
                })
                .subscribe(getStringSubscriber());
    }

    private Subscriber getStringSubscriber() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
            }
        };
    }

    private void flatMap() {
        Log.d(TAG, "===========flatMap===========");
        Observable.just("test1", "test2")
                .flatMap(new Func1<String, Observable<? extends ActivityClass>>() {
                    @Override
                    public Observable<? extends ActivityClass> call(String s) {
                        return Observable.just(new ActivityClass(s, null));
                    }
                }, 1)
                .subscribe(new Action1<ActivityClass>() {
                    @Override
                    public void call(ActivityClass activityClass) {
                        Log.d(TAG, activityClass.getActivityName());
                    }
                });
    }

    private void groupBy() {
        Log.d(TAG, "===========groupBy===========");
        String[] array = {"1", "2", "3"};
        Observable.from(array)
                .groupBy(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return s+"ddd";
                    }
                })
                .subscribe(new Subscriber<GroupedObservable<Object, String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final GroupedObservable<Object, String> objectStringGroupedObservable) {
                        objectStringGroupedObservable.subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.d(TAG, "key:" + objectStringGroupedObservable.getKey() +", value:" + s);
                            }
                        });
                    }
                });
    }

    private void buffer() {
        Log.d(TAG, "===========buffer===========");
        String[] array = {"Chris", "Devin", "Shiny","Robert", "Joy", "Denny"};
        Observable.from(array)
                .buffer(2)
                //.buffer(2, 3)
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        for(String s : strings) {
                            Log.d(TAG, s);
                        }
                    }
                });
    }

    private void scan() {
        Log.d(TAG, "===========scan===========");
        Observable.just(1, 2, 3, 4, 5)
                .scan(10, new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer sum, Integer item) {
                        return sum + item;
                    }
                }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                Log.d(TAG, "Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }
}
