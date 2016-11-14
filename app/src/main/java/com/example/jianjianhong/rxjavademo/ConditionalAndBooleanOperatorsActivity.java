package com.example.jianjianhong.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by jianjianhong on 2016/5/25.
 */
public class ConditionalAndBooleanOperatorsActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "CBOperatorsActivity";

    private Button allBt;
    private Button ambBt;
    private Button containsBt;
    private Button existsBt;
    private Button isEmptyBt;
    private Button defaultIfEmptyBt;
    private Button sequenceEqualBt;
    private Button skipUntilBt;
    private Button skipWhileBt;
    private Button takeUntilBt;
    private Button takeWhileBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditioanl_boolean_operators);

        initView();
        initEvent();
    }

    private void initView() {
        allBt = (Button)findViewById(R.id.cbopAct_all);
        ambBt = (Button)findViewById(R.id.cbopAct_amb);
        containsBt = (Button)findViewById(R.id.cbopAct_contains);
        existsBt = (Button)findViewById(R.id.cbopAct_exists);
        isEmptyBt = (Button)findViewById(R.id.cbopAct_isEmpty);
        defaultIfEmptyBt = (Button)findViewById(R.id.cbopAct_defaultIfEmpty);
        sequenceEqualBt = (Button)findViewById(R.id.cbopAct_sequenceEqual);
        skipUntilBt = (Button)findViewById(R.id.cbopAct_skipUntil);
        skipWhileBt = (Button)findViewById(R.id.cbopAct_skipWhile);
        takeUntilBt = (Button)findViewById(R.id.cbopAct_takeUntil);
        takeWhileBt = (Button)findViewById(R.id.cbopAct_takeWhile);
    }

    private void initEvent() {
        allBt.setOnClickListener(this);
        ambBt.setOnClickListener(this);
        containsBt.setOnClickListener(this);
        existsBt.setOnClickListener(this);
        isEmptyBt.setOnClickListener(this);
        defaultIfEmptyBt.setOnClickListener(this);
        sequenceEqualBt.setOnClickListener(this);
        skipUntilBt.setOnClickListener(this);
        skipWhileBt.setOnClickListener(this);
        takeUntilBt.setOnClickListener(this);
        takeWhileBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbopAct_all:
                all();
                break;
            case R.id.cbopAct_amb:
                amb();
                break;
            case R.id.cbopAct_contains:
                contains();
                break;
            case R.id.cbopAct_exists:
                exists();
                break;
            case R.id.cbopAct_isEmpty:
                isEmpty();
                break;
            case R.id.cbopAct_defaultIfEmpty:
                defaultIfEmpty();
                break;
            case R.id.cbopAct_sequenceEqual:
                sequenceEqual();
                break;
            case R.id.cbopAct_skipUntil:
                skipUntil();
                break;
            case R.id.cbopAct_skipWhile:
                skipWhile();
                break;
            case R.id.cbopAct_takeUntil:
                takeUntil();
                break;
            case R.id.cbopAct_takeWhile:
                takeWhile();
                break;
        }
    }

    private void all() {
        Log.d(TAG, "===========all==============");
        getObservable().all(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 2;
            }
        }).subscribe(getBooleanSubscriber());
    }

    private void amb() {
        Log.d(TAG, "===========amb==============");
        Observable o1 = Observable.just(1, 2, 3);
        Observable o2 = Observable.just(4, 5, 6).delay(2, TimeUnit.SECONDS);
        Observable.amb(o1, o2).subscribe(getIntegerSubscriber());
    }

    private void contains() {
        Log.d(TAG, "===========contains==============");
        getObservable().contains(3).subscribe(getBooleanSubscriber());
    }

    private void exists() {
        Log.d(TAG, "===========exists==============");
        getObservable().exists(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer.equals(100);
            }
        }).subscribe(getBooleanSubscriber());
    }

    private void isEmpty() {
        Log.d(TAG, "===========isEmpty==============");
        Observable.empty().isEmpty().subscribe(getBooleanSubscriber());
    }

    private void defaultIfEmpty() {
        Log.d(TAG, "===========defaultIfEmpty==============");
        Observable.empty().defaultIfEmpty(1000).subscribe(getObjectSubscriber());
    }

    private void sequenceEqual() {
        Log.d(TAG, "===========sequenceEqual==============");
        Observable.sequenceEqual(Observable.just(1, 2, 3), Observable.just(1, 2, 3)).subscribe(getBooleanSubscriber());
    }

    private void skipUntil() {
        Log.d(TAG, "===========skipUntil==============");
        Observable o1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if(subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onNext(1);
                    subscriber.onNext(2);
                    subscriber.onNext(3);
                    Thread.sleep(1000);
                    subscriber.onNext(4);
                    subscriber.onNext(5);
                    subscriber.onNext(6);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }
            }
        });
        Observable o2 = Observable.just(0).delay(1, TimeUnit.SECONDS);
        o1.skipUntil(o2).subscribe(getIntegerSubscriber());
    }

    private void skipWhile() {
        Log.d(TAG, "===========skipWhile==============");
        Observable.just(1, 2, 3, 10, 4, 5, 6, 7)
                .skipWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer != 4;
                    }
                })
                .subscribe(getIntegerSubscriber());
    }

    private void takeUntil() {
        Log.d(TAG, "===========skipWhile==============");
        Observable o1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if(subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onNext(1);
                    subscriber.onNext(2);
                    subscriber.onNext(3);
                    Thread.sleep(1000);
                    subscriber.onNext(4);
                    subscriber.onNext(5);
                    subscriber.onNext(6);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }
            }
        });
        Observable o2 = Observable.just(0).delay(1, TimeUnit.SECONDS);
        o1.takeUntil(o2).subscribe(getIntegerSubscriber());
    }

    private void takeWhile() {
        Log.d(TAG, "===========takeWhile==============");
        Observable.just(1, 2, 3, 10, 4, 5, 6, 7)
                .takeWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer != 4;
                    }
                })
                .subscribe(getIntegerSubscriber());
    }

    public Observable<Integer> getObservable() {
        return Observable.just(1, 2, 3, 4, 5);
    }

    public Subscriber<Integer> getIntegerSubscriber() {
        return new Subscriber<Integer>() {
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
            public void onNext(Integer integer) {
                Log.d(TAG, integer.toString());
            }
        };
    }

    public Subscriber<Boolean> getBooleanSubscriber() {
        return new Subscriber<Boolean>() {
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
            public void onNext(Boolean b) {
                Log.d(TAG, b.toString());
            }
        };
    }

    public Subscriber<Object> getObjectSubscriber() {
        return new Subscriber<Object>() {
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
            public void onNext(Object b) {
                Log.d(TAG, b.toString());
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
