package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

public class FindPswActivity extends AppCompatActivity {
private EditText et_validate_name,et_user_name,et_validate_reset_name;
private Button btn_validate,btn_validate_set;
private TextView tv_main_title;
private TextView tv_back;
    //from为security时是从设置密保界面跳转过来的，否则就是从登录界面跳转过来的
private String from;
private TextView tv_reset_psw,tv_user_name,tv_reset_psw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //获取从登录界面和设置界面传递过来的数据
        from=getIntent().getStringExtra("from");
        init();
    }
    /*获取界面控件及处理相应控件的点击事件*/
    private void init() {
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_back=findViewById(R.id.tv_back);
        et_validate_name=findViewById(R.id.et_validate_name);
        btn_validate=findViewById(R.id.btn_validate);
        tv_reset_psw=findViewById(R.id.tv_reset_psw);
        et_user_name=findViewById(R.id.et_user_name);
        tv_user_name=findViewById(R.id.tv_user_name);
        et_validate_reset_name=findViewById(R.id.et_validate_reset_name);
        tv_reset_psw2=findViewById(R.id.tv_reset_psw);
        if ("security".equals(from)){//设置密保
            tv_main_title.setText("设置密保");
            btn_validate.setText("设置");
        }else {
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPswActivity.this.finish();
            }
        });
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validateName=et_validate_name.getText().toString().trim();
                if ("security".equals(from)){//设置密保
                    if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_LONG).show();
                         return;
                    }else {
                        Toast.makeText(FindPswActivity.this,"密保设置成功",Toast.LENGTH_LONG).show();
                        //保存密保到SharedPreference中
                        saveSecurity(validateName);
                        FindPswActivity.this.finish();
                    }
                }else{//找回密码
                    String userName=et_user_name.getText().toString().trim();
                    String sp_security=readSecurity(userName);
                    if (TextUtils.isEmpty(userName)){
                        Toast.makeText(FindPswActivity.this,"请输入您的用户名",Toast.LENGTH_LONG).show();
                    return;
                    }else if (!isExistUserName(userName)){
                        Toast.makeText(FindPswActivity.this,"您输入的用户名不存在",Toast.LENGTH_LONG).show();
                    return;
                    }else if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_LONG).show();
                   return;
                    }
                    if (!validateName.equals(sp_security)){
                        Toast.makeText(FindPswActivity.this,"输入的密保不正确",Toast.LENGTH_LONG).show();
                   return;
                    }else {
                        //输入的密保正确，重新给用户设置一个密码
                        tv_reset_psw2.setVisibility(View.VISIBLE);
                        et_validate_reset_name.setVisibility(View.VISIBLE);
                        btn_validate.setText("确认修改");
                        String newPsw=et_validate_reset_name.getText().toString().trim();
                        if (!TextUtils.isEmpty(newPsw)){
                            savePsw(userName,newPsw);
                        }

                    }
                }
            }
        });
    }
    /*保存密保到SharedPreferences中*/
    private void saveSecurity(String validateName){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        //存入用户对应的密保
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);
        editor.commit();
    }
    /*从SharedPreferences中读取密保*/
    private String readSecurity(String userName){
       SharedPreferences sp=getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
       String security=sp.getString(userName+"_security","");
       return security;
    }
    /*从SharedPreferences中根据用户输入的用户名来判定是否有此用户*/
    private boolean isExistUserName(String userName){
        boolean hasUserName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPsw)){
            hasUserName=true;
        }
        return hasUserName;
    }
    private void savePsw(String userName,String newPsw){
        String md5Psw= MD5Utils.md5(newPsw);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putString(userName,md5Psw);
        editor.commit();
        FindPswActivity.this.finish();
    }
}
