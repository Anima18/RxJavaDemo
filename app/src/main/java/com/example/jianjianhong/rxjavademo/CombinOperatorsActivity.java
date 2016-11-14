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
import rx.functions.Func3;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * Created by jianjianhong on 2016/5/30.
 */
public class CombinOperatorsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "CombinOperatorsActivity";

    private Button combineBt;
    private Button joinBt;
    private Button mergeBt;
    private Button startWithBt;
    private Button zipBt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_combin_operators);
        initView();
        initEvent();
    }

    private void initView() {
        combineBt = (Button)findViewById(R.id.cbpAct_combine);
        joinBt = (Button)findViewById(R.id.cbpAct_join);
        mergeBt = (Button)findViewById(R.id.cbpAct_merge);
        startWithBt = (Button)findViewById(R.id.cbpAct_startWith);
        zipBt = (Button)findViewById(R.id.cbpAct_zip);
    }

    private void initEvent() {
        combineBt.setOnClickListener(this);
        joinBt.setOnClickListener(this);
        mergeBt.setOnClickListener(this);
        startWithBt.setOnClickListener(this);
        zipBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbpAct_combine:
                combine();
                break;
            case R.id.cbpAct_join:
                join();
                break;
            case R.id.cbpAct_merge:
                merge();
                break;
            case R.id.cbpAct_startWith:
                startWith();
                break;
            case R.id.cbpAct_zip:
                zip();
                break;
        }
    }

    private void combine() {
        Observable O1 = Observable.just("1", "2", "3", "4");
        Observable O2 = Observable.just("a", "b", "c", "d", "e");
        Observable O3 = Observable.just("!", "#", "@", "$", "%" , "&");
        Observable.combineLatest(O1, O2, O3, new Func3() {
            @Override
            public Object call(Object o, Object o2, Object o3) {
                return o.toString() + o2.toString() + o3.toString();
            }
        }).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String i) {
                        Log.d(TAG, i.toString());
                    }
                });
    }

    private void join() {
        Observable O1 = Observable.just("1", "2", "3", "4");
        Observable O2 = Observable.just("a", "b", "c", "d", "e");
        O1.join(O2, new Func1() {
            @Override
            public Object call(Object o) {
                return o;
            }
        }, new Func1() {
            @Override
            public Object call(Object o) {
                return o;
            }
        }, new Func2() {
            @Override
            public Object call(Object o, Object o2) {
                return o.toString() + o2.toString();
            }
        })
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
                e.printStackTrace();
            }

            @Override
            public void onNext(String i) {
                Log.d(TAG, i.toString());
            }
        });
    }

    private void merge() {
        Observable O1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    for (int i = 1; i < 3; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(1000);
                    }
                    subscriber.onCompleted();
                }catch(Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread());

        Observable O2 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    for (int i = 11; i < 13; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(500);
                    }
                    subscriber.onCompleted();
                }catch(Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.merge(O1, O2)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(Integer i) {
                        Log.d(TAG, i.toString());
                    }
                });
    }

    private void startWith() {
        Observable.just(1, 2, 3, 4)
                .startWith(-1, 0)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(Integer i) {
                        Log.d(TAG, i.toString());
                    }
                });
    }

    private void zip() {
        Observable O1 = Observable.just("1", "2", "3", "4");
        Observable O2 = Observable.just("a", "b", "c", "d", "e");
        Observable.zip(O1, O2, new Func2() {
            @Override
            public Object call(Object o, Object o2) {
                return o.toString() + o2.toString();
            }
        })
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }
            @Override
            public void onNext(String i) {
                Log.d(TAG, i.toString());
            }
        });
    }
}
