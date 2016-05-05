package xzh.com.wangyinews.ui.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import xzh.com.wangyinews.R;
import xzh.com.wangyinews.base.BaseActivity;
import xzh.com.wangyinews.ui.news.adapter.TabStripAdapter;
import xzh.com.wangyinews.view.strip.TabStrip;

public class NewsActivity extends BaseActivity {

    @InjectView(R.id.tabstrip)
    TabStrip tabstrip;
    @InjectView(R.id.category_layout)
    RelativeLayout categoryLayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.main_layout)
    LinearLayout mainLayout;

    private TabStripAdapter adapter;
    public final static int CHANNEL_RESULT = 0x002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        adapter = new TabStripAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabstrip.setViewPager(viewPager);
    }

    @OnClick(R.id.add_channel)
    void addChannel() {
       startActivity(new Intent(this,ChannelManageActivity.class));
    }

}