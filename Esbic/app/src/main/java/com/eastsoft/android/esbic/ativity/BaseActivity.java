package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.dialog.MyDialog;

/**
 * Created by ll on 2016/1/9.
 */
public class BaseActivity extends Activity{
    private MediaPlayer music = null;// 播放器引用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //单例化Intent
    protected Intent getIntents(){
        Intent intents=new Intent();
        if (intents==null){
            intents=new Intent();
        }else{
            return intents;
        }
        return null;
    }
    //显示Dialog
    protected void showDialogs(Context context, int id){
        MyDialog myDialog=new MyDialog(context);
        Dialog dialog=myDialog.getDialog();
        View view=myDialog.getDialogView(id);
        dialog.setContentView(view);
        Window window=dialog.getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        window.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }
    //统一显示ShortToast
    protected void showShortToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }
    //统一显示LongToast
    protected void showLongToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }
    //用于播放按键音
    protected void playMusic() {
        music = MediaPlayer.create(this,R.raw.sound1);
        music.start();

    }
}
