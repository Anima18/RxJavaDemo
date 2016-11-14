package com.example.jianjianhong.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jianjianhong on 2016/5/30.
 */
public class FilterOperatorsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "FilterOperatorsActivity";

    private Button debounceBt;
    private Button distinctBt;
    private Button elementAtBt;
    private Button filterBt;
    private Button firstBt;
    private Button ignoreElementsBt;
    private Button lastBt;
    private Button sampleBt;
    private Button skipBt;
    private Button takeLastBt;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter_operators);
        initView();
        initEvent();
    }

    private void initView() {
        debounceBt = (Button)findViewById(R.id.fpAct_debounce);
        distinctBt = (Button)findViewById(R.id.fpAct_distinct);
        elementAtBt = (Button)findViewById(R.id.fpAct_elementAt);
        filterBt = (Button)findViewById(R.id.fpAct_filter);
        firstBt = (Button)findViewById(R.id.fpAct_first);
        ignoreElementsBt = (Button)findViewById(R.id.fpAct_ignoreElements);
        lastBt = (Button)findViewById(R.id.fpAct_last);
        sampleBt = (Button)findViewById(R.id.fpAct_sample);
        skipBt = (Button)findViewById(R.id.fpAct_skip);
        takeLastBt = (Button)findViewById(R.id.fpAct_takeLast);
    }

    private void initEvent() {
        debounceBt.setOnClickListener(this);
        distinctBt.setOnClickListener(this);
        elementAtBt.setOnClickListener(this);
        filterBt.setOnClickListener(this);
        firstBt.setOnClickListener(this);
        ignoreElementsBt.setOnClickListener(this);
        lastBt.setOnClickListener(this);
        sampleBt.setOnClickListener(this);
        skipBt.setOnClickListener(this);
        takeLastBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fpAct_debounce:
                debounce();
                break;
            case R.id.fpAct_distinct:
                distinct();
                break;
            case R.id.fpAct_elementAt:
                elementAt();
                break;
            case R.id.fpAct_filter:
                filter();
                break;
            case R.id.fpAct_first:
                first();
                break;
            case R.id.fpAct_ignoreElements:
                ignoreElements();
                break;
            case R.id.fpAct_last:
                last();
                break;
            case R.id.fpAct_sample:
                sample();
                break;
            case R.id.fpAct_skip:
                skip();
                break;
            case R.id.fpAct_takeLast:
                takeLast();
                break;
        }
    }

    private void debounce() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                try {
                    //产生结果的间隔时间分别为100、200、300...900毫秒
                    for (int i = 1; i < 10; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(i * 100);
                    }
                    subscriber.onCompleted();
                }catch(Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .debounce(400, TimeUnit.MILLISECONDS)  //超时时间为400毫秒
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(Integer s) {
                        Log.d(TAG, s.toString()/**/);
                    }
                });
    }

    private void distinct() {
        /*Func1<ActivityClass, ActivityClass> TO_UPPER_WITH_EXCEPTION = new Func1<ActivityClass, ActivityClass>() {
            @Override
            public ActivityClass call(ActivityClass s) {
                s.setActivityName(s.getActivityName().toLowerCase());
                return s;
            }
        };*/
        Observable.just(new ActivityClass("test1", null), new ActivityClass("test2", null), new ActivityClass("test1", null))
                .distinct()
                //.distinctUntilChanged()
                .subscribe(new Action1<ActivityClass>() {
                    @Override
                    public void call(ActivityClass activityClass) {
                        Log.d(TAG, activityClass.toString());
                    }
                });
    }

    private void elementAt() {
        Observable.just("1", "2", "3", "4")
                //.elementAt(2)
                .elementAtOrDefault(10, "10")
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

    private void filter() {
        Object[] array = {1, "hello world"};
        Observable.from(array)
                .ofType(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String integer) {
                        Log.d(TAG, integer.toString());
                    }
                });

        Observable.just(1, 2, 3, 4)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, integer.toString());
                    }
                });
    }

    private void first() {
        Observable.just(1, 2, 3, 4)
                //.first()
                /*.first(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer i) {
                        return i > 2;
                    }
                })*/
                .firstOrDefault(10, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer i) {
                        return i > 10;
                    }
                })
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

    private void ignoreElements() {
        Observable.just(1, 2, 3, 4)
                .ignoreElements()
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

    private void last() {
        Observable
                //.empty()
                .just(1, 2, 3, 4)
                //.last()
                /*.last(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 3;
                    }
                })*/
                //.lastOrDefault(10)
                .lastOrDefault(10, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, integer.toString());
            }
        });
    }

    private void sample() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    for (int i = 1; i < 10; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(1000);
                    }
                    subscriber.onCompleted();
                }catch(Exception e){
                    subscriber.onError(e);
                }
            }
        })
                //.sample(2, TimeUnit.SECONDS)
                //.throttleFirst(2, TimeUnit.SECONDS)
                .throttleLast(2, TimeUnit.SECONDS)
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
                    public void onNext(Integer integer) {
                        Log.d(TAG, integer.toString());
                    }
                });
    }

    private void skip() {
        Observable.just(1, 2, 3, 4)
                .skip(2)
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

    private void takeLast() {
        Observable.just(1, 2, 3, 4)
                .takeLastBuffer(2)
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {

                    }
                });

    }

}
