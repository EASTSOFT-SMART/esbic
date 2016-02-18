package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/25.
 */
public class CallManagementCenterActivity extends Activity {
    private TextView isCallingCenter,isTalking,time;
    private Button hangUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_management_center);
        initData();

    }
    private void initData(){
        isCallingCenter=(TextView) this.findViewById(R.id.is_calling_center);
        time=(TextView) this.findViewById(R.id.time);
        //hangUp=(Button)this.findViewById(R.id.hang_up);
        //hangUp.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View view) {

         //   }
        //});
    }
}
