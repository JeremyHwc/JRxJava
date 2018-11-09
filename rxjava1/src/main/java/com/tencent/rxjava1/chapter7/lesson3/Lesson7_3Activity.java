package com.tencent.rxjava1.chapter7.lesson3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tencent.rxjava1.R;


public class Lesson7_3Activity extends AppCompatActivity implements Lesson7_3Contract.View {

    private TextView tvText;

    private Lesson7_3Contract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson7_3);
        mPresenter = new Lesson7_3Presenter(this);
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
