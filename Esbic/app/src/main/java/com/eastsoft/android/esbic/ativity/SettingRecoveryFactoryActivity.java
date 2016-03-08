package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingRecoveryFactoryActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton back;
    private Button cancel,recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory_reset_dialog);
        initData();
    }

    private void initData(){
        back=(ImageButton)this.findViewById(R.id.factory_reset_back);
        cancel=(Button)this.findViewById(R.id.reset_cancel);
        recover=(Button)this.findViewById(R.id.reset_recovery);
        cancel.setOnClickListener(this);
        recover.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
         if (view.getId()==cancel.getId()){
             this.finish();
         }
         if (view.getId()==recover.getId()){

         }
         if (view.getId()==back.getId()){
             this.finish();
         }
    }
}
