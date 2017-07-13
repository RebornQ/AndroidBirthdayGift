package soft.weac.birthdaygift.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import soft.weac.birthdaygift.CakeActivity;
import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.fragment.MessageDialog;
import soft.weac.birthdaygift.presenter.TextViewPresenter;

/**
 * Created by Haoye on 2016/1/15.
 */
public class SinglyTextView extends android.support.v7.widget.AppCompatTextView implements TextIndexListener{
    private String  mOriginalStr;//------用于保存原始字符串
    private long    mDuration = 500;//---默认显示每个字符的时间间隔
    private int     mIndex    = 0;//-----记录将要显示的字符的位置
    private Handler mHandler;
    private final int SHOW_NEXT_CHAR = 1;

    public SinglyTextView(Context context){
        super(context);

        init();
        start();
    }

    public SinglyTextView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);

        init();
        start();
    }

    public SinglyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);

        init();
        start();
    }

    public void init() {
//        mOriginalStr = getText().toString();//---保存字符串
        mOriginalStr = (String) SinglyTextView.this.getResources().getText(R.string.myMessage);

        MessageDialog.mOriginalStrTemp = mOriginalStr;
        if (mIndex == 0) {
            this.setText("");//-----先清空
        }

        mHandler = new Handler(){
            @SuppressLint("HandlerLeak")
            public  void handleMessage(Message msg){
                if (msg.what == SHOW_NEXT_CHAR && mIndex < mOriginalStr.length()){
                    if (CakeActivity.pagerPosition == 0) {
                        if (!MessageDialog.isMAXIndex) {
                            SinglyTextView.this.setText(SinglyTextView.this.getText().toString()
                                    + mOriginalStr.charAt(mIndex));
                            mIndex++;
                        } else {
                            mIndex = mOriginalStr.length();
                        }
                        if (mIndex == mOriginalStr.length()) {
                            MessageDialog.isMAXIndex = true;
//                            Log.i("Index---", "I must stop!!");
                        }
                        new TextViewPresenter(SinglyTextView.this, SinglyTextView.this);
                    }
                }

            }
        };
    }

    /**
     * 设置显示每个字符的时间间隔
     * @param duration
     */
    public void setDuration(long duration) {
        mDuration = duration;
    }

    /**
     * 启动新线程
     */
    public void start() {
         new Thread(){
            public void run()
            {
                while (mIndex < mOriginalStr.length())
                {
                    try {
                        if (mOriginalStr.charAt(mIndex) == '！' || mOriginalStr.charAt(mIndex) == '，' || mOriginalStr.charAt(mIndex) == '。') {
                            Thread.sleep(400);
//                            Log.i("especial char---", "I found!!");
                        }
                        Thread.sleep(100);
                        mHandler.sendEmptyMessage(SHOW_NEXT_CHAR);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            }
        }.start();
    }

    /**
     * 设置当前已读取的文本长度
     * @param index
     */
    public void setmIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public boolean getIsMAXIndex() {
        return MessageDialog.isMAXIndex;
    }
}