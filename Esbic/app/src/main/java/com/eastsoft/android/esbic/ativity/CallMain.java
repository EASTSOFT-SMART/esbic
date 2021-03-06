package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.telecom.Call;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.CallOtherRecordAdapter;
import com.eastsoft.android.esbic.adapter.CallRecordAdapter;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;
import com.eastsoft.android.esbic.dialog.MyDialog;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.IntercomTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.table.IntercomInfo;
import com.eastsoft.android.esbic.util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sofa on 2016/1/25.
 */
public class CallMain extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{
    private ImageButton back;
    private Button history,help,call;
    private TextView phoneNumber, back2;
    private GridView inputBoard;
    private ListView historyList;
    private String list[];
    private List<Map<String,Object>> mapList;
    private Intent intent;
    private InputKeyBoardAdapter inputKeyBoardAdapter;
    private LinearLayout linearLayout;
    private String number="";
    private int[] icon;
    private IModelService modelService;
    private List<IntercomInfo> intercomInfos = new ArrayList<>();
    private CallOtherRecordAdapter callOtherRecordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_main);
        initData();
    }
    private void initData(){
        back=(ImageButton)this.findViewById(R.id.back);
        back2=(TextView) this.findViewById(R.id.back2);
        call=(Button) this.findViewById(R.id.call);
        history=(Button) this.findViewById(R.id.hository);
        help=(Button) this.findViewById(R.id.help);
        linearLayout=(LinearLayout)this.findViewById(R.id.control_help);
        phoneNumber=(TextView)this.findViewById(R.id.tel_number);
        phoneNumber.setText("");
        inputBoard=(GridView)this.findViewById(R.id.input_board);
        historyList=(ListView)this.findViewById(R.id.hostory_list);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
        call.setOnClickListener(this);
        history.setOnClickListener(this);
        help.setOnClickListener(this);
        //historyList.setAdapter();
        initAdapter();
        inputBoard.setAdapter(inputKeyBoardAdapter);
        inputBoard.setOnItemClickListener(this);
        inputBoard.setOnItemSelectedListener(this);

        modelService = ((MyApplication)getApplication()).getModelService();
        List<IntercomInfo> infos = modelService.getIntecomInfo();
        for(IntercomInfo intercomInfo : infos)
        {
            if(intercomInfo.getType() == IntercomTypeEnum.CALL_ROOM.getType())
            {
                intercomInfos.add(intercomInfo);
            }
        }
        callOtherRecordAdapter=new CallOtherRecordAdapter(intercomInfos, CallMain.this);
        historyList.setAdapter(callOtherRecordAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()||view.getId()==back2.getId()){
            playButtonMusic(musicButtonId);
            this.finish();
        }
        if (view.getId()==call.getId()){
            String callNumber=phoneNumber.getText().toString();
            playButtonMusic(musicButtonId);
            if (!callNumber.equals("")){
                if(callNumber.length() != 4 && callNumber.length() != 8)
                {
                    showLongToast("输入的号码格式错误，请查看操作说明！");
                    return;
                }
                if(callNumber.length() == 4)
                {
                    DeviceInfo info = ((MyApplication)getApplication()).getModelService().getDeviceInfo();
                    if(info == null)
                    {
                        showLongToast("请先设置本机的设备信息！");
                        return;
                    }
                }
                intent=getIntents();
                intent.setClass(CallMain.this,ConversationActivity.class);
                intent.putExtra("roomNumber", phoneNumber.getText());
                startActivity(intent);
                this.finish();
            }else{
                Toast.makeText(CallMain.this,"输入的号码不能为空！",Toast.LENGTH_SHORT).show();
                return;
            }

        }
        if (view.getId()==history.getId()){
             playButtonMusic(musicButtonId);
             linearLayout.setVisibility(View.GONE);
             historyList.setVisibility(View.VISIBLE);

        }
        if (view.getId()==help.getId()){
            playButtonMusic(musicButtonId);
            linearLayout.setVisibility(View.VISIBLE);
            historyList.setVisibility(View.GONE);
        }

    }
   //初始化Adapter
    public void initAdapter(){
        //SimpleAdapter simpleAdapter=new SimpleAdapter();
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon,"重拨");

    }

   //private void showHelpDialog(){
   //    MyDialog myDialog=new MyDialog(CallMain.this);
   //    final  Dialog dialog=myDialog.getDialog();
   //    View view=myDialog.getDialogView(R.layout.call_main_help);
   //    dialog.setContentView(view);
   //    Window  dialogWindow=dialog.getWindow();
   //    WindowManager.LayoutParams lp=dialogWindow.getAttributes();
   //    dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
   //    lp.width=ViewGroup.LayoutParams.WRAP_CONTENT;
   //    lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;
   //    dialogWindow.setAttributes(lp);
   //    dialog.show();
   //}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playNumberSingByNumber(i);
        if (i<9){
             number=phoneNumber.getText().toString();
             number=number+String.valueOf(i+1);
             phoneNumber.setText(number);
        }
        if (i==9){
            if(intercomInfos.size() == 0)
            {
                showLongToast("没有历史记录！");
                return;
            }
            IntercomInfo intercomInfo = intercomInfos.get(0);
            DeviceInfo deviceInfo = JsonUtil.fromJson( intercomInfo.getDevice(), DeviceInfo.class);
            intent=getIntents();
            intent.setClass(CallMain.this, ConversationActivity.class);
            intent.putExtra("roomNumber",String.format("%02d", deviceInfo.getBuilding_no())
                    + String.format("%02d", deviceInfo.getUnit_no())
                    + String.format("%02d", deviceInfo.getLayer_no())
                    + String.format("%02d", deviceInfo.getRoom_no()));
            startActivity(intent);
            this.finish();
        }
        if (i==10){
            number=phoneNumber.getText().toString();
            number+=String.valueOf(0);
            phoneNumber.setText(number);
        }
        if (i==11){
            number=phoneNumber.getText().toString();
            int numLength=number.length();
            if (numLength>=2){
                String num=number.substring(0,numLength-1);
                phoneNumber.setText(num);
            }else{
                phoneNumber.setText("");
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {playNumberSingByNumber(position);
        if (position<9){
            number=phoneNumber.getText().toString();
            number=number+String.valueOf(position+1);
            phoneNumber.setText(number);
        }
        if (position==9){
            phoneNumber.setText("");
        }
        if (position==10){
            number=phoneNumber.getText().toString();
            number+=String.valueOf(0);
            phoneNumber.setText(number);
        }
        if (position==11){
            number=phoneNumber.getText().toString();
            int numLength=number.length();
            if (numLength>=2){
                String num=number.substring(0,numLength-1);
                phoneNumber.setText(num);
            }else{
                phoneNumber.setText("");
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
