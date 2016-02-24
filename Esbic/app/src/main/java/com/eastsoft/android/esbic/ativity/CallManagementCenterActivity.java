package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/25.
 */
public class CallManagementCenterActivity extends BaseActivity implements View.OnClickListener {
    private TextView isCallingCenter,isTalking;
    private Button hangUp;
    private Chronometer timer;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_management_center);
        initData();

    }
    private void initData(){
        isCallingCenter=(TextView) this.findViewById(R.id.is_calling_center);
        timer=(Chronometer) this.findViewById(R.id.timer);
        hangUp=(Button)this.findViewById(R.id.hang_up);
        back=(ImageButton)this.findViewById(R.id.call_center_management_back);
        hangUp.setOnClickListener(this);
        back.setOnClickListener(this);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==hangUp.getId()){
            playButtonMusic(musicButtonId);
            this.finish();
        }
        if (view.getId()==back.getId()){
            playButtonMusic(musicButtonId);
            this.finish();
        }

    }
}
