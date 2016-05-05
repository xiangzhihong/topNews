package xzh.com.wangyinews.ui.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import xzh.com.wangyinews.ui.news.ui.NewsFragment;

/**
 * Created by xiangzhihong on 2016/4/27 on 16:22.
 */
public class TabStripAdapter extends FragmentPagerAdapter {

    private List<String> catalogs = new ArrayList<String>();

    public TabStripAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {
        catalogs.add("推荐");
        catalogs.add("热点");
        catalogs.add("上海");
        catalogs.add("视频");
        catalogs.add("社会");
        catalogs.add("军事");
        catalogs.add("评论");
        catalogs.add("娱乐");
        catalogs.add("科技");
        catalogs.add("体育");
        catalogs.add("科技");
        catalogs.add("更多");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return catalogs.get(position);
    }

    @Override
    public int getCount() {
        return catalogs.size();
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.newInstance(position);
    }
}
