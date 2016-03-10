package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/18.
 */
public class SettingProjectPassWordActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView newProPasswordOne,newProPasswordTwo,newProPasswordThree,newProPasswordFour,
            newProPasswordFive,newProPasswordSix;
    private TextView confrimProPasswordOne,confrimProPasswordTwo,confrimProPasswordThree,
            confrimProPasswordFour,confrimProPasswordFive,confrimProPasswordSix;
    private GridView inputKeyboard;
    private int[] icon;
    private ImageButton back;
    private List<Map<String, Object>> mapList;
    private List<TextView> editTextList = new ArrayList<>();
    private TextView currentEditText, back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_password_alter);

        back = (ImageButton)this.findViewById(R.id.project_password_alter_back);
        back.setOnClickListener(this);
        back2 = (TextView)this.findViewById(R.id.project_password_alter_back2);
        back2.setOnClickListener(this);

        newProPasswordOne=(TextView)findViewById(R.id.project_password_num_one);
        newProPasswordTwo=(TextView)findViewById(R.id.project_password_num_two);
        newProPasswordThree=(TextView)findViewById(R.id.project_password_num_three);
        newProPasswordFour=(TextView)findViewById(R.id.project_password_num_four);
        newProPasswordFive=(TextView)findViewById(R.id.project_password_num_five);
        newProPasswordSix=(TextView)findViewById(R.id.project_password_num_six);
        confrimProPasswordOne=(TextView)findViewById(R.id.confirm_project_password_num_one);
        confrimProPasswordTwo=(TextView)findViewById(R.id.confirm_project_password_num_two);
        confrimProPasswordThree=(TextView)findViewById(R.id.confirm_project_password_num_three);
        confrimProPasswordFour=(TextView)findViewById(R.id.confirm_project_password_num_four);
        confrimProPasswordFive=(TextView)findViewById(R.id.confirm_project_password_num_five);
        confrimProPasswordSix=(TextView)findViewById(R.id.confirm_project_password_num_six);

        editTextList.add(newProPasswordOne);
        editTextList.add(newProPasswordTwo);
        editTextList.add(newProPasswordThree);
        editTextList.add(newProPasswordFour);
        editTextList.add(newProPasswordFive);
        editTextList.add(newProPasswordSix);
        editTextList.add(confrimProPasswordOne);
        editTextList.add(confrimProPasswordTwo);
        editTextList.add(confrimProPasswordThree);
        editTextList.add(confrimProPasswordFour);
        editTextList.add(confrimProPasswordFive);
        editTextList.add(confrimProPasswordSix);

        currentEditText = newProPasswordOne;
        currentEditText.setFocusable(true);

        inputKeyboard=(GridView)this.findViewById(R.id.alter_propwd_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon, "清空");
        inputKeyboard.setAdapter(inputKeyBoardAdapter);
        inputKeyboard.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == back.getId() || view.getId() == back2.getId())
        {
            playButtonMusic(musicButtonId);
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        textHowToShow(i);
    }

    //设置TextView从左往右显示
    public void textHowToShow(int position){
        if(position<9 || position==10){
            String tmp = position==10?"0":(String.valueOf(position+1));
            if (currentEditText.getText().toString().trim().compareTo("") == 0){
                currentEditText.setText(tmp);
            }else {
                int index = editTextList.indexOf(currentEditText);
                if(index != editTextList.size()-1)
                {
                    currentEditText.setFocusable(false);
                    currentEditText = editTextList.get(index + 1);
                    currentEditText.setText(tmp);
                    currentEditText.setFocusable(true);
                }
            }
            String passwordOne =newProPasswordOne.getText().toString() + newProPasswordTwo.getText().toString()
                    + newProPasswordThree.getText().toString() + newProPasswordFour.getText().toString()
                    + newProPasswordFive.getText().toString() + newProPasswordSix.getText().toString();
            String passwordTwo=confrimProPasswordOne.getText().toString()+confrimProPasswordTwo.getText().toString()
                    + confrimProPasswordThree.getText().toString()+confrimProPasswordFour.getText().toString()
                    + confrimProPasswordFive.getText().toString()+confrimProPasswordSix.getText().toString();
            if (passwordOne.equals(passwordTwo)) {
                MyApplication myApplication = (MyApplication)getApplication();
                myApplication.getModelService().setProjectPassword(passwordOne);
                showShortToast("工程密码设置成功！");
            }else{
                if(currentEditText == confrimProPasswordSix)
                {
                    showShortToast("两次密码输入不匹配，请重试！");
                    confrimProPasswordOne.setText("");
                    confrimProPasswordTwo.setText("");
                    confrimProPasswordThree.setText("");
                    confrimProPasswordFour.setText("");
                    confrimProPasswordFive.setText("");
                    confrimProPasswordSix.setText("");
                    currentEditText = confrimProPasswordOne;
                }
            }
        }else if (position==9){
            newProPasswordOne.setText("");
            newProPasswordTwo.setText("");
            newProPasswordThree.setText("");
            newProPasswordFour.setText("");
            newProPasswordFive.setText("");
            newProPasswordSix.setText("");
            confrimProPasswordOne.setText("");
            confrimProPasswordTwo.setText("");
            confrimProPasswordThree.setText("");
            confrimProPasswordFour.setText("");
            confrimProPasswordFive.setText("");
            confrimProPasswordSix.setText("");
            currentEditText = newProPasswordOne;
        }else if (position==11){
            deleteTextViewFromRight();
        }
    }

    //设置删除的时候从右往左删除
    private void deleteTextViewFromRight(){
        if (currentEditText.getText().toString().trim().compareTo("") == 0){
            int index = editTextList.indexOf(currentEditText);
            if(index > 0)
            {
                currentEditText.setFocusable(false);
                currentEditText = editTextList.get(index - 1);
                currentEditText.setText("");
                currentEditText.setFocusable(true);
            }
        }else {
            currentEditText.setText("");
        }
    }
}
