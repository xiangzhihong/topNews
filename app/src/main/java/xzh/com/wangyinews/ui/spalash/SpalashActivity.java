package xzh.com.wangyinews.ui.spalash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import xzh.com.wangyinews.R;
import xzh.com.wangyinews.base.BaseActivity;
import xzh.com.wangyinews.ui.main.MainActivity;

public class SpalashActivity extends BaseActivity {
    private AlphaAnimation start_anima;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_spalash, null);
        setContentView(view);
        initView();
        initData();
    }
    private void initData() {
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(2000);
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                redirectTo();
            }
        });
    }

    private void initView() {

    }

    private void redirectTo() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
