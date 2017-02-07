package com.rdc.bustogo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.bustogo.R;
import com.rdc.bustogo.ui.customview.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Goo on 2017-1-31.
 */
public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_bus)
    TextView tvBus;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public static Activity welcomeActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeActivity = this;
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        showAnim();
    }


    private void showAnim() {
        AlphaAnimation mAlphaAnim = new AlphaAnimation(0, 1);
        mAlphaAnim.setDuration(2000);
        ivLogo.startAnimation(mAlphaAnim);
        tvTitle.startAnimation(mAlphaAnim);
        tvBus.startAnimation(mAlphaAnim);
        tvSub.startAnimation(mAlphaAnim);
    }


    @OnClick({R.id.tv_bus, R.id.tv_sub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bus:
                startActivity(new Intent(WelcomeActivity.this,
                        LoginActivity.class));
                overridePendingTransition(R.anim.translate_right_in,
                        R.anim.translate_not_move);
                break;
            case R.id.tv_sub:
                CustomToast.showToast(this, "地铁系统尚未开通");
                break;
        }
    }
}
