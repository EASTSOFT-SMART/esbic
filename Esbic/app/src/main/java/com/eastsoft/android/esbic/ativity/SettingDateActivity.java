package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingDateActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView yearMonthDay,hourMinuteSecond;
    private GridView inputKeyBoard;
    private List<Map<String,Object>> mapList;
    private int[] touchNumber = new int[]{R.drawable.input_keyboard_zero_button};
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_date_set);
    }

    private void initData(){
        yearMonthDay=(TextView)this.findViewById(R.id.date_ymd);
        hourMinuteSecond=(TextView)this.findViewById(R.id.date_hms);
        inputKeyBoard=(GridView)this.findViewById(R.id.setting_date_input_keyboard);
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
        simpleDateFormat=new SimpleDateFormat("yyMMdd");
        date=new Date();
        yearMonthDay.setText(simpleDateFormat.format(date));
        hourMinuteSecond.setText(new SimpleDateFormat("HHmmss").format(date));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        position+=1;
        if (position==1){
            yearMonthDay.setText("");
        }
        if (yearMonthDay.getText().toString().length()==8){
            hourMinuteSecond.setText("");
        }
        if (i<9){
            String num=yearMonthDay.getText().toString();
            if (num.length()<8){
                yearMonthDay.setText(num+String.valueOf(i+1));
            }else{
                String numTwo=hourMinuteSecond.getText().toString();
                if (numTwo.length()<6){
                    hourMinuteSecond.setText(numTwo+String.valueOf(i+1));
                }
            }
        }else if (i==10){
            String num=yearMonthDay.getText().toString();
            if (num.length()<8){
                yearMonthDay.setText(num+String.valueOf(0));
            }else{
                String numTwo=hourMinuteSecond.getText().toString();
                if (numTwo.length()<6){
                    hourMinuteSecond.setText(numTwo+String.valueOf(0));
                }
            }
        }else if (i==11){
            String num=yearMonthDay.getText().toString();
            if (num.length()<=8){
                if (num.length()==8){
                    yearMonthDay.setText(num.substring(0,6));
                }else if (num.length()==7){
                    yearMonthDay.setText(num.substring(0,5));
                }
                else if (num.length()==6){
                    yearMonthDay.setText(num.substring(0,4));
                }else if (num.length()==5){
                    yearMonthDay.setText(num.substring(0,3));
                }else if (num.length()==4) {
                    yearMonthDay.setText(num.substring(0, 2));
                }else if (num.length()==3) {
                    yearMonthDay.setText(num.substring(0, 1));
                }else if (num.length()==2) {
                    yearMonthDay.setText(num.substring(0));
                }else if (num.length()==1) {
                    yearMonthDay.setText("");
                }
            }else{
                String numTwo=hourMinuteSecond.getText().toString();
                if (numTwo.length()==6){
                    hourMinuteSecond.setText(num.substring(0,4));
                }else if (num.length()==5){
                    hourMinuteSecond.setText(num.substring(0,3));
                }else if (num.length()==4){
                    hourMinuteSecond.setText(num.substring(0,2));
                }else if (num.length()==3){
                    hourMinuteSecond.setText(num.substring(0,1));
                }else if (num.length()==2) {
                    hourMinuteSecond.setText(num.substring(0));
                }else if (num.length()==4) {
                    hourMinuteSecond.setText("");
                }
            }
        }else if (i==9){
            String num=yearMonthDay.getText().toString()+hourMinuteSecond.getText().toString();
            if (num.length()<=8){
                yearMonthDay.setText("");
            }else{
                hourMinuteSecond.setText("");
            }
        }
        String numOne=yearMonthDay.getText().toString();
        String numTwo=hourMinuteSecond.getText().toString();
        if (numOne.length()==8&&numTwo.length()==6){
            if (numOne.equals(numTwo)){

            }else{
                showShortToast("两次密码输入不匹配，请重新输入");
                yearMonthDay.setText("");
                hourMinuteSecond.setText("");
            }
        }
    }


}
