package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SecurityAlterUserPwdActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView newUserPasswordOne,newUserPasswordTwo,newUserPasswordThree,newUserPasswordFour;
    private TextView confrimUserPasswordOne,confrimUserPasswordTwo,confrimUserPasswordThree,confrimUserPasswordFour;
    private GridView inputKeyboard;
    private int[] icon;
    String passwordOne,passwordTwo,passwordThree,passwordFour,confrimPasswordOne,
            confrimPasswordTwo,confrimPasswordThree,confrimPasswordFour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_password_alter);
        newUserPasswordOne=(TextView)this.findViewById(R.id.user_password_num_one);
        newUserPasswordTwo=(TextView)this.findViewById(R.id.user_password_num_two);
        newUserPasswordThree=(TextView)this.findViewById(R.id.user_password_num_three);
        newUserPasswordFour=(TextView)this.findViewById(R.id.user_password_num_four);
        confrimUserPasswordOne=(TextView)this.findViewById(R.id.confirm_user_password_num_one);
        confrimUserPasswordTwo=(TextView)this.findViewById(R.id.confirm_user_password_num_two);
        confrimUserPasswordThree=(TextView)this.findViewById(R.id.confirm_user_password_num_three);
        confrimUserPasswordFour=(TextView)this.findViewById(R.id.confirm_user_password_num_four);
        inputKeyboard=(GridView)this.findViewById(R.id.alter_userpsd_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon);
        inputKeyboard.setAdapter(inputKeyBoardAdapter);
        inputKeyboard.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        textHowToShow(i);
    }
    //设置TextView从左往右显示
    public void textHowToShow(int position){
        if(position<9){
                if (newUserPasswordOne.getText().equals("")){
                    newUserPasswordOne.setText(String.valueOf(position+1));
                }else if (newUserPasswordTwo.getText().equals("")){
                    newUserPasswordTwo.setText(String.valueOf(position+1) );
                }else if (newUserPasswordThree.getText().equals("")){
                    newUserPasswordThree.setText(String.valueOf(position+1));
                }else if (newUserPasswordFour.getText().equals("")){
                    newUserPasswordFour.setText(String.valueOf(position+1));
                }else{
                    if (confrimUserPasswordOne.getText().equals("")){
                        confrimUserPasswordOne.setText(String.valueOf(position+1));
                    }else if (confrimUserPasswordTwo.getText().equals("")){
                        confrimUserPasswordTwo.setText(String.valueOf(position+1));
                    }else if (confrimUserPasswordThree.getText().equals("")){
                        confrimUserPasswordThree.setText(String.valueOf(position+1));
                    }else if (confrimUserPasswordFour.getText().equals("")){
                        confrimUserPasswordFour.setText(String.valueOf(position+1));
                    }else{
                            String passwordOne =newUserPasswordOne.getText().toString() + newUserPasswordTwo.getText().toString()
                                    + newUserPasswordThree.getText().toString() + newUserPasswordFour.getText().toString();
                            String passwordTwo=confrimUserPasswordOne.getText().toString()+confrimUserPasswordTwo.getText().toString()+
                                    confrimUserPasswordThree.getText().toString()+confrimUserPasswordFour.getText().toString();
                            if (passwordOne.equals(passwordTwo)) {
                                this.finish();
                            }else{
                                showShortToast("两次密码输入不匹配，请重试！");
                                confrimUserPasswordOne.setText("");
                                confrimUserPasswordTwo.setText("");
                                confrimUserPasswordThree.setText("");
                                confrimUserPasswordFour.setText("");
                                newUserPasswordOne.setText("");
                                newUserPasswordTwo.setText("");
                                newUserPasswordThree.setText("");
                                newUserPasswordFour.setText("");
                        }
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
        }else if (position==10){
            if (newUserPasswordOne.getText().equals("")){
                newUserPasswordOne.setText(String.valueOf(0));
            }else if (newUserPasswordTwo.getText().equals("")){
                newUserPasswordTwo.setText(String.valueOf(0) );
            }else if (newUserPasswordThree.getText().equals("")){
                newUserPasswordThree.setText(String.valueOf(0));
            }else if (newUserPasswordFour.getText().equals("")){
                newUserPasswordFour.setText(String.valueOf(0));
            }else{
                if (confrimUserPasswordOne.getText().equals("")){
                    confrimUserPasswordOne.setText(String.valueOf(position+1));
                }else if (confrimUserPasswordTwo.getText().equals("")){
                    confrimUserPasswordTwo.setText(String.valueOf(position+1));
                }else if (confrimUserPasswordThree.getText().equals("")){
                    confrimUserPasswordThree.setText(String.valueOf(position+1));
                }else if (confrimUserPasswordFour.getText().equals("")){
                    confrimUserPasswordFour.setText(String.valueOf(position+1));
                }else{
                    String passwordOne =newUserPasswordOne.getText().toString() + newUserPasswordTwo.getText().toString()
                            + newUserPasswordThree.getText().toString() + newUserPasswordFour.getText().toString();
                    String passwordTwo=confrimUserPasswordOne.getText().toString()+confrimUserPasswordTwo.getText().toString()+
                            confrimUserPasswordThree.getText().toString()+confrimUserPasswordFour.getText().toString();
                    if (passwordOne.equals(passwordTwo)) {
                        this.finish();
                    }else{
                        showShortToast("两次密码输入不匹配，请重试！");
                        confrimUserPasswordOne.setText("");
                        confrimUserPasswordTwo.setText("");
                        confrimUserPasswordThree.setText("");
                        confrimUserPasswordFour.setText("");
                        newUserPasswordOne.setText("");
                        newUserPasswordTwo.setText("");
                        newUserPasswordThree.setText("");
                        newUserPasswordFour.setText("");
                    }

                }

            }
        }else if (position==11){
            deleteTextViewFromRight();
        }
    }

    //设置删除的时候从右往左删除
    private void deleteTextViewFromRight(){
        if (!confrimUserPasswordFour.getText().equals("")){
            confrimUserPasswordFour.setText("");
        }else if (!confrimUserPasswordThree.getText().equals("")){
            confrimUserPasswordThree.setText("");
        }else if (!confrimUserPasswordTwo.getText().equals("")){
            confrimUserPasswordTwo.setText("");
        }else if (!confrimUserPasswordOne.getText().equals("")){
            confrimUserPasswordOne.setText("");
        }else {
            if (!newUserPasswordFour.getText().equals("")) {
                newUserPasswordFour.setText("");
            } else if (!newUserPasswordThree.getText().equals("")) {
                newUserPasswordThree.setText("");
            } else if (!newUserPasswordThree.getText().equals("")) {
                newUserPasswordThree.setText("");
            } else if (!newUserPasswordFour.getText().equals("")) {
                newUserPasswordFour.setText("");
            } else {
                return;
            }
        }
    }

    //判断密码是否已经输入完成
    private boolean passwordIsFull(){
        passwordOne=newUserPasswordOne.getText().toString();
        passwordTwo=newUserPasswordTwo.getText().toString();
        passwordThree=newUserPasswordThree.getText().toString();
        passwordFour=newUserPasswordFour.getText().toString();
        confrimPasswordOne=confrimUserPasswordOne.getText().toString();
        confrimPasswordTwo=confrimUserPasswordTwo.getText().toString();
        confrimPasswordThree=confrimUserPasswordThree.getText().toString();
        confrimPasswordFour=confrimUserPasswordFour.getText().toString();
        if (!passwordOne.equals("")&&!passwordTwo.equals("")&&!passwordThree.equals("")
                &&!passwordFour.equals("")&&!confrimPasswordOne.equals("")&&!confrimPasswordTwo.equals("")
                &&!confrimPasswordThree.equals("")&&!confrimPasswordFour.equals("")){
            return true;
        }else{
            return false;
        }
    }
}
