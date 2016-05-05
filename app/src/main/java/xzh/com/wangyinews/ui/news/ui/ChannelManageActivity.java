package xzh.com.wangyinews.ui.news.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import xzh.com.wangyinews.R;
import xzh.com.wangyinews.WYApplication;
import xzh.com.wangyinews.base.BaseActivity;
import xzh.com.wangyinews.ui.main.MainActivity;
import xzh.com.wangyinews.utils.sql.SqlLiteHelper;
import xzh.com.wangyinews.view.drag.DragGridView;
import xzh.com.wangyinews.view.drag.RecomendGridView;
import xzh.com.wangyinews.view.drag.adapter.DragAdapter;
import xzh.com.wangyinews.view.drag.adapter.RecomendAdapter;
import xzh.com.wangyinews.view.drag.bean.ChannelItem;
import xzh.com.wangyinews.view.drag.bean.ChannelManage;

/**
 * Created by xiangzhihong on 2016/4/27 on 18:05.
 */
public class ChannelManageActivity extends BaseActivity implements OnItemClickListener {

    @InjectView(R.id.use_gridview)
    DragGridView useGridview;
    @InjectView(R.id.recommend_gridview)
    RecomendGridView recommendGridview;
    @InjectView(R.id.edit_use_channel)
    TextView editChannel;
    @InjectView(R.id.drag_tip)
    TextView dragTip;

    private DragAdapter useAdapter;
    private RecomendAdapter recommendAdapter;
    private ArrayList<ChannelItem> useList = new ArrayList<ChannelItem>();
    private ArrayList<ChannelItem> recommendList = new ArrayList<ChannelItem>();
    boolean isMove = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        useList = ((ArrayList<ChannelItem>) ChannelManage.getManage(SqlLiteHelper.getSQLHelper()).getUserChannel());
        recommendList = ((ArrayList<ChannelItem>) ChannelManage.getManage(SqlLiteHelper.getSQLHelper()).getOtherChannel());
        useAdapter = new DragAdapter(this);
        useAdapter.setListDate(useList);
        useGridview.setAdapter(useAdapter);
        recommendAdapter = new RecomendAdapter(this, recommendList);
        recommendGridview.setAdapter(recommendAdapter);
        recommendGridview.setOnItemClickListener(this);
        useGridview.setOnItemClickListener(this);
    }

    @OnClick(R.id.close_channel_btn)
    void closeChannel() {
        onBackPressed();
    }

    @OnClick(R.id.edit_use_channel)
    void editChannel() {
        if (dragTip.getVisibility()==View.GONE){
            useAdapter.editChannel(true);
            editChannel.setText("完成");
            dragTip.setVisibility(View.VISIBLE);
        }else {
            useAdapter.editChannel(false);
            editChannel.setText("编辑");
            dragTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        if (isMove) {
            return;
        }
        switch (parent.getId()) {
            //position为 0的不可以进行任何操作
            case R.id.use_gridview:
                if (position != 0 ) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.item_channel_name);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        recommendAdapter.setVisible(false);
                        //添加到最后一个
                        recommendAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    recommendGridview.getChildAt(recommendGridview.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, channel, useGridview);
                                    useAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.recommend_gridview:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.item_channel_name);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((RecomendAdapter) parent.getAdapter()).getItem(position);
                    useAdapter.setVisible(false);
                    //添加到最后一个
                    useAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                useGridview.getChildAt(useGridview.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, channel, recommendGridview);
                                recommendAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        moveView.getLocationInWindow(initLocation);
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                if (clickGridView instanceof DragGridView) {
                    recommendAdapter.setVisible(true);
                    recommendAdapter.notifyDataSetChanged();
                    useAdapter.remove();
                } else {
                    useAdapter.setVisible(true);
                    useAdapter.notifyDataSetChanged();
                    recommendAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    private void saveChannel() {
        ChannelManage.getManage(SqlLiteHelper.getSQLHelper()).deleteAllChannel();
        ChannelManage.getManage(SqlLiteHelper.getSQLHelper()).saveUserChannel(useAdapter.getChannnelLst());
        ChannelManage.getManage(SqlLiteHelper.getSQLHelper()).saveOtherChannel(recommendAdapter.getChannnelLst());
    }

    @Override
    public void onBackPressed() {
        saveChannel();
        if (useAdapter.isListChanged()) {
            Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
            setResult(NewsActivity.CHANNEL_RESULT, intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
