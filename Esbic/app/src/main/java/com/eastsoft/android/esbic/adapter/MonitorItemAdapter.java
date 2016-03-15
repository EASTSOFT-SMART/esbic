package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.ativity.StandByActivity;

import java.util.List;

/**
 * Created by Mr Wang on 2016/2/23.
 */
public class MonitorItemAdapter extends BaseAdapter
{
    private String[] placeName;
    private LayoutInflater inflater;
    private boolean[] state;
    private boolean[] isStart;
    private LinearLayout backgroud;
    private Chronometer chronometer;
    private TextView hangState;

    public MonitorItemAdapter(String[] placeNameList, boolean[] state, boolean[] isStart, Context context)
    {
        this.placeName = placeNameList;
        this.inflater = LayoutInflater.from(context);
        this.state = state;
        this.isStart = isStart;
    }

    @Override
    public int getCount()
    {
        return 8;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        view = inflater.inflate(R.layout.monitor_exchange, null);
        backgroud = (LinearLayout) view.findViewById(R.id.monitor_background);
        hangState = (TextView) view.findViewById(R.id.monitor_state);
        chronometer = (Chronometer) view.findViewById(R.id.monitor_time);
        TextView place = (TextView) view.findViewById(R.id.monitor_place);
        place.setText(placeName[i]);
        if (i == 0 && state[0])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup);
            hangState.setText("挂断");
            if(isStart[0] == true)
            {
                chronometer.start();
            }
        } else if (i == 0)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display);
            hangState.setText("监视");
            chronometer.stop();
        }

        if (i == 1 && state[1])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup_gray);
            hangState.setText("挂断");
            if(isStart[1] == true)
            {
                chronometer.start();
            }
        } else if (i == 1)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display_gray);
            hangState.setText("监视");
            chronometer.stop();
        }

        if (i == 2 && state[2])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup_gray);
            hangState.setText("挂断");
            if(isStart[2] == true)
            {
                chronometer.start();
            }
        } else if (i == 2)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display_gray);
            hangState.setText("监视");
            chronometer.stop();
        }


        if (i == 3 && state[3])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup);
            hangState.setText("挂断");
            if(isStart[3] == true)
            {
                chronometer.start();
            }
        } else if (i == 3)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display);
            chronometer.stop();
        }


        if (i == 4 && state[4])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup);
            hangState.setText("挂断");
            if(isStart[4] == true)
            {
                chronometer.start();
            }
        } else if (i == 4)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display);
            chronometer.stop();
        }


        if (i == 5 && state[5])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup_gray);
            hangState.setText("挂断");
            if(isStart[5] == true)
            {
                chronometer.start();
            }
        } else if (i == 5)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display_gray);
            chronometer.stop();
        }


        if (i == 6 && state[6])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup_gray);
            hangState.setText("挂断");
            if(isStart[6] == true)
            {
                chronometer.start();
            }
        } else if (i == 6)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display_gray);
            chronometer.stop();
        }


        if (i == 7 && state[7])
        {
            backgroud.setBackgroundResource(R.drawable.monitor_hangup);
            hangState.setText("挂断");
            if(isStart[7] == true)
            {
                chronometer.start();
            }
        } else if (i == 7)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display);
            chronometer.stop();
        }

        return view;
    }

    public void hangUpAll(int i)
    {
        if (i == 3 || i == 4 || i == 7)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display);
            hangState.setText("监视");
        } else if (i == 1 || i == 2 || i == 5 || i == 6)
        {
            backgroud.setBackgroundResource(R.drawable.monitor_display_gray);
            hangState.setText("监视");
        }
    }
}
