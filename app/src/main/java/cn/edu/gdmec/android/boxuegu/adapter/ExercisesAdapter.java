package cn.edu.gdmec.android.boxuegu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ExercisesDetailActivity;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;

/**
 * Created by Administrator on 2018/4/16.
 */

public class ExercisesAdapter extends BaseAdapter{
    private Context mContext;
    private List<ExercisesBean> ebl;
    public ExercisesAdapter(Context context){
        this.mContext=context;
    }
    public void setData(List<ExercisesBean> ebl){
        this.ebl=ebl;
        notifyDataSetChanged();
    }
    /*获取Item的总数*/
    @Override
    public int getCount() {
        return ebl==null?0:ebl.size();
    }
    /*根据position得到对应Item的对象*/
    @Override
    public ExercisesBean getItem(int position) {
        return ebl==null?null:ebl.get(position);
    }
    /*根据position得到对应Item的id*/
    @Override
    public long getItemId(int position) {
        return position;
    }
    /*得到对应position对应的Item视图，position是当前Item的位置，
        * convertView参数就是滑出屏幕的Item的View*/
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
   final ViewHolder vh;
        //复用View
   if (view==null){
       vh=new ViewHolder();
       view= LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item,null);
       vh.title=view.findViewById(R.id.tv_title);
       vh.content=view.findViewById(R.id.tv_content);
       vh.order=view.findViewById(R.id.tv_order);
       view.setTag(vh);
   }else {
       vh= (ViewHolder) view.getTag();
   }
        //获取position对应的Item的数据对象
      final ExercisesBean bean=getItem(position);
      SharedPreferences sp=mContext.getSharedPreferences("Click",Context.MODE_PRIVATE);
      boolean isFinish=sp.getBoolean("isFinish"+position,false);
       if (bean!=null){
           vh.order.setText(position+1+"");
           vh.title.setText(bean.title);
           if (isFinish){
               vh.content.setText("已经完成");
           }else {
            vh.content.setText(bean.content);}
           vh.order.setBackgroundResource(bean.background);
       }
        //每个Item的点击事件
       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (bean==null){
                   return;
               }
               //跳转到习题详情页面
               Intent intent=new Intent(mContext, ExercisesDetailActivity.class);
               //把章节Id传递到习题详情页面
               intent.putExtra("id",bean.id);
               //把标题传递到习题详情页面
               intent.putExtra("title",bean.title);
               ((Activity)mContext).startActivityForResult(intent,000);
           }
       });

       return view;
    }
    class ViewHolder{
        public TextView title,content;
        public TextView order;
    }
}
