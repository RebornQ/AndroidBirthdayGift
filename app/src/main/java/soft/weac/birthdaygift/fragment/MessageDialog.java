package soft.weac.birthdaygift.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import soft.weac.birthdaygift.CakeActivity;
import soft.weac.birthdaygift.R;
import soft.weac.birthdaygift.view.SinglyTextView;
import soft.weac.birthdaygift.view.TextIndexListener;

/**
 * Created by Arcobaleno on 2017/7/10.
 */

public class MessageDialog extends DialogFragment implements TextIndexListener{

    View view;
    public static boolean isMAXIndex = false;
    public static String  mOriginalStrTemp;

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
        SinglyTextView singlyTextView = (SinglyTextView) view.findViewById(R.id.myMessage);
//        singlyTextView.setText(getContext().getResources().getText(R.string.myMessage));
        ((CakeActivity)getActivity()).getmTextView(singlyTextView);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) singlyTextView.getLayoutParams();
        params.width= (int) (dm.widthPixels * 0.85 * 0.8);//设置当前控件布局的宽度
        params.height = (int) (dm.heightPixels * 0.7 * 0.95);//设置当前控件布局的高度
        singlyTextView.setGravity(Gravity.CENTER);//这是布局文件中的Android：gravity属性
        singlyTextView.setLayoutParams(params);//将设置好的布局参数应用到控件中
    }

    @Override
    public boolean getIsMAXIndex() {
        return isMAXIndex;
    }

}
