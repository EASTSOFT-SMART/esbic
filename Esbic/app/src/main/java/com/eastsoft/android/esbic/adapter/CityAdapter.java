package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import java.util.List;

public class CityAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<String> infos;
    private boolean[] status;
    private TextView city_name;

    public CityAdapter(List<String> infos, boolean[] status, Context context)
    {
        this.infos = infos;
        this.status = status;
        this.context = context;
    }

    @Override
    public int getCount()
    {
       return infos.size();
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
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.city, null);
        city_name = (TextView) view.findViewById(R.id.city_name);
        city_name.setText(infos.get(i));
        if(status[i] == true)
        {
            city_name.setTextColor(Color.BLACK);
        }
        return view;
    }
}