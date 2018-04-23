package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;

public class ChangeUserInfoActivity extends AppCompatActivity {

    private TextView tv_back;
    private TextView tv_main_title;
    private TextView tv_save;
    private RelativeLayout rl_title_bar;
    private EditText et_content;
    private ImageView iv_detele;
    private String title,content;
    private int flag;//flag为1时表示修改昵称，为2时表示修改签名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        //从个人资料界面传递过来的标题和内容
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        flag=getIntent().getIntExtra("flag",0);
        tv_back =  findViewById(R.id.tv_back);
        tv_main_title =  findViewById(R.id.tv_main_title);
        tv_main_title.setText(title);
        tv_save =  findViewById(R.id.tv_save);
        rl_title_bar =  findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_save.setVisibility(View.VISIBLE);
        et_content =  findViewById(R.id.et_content);
        iv_detele =  findViewById(R.id.iv_detele);
        if (!TextUtils.isEmpty(content)){
            et_content.setText(content);
            et_content.setSelection(content.length());
        }
        contentListener();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeUserInfoActivity.this.finish();
            }
        });
        iv_detele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_content.setText("");
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data=new Intent();
                String etContent=et_content.getText().toString().trim();
                switch (flag){
                    case 1:
                        if (!TextUtils.isEmpty(etContent)){
                            data.putExtra("nickName",etContent);
                            setResult(RESULT_OK,data);
                            Toast.makeText(ChangeUserInfoActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                        ChangeUserInfoActivity.this.finish();
                        }else {
                            Toast.makeText(ChangeUserInfoActivity.this,"昵称不能为空",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(etContent)){
                            data.putExtra("signature",etContent);
                            setResult(RESULT_OK,data);
                            Toast.makeText(ChangeUserInfoActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                            ChangeUserInfoActivity.this.finish();
                        }else {
                            Toast.makeText(ChangeUserInfoActivity.this,"签名不能为空",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 3:
                        if (!TextUtils.isEmpty(etContent)){
                            data.putExtra("qq",etContent);
                            setResult(RESULT_OK,data);
                            Toast.makeText(ChangeUserInfoActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                            ChangeUserInfoActivity.this.finish();
                        }else {
                            Toast.makeText(ChangeUserInfoActivity.this,"QQ不能为空",Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        });
    }
    /*监听个人资料修改界面输入的文字*/
    private void contentListener() {
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            public void onTextChanged(CharSequence charSequence, int i, int start, int before) {
            Editable editable=et_content.getText();
            int len=editable.length();//输入的文本的长度
            if (len>0){
                iv_detele.setVisibility(View.VISIBLE);
            }else {
                iv_detele.setVisibility(View.GONE);
            }
            switch (flag){
                case 1://昵称
                    //昵称限制最多8个文字，超过8个需要截取掉多余的文字
                    if (len>8){
                        int selEndIndex= Selection.getSelectionEnd(editable);
                        String str=editable.toString();
                        //截取新字符串
                        String newStr=str.substring(0,8);
                        et_content.setText(newStr);
                        editable=et_content.getText();
                        //新字符串的长度
                        int newLen=editable.length();
                        //旧光标位置超过新字符串的长度
                        if (selEndIndex>newLen){
                            selEndIndex=editable.length();
                        }
                        //设置新光标所在的位置
                        Selection.setSelection(editable,selEndIndex);
                    }
                    break;
                case 2:
                    //签名
                    //签名最多是16个文字，超过16个需要截取掉多余的文字
                    if (len>16){
                        int selEndIndex=Selection.getSelectionEnd(editable);
                        String str=editable.toString();
                        String newStr=str.substring(0,16);
                        et_content.setText(newStr);
                        editable=et_content.getText();
                        int newLen=editable.length();
                        if (selEndIndex>newLen){
                            selEndIndex=editable.length();
                        }
                        Selection.setSelection(editable,selEndIndex);
                    }
                    break;
                case 3:
                    if (len>12){
                        int selEndIndex=Selection.getSelectionEnd(editable);
                        String str=editable.toString();
                        String newStr=str.substring(0,12);
                        et_content.setText(newStr);
                        editable=et_content.getText();
                        int newLen=editable.length();
                        if (selEndIndex>newLen){
                            selEndIndex=editable.length();
                        }
                        Selection.setSelection(editable,selEndIndex);
                    }
                    break;
                    default:break;
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
