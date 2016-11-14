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
public class ErrorHandlingOperatorsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "errorHandlingActivity";

    private Button onErrorReturnBt;
    private Button onErrorResumeNextBt;
    private Button onExceptionResumeNextBt;
    private Button retryBt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_error_handling_operators);
        initView();
        initEvent();
    }

    private void initView() {
        onErrorReturnBt = (Button)findViewById(R.id.erpAct_errorReturn);
        onErrorResumeNextBt = (Button)findViewById(R.id.erpAct_errorResumeNext);
        onExceptionResumeNextBt = (Button)findViewById(R.id.erpAct_exceptionResumeNext);
        retryBt = (Button)findViewById(R.id.erpAct_retry);
    }

    private void initEvent() {
        onErrorReturnBt.setOnClickListener(this);
        onErrorResumeNextBt.setOnClickListener(this);
        onExceptionResumeNextBt.setOnClickListener(this);
        retryBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.erpAct_errorReturn:
                onErrorReturn();
                break;
            case R.id.erpAct_errorResumeNext:
                onErrorResumeNext();
                break;
            case R.id.erpAct_exceptionResumeNext:
                onExceptionResumeNext();
                break;
            case R.id.erpAct_retry:
                retryByCount(2);
                break;
        }
    }


    /**
     * 让Observable遇到错误时发射一个特殊的项并且正常终止。
     */
    private void onErrorReturn() {
        initErrorObservable().onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                return 0;
            }
        }).subscribe(initSubscriber());
    }

    /**
     * 让Observable在遇到错误时开始发射第二个Observable的数据序列。
     */
    private void onErrorResumeNext() {
        initErrorObservable().onErrorResumeNext(Observable.just(4, 5)).subscribe(initSubscriber());
    }

    /**
     * 让Observable在遇到错误时继续发射后面的数据项。
     */
    private void onExceptionResumeNext() {
        initExceptionObservable().onExceptionResumeNext(Observable.just(4, 5)).subscribe(initSubscriber());
    }

    private void retry() {
        initErrorObservable().retry().subscribe(initSubscriber());
    }

    private void retryByCount(long count) {
        initErrorObservable().retry(count).subscribe(initSubscriber());
    }

    private void retryByFunc() {
        initErrorObservable().retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer integer, Throwable throwable) {
                return true;
            }
        }).subscribe(initSubscriber());
    }

    public Observable initErrorObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(1/0);
                subscriber.onNext(5);
            }
        });
    }

    public Observable<Integer> initExceptionObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                try {
                    throw new Exception();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(5);
            }
        });
    }

    public Subscriber<Integer> initSubscriber() {
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
