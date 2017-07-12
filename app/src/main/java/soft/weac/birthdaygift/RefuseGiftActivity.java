package soft.weac.birthdaygift;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import soft.weac.birthdaygift.activity.ActivityManagerApplication;
import soft.weac.birthdaygift.fragment.CountdownDialog;
import soft.weac.birthdaygift.view.MSinglyTextView;

/**
 * Created by Arcobaleno on 2017/7/11.
 */

public class RefuseGiftActivity extends AppCompatActivity {

    private long firstTime = 0;
    public static boolean isMAXIndex = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuse);

        ActivityManagerApplication.addDestroyActivity(RefuseGiftActivity.this, "RefuseGiftActivity");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        ImageView iv_sad = (ImageView) findViewById(R.id.sad_BoBo);
        MSinglyTextView mSinglyTextView = (MSinglyTextView) findViewById(R.id.tx_sad);

//        DisplayMetrics dm = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_sad.getLayoutParams();
//        params.width = (int) (dm.widthPixels * 0.5);//设置当前控件布局的宽度
//        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;//设置当前控件布局的高度
//        iv_sad.setLayoutParams(params);//将设置好的布局参数应用到控件中
//        LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) mSinglyTextView.getLayoutParams();
//        params.width = (int) (dm.widthPixels * 0.5);//设置当前控件布局的宽度
//        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;//设置当前控件布局的高度
//        mSinglyTextView.setGravity(Gravity.CENTER);
//        mSinglyTextView.setLayoutParams(params_1);//将设置好的布局参数应用到控件中

        showDialog();

    }

    private void showDialog() {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        CountDownTimer timer = new CountDownTimer(8000, 1000) {

            //每隔一秒会回调一次方法onTick，然后10秒之后会回调onFinish方法
            @Override
            public void onTick(long millisUntilFinished) {
                //(millisUntilFinished / 1000) = 10000/1000

            }

            @Override
            public void onFinish() {
                try {
                    new CountdownDialog()
                            .show(fragmentManager, "Countdown");
                } catch (IllegalStateException e) {
                    Log.e("IllegalStateException", e.getLocalizedMessage());
                }
            }
        };
        timer.start();
    }

    @Override//重写物理返回键逻辑,实现按返回键退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(RefuseGiftActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                    //两次按键小于2秒时，退出应用
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
