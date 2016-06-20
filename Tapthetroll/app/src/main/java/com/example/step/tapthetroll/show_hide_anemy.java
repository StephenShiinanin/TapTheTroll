package com.example.step.tapthetroll;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.*;
import  android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 04/05/16.
 */

public class show_hide_anemy extends  View{

    private   float player_x =0;
    private  float player_y =0;

    public ArrayList<String> cpu_xList = new ArrayList<String> ();
    public ArrayList<String> cpu_yList = new ArrayList<String> ();
    private  float cpu_x =0;
    private  float cpu_y =0;
    private  Canvas this_Canvas ;
    public Bitmap mBitmap;
    private Paint paint ;
    public  int point =0;
    public  int level =0;
    public  int target =0;
    public  MediaPlayer killsound= null;

    public show_hide_anemy(Context ctx, AttributeSet attrs, int lvl,int target_point) {
        super(ctx, attrs);
        /*
         *MEMBANGUN OBJECT UNTUK DOT
         * ATAU POINTER PLAYER
         */
        level = lvl;
        target =target_point;
        paint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.trolimg);
        //mBitmap = Bitmap.createScaledBitmap(mBitmap,300,300,false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(level != 0) {
            for (int i = 0; i < level; i++) {
                this.computer(canvas, i);
            }
            this.player(canvas);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("POINT: " + point, 50, 50, paint);
            canvas.drawText("TARGET: " + target, 50, 120, paint);
        }

    }

    public  void player(Canvas canvas){

//        paint.setColor(Color.RED);
//        canvas.drawCircle(player_x, player_y, 100, paint);
//        paint.setTextSize(50);
        paint.setColor(Color.RED);
        //  canvas.drawText("X : "+player_x+" , Y : "+player_y, 20, 800, paint);

        /*
         * apakah lawan kena ?
         */
        int kill =0;
        for(int i =0; i<cpu_xList.size(); i++){
            if(Math.abs(Float.parseFloat((cpu_xList.get(i)))  - player_x) <= 80
                    && Math.abs( Float.parseFloat((cpu_yList.get(i)))  - player_y) <=80){
                point++;
                kill++;

            }

        }

        if(kill == 1){
            canvas.drawText("POINT KENA ... ", 20, 600, paint);
            MainActivity.kill_sound_efect.start();

        }
        else if(kill ==2){
            canvas.drawText("DOUBLE KENA ... ", 20, 600, paint);
            MainActivity.kill_sound_efect.start();
        }
        else if(kill ==3){
            canvas.drawText("TRIPLE KENA ... ", 20, 600, paint);
            MainActivity.kill_sound_efect.start();
        }
        else if(kill == 0){
            canvas.drawText("", 50, 600, paint);
        }
        cpu_xList.clear();
        cpu_yList.clear();

    }

    public  void computer(Canvas canvas , int index){

        Random rand = new Random();
        if(index == 0) {
//            paint.setColor(Color.CYAN);
        }
        else if(index ==1){
            paint.setColor(Color.GREEN);
        }
        else if(index ==2){
            paint.setColor(Color.YELLOW);
        }
        cpu_x = rand.nextFloat() *1000;
        cpu_y = rand.nextFloat() *1000;
        cpu_xList.add(String.valueOf(cpu_x));
        cpu_yList.add(String.valueOf(cpu_y));
//        canvas.drawCircle(cpu_x,cpu_y,150,paint);
        canvas.drawBitmap(mBitmap,cpu_x-100,cpu_y-80,paint);
        paint.setColor(Color.BLACK);
        //canvas.drawText(""+(index+1) ,cpu_x,cpu_y,paint);
        paint.setTextSize(80);
        //  canvas.drawText("X : "+cpu_x+" , Y : "+cpu_y, 20, 500, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        player_x = event.getX();
        player_y = event.getY();
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                invalidate();
                return  true;

            default:
                return  false;
        }

    }
}