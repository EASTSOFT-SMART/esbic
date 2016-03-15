package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;
import com.eastsoft.android.esbic.util.TimeUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingDateActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView dateYear,dateMonth,dateDay,dateHour,dateMinute,dateSecond, back2;
    private GridView inputKeyBoard;
    private int[] icon;
    private SimpleDateFormat simpleDateFormat;
    private int position=0;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_date_set);
        initData();
    }

    private void initData(){
        dateYear=(TextView)this.findViewById(R.id.date_year);
        dateMonth=(TextView)this.findViewById(R.id.date_month);
        dateDay=(TextView)this.findViewById(R.id.date_day);
        dateHour=(TextView)this.findViewById(R.id.date_hour);
        dateMinute=(TextView)this.findViewById(R.id.date_minute);
        dateSecond=(TextView)this.findViewById(R.id.date_second);
        inputKeyBoard=(GridView)this.findViewById(R.id.setting_date_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon,"确认");
        inputKeyBoard.setAdapter(inputKeyBoardAdapter);
        inputKeyBoard.setOnItemClickListener(this);
        simpleDateFormat=new SimpleDateFormat("yyMMdd");

        String time = TimeUtil.getDateTimeofNow4(); // yyyyMMddHHmmss
        dateYear.setText(time.substring(0,4));
        dateMonth.setText(time.substring(4,6));
        dateDay.setText(time.substring(6,8));
        dateHour.setText(time.substring(8,10));
        dateMinute.setText(time.substring(10,12));
        dateSecond.setText(time.substring(12));

        back = (ImageButton)this.findViewById(R.id.date_setting_back);
        back.setOnClickListener(this);
        back2 = (TextView) this.findViewById(R.id.date_setting_back2);
        back2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        playMusic();
        if (view.getId() == back.getId() || view.getId() == back2.getId())
        {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playMusic();
        textHowToShow(i);
    }

    //设置TextView从左往右显示
    public void textHowToShow(int position){
        if(position<9){
            if (dateYear.getText().equals("")||dateYear.getText().length()<4){
                String year=dateYear.getText().toString();
                dateYear.setText(year+String.valueOf(position+1));
            }else if (dateMonth.getText().equals("")||dateMonth.getText().length()<2){
                String month=dateMonth.getText().toString();
                dateMonth.setText(month+String.valueOf(position+1) );
            }else if (dateDay.getText().equals("")||dateDay.getText().length()<2){
                String day=dateDay.getText().toString();
                dateDay.setText(day+String.valueOf(position+1));
            }else{
                if (dateHour.getText().equals("")||dateHour.getText().length()<2){
                    String hour=dateHour.getText().toString();
                    dateHour.setText(hour+String.valueOf(position+1));
                }else if (dateMinute.getText().equals("")||dateMinute.getText().length()<2){
                    String minute=dateMinute.getText().toString();
                    dateMinute.setText(minute+String.valueOf(position+1));
                }else if (dateSecond.getText().equals("")||dateSecond.getText().length()<2){
                    String second=dateSecond.getText().toString();
                    dateSecond.setText(second+String.valueOf(position+1));
                }else{
                    return;
                }
            }
        }else if (position==9){
            String timeString =dateYear.getText().toString() +dateMonth.getText().toString()
                    +dateDay.getText().toString() +"."+dateHour.getText().toString()+dateMinute.getText().toString()
                    +dateSecond.getText().toString();
            setDate(timeString);
        }else if (position==10){
            if (dateYear.getText().equals("")||dateYear.getText().length()<4){
                String year=dateYear.getText().toString();
                dateYear.setText(year+String.valueOf(0));
            }else if (dateMonth.getText().equals("")||dateMonth.getText().length()<2){
                String month=dateMonth.getText().toString();
                dateMonth.setText(month+String.valueOf(0) );
            }else if (dateDay.getText().equals("")||dateMonth.getText().length()<2){
                String day=dateDay.getText().toString();
                dateDay.setText(day+String.valueOf(0));
            }else{
                if (dateHour.getText().equals("")||dateHour.getText().length()<2){
                    String hour=dateHour.getText().toString();
                    dateHour.setText(hour+String.valueOf(0));
                }else if (dateMinute.getText().equals("")||dateMinute.getText().length()<2){
                    String minute=dateMinute.getText().toString();
                    dateMinute.setText(minute+String.valueOf(0));
                }else if (dateSecond.getText().equals("")||dateSecond.getText().length()<2){
                    String second=dateSecond.getText().toString();
                    dateSecond.setText(second+String.valueOf(0));
                }
            }
        }else if (position==11){
            deleteTextViewFromRight();
        }
    }

    //设置删除的时候从右往左删除
    private void deleteTextViewFromRight(){
        if (!dateSecond.getText().equals("")){
            dateSecond.setText("");
        }else if (!dateMinute.getText().equals("")){
            dateMinute.setText("");
        }else if (!dateHour.getText().equals("")){
            dateHour.setText("");
        }else {
            if (!dateDay.getText().equals("")) {
                dateDay.setText("");
            } else if (!dateMonth.getText().equals("")) {
                dateMonth.setText("");
            } else if (!dateMonth.getText().equals("")) {
                dateMonth.setText("");
            } else if (!dateYear.getText().equals("")) {
                dateYear.setText("");
            } else {
                return;
            }
        }
    }

    public void setDate(String date){
        try {
            // 修改时间需要获取root权限
            Process process = Runtime.getRuntime().exec("su");
//            String datetime="20131023.112800"; //测试的设置的时间【时间格式 yyyyMMdd.HHmmss】
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("setprop persist.sys.timezone GMT\n");
            os.writeBytes("/system/bin/date -s "+date+"\n");
            os.writeBytes("clock -w\n");
            os.writeBytes("exit\n");
            os.flush();
            showLongToast("设置成功！");
        } catch (IOException e) {
            e.printStackTrace();
            showLongToast("设置失败！");
        }
    }
}
