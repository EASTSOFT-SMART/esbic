package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sofa on 2016/1/23.
 */
public class LeaveHome extends BaseActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{
    private TextView numOne,numTwo,numThree,numFour;
    private GridView inputKeyBoard;
    private List<Map<String,Object>> listItems;
    private int[] icon;
    //   R.drawable.small_circle,R.drawable.small_circle,R.drawable.small_circle,R.drawable.small_circle,R.drawable.small_circle,
      //      R.drawable.small_circle,R.drawable.small_circle,R.drawable.small_circle,R.drawable.small_circle,R.drawable.small_circle,
      //      R.drawable.small_circle,R.drawable.small_circle
   // };
    private String password="1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.leave_home);
        initData();
    }

    public void initData(){
        numOne=(TextView) this.findViewById(R.id.num_one);
        numTwo=(TextView) this.findViewById(R.id.num_two);
        numThree=(TextView) this.findViewById(R.id.num_three);
        numFour=(TextView) this.findViewById(R.id.num_four);
        inputKeyBoard=(GridView) this.findViewById(R.id.leave_home_input_keyboard);
        icon=new int[]{R.drawable.num_zero,R.drawable.num_one,R.drawable.num_two,R.drawable.num_three
        ,R.drawable.num_four,R.drawable.num_five,R.drawable.num_six,R.drawable.num_seven,R.drawable.num_eight,
                R.drawable.num_nine,R.drawable.num_delete,R.drawable.num_clear_all};
        listItems=new ArrayList<Map<String, Object>>();
        for(int i=0;i<icon.length;i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("icon",icon[i]);
            listItems.add(listItem);

        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems, R.layout.input_keyboard_item, new String[]
                {"icon"}, new int[]{R.id.keyboard_child});
        inputKeyBoard.setAdapter(simpleAdapter);
        inputKeyBoard.setOnItemClickListener(this);
        inputKeyBoard.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("GrideView的第几个子列表",String.valueOf(i));
        textHowToShow(i);
        if (i==11){
            deleteTextViewFromRight();
        }
        if (!numFour.getText().toString().equals("")){
            String password = numOne.getText().toString() + numTwo.getText().toString()
                    + numThree.getText().toString() + numFour.getText().toString();
            if (password.equals(this.password)) {
                Intent intent = getIntents();
                intent.setClass(LeaveHome.this, MainActivity.class);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("GrideView的第几个子列表",String.valueOf(i));
        textHowToShow(i);
        if (i==11){
            deleteTextViewFromRight();
        }
        getUserSettingPsd();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //设置TextView从左往右显示
    public void textHowToShow(int position){
         if(position<9){
             if (numOne.getText().equals("")){
                 numOne.setText(String.valueOf(position+1));
             }else if (numTwo.getText().equals("")){
                 numTwo.setText(String.valueOf(position+1) );
             }else if (numThree.getText().equals("")){
                 numThree.setText(String.valueOf(position+1));
             }else if (numFour.getText().equals("")){
                 numFour.setText(String.valueOf(position+1));
             }else{
                 return;
             }
         }else if (position==9){
             return;
         }else if (position==10){
             if (numOne.getText().equals("")){
                 numOne.setText(String.valueOf(0));
             }else if (numTwo.getText().equals("")){
                 numTwo.setText(String.valueOf(0) );
             }else if (numThree.getText().equals("")){
                 numThree.setText(String.valueOf(0));
             }else if (numFour.getText().equals("")){
                 numFour.setText(String.valueOf(0));
             }else{
                 return;
             }
         }
    }
    //设置删除的时候从右往左删除
    private void deleteTextViewFromRight(){
        if (!numFour.getText().equals("")){
            numFour.setText("");
        }else if (!numThree.getText().equals("")){
            numThree.setText("");
        }else if (!numTwo.getText().equals("")){
            numTwo.setText("");
        }else if (!numOne.getText().equals("")){
            numOne.setText("");
        }else{
            return;
        }
    }

    //判断设置界面用户设置的密码与输入的密码是否对应
    private void getUserSettingPsd(){
        SharedPreferences sharedPreferences=getSharedPreferences("", Context.MODE_PRIVATE);
        password=sharedPreferences.getString("","");
        String inputPassword=numTwo.getText().toString()+numTwo.getText().toString()+numThree.getText().toString()+
                numFour.getText().toString();
        if (inputPassword.equals(password)) {
             Intent intent=getIntents();
             intent.setClass(LeaveHome.this,MainActivity.class);
             this.onDestroy();
        }else{
            numFour.setText("");
            numThree.setText("");
            numTwo.setText("");
            numOne.setText("");
            Toast.makeText(this,"密码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
        }
    }


}