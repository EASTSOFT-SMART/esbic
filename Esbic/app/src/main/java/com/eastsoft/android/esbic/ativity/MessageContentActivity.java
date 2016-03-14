package com.eastsoft.android.esbic.ativity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.DateSorter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.MessageAdapter;
import com.eastsoft.android.esbic.jni.MessageInfoEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.table.MessageInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofa on 2016/1/26.
 */
public class MessageContentActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ListView messageListView;
    private ImageButton back;
    private TextView messageTitle,messageContent, back2, message_unread_count;
    private MessageAdapter messageAdapter;
    private List<MessageInfo> messageInfos = new ArrayList<>();
    private Context context;
    private Intent intent;
    private IModelService modelService;
    private int unReadMsgCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_content);
        initData();
    }
    private void initData(){
        modelService = ((MyApplication)getApplication()).getModelService();
        messageInfos = modelService.getMessageInfo();

        message_unread_count = (TextView)findViewById(R.id.message_unread);
        messageTitle =(TextView)this.findViewById(R.id.message_title);
        messageContent=(TextView)this.findViewById(R.id.message_content);
        if(messageInfos.size() > 0)
        {
            messageTitle.setText((messageInfos.get(0).getType()==0?"消息":"广告")+"详情");
            messageContent.setText(messageInfos.get(0).getMessage());
        }

        back=(ImageButton)this.findViewById(R.id.message_con_back);
        back.setOnClickListener(this);
        back2 = (TextView)this.findViewById(R.id.message_con_back2);
        back2.setOnClickListener(this);

        unReadMsgCount = 0;
        for(MessageInfo info : messageInfos)
        {
            if(info.isRead())
            {
                continue;
            }
            unReadMsgCount++;
        }
        message_unread_count.setText("您有"+unReadMsgCount+"条未读消息");
        messageListView =(ListView)this.findViewById(R.id.message_content_item);
        messageAdapter=new MessageAdapter(messageInfos,this);
        messageListView.setAdapter(messageAdapter);
        messageListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (view.getId()==back.getId() || view.getId() == back2.getId())
      {
          playButtonMusic(musicButtonId);
          MessageContentActivity.this.finish();
      }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MessageInfo messageInfo = messageInfos.get(i);
        messageTitle.setText((messageInfo.getType()==0?"消息":"广告")+"详情");
        messageContent.setText(messageInfo.getMessage());
        if (!messageInfo.isRead()){
            messageInfo.setRead(true);
            ContentValues values = new ContentValues();
            values.put("isRead", true);
            DataSupport.update(MessageInfo.class, values, messageInfo.getId());
            unReadMsgCount--;
            message_unread_count.setText("您有"+unReadMsgCount+"条未读消息");
        }
       messageAdapter.notifyDataSetChanged();

    }

}
