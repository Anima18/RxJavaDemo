package com.example.jianjianhong.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.MathObservable;

/**
 * Created by jianjianhong on 2016/5/25.
 */
public class AverageOperatorsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private final static String TAG = "AverageActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        button = (Button)findViewById(R.id.single_bt);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.single_bt:
                averageInteger();
                averageFloat();
                averageLong();
                averageDouble();
                sumInteger();
                sumFloat();
                sumLong();
                sumDouble();
                min();
                max();
                count();
                break;
        }
    }

    private void averageInteger() {
        Log.d(TAG, "===========averageInteger==============");
        MathObservable.averageInteger(getObservable()).subscribe(getIntegerSubscriber());
    }

    private void averageFloat() {
        Log.d(TAG, "===========averageFloat==============");
        MathObservable.averageFloat(Observable.just(1f, 1.2f)).subscribe(getFloatSubscriber());
    }

    private void averageLong() {
        Log.d(TAG, "===========averageLong==============");
        MathObservable.averageLong(Observable.just(1L, 2L)).subscribe(getLongSubscriber());
    }

    private void averageDouble() {
        Log.d(TAG, "===========averageDouble==============");
        MathObservable.averageDouble(Observable.just(1d, 1.2d)).subscribe(getDoubleSubscriber());
    }

    private void min() {
        Log.d(TAG, "===========min==============");
        MathObservable.min(getObservable()).subscribe(getIntegerSubscriber());
    }

    private void max() {
        Log.d(TAG, "===========max==============");
        MathObservable.max(getObservable()).subscribe(getIntegerSubscriber());
    }

    private void sumInteger() {
        Log.d(TAG, "===========sumInteger==============");
        MathObservable.sumInteger(getObservable()).subscribe(getIntegerSubscriber());
    }

    private void sumFloat() {
        Log.d(TAG, "===========sumFloat==============");
        MathObservable.sumFloat(Observable.just(1f, 1.2f)).subscribe(getFloatSubscriber());
    }

    private void sumLong() {
        Log.d(TAG, "===========sumLong==============");
        MathObservable.sumLong(Observable.just(1L, 2L)).subscribe(getLongSubscriber());
    }

    private void sumDouble() {
        Log.d(TAG, "===========sumDouble==============");
        MathObservable.sumDouble(Observable.just(1d, 1.2d)).subscribe(getDoubleSubscriber());
    }

    private void count() {
        Log.d(TAG, "===========count==============");
        getObservable().count().subscribe(getIntegerSubscriber());
    }

    public Observable getObservable() {
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

    public Subscriber<Float> getFloatSubscriber() {
        return new Subscriber<Float>() {
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
            public void onNext(Float integer) {
                Log.d(TAG, integer.toString());
            }
        };
    }

    public Subscriber<Long> getLongSubscriber() {
        return new Subscriber<Long>() {
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
            public void onNext(Long integer) {
                Log.d(TAG, integer.toString());
            }
        };
    }

    public Subscriber<Double> getDoubleSubscriber() {
        return new Subscriber<Double>() {
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
            public void onNext(Double integer) {
                Log.d(TAG, integer.toString());
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
