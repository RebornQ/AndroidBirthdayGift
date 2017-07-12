package soft.weac.birthdaygift.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import soft.weac.birthdaygift.CakeActivity;
import soft.weac.birthdaygift.R;

/**
 * Created by Arcobaleno on 2017/6/8.
 */

public class AudioService extends Service implements MediaPlayer.OnCompletionListener{


    public static MediaPlayer player;

//    public static int result = 0;
//
//    private final String TAG = "MusicService";
//    /**
//     * 音频管理类
//     */
//    private AudioManager mAudioManager;
//    /**
//     * 是否播放音乐
//     */
//    private static boolean vIsActive=false;
//    /**
//     * 音乐监听器
//     */
//    private MyOnAudioFocusChangeListener mListener;

    private final IBinder binder = new AudioBinder();
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return binder;
    }
    /**
     * 当Audio播放完的时候触发该动作
     */
    @Override
    public void onCompletion(MediaPlayer player) {
        // TODO Auto-generated method stub
        stopSelf();//结束了，则结束Service
    }

    //在这里我们需要实例化MediaPlayer对象
    public void onCreate(){
        super.onCreate();
        //我们从raw文件夹中获取一个应用自带的mp3文件
        player = MediaPlayer.create(this, R.raw.mysoul);
        player.setOnCompletionListener(this);
//        if(!player.isPlaying()){
//            player.start();
//            CakeActivity.isAnim = true;
//        }

//        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
//        mListener = new MyOnAudioFocusChangeListener();

        //监听音频播放完的代码，实现音频的自动循环播放
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer arg0) {
                player.start();
                player.setLooping(true);
                CakeActivity.isAnim = true;
            }
        });
        Log.e("onCreate---", "success");
    }

    /**
     * 该方法在SDK2.0才开始有的，替代原来的onStart方法
     */
    public int onStartCommand(Intent intent, int flags, int startId){
        if(!player.isPlaying()){
            player.start();
            CakeActivity.isAnim = true;
        }
        Log.e("onStartCommand---", "success");

//        setMusicControl();

        return START_STICKY;
    }

    public void onDestroy(){
        //super.onDestroy();
        if(player.isPlaying()){
            player.stop();
        }
        player.release();

//        //Service保活方法之一：在Service的onDestroy()方法中重启Service
//        Intent localIntent = new Intent();
//        localIntent.setClass(this, AudioService.class); //销毁时重新启动Service
//        this.startService(localIntent);

        Log.e("onDestroy---", "success");
    }

    //为了和Activity交互，我们需要定义一个Binder对象
    public class AudioBinder extends Binder {

        //返回Service对象
        public AudioService getService(){
            return AudioService.this;
        }
    }

//    //后退播放进度
//    public void haveFun(){
//        if(player.isPlaying() && player.getCurrentPosition()>2500){
//            player.seekTo(player.getCurrentPosition()-2500);
//        }
//    }

//    /**
//     * 内部类：音乐监听器
//     */
//    public class MyOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
//        @Override
//        public void onAudioFocusChange(int focusChange) {
//            // TODO Auto-generated method stub
//            Log.i(TAG, "focusChange=" + focusChange);
//        }
//    }

//    private void setMusicControl() {
//        // Request audio focus for playback
//        result = mAudioManager.requestAudioFocus(mListener,
//                AudioManager.STREAM_MUSIC,
//                AudioManager.AUDIOFOCUS_GAIN);
//
//        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
//        {
//            Log.i(TAG, "requestAudioFocus successfully.");
//
//            // Start playback.
////            player.start();
//            CakeActivity.isAnim = true;
//        }
//        else
//        {
//            CakeActivity.isAnim = false;
//            Log.e(TAG, "requestAudioFocus failed.");
//        }
//    }
}

