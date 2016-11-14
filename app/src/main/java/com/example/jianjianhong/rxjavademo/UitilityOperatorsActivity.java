package com.example.jianjianhong.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * Created by jianjianhong on 2016/5/30.
 */
public class UitilityOperatorsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "UitilityActivity";

    private Button delayBt;
    private Button doBt;
    private Button materializeBt;
    private Button timeOutBt;
    private Button toBt;

    private Subscription subscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_utility_operators);
        initView();
        initEvent();
    }

    private void initView() {
        delayBt = (Button)findViewById(R.id.upAct_delay);
        doBt = (Button)findViewById(R.id.upAct_do);
        materializeBt = (Button)findViewById(R.id.upAct_materialize);
        timeOutBt = (Button)findViewById(R.id.upAct_timeOut);
        toBt = (Button)findViewById(R.id.upAct_to);
    }

    private void initEvent() {
        delayBt.setOnClickListener(this);
        doBt.setOnClickListener(this);
        materializeBt.setOnClickListener(this);
        timeOutBt.setOnClickListener(this);
        toBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upAct_delay:
                delay();
                break;
            case R.id.upAct_do:
                doAction();
                break;
            case R.id.upAct_materialize:
                materialize();
                break;
            case R.id.upAct_timeOut:
                timeOut();
                break;
            case R.id.upAct_to:
                getIterator();
                toFuture();
                toIterable();
                toList();
                toMap();
                toMultiMap();
                toSortedList();
                break;
        }
    }

    private void delay() {
        initObservable().delay(2, TimeUnit.SECONDS).subscribe(initSubscriber());
    }

    private void delayByFunc() {
        initObservable().delay(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.just(integer);
            }
        }).subscribe(initSubscriber());
    }

    private void doAction() {
        initObservable().doOnEach(new Action1<Notification<? super Integer>>() {
            @Override
            public void call(Notification<? super Integer> notification) {
                Log.d(TAG, "doOnEach---->" + notification.getValue());
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "doOnNext---->" + integer);
            }
        }).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "doOnSubscribe");
            }
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "doOnUnsubscribe");
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "doOnError");
            }
        }).doOnTerminate(new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "doOnTerminate");
            }
        }).subscribe(initSubscriber());
    }

    private void materialize() {
        initObservable().materialize().dematerialize().subscribe(initSubscriber());
    }

    private void timeOut() {
        subscription = getTimeOutObservable().timeout(5, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).subscribe(initSubscriber());
    }

    private void timeOutByObservable() {
        subscription = getTimeOutObservable().timeout(5, TimeUnit.SECONDS, Observable.just(1000)).subscribeOn(Schedulers.newThread()).subscribe(initSubscriber());
    }

    private void timeOutByFunc() {
        subscription = getTimeOutObservable().timeout(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.just(1).delay(5, TimeUnit.SECONDS);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(initSubscriber());
    }

    private void timeOutByFuncAndBak() {
        subscription = getTimeOutObservable().timeout(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.just(1).delay(5, TimeUnit.SECONDS);
            }
        }, Observable.just(1000)).subscribeOn(Schedulers.newThread()).subscribe(initSubscriber());
    }

    private void getIterator() {
        Log.d(TAG, "===========getIterator==============");
        Iterator<Integer> iterator = getObservable().toBlocking().getIterator();
        while(iterator.hasNext()) {
            Log.d(TAG, iterator.next().toString());
        }
    }

    private void toFuture() {
        try {
            Log.d(TAG, "===========toFuture==============");
            List<Integer> list = getObservable().toList().toBlocking().toFuture().get();
            for(Integer i : list) {
                Log.d(TAG, i.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void toIterable() {
        Log.d(TAG, "===========toIterable==============");
        Iterator<Integer> iterator = getObservable().toBlocking().toIterable().iterator();
        while(iterator.hasNext()) {
            Log.d(TAG, iterator.next().toString());
        }
    }

    private void toList() {
        Log.d(TAG, "===========toList==============");
        getObservable().toList()
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        for(Integer i : integers) {
                            Log.d(TAG, i.toString());
                        }
                    }
                });
    }

    private void toMap() {
        Log.d(TAG, "===========toMap==============");
        getObservable().toMap(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer.toString();
            }
        }, new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer.toString();
            }
        }).subscribe(new Subscriber<Map<String, String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Map<String, String> stringIntegerMap) {
                Iterator<String> it = stringIntegerMap.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Log.d(TAG, "Key: "+key+ ", value:"+stringIntegerMap.get(key));
                }
            }
        });
    }

    private void toMultiMap() {
        Log.d(TAG, "===========toMultiMap==============");
        getObservable().toMultimap(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer.toString();
            }
        }).subscribe(new Subscriber<Map<String, Collection<Integer>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Map<String, Collection<Integer>> stringCollectionMap) {
                Iterator<String> it = stringCollectionMap.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Log.d(TAG, "Key: "+key+ ", value:"+stringCollectionMap.get(key));
                }
            }
        });
    }

    private void toSortedList() {
        Log.d(TAG, "===========toSortedList==============");
        getObservable().toSortedList().subscribe(new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Integer> integers) {
                for(Integer i : integers) {
                    Log.d(TAG, i.toString());
                }
            }
        });
    }

    public Observable<Integer> getObservable() {
        return Observable.just(1, 5,3,4, 2);
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

    public Observable<Integer> getTimeOutObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                try {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(i * 1000);
                    }
                    subscriber.onCompleted();
                }catch(Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable initObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onCompleted();
            }
        });
    }

    public Subscriber initSubscriber() {
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

    @Override
    protected void onStop() {
        super.onStop();
        subscription.unsubscribe();
    }
}
