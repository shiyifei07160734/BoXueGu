package cn.edu.gdmec.android.boxuegu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ActivityVideoPlayActivity;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;

public class PlayHistoryListItemAdapter extends BaseAdapter {
    private Context context;
    private List<VideoBean> objects = new ArrayList<VideoBean>();
    private LayoutInflater layoutInflater;

    public PlayHistoryListItemAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /*设置数据更新界面*/
    public void setData(List<VideoBean> vb1) {
        this.objects = vb1;
        notifyDataSetChanged();
    }

    /*获取Item的总数*/
    @Override
    public int getCount() {
        return objects.size();
    }

    /*根据position得到对应Item的对象*/
    @Override
    public VideoBean getItem(int position) {
        return objects.get(position);
    }

    /*根据position得到对应Item的Id*/
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*得到相应position对应的Item视图
    * position是当前Item的位置，convertView参数就是滑出屏幕的Item的View*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.play_history_list_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((VideoBean) getItem(position), (ViewHolder) convertView.getTag(), convertView);
        return convertView;
    }

    private void initializeViews(final VideoBean object, ViewHolder holder, View convertView) {
        if (object != null) {
            holder.tv_title.setText(object.title);
            holder.tv_video_title.setText(object.secondTitle);
            switch (object.chapterId) {
                case 1:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon1);
                    break;
                case 2:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon2);
                    break;
                case 3:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon3);
                    break;
                case 4:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon4);
                    break;
                case 5:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon5);
                    break;
                case 6:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon6);
                    break;
                case 7:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon7);
                    break;
                case 8:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon8);
                    break;
                case 9:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon9);
                    break;
                case 10:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon10);
                    break;
                default:
                    holder.iv_icon.setImageResource(R.drawable.video_play_icon1);
                    break;

            }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (object == null) {
                    return;
                }
                //跳转到播放视频界面
                Intent intent = new Intent(context, ActivityVideoPlayActivity.class);
                intent.putExtra("videoPath", object.videoPath);
                context.startActivity(intent);
            }
        });
    }
}
    protected class ViewHolder{
        public TextView tv_title,tv_video_title;
        public ImageView iv_icon;

        public ViewHolder(View view){
            iv_icon=(ImageView) view.findViewById(R.id.iv_video_icon);
            tv_video_title=(TextView) view.findViewById(R.id.tv_video_title);
            tv_title=(TextView) view.findViewById(R.id.tv_adapter_title);


    }
}
}