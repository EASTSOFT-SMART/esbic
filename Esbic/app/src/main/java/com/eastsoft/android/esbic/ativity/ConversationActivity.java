package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/26.
 */
public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    private TextView isCalling,isConversation,time;
    private ImageButton hangUp;
    private Intent intent;
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
        hangUp=(ImageButton)this.findViewById(R.id.conversation_hang_up);
        hangUp.setOnClickListener(this);
        isConversation.setVisibility(View.GONE);
        intent=getIntent();
        String roomNumber=intent.getStringExtra("roomNumber");
        isCalling.setText(isCalling.getText().toString()+roomNumber);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==hangUp.getId()){
            this.finish();
        }


    }
}
