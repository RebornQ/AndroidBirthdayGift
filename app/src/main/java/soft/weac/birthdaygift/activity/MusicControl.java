package soft.weac.birthdaygift.activity;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

import soft.weac.birthdaygift.service.AudioService;

import static soft.weac.birthdaygift.activity.CakeActivity.isAnim;
import static soft.weac.birthdaygift.activity.CakeActivity.musicControl;
import static soft.weac.birthdaygift.activity.CakeActivity.operatingAnim;

/**
 * Created by Arcobaleno on 2017/6/9.
 */

class MusicControl {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar mSoundSeekBar = null;
//    ImageButton mMusicControl;
//    Animation mOperatingAnim;
    private int position = 0;
    private boolean isPlaying;
    private boolean isSeeking;

    public MusicControl() {}

    MusicControl(SeekBar soundSeekBar) {
        mSoundSeekBar = soundSeekBar;
        init();
        seekBarControl();
        animControl();
        musicControl();
    }

    private void seekBarControl() {
        mSoundSeekBar.setMax(AudioService.player.getDuration());
        isSeeking=false;
        final Timer mTimer = new Timer();
        final TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(!isPlaying) {
                    return;
                }
                if(isSeeking){
                    return;
                }
                try {
                    mSoundSeekBar.setProgress(AudioService.player.getCurrentPosition());
                } catch (IllegalStateException e) {
                    Log.e("mSoundSeekBar--", "find IllegalStateException!!!!");
                    //杀死该应用进程
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        };
        mTimer.schedule(mTimerTask, 0, 10);
        mSoundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //position=seekBar.getProgress();
                //Log.e("tracktouch","ontracktouchchange");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking=true;
                Log.e("TrackTouch","StartTrackTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                AudioService.player.pause();
                position=seekBar.getProgress();
                AudioService.player.seekTo(position);
                AudioService.player.start();
                musicControl.startAnimation(operatingAnim);
                isSeeking=false;
                isPlaying = true;
                Log.e("TrackTouch","StopTrackTouch");
            }
        });
    }

    private void animControl() {
        //旋转按钮
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            musicControl.startAnimation(operatingAnim);
        }
    }

    private void init() {
        if (AudioService.player.isPlaying()) {
            position=mSoundSeekBar.getProgress();
            //AudioService.player.seekTo(position); //此处不能用！！！否则会导致切换Fragment的时候重头开始播放音乐！！！（因为设计是）
//            Log.e("init--", "init!!!");
            isPlaying = true;
        }
    }

    private void onPause(MediaPlayer mediaPlayer) {//如果突然电话到来，停止播放音乐
        if(mediaPlayer.isPlaying()){
            position = mediaPlayer.getCurrentPosition();//保存当前播放点
            mediaPlayer.pause();
        }
    }

    private void onResume(MediaPlayer mediaPlayer) {
        if(position>0) {//如果电话结束，继续播放音乐
            mediaPlayer.start();
            mediaPlayer.seekTo(position);
            position = 0;
        }
    }

    private void musicControl() {
        musicControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "musicControl");
                if (isAnim) {
                    musicControl.clearAnimation();
                    onPause(AudioService.player);
                    mediaPlayer.release();
                    isAnim = false;
                    isPlaying = false;
                } else {

                    if (!isPlaying) {
                        if (position > 0) {
                            onResume(AudioService.player);
                        } else {
                            AudioService.player.start();
                        }
                    } else {
                        if (mediaPlayer != null) {
                            onPause(AudioService.player);
                            mediaPlayer.release();
                        }
                    }
                    isPlaying = !isPlaying;

                    if (operatingAnim != null) {
                        Log.e("WantAnim--", "Yes");
                        musicControl.startAnimation(operatingAnim);
//                        AudioService.player.start();
                        isAnim = true;
                    }
                }
            }
        });
    }
}
