package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.eastsoft.android.esbic.R;
import java.util.List;

/**
 * Created by Mr Wang on 2016/2/23.
 */
public class MonitorItemAdapter extends BaseAdapter {
    private List<String> placeNameList;
    private LayoutInflater inflater;
    private boolean state;

    public MonitorItemAdapter(List<String> placeNameList, Context context){
        this.placeNameList=placeNameList;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.monitor_exchange,null);
        LinearLayout backgroud=(LinearLayout)view.findViewById(R.id.monitor_background);
        TextView state=(TextView)view.findViewById(R.id.monitor_state);
        Chronometer chronometer=(Chronometer)view.findViewById(R.id.monitor_time);
        TextView place=(TextView)view.findViewById(R.id.monitor_place);
        if (i==0){
            backgroud.setBackgroundResource(R.drawable.monitor_hangup);
            state.setText("挂断");
        }
        if (i==3||i==4||i==7){
            backgroud.setBackgroundResource(R.drawable.monitor_display);
        }
        if (i==1||i==2||i==5||i==6){
            backgroud.setBackgroundResource(R.drawable.monitor_display_gray);
        }
        return view;
    }

    public void initState(boolean state){
       this.state=state;
    }

    public void changeState(){
        if (state){
            if(true){

            }
        }
    }

    public void recoverState(){

    }
}
