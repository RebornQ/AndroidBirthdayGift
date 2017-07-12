package soft.weac.birthdaygift.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.animutils.AnimationsContainer;


public class AcceptGiftDialog extends DialogFragment {

    View view;
    ImageView bt_gift;
//    boolean isClickOne = true;
//    AnimationDrawable animationDrawable;
    AnimationsContainer.FramesSequenceAnimation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);//
        view = inflater.inflate(R.layout.dialog_fragment_accept, container, false);
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
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), (int) (dm.heightPixels * 0.7));
        }
    }

    private void refresh() {

//        handler=new Handler(){
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            public void handleMessage(Message msg){
//                if(msg.what==0x100){
//                    Intent intent = new Intent(getActivity(), CakeActivity.class);
//                    startActivity(intent);
//                    int version = Integer.valueOf(android.os.Build.VERSION.SDK);
//                    if(version > 5 ){
//                        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//                    }
////                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), bt_gift, "shareNames").toBundle());
//                    getActivity().finish();
//                }
//            }
//        };

        final View mLinearLayout = view.findViewById(R.id.dialog_surprise);
        mLinearLayout.setVisibility(View.VISIBLE);
        bt_gift = (ImageView) view.findViewById(R.id.bt_gift);
        final TextView textView = (TextView) view.findViewById(R.id.tx2);
//        bt_gift.setBackgroundResource(R.drawable.giftboxanim);
//        animationDrawable=(AnimationDrawable)bt_gift.getBackground();
        bt_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (animation == null)
                    animation = AnimationsContainer.getInstance(R.array.loading_anim, 30).createProgressDialogAnim(getActivity(), bt_gift);

                mLinearLayout.setBackgroundColor(Color.BLACK);
                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) bt_gift.getLayoutParams();//获取当前控件的布局对象
                params.height= LinearLayout.LayoutParams.MATCH_PARENT;//设置当前控件布局的高度
                params.width= LinearLayout.LayoutParams.MATCH_PARENT;//设置当前控件布局的宽度
                bt_gift.setLayoutParams(params);//将设置好的布局参数应用到控件中

                textView.setVisibility(View.GONE);

                animation.start();
//                Log.i("PlayTimeSum---", "" + animation.getPlayTimeSum());
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(animation.getPlayTimeSum());
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        Message msg=new Message();
//                        msg.what=0x100;
//                        handler.sendMessage(msg);
//                        animation.stop();
//                    }
//                }).start();

//                if (isClickOne) {
//                } else {
//                    animation.stop();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Message msg=new Message();
//                            msg.what=0x100;
//                            handler.sendMessage(msg);
//                        }
//                    }).start();
//                }
//                isClickOne = !isClickOne;
            }
        });
    }
}
