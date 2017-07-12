package soft.weac.birthdaygift.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.view.ImageUtil;
import soft.weac.birthdaygift.view.MyGallery;

/**
 * Created by Arcobaleno on 2017/6/7.
 */

public class AlbumFragment extends Fragment {

    private View view;

    /**
     * 图片资源数组
     */
    private int[] imageResIDs;
    private MyGallery gallery;
    private int index = 0;
    private final int AUTOPLAY = 2;
    private TextView tx_galleryTips;

    private boolean isFirstRun = true;

    Timer timer = null;
    MyTimerTask task = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_album, container, false);
        refresh();
//        Log.e("onCreateView--", "I am onCreateView!!!");
        return view;
    }
    //刷新Fragment的内容
    public void refresh() {
//        Log.e("refresh--", "I am refresh!!!");
        View LinearLayout = view.findViewById(R.id.albumFragment);
        LinearLayout.setVisibility(View.VISIBLE);

        tx_galleryTips = (TextView) view.findViewById(R.id.galleryTips);

        imageResIDs = new int[]{R.mipmap.test_0, R.mipmap.test_1, R.mipmap.test_2, R.mipmap.test_3,
                R.mipmap.test_4};

        gallery = (MyGallery) view.findViewById(R.id.mygallery);

        ImageAdapter adapter = new ImageAdapter();
        gallery.setAdapter(adapter);
        gallery.setSpacing(50); //图片之间的间距
        gallery.setSelection((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % imageResIDs.length);

        final CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(getContext(), (millisUntilFinished / 1000) + "秒后再次自动播放", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {

                if (task == null) {
                    task = new MyTimerTask();
//                    Log.i("TaskNull--", "true");
                }

                try {
                    timer = new Timer();
                    timer.schedule(task, 3000, 3000);
//                    Log.i("timer", "true");
                } catch (IllegalStateException e) {
                    Log.e("IllegalStateException-", e.getMessage());
                    timer.cancel();
                    timer.purge();
                    timer = null;
                    task = null;//清空Task
                    task.cancel();
                }
            }
        };

        gallery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("onTouch", "true");
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //按下
//                        Log.i("ACTION_DOWN", "true");
                        if (timer != null && task != null) {
                            timer.cancel();
                            timer.purge();
                            timer = null;
                            task.cancel();
                            task = null;//清空Task
//                            Log.i("ACTION_DOWN-", "true");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起
                        countDownTimer.start();
                        break;

                }
                return false;
            }
        });

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onItemSelected--", String.valueOf(position % imageResIDs.length));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 设置点击事件监听
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "当前位置position:"+position+"的图片被选中了", Toast.LENGTH_SHORT).show();
            }
        });


        if (isFirstRun) {
            task = new MyTimerTask();
            try {
                timer = new Timer();
                timer.schedule(task, 3000, 3000);
                Log.i("timerIsFirstRun", "true");
                isFirstRun = false;
            } catch (IllegalStateException e) {
                Log.e("IllegalStateException--", e.getMessage());
                timer.cancel();
                timer.purge();
                timer = null;
                task = null;//清空Task
                task.cancel();
            }
        }



    }

    /**
     * 定时器，实现自动播放
     */
//    private TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//
//        }
//    };


    private class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;//用于循环滚动
        }

        @Override
        public Object getItem(int position) {
            if (position >= imageResIDs.length) {
                position = position % imageResIDs.length;
            }

            return position;
        }

        @Override
        public long getItemId(int position) {
            if (position >= imageResIDs.length) {
                position = position % imageResIDs.length;
            }
//            Log.i("getItemId--", String.valueOf(position));
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;
            if (convertView != null) {
                imageView = (ImageView) convertView;
            } else {
                imageView = new ImageView(getContext());
            }

            if (position >= imageResIDs.length) {
//                Log.i("Position--", String.valueOf(position));
//                Log.i("imageResIDs.length--", String.valueOf(imageResIDs.length));
                position = position % imageResIDs.length;
//                Log.i("PositionResult--", String.valueOf(position));
            }
//            Log.e("GetViewPosition--", String.valueOf(position));
            try {
                gallery.setSelection(position);
                Bitmap bitmap = ImageUtil.getImageBitmap(getResources(),
                        imageResIDs[position]);
                BitmapDrawable drawable = new BitmapDrawable(bitmap);
                drawable.setAntiAlias(true); // 消除锯齿
                imageView.setImageDrawable(drawable);
                Gallery.LayoutParams params = new Gallery.LayoutParams(480, 800);
                imageView.setLayoutParams(params);
                tx_galleryTips.setGravity(Gravity.CENTER);
                if (position == 0) {
                    tx_galleryTips.setText(getContext().getText(R.string.AlbumText));
                } else if (position == 1) {
                    tx_galleryTips.setText(getContext().getText(R.string.AlbumText));
                }else if (position == 2) {
                    tx_galleryTips.setText(getContext().getText(R.string.AlbumText));
                } else if (position == 3) {
                    tx_galleryTips.setText(getContext().getText(R.string.AlbumText));
                } else if (position == 4) {
                    tx_galleryTips.setText(getContext().getText(R.string.AlbumText));
                } else {
                    tx_galleryTips.setText("");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.i("Exception---", e.getMessage() + ",position:" + position);
//                position = Integer.MAX_VALUE % imageResIDs.length;
//                Log.i("Exception---", "position:" + position);
            }
            return imageView;
        }
    }

    /**
     * 定时器，实现自动播放
     */
    class MyTimerTask extends TimerTask {

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case AUTOPLAY:
                        gallery.setSelection(index);
                        break;
                    default:
                        break;
                }
            }
        };

        @Override
        public void run() {
            Message message = new Message();
            message.what = AUTOPLAY;
            index = gallery.getSelectedItemPosition();
            index++;
            handler.sendMessage(message);
        }
    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        timer.cancel();
//        Log.i("Timer---", "Cancelled");
//    }
}
