package com.eastsoft.android.esbic.ativity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.MessageAdapter;
import com.eastsoft.android.esbic.util.MessageUtil;

import java.util.List;

/**
 * Created by sofa on 2016/1/26.
 */
public class MessageContentActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ListView messageContentTitle;
    private ImageButton back;
    private TextView meessageTitle,messageContent;
    private MessageAdapter messageAdapter;
    private List<MessageUtil> messageUtilList;
    private Context context;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_content);
        initData();
    }
    private void initData(){
        meessageTitle=(TextView)this.findViewById(R.id.message_title);
        messageContent=(TextView)this.findViewById(R.id.message_content);
        back=(ImageButton)this.findViewById(R.id.message_con_back);
        back.setOnClickListener(this);
        messageContentTitle=(ListView)this.findViewById(R.id.message_content_item);
        messageAdapter=new MessageAdapter(messageUtilList,this);
        messageContentTitle.setAdapter(messageAdapter);

    }

    @Override
    public void onClick(View view) {
      if (view.getId()==back.getId()){
          playButtonMusic(musicButtonId);
          MessageContentActivity.this.finish();
      }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}
