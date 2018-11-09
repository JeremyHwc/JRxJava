package com.tencent.rxjava2.chapter7.lesson2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tencent.rxjava2.R;


public class Lesson7_2Activity extends AppCompatActivity implements Lesson7_2Contract.View {

    private TextView tvText;

    private Lesson7_2Contract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson7_2);
        mPresenter = new Lesson7_2Presenter(this);
        tvText = (TextView) findViewById(R.id.tvText);
        findViewById(R.id.async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.loadData();
            }
        });
    }

    @Override
    public void update(String text) {
        tvText.setText(text);
    }
}
