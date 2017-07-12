package soft.weac.birthdaygift;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import soft.weac.birthdaygift.activity.ActivityManagerApplication;
import soft.weac.birthdaygift.fragment.AlbumFragment;
import soft.weac.birthdaygift.fragment.CakeFragment;
import soft.weac.birthdaygift.fragment.MessageFragment;
import soft.weac.birthdaygift.presenter.TextViewPresenter;
import soft.weac.birthdaygift.service.AudioService;
import soft.weac.birthdaygift.view.SinglyTextView;


public class CakeActivity extends AppCompatActivity{

    static AudioService audioService;
    Intent intent;
//    static boolean isBind = false;
    public static ImageButton musicControl;
    public static Animation operatingAnim;
    private SeekBar mSoundSeekBar = null;
    public static boolean isAnim = true;
    public static int pagerPosition = 3;

    SinglyTextView singlyTextView = null;
//    Context context = null;

    Handler handler;
    private long firstTime = 0;

    //使用ServiceConnection来监听Service状态的变化
    public static ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            audioService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //这里我们实例化audioService,通过binder来实现
            audioService = ((AudioService.AudioBinder)binder).getService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==0x100){
                    new MusicControl(mSoundSeekBar/*, musicControl, operatingAnim*/);
                }
            }
        };

        final ViewPager pager=(ViewPager)findViewById(R.id.content);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        musicControl = (ImageButton) findViewById(R.id.musicControl);
        mSoundSeekBar=(SeekBar) findViewById(R.id.SoundSeekBar);

        intent = new Intent();
        intent.setClass(CakeActivity.this, AudioService.class);
        startService(intent);   //可实现当Activity被销毁之后还可以播放音乐
        delay2();
//        bindService(intent, conn, Context.BIND_AUTO_CREATE);  //当Activity被销毁后会自动解绑
//        isBind = true;

//        musicControl.setBackgroundResource(R.drawable.music_pause);

//        navigation.setSelectedItemId(R.id.navigation_cake);

        //旋转按钮
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotateanim);
//        LinearInterpolator lin = new LinearInterpolator();
//        operatingAnim.setInterpolator(lin);
//        if (operatingAnim != null) {
//            musicControl.startAnimation(operatingAnim);
//        }

        setupViewPager(pager);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_message:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.navigation_cake:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.navigation_album:
                        pager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
                pagerPosition = position;
//                Log.i("PagerPosition", "" + pagerPosition);
                if (position == 0) {
                    if (singlyTextView != null) {
                        new TextViewPresenter(singlyTextView);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
        pager.setCurrentItem(1);
        ActivityManagerApplication.destroyActivity("MyLauncherActivity");

//        finish();
    }

    private boolean isServiceRunning(String serviceName) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void delay2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg=new Message();
                msg.what=0x100;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new CakeFragment());
        adapter.addFragment(new AlbumFragment());
        viewPager.setAdapter(adapter);
    }

//    private void playAudio(Intent intent) {
//        final MediaPlayer mp =MediaPlayer.create(this, R.raw.mysoul);
//        mp.start();
//    }

    public void setmTextView(Context context, SinglyTextView singlyTextView) {
        singlyTextView.setText(context.getResources().getText(R.string.myMessage));
        singlyTextView.init();
        singlyTextView.start();
    }

    public void getmTextView(SinglyTextView singlyTextView) {
        this.singlyTextView = singlyTextView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }

        //结束Service
//        unbindService(conn);
//        if (AudioService.player != null) {
//            AudioService.player.stop();
//        }
        stopService(intent);
    }

    @Override//重写物理返回键逻辑,实现按返回键退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(CakeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                    //两次按键小于2秒时，退出应用
                    stopService(intent);
                    Intent intent_exit = new Intent(Intent.ACTION_MAIN);
                    intent_exit.addCategory(Intent.CATEGORY_HOME);
                    intent_exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent_exit);
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}


/**
 * Created by bruce on 2016/11/1.
 * ViewPagerAdapter
 */

class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }


}
