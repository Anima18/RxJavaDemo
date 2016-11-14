package com.example.jianjianhong.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jianjianhong.rxjavademo.R;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func0;
import rx.util.async.Async;

/**
 * Created by jianjianhong on 2016/5/25.
 */
public class AsyncOperatorsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private final static String TAG = "AsyncActivity";


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
                start();
                break;
        }
    }

    private void start() {
        Async.start(new Func0<Integer>() {
            @Override
            public Integer call() {
                try {
                    Thread.sleep(1000);
                    return 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }).subscribe(getSubscriber());
    }

    private void toAsync() {
        Async.toAsync(new Action0() {
            @Override
            public void call() {

            }
        });
    }

    public Subscriber<Integer> getSubscriber() {
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
}
