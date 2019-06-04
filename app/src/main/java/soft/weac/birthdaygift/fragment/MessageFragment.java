package soft.weac.birthdaygift.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.view.MarqueeTextView;

/**
 * Created by Arcobaleno on 2017/6/7.
 */

public class MessageFragment extends Fragment{

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_message, container, false);
        refresh();
        return view;
    }
    //刷新Fragment的内容
    public void refresh() {
        View linearLayout = view.findViewById(R.id.messageFragment);
        linearLayout.setVisibility(View.VISIBLE);

        MarqueeTextView marqueeTextView = (MarqueeTextView) view.findViewById(R.id.messageAbove);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) marqueeTextView.getLayoutParams();
        params.width= (int) (dm.widthPixels * 0.35);//设置当前控件布局的宽度
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;//设置当前控件布局的高度
        marqueeTextView.setGravity(Gravity.CENTER);
        marqueeTextView.setLayoutParams(params);//将设置好的布局参数应用到控件中

        Button button = (Button) view.findViewById(R.id.bt_showMessage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button.setTextColor(Color.WHITE);
                new MessageDialog()
                        .show(getFragmentManager(), "Message");
            }
        });

    }
}