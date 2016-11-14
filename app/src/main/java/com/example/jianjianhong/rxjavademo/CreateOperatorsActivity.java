package com.example.jianjianhong.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by jianjianhong on 2016/5/30.
 */
public class CreateOperatorsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "CreateOperatorsActivity";

    private Button createButton;
    private Button emptyButton;
    private Button errorButton;
    private Button neverButton;
    private Button fromButton;
    private Button justButton;
    private Button deferButton;
    private Button intervalButton;
    private Button rangeButton;
    private Button repeatButton;
    private Button timerButton;

    private Subscription intervalSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_operators);
        initView();
        initEvent();
    }

    private void initView() {
        createButton = (Button)findViewById(R.id.cpAct_create);
        emptyButton = (Button)findViewById(R.id.cpAct_empty);
        errorButton = (Button)findViewById(R.id.cpAct_error);
        neverButton = (Button)findViewById(R.id.cpAct_never);
        fromButton = (Button)findViewById(R.id.cpAct_from);
        justButton = (Button)findViewById(R.id.cpAct_just);
        deferButton = (Button)findViewById(R.id.cpAct_defer);
        intervalButton = (Button)findViewById(R.id.cpAct_interval);
        rangeButton = (Button)findViewById(R.id.cpAct_range);
        repeatButton = (Button)findViewById(R.id.cpAct_repeat);
        timerButton = (Button)findViewById(R.id.cpAct_timer);
    }

    private void initEvent() {
        createButton.setOnClickListener(this);
        emptyButton.setOnClickListener(this);
        errorButton.setOnClickListener(this);
        neverButton.setOnClickListener(this);
        fromButton.setOnClickListener(this);
        justButton.setOnClickListener(this);
        deferButton.setOnClickListener(this);
        intervalButton.setOnClickListener(this);
        rangeButton.setOnClickListener(this);
        repeatButton.setOnClickListener(this);
        timerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cpAct_create:
                create();
                break;
            case R.id.cpAct_empty:
                empty();
                break;
            case R.id.cpAct_error:
                error();
                break;
            case R.id.cpAct_never:
                never();
                break;
            case R.id.cpAct_from:
                from();
                break;
            case R.id.cpAct_just:
                just();
                break;
            case R.id.cpAct_defer:
                defer();
                break;
            case R.id.cpAct_interval:
                interval();
                break;
            case R.id.cpAct_range:
                range();
                break;
            case R.id.cpAct_repeat:
                repeat();
                break;
            case R.id.cpAct_timer:
                timer();
                break;
        }
    }

    int index = 0;
    private void create() {
        Log.d(TAG, "===========create===========");
       Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(subscriber.isUnsubscribed())
                    return;
                for (int i = 1; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Object>() {
           @Override
           public void onCompleted() {
               Log.d(TAG, "onCompleted");
           }

           @Override
           public void onError(Throwable e) {
               Log.d(TAG, "onError");
           }

           @Override
           public void onNext(Object o) {
               Log.d(TAG, "Next: " + o.toString());
           }
       });
    }

    private void empty() {
        Log.d(TAG, "===========empty===========");
        Observable.empty().subscribe(getSubscriber());
    }

    private void error() {
        Log.d(TAG, "===========error===========");
        Observable.error(new IOException()).subscribe(getSubscriber());
    }

    private void never() {
        Log.d(TAG, "===========never===========");
        Observable.never().subscribe(getSubscriber());
    }

    private void from() {
        Log.d(TAG, "===========from===========");
        Integer[] array = {1, 2, 3, 4, 5};
        Observable.from(array).subscribe(getSubscriber());
    }

    private void just() {
        Log.d(TAG, "===========just===========");
        Observable.just(1, 2, 3).subscribe(getSubscriber());
    }

    int i = 0;
    private void defer() {
        Log.d(TAG, "===========defer===========");
        Observable<Integer> observable = Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(i);
            }
        });
        i = 1;
        observable.subscribe(getSubscriber());
        i = 2;
        observable.subscribe(getSubscriber());
    }

    private void interval() {
        Log.d(TAG, "===========interval===========");
        if(intervalSub == null) {
            intervalSub = Observable.interval(2, TimeUnit.SECONDS)
                    .subscribe(new Subscriber<Long>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError");
                        }

                        @Override
                        public void onNext(Long aLong) {
                            Log.d(TAG, aLong.toString());
                        }
                    });
        }
    }

    private void range() {
        Log.d(TAG, "===========range===========");
        Observable.range(1, 5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, integer.toString());
            }
        });
    }

    private void repeat() {
        Log.d(TAG, "===========repeat===========");
        Observable.just(1,2,3).repeat(2).subscribe(getSubscriber());
    }

    private void timer() {
        Log.d(TAG, "===========timer===========");
        Observable.just(1, 2).timer(2, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(Long i) {
                        Log.d(TAG, i.toString());
                    }
                });
    }

    private Subscriber<Object> getSubscriber() {
        return new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "Next: " + o.toString());
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(intervalSub != null) {
            intervalSub.unsubscribe();
        }
    }
}
