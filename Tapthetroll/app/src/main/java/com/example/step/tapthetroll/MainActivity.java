package com.example.step.tapthetroll;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public  int level ,time_level ,taget_point,point =0;
    private RelativeLayout paper ,login = null;
    private RadioGroup RG = null;
    private  RadioButton RB = null;
    private show_hide_anemy  canvas =null;
    private Button newgame , Btnexit ,Btnteam, Btnscore = null;
    private  TextView time = null;
    private CountDownTimer lamawaktu;
    public  MediaPlayer lv1,lv2,lv3  = null;
    public static MediaPlayer kill_sound_efect= null;
    public  EditText nama = null;
    public SharedPreferences shplv1 ,shplv2,shplv3  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newgame = (Button)findViewById(R.id.startgame);
        paper = (RelativeLayout) findViewById(R.id.base);
        login = (RelativeLayout)findViewById(R.id.login);
        RG = (RadioGroup)findViewById(R.id.radioGroup);
        time = (TextView)findViewById(R.id.time);
        time.setText("");

        Btnexit = (Button) findViewById(R.id.btn_exit);
        Btnteam = (Button)findViewById(R.id.btn_team);
        Btnscore = (Button)findViewById(R.id.btn_score);

        ///sharedpreference setiap level
        shplv1 =  getSharedPreferences("lv1", Context.MODE_PRIVATE);
        shplv2 =  getSharedPreferences("lv2", Context.MODE_PRIVATE);
        shplv3 =  getSharedPreferences("lv3", Context.MODE_PRIVATE);

        newgame.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
                if(level != 0) {
                    login.setVisibility(v.GONE);
                    this.game();
                }
                else{
                    Toast.makeText(getBaseContext(), "AGAN LUPA PILIH LEVEL" , Toast.LENGTH_SHORT ).show();
                }
            }

            Canvas kanvas = new Canvas();

            public  void game(){
                if(level != 0) {
                    paper = (RelativeLayout) findViewById(R.id.base);
                    canvas = new show_hide_anemy(MainActivity.this, null, level,taget_point);
                    paper.addView(canvas, 0);

                    //start song

                    lv1 =  MediaPlayer.create(MainActivity.this,R.raw.easy);
                    lv2 =  MediaPlayer.create(MainActivity.this,R.raw.normal);
                    lv3 =  MediaPlayer.create(MainActivity.this,R.raw.hard);
                    if(level ==1 ){
                        lv1.setLooping(true);
                        lv1.start();

                    }
                    else if(level ==2){
                        lv2.setLooping(true);
                        lv2.start();
                    }
                    else if(level ==3){
                        lv3.setLooping(true);
                        lv3.start();
                    }
                    //efek suara
                    kill_sound_efect = MediaPlayer.create(MainActivity.this,R.raw.kill);
                    final MediaPlayer ketawa =  MediaPlayer.create(MainActivity.this,R.raw.trol_laught);
                    final MediaPlayer win  = MediaPlayer.create(MainActivity.this,R.raw.win);
                    final MediaPlayer lose  = MediaPlayer.create(MainActivity.this,R.raw.lose);
                    ketawa.setLooping(true);
                    ketawa.start();

                    //class timer

                    lamawaktu = new CountDownTimer(time_level,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            time.setText("seconds remaining: " +new SimpleDateFormat("mm:ss")
                                    .format(new Date( millisUntilFinished)));

                        }

                        @Override
                        public void onFinish() {
                            time.setText("seconds remaining: 00:00");
                            time.setText("");
                            String komen ="";
                            point = canvas.point;
                            if(canvas.point >= taget_point){
                                if((canvas.point - taget_point) > 10 ){
                                    komen ="OUTSTANDING";
                                }
                                else{
                                    komen ="YOU WIN";
                                }
                                win.start();
                                addscore();
                            }
                            else{
                                komen ="YOU LOSE";
                                lose.start();
                            }
                            Toast.makeText(getBaseContext(), "WAKTU HABIS   POINT : "+canvas.point, Toast.LENGTH_LONG ).show();

                            Toast.makeText(getBaseContext(), komen, Toast.LENGTH_LONG ).show();

                            kanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                            login.setVisibility(View.VISIBLE);
                            paper.removeViewAt(0);
                            lv1.stop();
                            lv2.stop();
                            lv3.stop();
                            ketawa.stop();
                        }
                    }.start();

                }

            }

            ///masukkan skor tertinggi
            public  void addscore(){

                View v =    (LayoutInflater.from(MainActivity.this)).inflate(R.layout.addnama,null);
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);

                ab.setView(v);

                nama = (EditText) v.findViewById(R.id.txtplayer);
                ab.setTitle("ADD NEW SCORE");
                ab.setCancelable(true);
                ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(level == 1 && point > shplv1.getInt("score",0)){
                            SharedPreferences.Editor edit = shplv1.edit();
                            edit.putString("nama", nama.getText().toString());
                            edit.putInt("score", point);
                            edit.commit();
                        }
                        else  if(level== 2 && point > shplv2.getInt("score",0) ){
                            SharedPreferences.Editor edit = shplv2.edit();
                            edit.putString("nama", nama.getText().toString());
                            edit.putInt("score", point);
                            edit.commit();
                        }
                        else if(level == 3 && point > shplv3.getInt("score",0)){
                            SharedPreferences.Editor edit = shplv3.edit();
                            edit.putString("nama", nama.getText().toString());
                            edit.putInt("score", point);
                            edit.commit();
                        }
                        Toast.makeText(getBaseContext(),"SAVE",Toast.LENGTH_LONG).show();

                    }
                    public void savepoint (){

                    }
                });

                Dialog dialog = ab.create();
                dialog.show();

            }

        });


        ///pemilihan level
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.lv1:
                        level =1;
                        time_level = 91000;   //301000
                        taget_point = 5;  //30
                        break;
                    case R.id.lv2:
                        level =2;
                        time_level = 61000;  //181000
                        taget_point = 8;   //50
                        break;
                    case R.id.lv3:
                        level =3;
                        time_level = 36000;
                        taget_point = 10;
                        break;
                    default:
                        level =0;
                        break;

                }
            }
        });
        //keluar
        Btnexit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        //nama tim
        Btnteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> TIM = new ArrayList<String>();
                TIM.add("131113816  - GERALD H SAMOSIR");
                TIM.add("131111220  - STEPHEN");
                TIM.add("131111254  - SANTUN A KURNIAWAN");
                //Create sequence of items
                final CharSequence[] TIMchr = TIM.toArray(new String[TIM.size()]);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("TIM ANDROID");

                dialogBuilder.setItems(TIMchr,null);

                dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                AlertDialog alertDialogObject = dialogBuilder.create();

                alertDialogObject.show();
            }
        });

        //view score
        Btnscore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String [] nama = new String[3];
                int  []  point = new int[3];

                nama[0] = shplv1.getString("nama","");
                point [0] = shplv1.getInt("score",0);

                nama[1] = shplv2.getString("nama","");
                point [1] = shplv2.getInt("score",0);

                nama[2] = shplv3.getString("nama","");
                point [2] = shplv3.getInt("score",0);

                List<String> highscore = new ArrayList<String>();
                if(point[0]>0) {
                    highscore.add("LEVEL 1  - " + nama[0] + " - " + point[0] + " POINT");
                }
                if(point[1]>0) {
                    highscore.add("LEVEL 2  - " + nama[1] + " - " + point[1] + " POINT");
                }
                if(point[2]>0) {
                    highscore.add("LEVEL 3  - " + nama[2] + " - " + point[2] + " POINT");
                }
                //Create sequence of items
                final CharSequence[] highscorechr = highscore.toArray(new String[highscore.size()]);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("HIGH SCORE");

                dialogBuilder.setItems(highscorechr,null);

                dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                AlertDialog alertDialogObject = dialogBuilder.create();

                alertDialogObject.show();

            }
        });

    }

}