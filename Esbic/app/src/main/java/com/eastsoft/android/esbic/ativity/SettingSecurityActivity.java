package com.eastsoft.android.esbic.ativity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingSecurityActivity extends BaseActivity implements View.OnClickListener{
    private  Button securityOne,securityTwo,securityThree,securityFour,securityFive,
             securitySix,securitySeven,securityEight;
    private Button securityLeaveHomeOne,securityLeaveHomeTwo,securityLeaveHomeThree,
            securityLeaveHomeFour,securityLeaveHomeFive,
            securityLeaveHomeSix,securityLeaveHomeSeven,securityLeaveHomeEight;
    private ImageButton back;
    private TextView back2;
    private Button[] buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_setting);

    }
    private void initData(){
        back=(ImageButton)this.findViewById(R.id.security_setting_back);
        back2=(TextView) this.findViewById(R.id.security_setting_back2);
        securityOne=(Button)this.findViewById(R.id.security_gohome_one);
        securityTwo=(Button)this.findViewById(R.id.security_gohome_two);
        securityThree=(Button)this.findViewById(R.id.security_gohome_three);
        securityFour=(Button)this.findViewById(R.id.security_gohome_four);
        securityFive=(Button)this.findViewById(R.id.security_gohome_five);
        securitySix=(Button)this.findViewById(R.id.security_gohome_six);
        securitySeven=(Button)this.findViewById(R.id.security_gohome_seven);
        securityEight=(Button)this.findViewById(R.id.security_gohome_eight);
        securityLeaveHomeOne=(Button)this.findViewById(R.id.security_leavehome_one);
        securityLeaveHomeTwo=(Button)this.findViewById(R.id.security_leavehome_two);
        securityLeaveHomeThree=(Button)this.findViewById(R.id.security_leavehome_three);
        securityLeaveHomeFour=(Button)this.findViewById(R.id.security_leavehome_four);
        securityLeaveHomeFive=(Button)this.findViewById(R.id.security_leavehome_five);
        securityLeaveHomeSix=(Button)this.findViewById(R.id.security_leavehome_six);
        securityLeaveHomeSeven=(Button)this.findViewById(R.id.security_leavehome_seven);
        securityLeaveHomeEight=(Button)this.findViewById(R.id.security_leavehome_eight);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
        buttons=new Button[]{securityOne,securityTwo,securityThree,securityFour,securityFive,
                securitySix,securitySeven,securityEight,securityLeaveHomeOne,securityLeaveHomeTwo,securityLeaveHomeThree,
                securityLeaveHomeFour,securityLeaveHomeFive,
                securityLeaveHomeSix,securityLeaveHomeSeven,securityLeaveHomeEight};
        for (int i=0;i<buttons.length;i++){
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == back.getId() || view.getId() == back2.getId())
        {
            playButtonMusic(musicButtonId);
            finish();
        }
        if (view.getId()==securityLeaveHomeOne.getId()){

        }
        if (view.getId()==securityLeaveHomeTwo.getId()){

        }
        if (view.getId()==securityLeaveHomeThree.getId()){

        }
        if (view.getId()==securityLeaveHomeFour.getId()){

        }
        if (view.getId()==securityLeaveHomeFive.getId()){

        }
        if (view.getId()==securityLeaveHomeSix.getId()){

        }
        if (view.getId()==securityLeaveHomeSeven.getId()){

        }
        if (view.getId()==securityLeaveHomeEight.getId()){

        }
        if (view.getId()==securityOne.getId()){

        }
        if (view.getId()==securityTwo.getId()){

        }if (view.getId()==securityThree.getId()){

        }
        if (view.getId()==securityFour.getId()){

        }
        if (view.getId()==securityFive.getId()){

        }
        if (view.getId()==securitySix.getId()){

        }
        if (view.getId()==securitySeven.getId()){

        }
        if (view.getId()==securityEight.getId()){

        }


    }
}
