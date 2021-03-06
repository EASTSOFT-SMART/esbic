package com.eastsoft.android.esbic.ativity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/3/4.
 */
public class AlarmActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private TextView numOne,numTwo,numThree,numFour;
    private GridView inputKeyBoard;
    private int[] icon;
    private ImageView alarmIcon;
    private String passWords;
    private String password="1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alarm);
        initData();
    }

    public void initData(){
        alarmIcon=(ImageView)this.findViewById(R.id.alarm_icon);
        numOne=(TextView) this.findViewById(R.id.alarm_num_one);
        numTwo=(TextView) this.findViewById(R.id.alarm_num_two);
        numThree=(TextView) this.findViewById(R.id.alarm_num_three);
        numFour=(TextView) this.findViewById(R.id.alarm_num_four);
        inputKeyBoard=(GridView) this.findViewById(R.id.alarm_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon,"确认");
        inputKeyBoard.setAdapter(inputKeyBoardAdapter);
        inputKeyBoard.setOnItemClickListener(this);

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
                this.finish();
            }else{
                showShortToast("密码错误，请重试！");
                numOne.setText("");
                numTwo.setText("");
                numFour.setText("");
                numThree.setText("");
            }
        }
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



}
