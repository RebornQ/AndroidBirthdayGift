package soft.weac.birthdaygift.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.view.SinglyTextView;
import soft.weac.birthdaygift.view.TextIndexListener;

/**
 * Created by Arcobaleno on 2017/7/10.
 */

public class MessageDialog extends DialogFragment implements TextIndexListener{

    View view;
    ScrollView scrollView_myMessage;
    public static boolean isMAXIndex = false;
    public static String  mOriginalStrTemp;
    private boolean isActionDown = false;

    private static String oldMsg;
    private static long time;

//    private boolean isFirstRun = true;
//
//    Timer timer = null;
//    MyTimerTask task = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置后背景透明
        view = inflater.inflate(R.layout.dialog_fragment_message, container, false);
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
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), (int) (dm.heightPixels * 0.65));
        }
    }

    private void refresh() {

        final View mLinearLayout = view.findViewById(R.id.messageDialog);
        mLinearLayout.setVisibility(View.VISIBLE);
        final SinglyTextView singlyTextView = (SinglyTextView) view.findViewById(R.id.myMessage);
        scrollView_myMessage = (ScrollView) view.findViewById(R.id.scrollView_myMessage);
//        singlyTextView.setText(getContext().getResources().getText(R.string.myMessage));
//        ((CakeActivity)getActivity()).getmTextView(singlyTextView);

        singlyTextView.setIndexListener(new TextIndexListener() {
            @Override
            public boolean getIsMAXIndex() {
                scrollView_myMessage.post(new Runnable() {
                    public void run() {
                        scrollView_myMessage.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                return false;
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) singlyTextView.getLayoutParams();
//        params.width= (int) (dm.widthPixels * 0.85 * 0.8);//设置当前控件布局的宽度
//        params.height = (int) (dm.heightPixels * 0.7 * 0.95);//设置当前控件布局的高度
//        singlyTextView.setGravity(Gravity.CENTER);//这是布局文件中的Android：gravity属性
//        singlyTextView.setLayoutParams(params);//将设置好的布局参数应用到控件中

        LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) scrollView_myMessage.getLayoutParams();
        params_1.width= (int) (dm.widthPixels * 0.85 * 0.7);//设置当前控件布局的宽度
//        params_1.height = (int) (dm.heightPixels * 0.7 * 0.6);//设置当前控件布局的高度
        params_1.topMargin = (int) (dm.heightPixels * 0.7 * 0.15);
//        params_1.bottomMargin = (int) (dm.heightPixels * 0.7 * 0.15);
        scrollView_myMessage.setLayoutParams(params_1);//将设置好的布局参数应用到控件中

        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.linear1);

        ViewGroup.LayoutParams lp;
        lp= linearLayout1.getLayoutParams();
        lp.width=(int) (dm.widthPixels * 0.85 * 0.7);
        lp.height=LinearLayout.LayoutParams.WRAP_CONTENT;
        linearLayout1.setLayoutParams(lp);

        Button bt_showAllMessage = (Button) view.findViewById(R.id.bt_showAllMessage);
        bt_showAllMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlyTextView.setmIndex(mOriginalStrTemp.length());
                singlyTextView.setText(mOriginalStrTemp);
                showToast(getContext(), "已停止自动下拉，下面也许还有留言哦~不信？你可以拉下去看看", Toast.LENGTH_SHORT);
            }
        });

        Button bt_close = (Button) view.findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        scrollView_myMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("onTouch", "true");
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //按下
//                        Log.i("ACTION_DOWN", "true");
                        if (!isActionDown) {
                            singlyTextView.setIndexListener(null);
//                            Toast.makeText(getContext(), "已停止自动下拉，下面也许还有留言哦~不信？你可以拉下去看看", Toast.LENGTH_SHORT).show();
                            showToast(getContext(), "已停止自动下拉，下面也许还有留言哦~不信？你可以拉下去看看", Toast.LENGTH_SHORT);
                            isActionDown = true;
                        }
//                        if (timer != null && task != null) {
//                            timer.cancel();
//                            timer.purge();
//                            timer = null;
//                            task.cancel();
//                            task = null;//清空Task
//                            Toast.makeText(getContext(), "已停止自动下拉，下面也许还有留言哦~不信？你可以拉下去看看", Toast.LENGTH_SHORT).show();
////                            Log.i("ACTION_DOWN-", "true");
//                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起
                        break;

                }
                return false;
            }
        });

//        if (isFirstRun) {
//            task = new MyTimerTask();
//            try {
//                timer = new Timer();
//                timer.schedule(task, 4000, 1000);//在4秒后执行此任务,每次间隔1秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
//                Log.i("timerIsFirstRun", "true");
//                isFirstRun = false;
//            } catch (IllegalStateException e) {
//                Log.e("IllegalStateException--", e.getMessage());
//                timer.cancel();
//                timer.purge();
//                timer = null;
//                task.cancel();
//                task = null;//清空Task
//            }
//        }

    }

    //避免反复弹出同一Toast
    public static void showToast(Context context, String msg, int duration) {
        if (!msg.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            Toast.makeText(context, msg, duration).show();
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于10秒时才显示
            if (System.currentTimeMillis() - time > 10000) {
                Toast.makeText(context, msg, duration).show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;
    }

    @Override
    public boolean getIsMAXIndex() {
        return isMAXIndex;
    }

//    /**
//     * 定时器，实现自动播放
//     */
//    private class MyTimerTask extends TimerTask {
//
//        private Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what) {
//                    case 5:
//                        scrollView_myMessage.post(new Runnable() {
//                            public void run() {
//                                scrollView_myMessage.fullScroll(ScrollView.FOCUS_DOWN);
//                            }
//                        });
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = 5;
//            handler.sendMessage(message);
//        }
//    }
}
