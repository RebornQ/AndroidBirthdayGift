package soft.weac.birthdaygift.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import soft.weac.birthdaygift.ActivityManagerApplication;
import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.fragment.AcceptGiftDialog;

/**
 * Created by Arcobaleno on 2017/6/10.
 */

public class MyLauncherActivity extends AppCompatActivity {

    private long firstTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylauncher);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityManagerApplication.addDestroyActivity(MyLauncherActivity.this, "MyLauncherActivity");

        final FragmentManager fragmentManager = getSupportFragmentManager();
        Button bt_yes = (Button) findViewById(R.id.bt_yes);
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AcceptGiftDialog()
                        .show(fragmentManager, "Surprise");
            }
        });

        Button bt_no = (Button) findViewById(R.id.bt_no);
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refuseDialog();
            }
        });
    }

    private void refuseDialog() {
        /*
        这里使用了 android.support.v7.app.AlertDialog.Builder
        可以直接在头部写 import android.support.v7.app.AlertDialog
        那么下面就可以写成 AlertDialog.Builder
        */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyLauncherActivity.this);
        builder.setTitle("提示");
        builder.setMessage("XX，你真的不要礼物了？？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MyLauncherActivity.this, RefuseGiftActivity.class);
                startActivity(intent);
                MyLauncherActivity.this.finish();
            }
        });
        builder.show();
    }

    @Override//重写物理返回键逻辑,实现按返回键退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(MyLauncherActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
