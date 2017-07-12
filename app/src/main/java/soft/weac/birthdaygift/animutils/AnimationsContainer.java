package soft.weac.birthdaygift.animutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

import soft.weac.birthdaygift.CakeActivity;
import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.activity.MyApplication;
import soft.weac.birthdaygift.interfaces.OnAnimationListener;

/**
 * TITLE
 * Created by shixiaoming on 16/12/27.
 */

public class AnimationsContainer {

//    private boolean playState;
    public int FPS = 30;  // 每秒播放帧数，fps = 1/t，t-动画两帧时间间隔58
    private int resId = R.array.loading_anim; //图片资源
    private Context mContext = MyApplication.getAppContext();
    // 单例
    private static AnimationsContainer mInstance;

    private ImageView bt_gift;
//    private long playTimeSum;

    public AnimationsContainer(){
    }
    //获取单例
    public static AnimationsContainer getInstance(int resId, int fps) {
        if (mInstance == null)
            mInstance = new AnimationsContainer();
        return mInstance;
    }

    public void setResId(int resId){
        this.resId = resId;
    }
    // 从xml中读取资源ID数组
    private int[] mProgressAnimFrames = getData(resId);

    /**
     * @param imageView
     * @return progress dialog animation
     */
    public FramesSequenceAnimation createProgressDialogAnim(Activity activity, ImageView imageView) {
        return new FramesSequenceAnimation(activity ,imageView, mProgressAnimFrames, FPS);
    }


    /**
     * 循环读取帧---循环播放帧
     */
    public class FramesSequenceAnimation {
        private int[] mFrames; // 帧数组
        private int mIndex; // 当前帧
        private boolean mShouldRun; // 开始/停止播放用
        private boolean mIsRunning; // 动画是否正在播放，防止重复播放
        private SoftReference<ImageView> mSoftReferenceImageView; // 软引用ImageView，以便及时释放掉
        private Handler mHandler;
        private int mDelayMillis;
        private OnAnimationStoppedListener mOnAnimationStoppedListener; //播放停止监听

        private OnAnimationListener onAnimationListener;
        private Activity activity;

        private Bitmap mBitmap = null;
        private BitmapFactory.Options mBitmapOptions;//Bitmap管理类，可有效减少Bitmap的OOM问题

        public FramesSequenceAnimation(Activity activity, ImageView imageView, int[] frames, int fps) {
            mHandler = new Handler();
            mFrames = frames;
            mIndex = -1;
            mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
            mShouldRun = false;
            mIsRunning = false;
            mDelayMillis = 1000 / fps;//帧动画时间间隔，毫秒

            bt_gift = imageView;
            this.activity = activity;

            imageView.setImageResource(mFrames[0]);

            // 当图片大小类型相同时进行复用，避免频繁GC
            if (Build.VERSION.SDK_INT >= 11) {
                Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Bitmap.Config config = bmp.getConfig();
                mBitmap = Bitmap.createBitmap(width, height, config);
                mBitmapOptions = new BitmapFactory.Options();
                //设置Bitmap内存复用
                mBitmapOptions.inBitmap = mBitmap;//Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
                mBitmapOptions.inMutable = true;//解码时返回可变Bitmap
                mBitmapOptions.inSampleSize = 1;//缩放比例
            }
        }
        //循环读取下一帧
        private int getNext() {
            mIndex++;
            if (mIndex >= mFrames.length){
                mIndex = mFrames.length-1;//120
                stop();
            }
            return mFrames[mIndex];
        }

        /**
         * 播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        public synchronized void start() {

//            playState = true;
            bt_gift.setClickable(false);
            setOnAnimationStoppedListener(new OnAnimationListener() {
                @Override
                public void AnimationStopped(boolean isStopped) {
                    if (isStopped) {
                        Intent intent = new Intent(activity, CakeActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//                        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
//                        if(version > 5 ){
//                            activity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//                        }
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), bt_gift, "shareNames").toBundle());
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Thread.sleep(3000);
//                                    activity.finish();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }).start();
                    }
                }
            });
            mShouldRun = true;
            if (mIsRunning)
                return;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ImageView imageView = mSoftReferenceImageView.get();
                    if (!mShouldRun || imageView == null) {
                        mIsRunning = false;
                        if (mOnAnimationStoppedListener != null) {
                            mOnAnimationStoppedListener.AnimationStopped();
                        }
                        return;
                    }

                    mIsRunning = true;
                    //新开线程去读下一帧
                    mHandler.postDelayed(this, mDelayMillis);

                    if (imageView.isShown()) {
                        int imageRes = getNext();
                        if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeResource(imageView.getResources(), imageRes, mBitmapOptions);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                            } else {
                                imageView.setImageResource(imageRes);
                                mBitmap.recycle();
                                mBitmap = null;
                            }
                        } else {
                            imageView.setImageResource(imageRes);
                        }
                    }

                }
            };

            mHandler.post(runnable);
        }

        /**
         * 停止播放
         */
        public synchronized void stop() {
            mShouldRun = false;
            bt_gift.setClickable(true);
            onAnimationListener.AnimationStopped(true);
//            activity.finish();
//            playState = false;
        }

        /**
         * 设置停止播放监听
         * @param listener
         */
        public void setOnAnimStopListener(OnAnimationStoppedListener listener){
            this.mOnAnimationStoppedListener = listener;
        }

        public void setOnAnimationStoppedListener(OnAnimationListener listener) {
            this.onAnimationListener = listener;
        }

        public long getPlayTimeSum() {
            long playTimeSum = (2000/FPS) * 86;
            return playTimeSum;
        }

//        public boolean getPlayState() {
//            return playState;
//        }
    }

    /**
     * 从xml中读取帧数组
     * @param resId
     * @return
     */
    private int[] getData(int resId){
        TypedArray array = mContext.getResources().obtainTypedArray(resId);

        int len = array.length();
        int[] intArray = new int[array.length()];

        for(int i = 0; i < len; i++){
            intArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return intArray;
    }

    /**
     * 停止播放监听
     */
    public interface OnAnimationStoppedListener{
        void AnimationStopped();
    }
}