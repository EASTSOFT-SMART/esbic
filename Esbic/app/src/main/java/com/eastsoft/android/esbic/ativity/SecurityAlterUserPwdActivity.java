package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SecurityAlterUserPwdActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText newUserPasswordOne,newUserPasswordTwo,newUserPasswordThree,newUserPasswordFour;
    private EditText confrimUserPasswordOne,confrimUserPasswordTwo,confrimUserPasswordThree,confrimUserPasswordFour;
    private GridView inputKeyboard;
    private int[] icon;
    private ImageButton back;
    private TextView back2;

    private List<EditText> editTextList = new ArrayList<>();
    private EditText currentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_password_alter);

        back = (ImageButton)this.findViewById(R.id.user_password_alter_back);
        back.setOnClickListener(this);
        back2 = (TextView) this.findViewById(R.id.user_password_alter_back2);
        back2.setOnClickListener(this);

        newUserPasswordOne=(EditText)this.findViewById(R.id.user_password_num_one);
        newUserPasswordOne.setOnFocusChangeListener(this);
        newUserPasswordTwo=(EditText)this.findViewById(R.id.user_password_num_two);
        newUserPasswordTwo.setOnFocusChangeListener(this);
        newUserPasswordThree=(EditText)this.findViewById(R.id.user_password_num_three);
        newUserPasswordThree.setOnFocusChangeListener(this);
        newUserPasswordFour=(EditText)this.findViewById(R.id.user_password_num_four);
        newUserPasswordFour.setOnFocusChangeListener(this);
        confrimUserPasswordOne=(EditText)this.findViewById(R.id.confirm_user_password_num_one);
        confrimUserPasswordOne.setOnFocusChangeListener(this);
        confrimUserPasswordTwo=(EditText)this.findViewById(R.id.confirm_user_password_num_two);
        confrimUserPasswordTwo.setOnFocusChangeListener(this);
        confrimUserPasswordThree=(EditText)this.findViewById(R.id.confirm_user_password_num_three);
        confrimUserPasswordThree.setOnFocusChangeListener(this);
        confrimUserPasswordFour=(EditText)this.findViewById(R.id.confirm_user_password_num_four);
        confrimUserPasswordFour.setOnFocusChangeListener(this);

        editTextList.add(newUserPasswordOne);
        editTextList.add(newUserPasswordTwo);
        editTextList.add(newUserPasswordThree);
        editTextList.add(newUserPasswordFour);
        editTextList.add(confrimUserPasswordOne);
        editTextList.add(confrimUserPasswordTwo);
        editTextList.add(confrimUserPasswordThree);
        editTextList.add(confrimUserPasswordFour);

        currentEditText = newUserPasswordOne;
        currentEditText.setFocusable(true);

        inputKeyboard=(GridView)this.findViewById(R.id.alter_userpsd_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon, "清空");
        inputKeyboard.setAdapter(inputKeyBoardAdapter);
        inputKeyboard.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playMusic();
        textHowToShow(i);
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
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus == false)
        {
            return;
        }
        if (view.getId() == R.id.user_password_num_one)
        {
            currentEditText = newUserPasswordOne;
        }
        if (view.getId() == R.id.user_password_num_two)
        {
            currentEditText = newUserPasswordTwo;
        }
        if (view.getId() == R.id.user_password_num_three)
        {
            currentEditText = newUserPasswordThree;
        }
        if (view.getId() == R.id.user_password_num_four)
        {
            currentEditText = newUserPasswordFour;
        }
        if (view.getId() == R.id.confirm_user_password_num_one)
        {
            currentEditText = confrimUserPasswordOne;
        }
        if (view.getId() == R.id.confirm_user_password_num_two)
        {
            currentEditText = confrimUserPasswordTwo;
        }
        if (view.getId() == R.id.confirm_user_password_num_three)
        {
            currentEditText = confrimUserPasswordThree;
        }if (view.getId() == R.id.confirm_user_password_num_four)
        {
            currentEditText = confrimUserPasswordFour;
        }
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
                String passwordOne =newUserPasswordOne.getText().toString() + newUserPasswordTwo.getText().toString()
                        + newUserPasswordThree.getText().toString() + newUserPasswordFour.getText().toString();
                String passwordTwo=confrimUserPasswordOne.getText().toString()+confrimUserPasswordTwo.getText().toString()+
                        confrimUserPasswordThree.getText().toString()+confrimUserPasswordFour.getText().toString();
                if (passwordOne.equals(passwordTwo)) {
                    MyApplication myApplication = (MyApplication)getApplication();
                    myApplication.getModelService().setUserPassword(passwordOne);
                    showShortToast("用户密码设置成功！");
                }else{
                    if(currentEditText == confrimUserPasswordFour)
                    {
                        showShortToast("两次密码输入不匹配，请重试！");
                        confrimUserPasswordOne.setText("");
                        confrimUserPasswordTwo.setText("");
                        confrimUserPasswordThree.setText("");
                        confrimUserPasswordFour.setText("");
                        currentEditText = confrimUserPasswordOne;
                        confrimUserPasswordOne.setFocusable(true);
                    }
                }
        }else if (position==9){
            confrimUserPasswordOne.setText("");
            confrimUserPasswordTwo.setText("");
            confrimUserPasswordThree.setText("");
            confrimUserPasswordFour.setText("");
            newUserPasswordOne.setText("");
            newUserPasswordTwo.setText("");
            newUserPasswordThree.setText("");
            newUserPasswordFour.setText("");
            currentEditText = newUserPasswordOne;
            newUserPasswordOne.setFocusable(true);
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
