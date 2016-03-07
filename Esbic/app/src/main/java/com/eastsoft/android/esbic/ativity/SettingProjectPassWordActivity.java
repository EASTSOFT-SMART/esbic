package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
public class SettingProjectPassWordActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView newProPasswordOne,newProPasswordTwo,newProPasswordThree,newProPasswordFour,
            newProPasswordFive,newProPasswordSix;
    private TextView confrimProPasswordOne,confrimProPasswordTwo,confrimProPasswordThree,
            confrimProPasswordFour,confrimProPasswordFive,confrimProPasswordSix;
    private GridView inputKeyboard;
    private int[] icon;
    private List<Map<String, Object>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_password_alter);
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
        inputKeyboard=(GridView)this.findViewById(R.id.alter_propwd_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon);
        inputKeyboard.setAdapter(inputKeyBoardAdapter);
        inputKeyboard.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    //设置TextView从左往右显示
    public void textHowToShow(int position){
        if(position<9){
            if (newProPasswordOne.getText().equals("")){
                newProPasswordOne.setText(String.valueOf(position+1));
            }else if (newProPasswordTwo.getText().equals("")){
                newProPasswordTwo.setText(String.valueOf(position+1) );
            }else if (newProPasswordThree.getText().equals("")){
                newProPasswordThree.setText(String.valueOf(position+1));
            }else if (newProPasswordFour.getText().equals("")){
                newProPasswordFour.setText(String.valueOf(position+1));
            }else if (newProPasswordFive.getText().equals("")){
                newProPasswordFive.setText(String.valueOf(position+1));
            }else if (newProPasswordSix.getText().equals("")){
                newProPasswordSix.setText(String.valueOf(position+1));
            }else{
                if (confrimProPasswordOne.getText().equals("")){
                    confrimProPasswordOne.setText(String.valueOf(position+1));
                }else if (confrimProPasswordTwo.getText().equals("")){
                    confrimProPasswordTwo.setText(String.valueOf(position+1));
                }else if (confrimProPasswordThree.getText().equals("")){
                    confrimProPasswordThree.setText(String.valueOf(position+1));
                }else if (confrimProPasswordFour.getText().equals("")){
                    confrimProPasswordFour.setText(String.valueOf(position+1));
                }else if (confrimProPasswordFive.getText().equals("")){
                    confrimProPasswordFive.setText(String.valueOf(position+1));
                }else if (confrimProPasswordSix.getText().equals("")){
                    confrimProPasswordSix.setText(String.valueOf(position+1));
                }else{
                    String passwordOne =newProPasswordOne.getText().toString() + newProPasswordTwo.getText().toString()
                            + newProPasswordThree.getText().toString() + newProPasswordFour.getText().toString()+
                            newProPasswordFive.getText().toString()+newProPasswordSix.getText().toString();
                    String passwordTwo=confrimProPasswordOne.getText().toString()+confrimProPasswordTwo.getText().toString()+
                            confrimProPasswordThree.getText().toString()+confrimProPasswordFour.getText().toString()+
                            confrimProPasswordFive.getText().toString()+confrimProPasswordSix.getText().toString();
                    if (passwordOne.equals(passwordTwo)) {
                        this.finish();
                    }else{
                        showShortToast("两次密码输入不匹配，请重试！");
                        confrimProPasswordSix.setText("");
                        confrimProPasswordFive.setText("");
                        confrimProPasswordFour.setText("");
                        confrimProPasswordThree.setText("");
                        confrimProPasswordTwo.setText("");
                        confrimProPasswordOne.setText("");
                        newProPasswordSix.setText("");
                        newProPasswordFive.setText("");
                        newProPasswordFour.setText("");
                        newProPasswordThree.setText("");
                        newProPasswordTwo.setText("");
                        newProPasswordOne.setText("");

                    }
                }
            }
        }else if (position==9){
            confrimProPasswordSix.setText("");
            confrimProPasswordFive.setText("");
            confrimProPasswordFour.setText("");
            confrimProPasswordThree.setText("");
            confrimProPasswordTwo.setText("");
            confrimProPasswordOne.setText("");
            newProPasswordSix.setText("");
            newProPasswordFive.setText("");
            newProPasswordFour.setText("");
            newProPasswordThree.setText("");
            newProPasswordTwo.setText("");
            newProPasswordOne.setText("");
        }else if (position==10){
                if (newProPasswordOne.getText().equals("")){
                    newProPasswordOne.setText(String.valueOf(position+1));
                }else if (newProPasswordTwo.getText().equals("")){
                    newProPasswordTwo.setText(String.valueOf(position+1) );
                }else if (newProPasswordThree.getText().equals("")){
                    newProPasswordThree.setText(String.valueOf(position+1));
                }else if (newProPasswordFour.getText().equals("")){
                    newProPasswordFour.setText(String.valueOf(position+1));
                }else if (newProPasswordFive.getText().equals("")){
                    newProPasswordFive.setText(String.valueOf(position+1));
                }else if (newProPasswordSix.getText().equals("")){
                    newProPasswordSix.setText(String.valueOf(position+1));
                }else{
                    if (confrimProPasswordOne.getText().equals("")){
                        confrimProPasswordOne.setText(String.valueOf(position+1));
                    }else if (confrimProPasswordTwo.getText().equals("")){
                        confrimProPasswordTwo.setText(String.valueOf(position+1));
                    }else if (confrimProPasswordThree.getText().equals("")){
                        confrimProPasswordThree.setText(String.valueOf(position+1));
                    }else if (confrimProPasswordFour.getText().equals("")){
                        confrimProPasswordFour.setText(String.valueOf(position+1));
                    }else if (confrimProPasswordFive.getText().equals("")){
                        confrimProPasswordFive.setText(String.valueOf(position+1));
                    }else if (confrimProPasswordSix.getText().equals("")){
                        confrimProPasswordSix.setText(String.valueOf(position+1));
                    }else{
                        String passwordOne =newProPasswordOne.getText().toString() + newProPasswordTwo.getText().toString()
                                + newProPasswordThree.getText().toString() + newProPasswordFour.getText().toString()+
                                newProPasswordFive.getText().toString()+newProPasswordSix.getText().toString();
                        String passwordTwo=confrimProPasswordOne.getText().toString()+confrimProPasswordTwo.getText().toString()+
                                confrimProPasswordThree.getText().toString()+confrimProPasswordFour.getText().toString()+
                                confrimProPasswordFive.getText().toString()+confrimProPasswordSix.getText().toString();
                        if (passwordOne.equals(passwordTwo)) {
                            this.finish();
                        }else{
                            showShortToast("两次密码输入不匹配，请重试！");
                            confrimProPasswordSix.setText("");
                            confrimProPasswordFive.setText("");
                            confrimProPasswordFour.setText("");
                            confrimProPasswordThree.setText("");
                            confrimProPasswordTwo.setText("");
                            confrimProPasswordOne.setText("");
                            newProPasswordSix.setText("");
                            newProPasswordFive.setText("");
                            newProPasswordFour.setText("");
                            newProPasswordThree.setText("");
                            newProPasswordTwo.setText("");
                            newProPasswordOne.setText("");

                        }
                    }
                }
        }else if (position==11){
            deleteTextViewFromRight();
        }
    }

    //设置删除的时候从右往左删除
    private void deleteTextViewFromRight() {
        if (!confrimProPasswordSix.getText().equals("")) {
            confrimProPasswordSix.setText("");
        } else if (!confrimProPasswordFive.getText().equals("")) {
            confrimProPasswordFive.setText("");
        } else if (!confrimProPasswordFour.getText().equals("")) {
            confrimProPasswordFour.setText("");
        } else if (!confrimProPasswordThree.getText().equals("")) {
            confrimProPasswordThree.setText("");
        } else if (!confrimProPasswordTwo.getText().equals("")) {
            confrimProPasswordTwo.setText("");
        } else if (!confrimProPasswordOne.getText().equals("")) {
            confrimProPasswordOne.setText("");
        } else {
            if (!newProPasswordSix.getText().equals("")) {
                newProPasswordSix.setText("");
            } else if (!newProPasswordFive.getText().equals("")) {
                newProPasswordFive.setText("");
            } else if (!newProPasswordFour.getText().equals("")) {
                newProPasswordFour.setText("");
            } else if (!newProPasswordThree.getText().equals("")) {
                newProPasswordThree.setText("");
            } else if (!newProPasswordTwo.getText().equals("")) {
                newProPasswordTwo.setText("");
            } else if (!newProPasswordOne.getText().equals("")) {
                newProPasswordOne.setText("");
            } else {
            }
        }
    }
}
