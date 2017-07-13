package soft.weac.birthdaygift.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import soft.weac.birthdaygift.RefuseGiftActivity;

/**
 * Created by Haoye on 2016/1/15.
 */
public class MSinglyTextView extends android.support.v7.widget.AppCompatTextView{
    private String  mOriginalStr;//------用于保存原始字符串
    private long    mDuration = 300;//---默认显示每个字符的时间间隔
    private int     mIndex    = 0;//-----记录将要显示的字符的位置
    private Handler mHandler;
    private final int SHOW_NEXT_CHAR = 1;
//    private int mTextIndex = 0;

    private TextIndexListener textIndexListener;

    public MSinglyTextView(Context context){
        super(context);

        init();
        start();
    }

    public MSinglyTextView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);

        init();
        start();
    }

    public MSinglyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);

        init();
        start();
    }

    private void init() {
        mOriginalStr = getText().toString();//---保存字符串
        this.setText("");//-----先清空
//        mTextIndex = mOriginalStr.length();

        mHandler = new Handler(){
            @SuppressLint("HandlerLeak")
            public  void handleMessage(Message msg){
                if (msg.what == SHOW_NEXT_CHAR && mIndex < mOriginalStr.length()){
                    MSinglyTextView.this.setText(MSinglyTextView.this.getText(). toString()
                                               + mOriginalStr.charAt(mIndex));
                    mIndex++;
                    if (mIndex == mOriginalStr.length()) {
                        RefuseGiftActivity.isMAXIndex = true;
                        MSinglyTextView.this.textIndexListener.getIsMAXIndex();
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
    private void start() {
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

    public void setIndexListener(TextIndexListener textIndexListener) {
        this.textIndexListener = textIndexListener;
    }
}