package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.Fragment.MainViewCourseFragment;
import cn.edu.gdmec.android.boxuegu.Fragment.MainViewExercisesFragment;
import cn.edu.gdmec.android.boxuegu.Fragment.MainViewMyinfoFragment;
import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.View.ExercisesView;
import cn.edu.gdmec.android.boxuegu.View.MyInfoView;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    /*中间内容栏*/
    private FrameLayout mBodyLayout;
    /*底部按钮栏*/
    public LinearLayout mBottomLayout;
    /*底部按钮*/
    private View mCourseBtn;
    private View mExerciseBtn;
    private View mMyInfoBtn;
    private TextView tv_course;
    private TextView tv_exercises;
    private TextView tv_myInfo;
    private ImageView iv_course;
    private ImageView iv_exercises;
    private ImageView iv_myInfo;
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    private MyInfoView mMyInfoView;
    private ExercisesView mExercisesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        initBottomBar();
        setListener();
        setInitStatus();
        getSupportFragmentManager().beginTransaction().add(R.id.main_body,new MainViewMyinfoFragment()).commit();
    }
    /*获取界面上的UI控件*/
    private void init() {
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("博学谷课程");
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FE"));
        tv_back.setVisibility(View.GONE);
        initBodyLayout();
    }
    /*获取底部导航栏上的控件*/
    private void initBottomBar() {
        mBottomLayout=findViewById(R.id.main_bottom_bar);
        mCourseBtn=findViewById(R.id.bottom_bar_course_btn);
        mExerciseBtn=findViewById(R.id.bottom_bar_exercises_btn);
        mMyInfoBtn=findViewById(R.id.bottom_bar_myinfo_btn);
        tv_course=findViewById(R.id.bottom_bar_text_course);
        tv_exercises=findViewById(R.id.bottom_bar_text_exercises);
        tv_myInfo=findViewById(R.id.bottom_bar_text_myinfo);
        iv_course=findViewById(R.id.bottom_bar_image_course);
        iv_exercises=findViewById(R.id.bottom_bar_image_exercises);
        iv_myInfo=findViewById(R.id.bottom_bar_image_myinfo);
    }
    /*设置底部三个按钮的点击监听事件*/
    private void setListener() {
        for (int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }
    private void setInitStatus() {
        clearBottomImageState();
        setSelectedStatus(2);
        createView(2);
    }
    /*显示对应的页面*/
   private void selectDisplayView(int index){
        removeAllView();
        createView(index);
        setSelectedStatus(index);
   }
    /*选择视图*/
   private void createView(int viewIndex){
       switch (viewIndex){
           case 0:
               //课程界面
               break;
           case 1:
               //习题界面
               break;
           case 2:
               //我的界面


               break;
       }
   }
   protected long exitTime;//记录第一次点击时的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
           if ((System.currentTimeMillis()-exitTime)>2000){
               Toast.makeText(MainActivity.this,"再按一次退出博学谷",Toast.LENGTH_LONG).show();
               exitTime=System.currentTimeMillis();
           }else {
               MainActivity.this.finish();
               if (readLoginStatus()){
                   clearLoginStatus();
               }
               System.exit(0);
           }
           return true;
       }
       return super.onKeyDown(keyCode,event);
    }
    /*获取SharedPreferences中的登录状态*/
      private boolean readLoginStatus(){
          SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
          boolean isLogin=sp.getBoolean("isLogin",false);
          return isLogin;
      }
    /*清除SharedPreferences中的登录状态*/
      private void clearLoginStatus(){
          SharedPreferences sp=getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
          SharedPreferences.Editor editor=sp.edit();
          editor.putBoolean("isLogin",false);
          editor.putString("loginUserName","");
          editor.commit();
      }
    private void initBodyLayout() {
        mBodyLayout=findViewById(R.id.main_body);
    }
    private void clearBottomImageState(){
       tv_course.setTextColor(Color.parseColor("#666666"));
       tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);
        for (int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }
    /*设置底部按钮的选中状态*/
    private void setSelectedStatus(int index){
        switch (index){
            case 0:
                mCourseBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.GONE);
                tv_main_title.setText("博学谷课程");
                tv_exercises.setTextColor(Color.parseColor("#666666"));
                tv_myInfo.setTextColor(Color.parseColor("#666666"));
                iv_exercises.setImageResource(R.drawable.main_exercises_icon);
                iv_myInfo.setImageResource(R.drawable.main_my_icon);
                break;
            case 1:
                mExerciseBtn.setSelected(true);
                iv_exercises.setImageResource(R.drawable.main_course_icon_selected);
                tv_exercises.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷习题");
                tv_course.setTextColor(Color.parseColor("#666666"));
                tv_myInfo.setTextColor(Color.parseColor("#666666"));
                iv_course.setImageResource(R.drawable.main_course_icon);
                iv_myInfo.setImageResource(R.drawable.main_my_icon);
                break;
            case 2:
         /*       if (mMyInfoView==null){
                mMyInfoView=new MyInfoView(this);
                mBodyLayout.addView(mMyInfoView.getView());
            }else {
                mMyInfoView.getView();
            }
            mMyInfoView.showView();*/
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.main_course_icon_selected);
                tv_myInfo.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.GONE);
                tv_course.setTextColor(Color.parseColor("#666666"));
                tv_exercises.setTextColor(Color.parseColor("#666666"));
                iv_course.setImageResource(R.drawable.main_course_icon);
                iv_exercises.setImageResource(R.drawable.main_exercises_icon);
                break;
        }
    }
    /*移除不需要的视图*/
    private void removeAllView(){
        for (int i=0;i<mBodyLayout.getChildCount();i++){
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //课程的点击事件
            case R.id.bottom_bar_course_btn:
                //clearBottomImageState();
                selectDisplayView(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new MainViewCourseFragment()).commit();
                break;
            //习题的点击事件
            case R.id.bottom_bar_exercises_btn:
                //clearBottomImageState();
                selectDisplayView(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new MainViewExercisesFragment()).commit();
             /*   if (mExercisesView==null){
                    mExercisesView=new ExercisesView(this);
                    mBodyLayout.addView(mExercisesView.getView());
                }else {
                    mExercisesView.getView();
                }
                mExercisesView.showView();*/
                break;
            //我的点击事件
            case R.id.bottom_bar_myinfo_btn:
                //clearBottomImageState();
                selectDisplayView(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new MainViewMyinfoFragment()).commit();
                break;
                default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            //从设置界面或登录界面传递过来的登录状态
            boolean isLogin=data.getBooleanExtra("isLogin",false);
            if (isLogin){//登录成功时显示课程界面
               setSelectedStatus(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new MainViewMyinfoFragment()).commit();
            }else {
                setSelectedStatus(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new MainViewMyinfoFragment()).commit();
            }
        }
        if (requestCode==000){
            setSelectedStatus(1);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new MainViewExercisesFragment()).commit();
        }
    }
}
