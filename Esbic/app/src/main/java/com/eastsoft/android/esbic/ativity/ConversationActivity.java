package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.service.IModelService;

/**
 * Created by sofa on 2016/1/26.
 */
public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    private TextView isCalling,isConversation,time;
    private ImageButton answer;
    private ImageButton hangUp;
    private ImageButton openLock;
    private Intent intent;
    private IModelService modelService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        initData();
    }
    private void  initData(){
        isCalling=(TextView)this.findViewById(R.id.conversation_state);
        isConversation=(TextView)this.findViewById(R.id.conversation_going);
        time=(TextView)this.findViewById(R.id.conversation_time);
        answer=(ImageButton)this.findViewById(R.id.conversation_answer);
        answer.setOnClickListener(this);
        hangUp=(ImageButton)this.findViewById(R.id.conversation_hang_up);
        hangUp.setOnClickListener(this);
        openLock=(ImageButton)this.findViewById(R.id.conversation_open_lock);
        openLock.setOnClickListener(this);
        isConversation.setVisibility(View.GONE);
        intent=getIntent();
        String tmp=intent.getStringExtra("value");
        isCalling.setText(isCalling.getText().toString()+tmp);

        modelService = ((MyApplication)getApplication()).getModelService();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==answer.getId()){
            modelService.ui_talk_answer();
        }
        if (view.getId()==hangUp.getId()){
            modelService.active_hang_up();
            this.finish();
        }
        if (view.getId()==openLock.getId()){
            modelService.unlock_door();
        }
    }
}
