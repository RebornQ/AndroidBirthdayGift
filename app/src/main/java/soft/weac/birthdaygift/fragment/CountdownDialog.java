package soft.weac.birthdaygift.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import soft.weac.birthdaygift.R;

/**
 * Created by Arcobaleno on 2017/7/11.
 */

public class CountdownDialog extends DialogFragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置后背景透明
        view = inflater.inflate(R.layout.dialog_fragment_countdown, container, false);
        refresh();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((dm.widthPixels), (dm.heightPixels));
        }
    }

    private void refresh() {

        final View mLinearLayout = view.findViewById(R.id.countdownLayout);
        mLinearLayout.setVisibility(View.VISIBLE);

        final TextView textView = (TextView) view.findViewById(R.id.mTimes);
        CountDownTimer timer = new CountDownTimer(6000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
//                Toast.makeText(getContext(), (millisUntilFinished / 1000) + "秒后可重发", Toast.LENGTH_SHORT).show();
                textView.setText((millisUntilFinished / 1000) + "秒后关闭程序");
            }

            @Override
            public void onFinish() {
                dismiss();
                Intent intent_exit = new Intent(Intent.ACTION_MAIN);
                intent_exit.addCategory(Intent.CATEGORY_HOME);
                intent_exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    startActivity(intent_exit);
                } catch (IllegalStateException e) {
                    Log.e("IllegalException", e.getLocalizedMessage());
                }
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        };
        timer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    private void getFocus() {
        getView().setFocusable(true);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件

                    dismiss();
                    Toast.makeText(getContext(), "没用的，你按返回还是会退出程序的~", Toast.LENGTH_SHORT).show();
                    return true;// 未处理
                }
                //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
                return false;
            }
        });
    }
}
