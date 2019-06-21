package com.mallotec.birthdaygift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.mallotec.birthdaygift.MyApplication;
import com.mallotec.birthdaygift.R;
import com.mallotec.birthdaygift.fragment.CountdownDialog;
import com.mallotec.birthdaygift.view.MSinglyTextView;
import com.mallotec.birthdaygift.view.TextIndexListener;

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

        MyApplication.addDestroyActivity(RefuseGiftActivity.this, "RefuseGiftActivity");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MSinglyTextView mSinglyTextView = (MSinglyTextView) findViewById(R.id.tx_sad);
        mSinglyTextView.setIndexListener(new TextIndexListener() {
            @Override
            public boolean getIsMAXIndex() {
                showDialog();
                return true;
            }
        });
    }

    private void showDialog() {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            new CountdownDialog()
                    .show(fragmentManager, "Countdown");
        } catch (IllegalStateException e) {
            Log.e("IllegalStateException", e.getLocalizedMessage());
        }
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
