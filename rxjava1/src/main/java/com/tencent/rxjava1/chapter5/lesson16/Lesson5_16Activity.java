package com.tencent.rxjava1.chapter5.lesson16;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.rxjava1.R;


public class Lesson5_16Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson5_16);
        findViewById(R.id.async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Caller.
//                        create(new CallerOnCall<String>() {
//                            @Override
//                            public void call(CallerEmitter<String> callerEmitter) {
//                                callerEmitter.onReceive("test");
//                                System.out.println("currentThread:" + Thread.currentThread());
//                                callerEmitter.onCompleted();
//                            }
//                        }).
//                        callOn(new NewThreadSwitcher()).
//                        callbackOn(new LooperSwitcher(getMainLooper())).
//                        call(new Callee<String>() {
//                            @Override
//                            public void onCall(Release release) {
//
//                            }
//
//                            @Override
//                            public void onReceive(String s) {
//                                System.out.println("onReceive:" + s);
//                                System.out.println("currentThread:" + Thread.currentThread());
//                            }
//
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable t) {
//
//                            }
//                        });
            }
        });
    }
}
