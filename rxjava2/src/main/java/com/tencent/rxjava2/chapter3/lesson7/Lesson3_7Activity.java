package com.tencent.rxjava2.chapter3.lesson7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.rxjava2.R;;


public class Lesson3_7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson3_7);
        findViewById(R.id.async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Telephoner.create(new TelephonerOnCall<String>() {
//                    @Override
//                    public void call(TelephonerEmitter<String> telephonerEmitter) {
//                        telephonerEmitter.onReceive("test");
//                        telephonerEmitter.onCompleted();
//                    }
//                }).call(new Receiver<String>() {
//                    @Override
//                    public void onCall(Drop d) {
//                        d.request(Long.MAX_VALUE);
//                        System.out.println("onCall");
//                    }
//
//                    @Override
//                    public void onReceive(String s) {
//                        System.out.println("onReceive:" + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("onCompleted");
//                    }
//                });
            }
        });
    }
}
