package com.eastsoft.android.esbic.ativity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.eastsoft.android.esbic.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sofa on 2016/1/26.
 */
public class PushMessageActivity extends BaseActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{
    private ImageView pushImage;
    private TextView pushText,numberOne,numberTwo,numberThree,numberFour;
    private GridView inputKeyBoard;
    private List<Map<String,Object>> listItems;
    private String touchNumber[]=new String[]{"1","2","3","4","5","6","7","8","9","","0","删除"};
    private int[] number;
    private String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_message);
        initData();
    }
    private void initData(){
        pushImage=(ImageView)this.findViewById(R.id.push_image);
        pushText=(TextView)this.findViewById(R.id.push_text);
        numberOne=(TextView)this.findViewById(R.id.push_num_one);
        numberTwo=(TextView)this.findViewById(R.id.push_num_two);
        numberThree=(TextView)this.findViewById(R.id.push_num_three);
        numberFour=(TextView)this.findViewById(R.id.push_num_four);
        inputKeyBoard=(GridView)this.findViewById(R.id.push_input_keyboard);
        inputKeyBoard.setOnItemSelectedListener(this);
        inputKeyBoard.setOnItemClickListener(this);
        number=new int[]{1,2,3,4,5,6,7,8,9,0};
        for(int i=0;i<touchNumber.length;i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("number",touchNumber[i]);
            listItems.add(listItem);

        }
        //SimpleAdapter simpleAdapter=new SimpleAdapter(this,listItems,R.layout.leave_home_item,
        //        new String[]{"number"},new int[]{R.id.keyboard_child});
        //inputKeyBoard.setAdapter(simpleAdapter);
    }
    //设置删除的时候从右往左删除
    private void deleteTextViewFromRight(){
        if (!numberFour.getText().equals("")){
            numberFour.setText("");
        }else if (!numberThree.getText().equals("")){
            numberThree.setText("");
        }else if (!numberTwo.getText().equals("")){
            numberTwo.setText("");
        }else if (!numberOne.getText().equals("")){
            numberOne.setText("");
        }else{
            return;
        }
    }

    //判断设置界面用户设置的密码与输入的密码是否对应
    private void getUserSettingPsd(){
        String inputPassword=numberOne.getText().toString()+numberTwo.getText().toString()+numberThree.getText().toString()+
                numberFour.getText().toString();
        if (inputPassword.equals(password)) {
            this.onDestroy();
        }else{
            numberFour.setText("");
            numberThree.setText("");
            numberTwo.setText("");
            numberOne.setText("");
            Toast.makeText(this,"密码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
        }
    }
    //设置TextView从左往右显示
    public void textHowToShow(int position){
        if(position<9){
            if (numberOne.getText().equals("")){
                numberOne.setText(String.valueOf(position+1));
            }else if (numberTwo.getText().equals("")){
                numberTwo.setText(String.valueOf(position+1) );
            }else if (numberThree.getText().equals("")){
                numberThree.setText(String.valueOf(position+1));
            }else if (numberFour.getText().equals("")){
                numberFour.setText(String.valueOf(position+1));
            }else{
                return;
            }
        }else if (position==9){
            return;
        }else if (position==10){
            if (numberOne.getText().equals("")){
                numberOne.setText(String.valueOf(0));
            }else if (numberTwo.getText().equals("")){
                numberTwo.setText(String.valueOf(0) );
            }else if (numberThree.getText().equals("")){
                numberThree.setText(String.valueOf(0));
            }else if (numberFour.getText().equals("")){
                numberFour.setText(String.valueOf(0));
            }else{
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        if(!numberOne.getText().equals("")&&!numberTwo.getText().equals("")
                &&!numberThree.getText().equals("")&&!numberFour.getText().equals("")
                ){
            //getUserSettingPsd();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("GrideView的第几个子列表",String.valueOf(i));
        textHowToShow(i);
        if (i==11){
            deleteTextViewFromRight();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("GrideView的第几个子列表",String.valueOf(i));
        textHowToShow(i);
        if (i==11){
            deleteTextViewFromRight();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
