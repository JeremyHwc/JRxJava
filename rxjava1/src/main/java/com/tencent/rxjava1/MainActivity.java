package com.tencent.rxjava1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.rxjava1.chapter3.lesson1.Lesson3_1Activity;
import com.tencent.rxjava1.chapter3.lesson10.Lesson3_10Activity;
import com.tencent.rxjava1.chapter3.lesson5.Lesson3_5Activity;
import com.tencent.rxjava1.chapter3.lesson6.Lesson3_6Activity;
import com.tencent.rxjava1.chapter3.lesson7.Lesson3_7Activity;
import com.tencent.rxjava1.chapter4.lesson1.Lesson4_1Activity;
import com.tencent.rxjava1.chapter4.lesson4.Lesson4_4Activity;
import com.tencent.rxjava1.chapter4.lesson5.Lesson4_5Activity;
import com.tencent.rxjava1.chapter4.lesson6.Lesson4_6Activity;
import com.tencent.rxjava1.chapter4.lesson8.Lesson4_8Activity;
import com.tencent.rxjava1.chapter5.lesson1.Lesson5_1Activity;
import com.tencent.rxjava1.chapter5.lesson10.Lesson5_10Activity;
import com.tencent.rxjava1.chapter5.lesson11.Lesson5_11Activity;
import com.tencent.rxjava1.chapter5.lesson15.Lesson5_15Activity;
import com.tencent.rxjava1.chapter5.lesson16.Lesson5_16Activity;
import com.tencent.rxjava1.chapter5.lesson17.Lesson5_17Activity;
import com.tencent.rxjava1.chapter5.lesson9.Lesson5_9Activity;
import com.tencent.rxjava1.chapter6.lesson1.Lesson6_1Activity;
import com.tencent.rxjava1.chapter6.lesson4.Lesson6_4Activity;
import com.tencent.rxjava1.chapter6.lesson5.Lesson6_5Activity;
import com.tencent.rxjava1.chapter7.lesson2.Lesson7_2Activity;
import com.tencent.rxjava1.chapter7.lesson3.Lesson7_3Activity;
import com.tencent.rxjava1.chapter7.lesson4.Lesson7_4Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnLesson3_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson3_1Activity.class);
            }
        });

        findViewById(R.id.btnLesson3_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson3_5Activity.class);
            }
        });

        findViewById(R.id.btnLesson3_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson3_6Activity.class);
            }
        });

        findViewById(R.id.btnLesson3_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson3_10Activity.class);
            }
        });

        findViewById(R.id.btnLesson3_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson3_7Activity.class);
            }
        });

        findViewById(R.id.btnLesson4_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson4_1Activity.class);
            }
        });

        findViewById(R.id.btnLesson4_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson4_4Activity.class);
            }
        });

        findViewById(R.id.btnLesson4_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson4_5Activity.class);
            }
        });

        findViewById(R.id.btnLesson4_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson4_8Activity.class);
            }
        });

        findViewById(R.id.btnLesson4_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson4_6Activity.class);
            }
        });

        findViewById(R.id.btnLesson5_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson5_1Activity.class);
            }
        });

        findViewById(R.id.btnLesson5_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson5_9Activity.class);
            }
        });

        findViewById(R.id.btnLesson5_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson5_10Activity.class);
            }
        });

        findViewById(R.id.btnLesson5_11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson5_11Activity.class);
            }
        });

        findViewById(R.id.btnLesson5_15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson5_15Activity.class);
            }
        });

        findViewById(R.id.btnLesson5_16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson5_16Activity.class);
            }
        });

        findViewById(R.id.btnLesson5_17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson5_17Activity.class);
            }
        });

        findViewById(R.id.btnLesson6_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson6_1Activity.class);
            }
        });

        findViewById(R.id.btnLesson6_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson6_4Activity.class);
            }
        });

        findViewById(R.id.btnLesson6_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson6_5Activity.class);
            }
        });

        findViewById(R.id.btnLesson7_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson7_2Activity.class);
            }
        });

        findViewById(R.id.btnLesson7_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson7_3Activity.class);
            }
        });

        findViewById(R.id.btnLesson7_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Lesson7_4Activity.class);
            }
        });
    }

    private void jump(Class<?> activity) {
        Intent tIntent = new Intent();
        tIntent.setClass(this, activity);
        startActivity(tIntent);
    }
}
