package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SecurityAlterUserPwdActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView newUserPassword,confrimUserPassword;
    private GridView inputKeyboard;
    private int[] icon;
    private List<Map<String, Object>> mapList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_password_alter);
        newUserPassword=(TextView)this.findViewById(R.id.new_user_password);
        confrimUserPassword=(TextView)this.findViewById(R.id.confirm_new_user_password);
        inputKeyboard=(GridView)this.findViewById(R.id.alter_psd_input_keyboard);
        icon=new int[]{R.drawable.input_keyboard_zero_button};
        mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("numButton", icon[0]);
            mapList.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, mapList, R.layout.input_keyboard_item, new String[]
                {"numButton"}, new int[]{R.id.keyboard_child});
        inputKeyboard.setAdapter(simpleAdapter);
        inputKeyboard.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i<9){
            String num=newUserPassword.getText().toString();
            if (num.length()<4){
                newUserPassword.setText(num+String.valueOf(i+1));
            }else{
                String numTwo=confrimUserPassword.getText().toString();
                if (numTwo.length()<4){
                    confrimUserPassword.setText(numTwo+String.valueOf(i+1));
                }
            }
        }else if (i==10){
            String num=newUserPassword.getText().toString();
            if (num.length()<4){
                newUserPassword.setText(num+String.valueOf(0));
            }else{
                String numTwo=confrimUserPassword.getText().toString();
                if (numTwo.length()<4){
                    confrimUserPassword.setText(numTwo+String.valueOf(0));
                }
            }
        }else if (i==11){
            String num=newUserPassword.getText().toString();
            if (num.length()<=4){
                if (num.length()==4){
                    newUserPassword.setText(num.substring(0,2));
                }else if (num.length()==3){
                    newUserPassword.setText(num.substring(0,1));
                }
                else if (num.length()==2){
                    newUserPassword.setText(num.substring(0));
                }else if (num.length()==1){
                    newUserPassword.setText("");
                }
            }else{
                String numTwo=confrimUserPassword.getText().toString();
                if (numTwo.length()==4){
                    confrimUserPassword.setText(num.substring(0,2));
                }else if (num.length()==3){
                    confrimUserPassword.setText(num.substring(0,1));
                }
                else if (num.length()==2){
                    confrimUserPassword.setText(num.substring(0));
                }else if (num.length()==1){
                    confrimUserPassword.setText("");
                }
            }
        }else if (i==9){
            String num=newUserPassword.getText().toString();
            if (num.length()<=4){
               newUserPassword.setText("");
            }else{
               confrimUserPassword.setText("");
            }
        }
        String numOne=newUserPassword.getText().toString();
        String numTwo=confrimUserPassword.getText().toString();
        if (numOne.length()==4&&numTwo.length()==4){
            if (numOne.equals(numTwo)){
                MyApplication myApplication = (MyApplication)getApplication();
                myApplication.getModelService().setUserPassword(numOne);
                showShortToast("设置成功");
            }else{
                showShortToast("两次密码输入不匹配，请重新输入");
                confrimUserPassword.setText("");
            }
        }
    }

}
