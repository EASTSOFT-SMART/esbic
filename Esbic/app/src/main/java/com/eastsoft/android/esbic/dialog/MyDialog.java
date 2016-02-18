package com.eastsoft.android.esbic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/26.
 */
public class MyDialog {
    private Context context;

    public  MyDialog(Context context){
        this.context=context;
    }

    public Dialog getDialog(){
        //new Dialog(context,样式);
        final Dialog dialog=new Dialog(context, R.style.dialog);
        return dialog;
    }

    public View getDialogView(int layout){
        View view= LayoutInflater.from(context).inflate(layout,null);
        return view;
    }
}
