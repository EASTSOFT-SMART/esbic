package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.eastsoft.android.esbic.R;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by Mr Wang on 2016/2/19.
 */
public class OnCallActivity extends BaseActivity implements View.OnClickListener{
    private SurfaceView surfaceView;
    private TextView onCallAddrOne,onCallAddrTwo;
    private Button hangUp,accept,unLock;
    private ImageButton back;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_call);
    }

    private void initData(){
        surfaceView=(SurfaceView)this.findViewById(R.id.on_call_player);
        surfaceHolder=surfaceView.getHolder();
        onCallAddrOne=(TextView)this.findViewById(R.id.on_call_addr_one);
        onCallAddrTwo=(TextView)this.findViewById(R.id.on_call_addr_two);
        hangUp=(Button)this.findViewById(R.id.on_call_hang_up);
        accept=(Button)this.findViewById(R.id.on_call_accept);
        unLock=(Button)this.findViewById(R.id.on_call_unlock);
        back=(ImageButton)this.findViewById(R.id.on_call_back);
        hangUp.setOnClickListener(this);
        accept.setOnClickListener(this);
        unLock.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==accept.getId()){

        }
        if (view.getId()==accept.getId()){

        }
        if (view.getId()==hangUp.getId()){

        }
        if (view.getId()==unLock.getId()){

        }
    }
}
