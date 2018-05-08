package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.adapter.VideoListItemAdapter;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class ActivityVideoListActivity extends Activity  {

    private TextView tv_intro;
    private TextView tv_video;
    private TextView tv_chapter_intro;
    private ListView lv_video_list;
    private ScrollView sv_chapter_intro;
    private VideoListItemAdapter adapter;
    private List<VideoBean> videoList;
    private int chapterId;
    private String intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        chapterId=getIntent().getIntExtra("id",0);
        intro=getIntent().getStringExtra("intro");
        initData();
        initView();
    }
    /*读取数据流，参数in是数据流*/
    private String read(InputStream in){
        BufferedReader reader=null;
        StringBuilder sb=null;
        String line=null;
        try {
            sb=new StringBuilder();//实例化一个StringBuilder对象
            //用InputStreamReader把in这个字节流转换成字符流BufferedReader
            reader=new BufferedReader(new InputStreamReader(in));
            while ((line=reader.readLine())!=null) {
                sb.append(line);
                sb.append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }finally {
            try {
                if (in!=null)
                    in.close();
                if (reader!=null)
                    reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    /*设置视频列表本地数据*/
    private void initData(){
        JSONArray jsonArray;
        try {
            InputStream is = getResources().getAssets().open("data.json");
            jsonArray = new JSONArray(read(is));
            videoList = new ArrayList<VideoBean>();
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObj=jsonArray.getJSONObject(i);
                if (jsonObj.getInt("chapterId")==chapterId) {

                    JSONArray jd = jsonObj.getJSONArray("data");
                    for (int j = 0; j < jd.length(); j++) {
                        VideoBean bean=new VideoBean();
                        bean.chapterId=jsonObj.getInt("chapterId");
                        JSONObject jsonObject=jd.getJSONObject(j);
                        bean.videoId = jsonObject.getInt("videoId");
                        bean.title = jsonObject.getString("title");
                        bean.secondTitle = jsonObject.getString("secondTitle");
                        bean.videoPath = jsonObject.getString("videoPath");
                        videoList.add(bean);
                        bean=null;
                    }
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    /*初始化界面UI控件*/
    private void initView() {
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        tv_video = (TextView) findViewById(R.id.tv_video);
        lv_video_list = (ListView) findViewById(R.id.lv_video_list);
        tv_chapter_intro = (TextView) findViewById(R.id.tv_chapter_intro);
        sv_chapter_intro = (ScrollView) findViewById(R.id.sv_chapter_intro);
        adapter = new VideoListItemAdapter(this);
        lv_video_list.setAdapter(adapter);
        adapter.setData(videoList);
        tv_chapter_intro.setText(intro);
        tv_intro.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_intro.setTextColor(Color.parseColor("#FFFFFF"));
        tv_video.setTextColor(Color.parseColor("#000000"));
        tv_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv_video_list.setVisibility(View.GONE);
                sv_chapter_intro.setVisibility(View.VISIBLE);
                tv_intro.setBackgroundColor(Color.parseColor("#30B4FF"));
                tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_intro.setTextColor(Color.parseColor("#FFFFFF"));
                tv_video.setTextColor(Color.parseColor("#000000"));
            }
        });
        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv_video_list.setVisibility(View.VISIBLE);
                sv_chapter_intro.setVisibility(View.GONE);
                tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_video.setBackgroundColor(Color.parseColor("#30B4FF"));
                tv_intro.setTextColor(Color.parseColor("#000000"));
                tv_video.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data!=null){
            //接收播放界面回传过来的被选中的视频的位置
            int position=data.getIntExtra("position",0);
            adapter.setSelectedPosition(position);//设置被选中的位置
            //视频选项卡被选中时所有图标的颜色值
            lv_video_list.setVisibility(View.VISIBLE);
            sv_chapter_intro.setVisibility(View.GONE);
            tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_video.setBackgroundColor(Color.parseColor("#30B4FF"));
            tv_intro.setTextColor(Color.parseColor("#000000"));
            tv_video.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

}