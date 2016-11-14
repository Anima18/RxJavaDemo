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
import rx.observables.ConnectableObservable;

/**
 * Created by jianjianhong on 2016/5/25.
 */
public class ConnectableOperatorsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private final static String TAG = "ConnectableActivity";


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
                //normalObservable();
                connectObservable();
                break;
        }
    }

    private void normalObservable() {
        Observable firstMillion  = Observable.range( 1, 1000000 ).sample(1, TimeUnit.MILLISECONDS);

        firstMillion.subscribe(
            new Subscriber<Integer>() {
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
                    Log.d(TAG, "Subscriber #1:" + integer.toString());
                }
            });

        firstMillion.subscribe(new Subscriber<Integer>() {
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
                Log.d(TAG, "Subscriber #2:" + integer.toString());
            }
        });
    }

    public void connectObservable() {
        ConnectableObservable firstMillion  = Observable.range( 1, 1000000 ).sample(1, TimeUnit.MILLISECONDS).publish();

        firstMillion.subscribe(
                new Subscriber<Integer>() {
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
                        Log.d(TAG, "Subscriber #1:" + integer.toString());
                    }
                });

        firstMillion.subscribe(new Subscriber<Integer>() {
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
                Log.d(TAG, "Subscriber #2:" + integer.toString());
            }
        });

        firstMillion.connect();
    }
}
