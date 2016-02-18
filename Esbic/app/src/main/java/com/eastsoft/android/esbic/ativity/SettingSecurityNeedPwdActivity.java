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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/16.
 */
public class SettingSecurityNeedPwdActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private TextView numOne, numTwo, numThree, numFour;
    private ImageButton back;
    private GridView inputKeyBoard;
    private int[] touchNumber = new int[]{R.drawable.input_keyboard_zero_button};
    private String password = "1234";
    private String passwordTwo="2345";
    private List<Map<String, Object>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_need_password);
        initData();
    }

    private void initData() {
        numOne = (TextView) this.findViewById(R.id.num_one);
        numTwo = (TextView) this.findViewById(R.id.num_two);
        numThree = (TextView) this.findViewById(R.id.num_three);
        numFour = (TextView) this.findViewById(R.id.num_four);
        back = (ImageButton) this.findViewById(R.id.title_back);
        inputKeyBoard = (GridView) this.findViewById(R.id.input_keyboard);
        back.setOnClickListener(this);
        mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("numButton", touchNumber[0]);
            mapList.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, mapList, R.layout.input_keyboard_item, new String[]
                {"numButton"}, new int[]{R.id.keyboard_child});
        inputKeyBoard.setAdapter(simpleAdapter);
        inputKeyBoard.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == back.getId()) {
            this.onDestroy();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("GrideView的第几个子列表", String.valueOf(i));
        textHowToShow(i);
        if (i == 11) {
            deleteTextViewFromRight();
        }
        if (!numFour.getText().equals("")) {
            String password = numOne.getText().toString() + numTwo.getText().toString()
                    + numThree.getText().toString() + numFour.getText().toString();
            if (password.equals(this.password)) {
                Intent intent = getIntents();
                intent.setClass(SettingSecurityNeedPwdActivity.this,SettingSecurityActivity.class);
                this.onDestroy();
            } else {
                showShortToast("密码错误，请重试！");
                numOne.setText("");
                numTwo.setText("");
                numFour.setText("");
                numThree.setText("");
            }
            if (password.equals(this.passwordTwo)) {
                Intent intent = getIntents();
                intent.setClass(SettingSecurityNeedPwdActivity.this,SettingSecurityActivity.class);
                this.onDestroy();
            } else {
                showShortToast("密码错误，请重试！");
                numOne.setText("");
                numTwo.setText("");
                numFour.setText("");
                numThree.setText("");
            }

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
            } else {
                return;
            }
        } else if (position == 9) {
            return;
        } else if (position == 10) {
            if (numOne.getText().equals("")) {
                numOne.setText(String.valueOf(0));
            } else if (numTwo.getText().equals("")) {
                numTwo.setText(String.valueOf(0));
            } else if (numThree.getText().equals("")) {
                numThree.setText(String.valueOf(0));
            } else if (numFour.getText().equals("")) {
                numFour.setText(String.valueOf(0));
            } else {
                return;
            }
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

    //判断设置界面用户设置的密码与输入的密码是否对应
    private void getUserSettingPsd() {
        SharedPreferences sharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE);
        password = sharedPreferences.getString("", "");
        String inputPassword = numTwo.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() +
                numFour.getText().toString();
        if (inputPassword.equals(password)) {
            Intent intent = getIntents();
            intent.setClass(SettingSecurityNeedPwdActivity.this, MainActivity.class);
            this.onDestroy();
        } else {
            numFour.setText("");
            numThree.setText("");
            numTwo.setText("");
            numOne.setText("");
            Toast.makeText(this, "密码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
}