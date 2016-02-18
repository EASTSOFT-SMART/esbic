package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.eastsoft.android.esbic.dialog.MyDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sofa on 2016/1/25.
 */
public class CallMain extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ImageButton back,call;
    private ImageButton history,help;
    private TextView phoneNumber;
    private GridView inputBoard;
    private ListView historyList;
    private String list[];
    private List<Map<String,Object>> mapList;
    private Intent intent;
    private SimpleAdapter simpleAdapter,simpleAdapterTwo;
    private LinearLayout linearLayout;
    private String number="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_main);
        initData();
    }
    private void initData(){
         back=(ImageButton)this.findViewById(R.id.back);
         call=(ImageButton)this.findViewById(R.id.call);
         history=(ImageButton)this.findViewById(R.id.hository);
         help=(ImageButton)this.findViewById(R.id.help);
         linearLayout=(LinearLayout)this.findViewById(R.id.control_help);
         phoneNumber=(TextView)this.findViewById(R.id.tel_number);
         inputBoard=(GridView)this.findViewById(R.id.input_board);
         historyList=(ListView)this.findViewById(R.id.hostory_list);
         back.setOnClickListener(this);
         call.setOnClickListener(this);
         history.setOnClickListener(this);
         help.setOnClickListener(this);
         //historyList.setAdapter();
         initAdapter();
         inputBoard.setAdapter(simpleAdapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()){
            this.finish();
        }
        if (view.getId()==call.getId()){
            if (!phoneNumber.equals("")){
                intent=getIntents();
                intent.setClass(CallMain.this,ConversationActivity.class);
                intent.putExtra("roomNumber", phoneNumber.getText());
                startActivityForResult(intent,1);
            }else{
                Toast.makeText(CallMain.this,"输入的号码不能为空",Toast.LENGTH_SHORT).show();
            }

        }
        if (view.getId()==history.getId()){
             linearLayout.setVisibility(View.GONE);
             historyList.setVisibility(View.VISIBLE);

        }
        if (view.getId()==help.getId()){
            linearLayout.setVisibility(View.VISIBLE);
            historyList.setVisibility(View.GONE);
        }

    }
   //初始化Adapter
    public void initAdapter(){
        //SimpleAdapter simpleAdapter=new SimpleAdapter();
        list=new String[]{"1","2","3","4","5","6","7","8","9","重播","0","删除"};
        mapList=new ArrayList<Map<String,Object>>();
        for (int i=0;i<list.length;i++){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("number",list[i]);
            mapList.add(map);
        }
       simpleAdapter=new SimpleAdapter(this,mapList,R.layout.call_main_item,new String[]{
              "number"},new int[]{R.id.input_kaybord_call_main});

    }


   private void showHelpDialog(){
       MyDialog myDialog=new MyDialog(CallMain.this);
       final  Dialog dialog=myDialog.getDialog();
       View view=myDialog.getDialogView(R.layout.call_main_help);
       dialog.setContentView(view);
       Window  dialogWindow=dialog.getWindow();
       WindowManager.LayoutParams lp=dialogWindow.getAttributes();
       dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
       lp.width=ViewGroup.LayoutParams.WRAP_CONTENT;
       lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;
       dialogWindow.setAttributes(lp);
       dialog.show();
   }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         if (i<9){
             number=phoneNumber.getText().toString();
             number+=String.valueOf(i+1);
             phoneNumber.setText(number);
         }
        if (i==9){
            phoneNumber.setText("");
        }
        if (i==10){
            number=phoneNumber.getText().toString();
            number+=String.valueOf(0);
            phoneNumber.setText(number);
        }
        if (i==12){
            number=phoneNumber.getText().toString();
            int numLength=number.length();
            if (numLength>=2){
                String num=number.substring(numLength-2,numLength-1);
                phoneNumber.setText(num);
            }else{
                phoneNumber.setText("");
            }

        }
    }




}