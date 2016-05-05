package xzh.com.wangyinews.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by xiangzhihong on 2016/4/27 on 19:12.
 */
public class RecomendGridView extends GridView {

    public RecomendGridView(Context mContext, AttributeSet paramAttributeSet) {
        super(mContext, paramAttributeSet);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
