package xzh.com.wangyinews.view.drag.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.wangyinews.R;
import xzh.com.wangyinews.view.drag.bean.ChannelItem;

/**
 * Created by xiangzhihong on 2016/4/27 on 18:55.
 */
public class DragAdapter extends BaseAdapter {
    private boolean isItemShow = false;
    private Context context;
    private int holdPosition;
    private boolean isChanged = false;
    private boolean isListChanged = false;
    boolean isVisible = true;
    public List<ChannelItem> channelList;
    public int remove_position = -1;
    private boolean editFlag = false;//编辑状态


    public DragAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public ChannelItem getItem(int position) {
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_channel, null);
            viewHolder=new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
//            viewHolder.resetView();
        }

        ChannelItem channel = getItem(position);
        viewHolder.itemChannelName.setText(channel.getName());
        initItem(viewHolder,position);
        return convertView;
    }

    private void initItem(ViewHolder viewHolder, int position) {
        if (position == 0) {
            viewHolder.itemChannelName.setTextColor(context.getResources().getColor(R.color.c9));
            viewHolder.itemChannelName.setEnabled(false);
        }else {
            viewHolder.itemChannelName.setTextColor(context.getResources().getColor(R.color.c6));
            viewHolder.itemChannelName.setEnabled(true);
        }
        if (isChanged && (position == holdPosition) && !isItemShow) {
            viewHolder.itemChannelName.setText("");
            viewHolder.itemChannelName.setSelected(true);
            viewHolder.itemChannelName.setEnabled(true);
            isChanged = false;
        }
        if (!isVisible && (position == channelList.size() - 1)) {
            viewHolder.itemChannelName.setText("");
            viewHolder.itemChannelName.setSelected(true);
            viewHolder.itemChannelName.setEnabled(true);
        }
        if (remove_position == position) {
            viewHolder.itemChannelName.setText("");
        }
        if (editFlag) {
           if (position == 0){
               viewHolder.channelEdit.setVisibility(View.GONE);
           }else {
               viewHolder.channelEdit.setVisibility(editFlag?View.VISIBLE:View.GONE);
           }
            viewHolder.channelEdit.setChecked(editFlag);
        }
    }

    /**
     * 添加频道列表
     */
    public void addItem(ChannelItem channel) {
        channelList.add(channel);
        isListChanged = true;
        notifyDataSetChanged();
    }

    /**
     * 拖动变更频道排序
     */
    public void exchange(int dragPostion, int dropPostion) {
        holdPosition = dropPostion;
        ChannelItem dragItem = getItem(dragPostion);
        if (dragPostion < dropPostion) {
            channelList.add(dropPostion + 1, dragItem);
            channelList.remove(dragPostion);
        } else {
            channelList.add(dropPostion, dragItem);
            channelList.remove(dragPostion + 1);
        }
        isChanged = true;
        isListChanged = true;
        notifyDataSetChanged();
    }

    public void editChannel(boolean editFlag) {
        this.editFlag = editFlag;
        notifyDataSetChanged();
    }

    public List<ChannelItem> getChannnelLst() {
        return channelList;
    }

    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
    }

    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        isListChanged = true;
        notifyDataSetChanged();
    }

    public void setListDate(List<ChannelItem> list) {
        channelList = list;
        notifyDataSetChanged();
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isListChanged() {
        return isListChanged;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setShowDropItem(boolean show) {
        isItemShow = show;
    }

    static class ViewHolder {
        @InjectView(R.id.item_channel_name)
        TextView itemChannelName;
        @InjectView(R.id.channel_edit)
        CheckBox channelEdit;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        void resetView(){
            itemChannelName.setText("");
            itemChannelName.setTextColor(Color.parseColor("#646464"));
        }
    }
}
