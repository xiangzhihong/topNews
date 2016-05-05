package xzh.com.wangyinews.ui.main;


import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.wangyinews.R;
import xzh.com.wangyinews.base.BaseActivity;
import xzh.com.wangyinews.ui.mine.MineActivity;
import xzh.com.wangyinews.ui.news.ui.NewsActivity;
import xzh.com.wangyinews.ui.topic.TopicActivity;
import xzh.com.wangyinews.ui.video.VideoActivity;

public class MainActivity extends BaseActivity implements
        OnCheckedChangeListener {
    @InjectView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @InjectView(android.R.id.tabs)
    TabWidget tabs;
    @InjectView(R.id.tabhost)
    TabHost tabHost;
    @InjectView(R.id.tab_home)
    RadioButton tabHome;
    @InjectView(R.id.tab_video)
    RadioButton tabVideo;
    @InjectView(R.id.tab_topic)
    RadioButton tabTopic;
    @InjectView(R.id.tab_mine)
    RadioButton tabMine;

    private Intent topic;
    private Intent video;
    private Intent news;
    private Intent mine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagBarTint(false);
        setContentView(R.layout.activity_tabhost);
        ButterKnife.inject(this);
//        UIUtil.initTintMgr(this, tabs).setStatusBarTintColor(getResources().getColor(R.color.c9));
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        LocalActivityManager groupActivity = new LocalActivityManager(this,
                false);
        groupActivity.dispatchCreate(savedInstanceState);
        tabHost.setup(groupActivity);
        initIntent();
        addSpec();
        ((RadioGroup) findViewById(R.id.tab_radiogroup))
                .setOnCheckedChangeListener(this);
    }

    private void initIntent() {
        news = new Intent(this, NewsActivity.class);
        video = new Intent(this, VideoActivity.class);
        topic = new Intent(this, TopicActivity.class);
        mine = new Intent(this, MineActivity.class);
    }

    private void addSpec() {
        tabHost.addTab(this.buildTagSpec("home", R.string.xinwen,
                R.drawable.home_selector, news));
        tabHost.addTab(this.buildTagSpec("video", R.string.video,
                R.drawable.video_selector, video));
        tabHost.addTab(this.buildTagSpec("topic", R.string.topic,
                R.drawable.topic_selector, topic));
        tabHost.addTab(this.buildTagSpec("mine", R.string.me, R.drawable.mine_selector,
                mine));
    }

    private TabHost.TabSpec buildTagSpec(String tagName, int string, int icon,
                                         Intent content) {
        return tabHost
                .newTabSpec(tagName)
                .setIndicator(getResources().getString(string),
                        getResources().getDrawable(icon)).setContent(content);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.tab_home:
                tabHost.setCurrentTabByTag("home");
                setCurrentChecked(tabHome);
                break;
            case R.id.tab_video:
                tabHost.setCurrentTabByTag("video");
                setCurrentChecked(tabVideo);
                break;
            case R.id.tab_topic:
                tabHost.setCurrentTabByTag("topic");
                setCurrentChecked(tabTopic);
                break;
            case R.id.tab_mine:
                tabHost.setCurrentTabByTag("mine");
                setCurrentChecked(tabMine);
                break;
        }
    }

    private void setCurrentChecked(RadioButton currentTab) {
        tabHome.setTextColor(getResources().getColor(R.color.c6));
        tabVideo.setTextColor(getResources().getColor(R.color.c6));
        tabTopic.setTextColor(getResources().getColor(R.color.c6));
        tabMine.setTextColor(getResources().getColor(R.color.c6));

        currentTab.setTextColor(getResources().getColor(R.color.c9));
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "再按一次退出网易新闻", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}