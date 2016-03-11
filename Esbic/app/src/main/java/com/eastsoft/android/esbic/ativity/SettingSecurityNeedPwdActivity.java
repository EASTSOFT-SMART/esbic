package com.eastsoft.android.esbic.ativity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/16.
 */
public class SettingSecurityNeedPwdActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private TextView numOne, numTwo, numThree, numFour;
    private GridView inputKeyBoard;
    private int[] icon;
    private String password;
    private List<Map<String, Object>> mapList;
    //用来表示开启哪一个Activity;
    private int activityMark=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_need_password);
        initData();
    }

    private void initData() {
        numOne = (TextView) this.findViewById(R.id.need_pwd_num_one);
        numTwo = (TextView) this.findViewById(R.id.need_pwd_num_two);
        numThree = (TextView) this.findViewById(R.id.need_pwd_num_three);
        numFour = (TextView) this.findViewById(R.id.need_pwd_num_four);
        inputKeyBoard = (GridView) this.findViewById(R.id.need_pwd_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon, "清空");
        inputKeyBoard.setAdapter(inputKeyBoardAdapter);
        inputKeyBoard.setOnItemClickListener(this);
        password = ((MyApplication)getApplication()).getModelService().getUserPassword();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playMusic();
        textHowToShow(i);
        if (i == 11) {
            deleteTextViewFromRight();
        }
    }

    //设置TextView从左往右显示
    public void textHowToShow(int position) {
        if (position < 9) {
            if (numOne.getText().equals("")) {
                numOne.setText(String.valueOf(position + 1));
            } else if (numTwo.getText().equals("")) {
                numTwo.setText(String.valueOf(position + 1));
            } else if (numThree.getText().equals("")) {
                numThree.setText(String.valueOf(position + 1));
            } else if (numFour.getText().equals("")) {
                numFour.setText(String.valueOf(position + 1));
                comparePwd();
            } else {
                return;
            }
        } else if (position == 9) {
            numOne.setText("");
            numTwo.setText("");
            numFour.setText("");
            numThree.setText("");
        } else if (position == 10) {
            if (numOne.getText().equals("")) {
                numOne.setText(String.valueOf(0));
            } else if (numTwo.getText().equals("")) {
                numTwo.setText(String.valueOf(0));
            } else if (numThree.getText().equals("")) {
                numThree.setText(String.valueOf(0));
            } else if (numFour.getText().equals("")) {
                numFour.setText(String.valueOf(0));
                comparePwd();
            } else {
                return;
            }
        }
    }

    private void comparePwd()
    {
        String password = numOne.getText().toString() + numTwo.getText().toString()
                + numThree.getText().toString() + numFour.getText().toString();
        if (password.equals(this.password)) {
            Intent intent = getIntents();
            intent.setClass(SettingSecurityNeedPwdActivity.this,SettingSecurityActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            showShortToast("密码错误，请重试！");
            numOne.setText("");
            numTwo.setText("");
            numFour.setText("");
            numThree.setText("");
        }
    }

    //设置删除的时候从右往左删除
    private void deleteTextViewFromRight() {
        if (!numFour.getText().equals("")) {
            numFour.setText("");
        } else if (!numThree.getText().equals("")) {
            numThree.setText("");
        } else if (!numTwo.getText().equals("")) {
            numTwo.setText("");
        } else if (!numOne.getText().equals("")) {
            numOne.setText("");
        } else {
            return;
        }
    }



}