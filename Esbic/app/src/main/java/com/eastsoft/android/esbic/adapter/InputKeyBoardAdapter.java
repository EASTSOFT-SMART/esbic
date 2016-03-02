package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.ativity.BaseActivity;

/**
 * Created by Mr Wang on 2016/3/2.
 */
public class InputKeyBoardAdapter extends BaseAdapter {
    private int[] buttonIcon;
    private LayoutInflater inflater;

    public InputKeyBoardAdapter(Context context,int[] buttonIcon){
        inflater=LayoutInflater.from(context);
        this.buttonIcon=buttonIcon;
    }

    @Override
    public int getCount() {
        return 12;
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
        View v=inflater.inflate(R.layout.input_keyboard_item,null);
        Button button=(Button)v.findViewById(R.id.keyboard_child);
        if (i==11){
            button.setBackgroundResource(buttonIcon[0]);
        }else{
            if (i==0){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("1");
            }else if (i==1){
                    button.setBackgroundResource(buttonIcon[1]);
                    button.setText("2");
            }else if (i==2){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("3");
            }else if (i==3){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("4");
            }else if (i==4){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("5");
            }else if (i==5){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("6");
            }else if (i==6){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("7");
            }else if (i==7){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("8");
            }else if (i==8){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("9");
            }else if (i==9){
                button.setBackgroundResource(buttonIcon[1]);
                button.setTextSize(24);
                button.setText("删除");
            }else if (i==10){
                button.setBackgroundResource(buttonIcon[1]);
                button.setText("0");
            }
        }
        return v;
    }
}
