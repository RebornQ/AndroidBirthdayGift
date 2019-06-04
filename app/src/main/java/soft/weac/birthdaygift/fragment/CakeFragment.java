package soft.weac.birthdaygift.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import soft.weac.birthdaygift.R;

public class CakeFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_cake, container, false);
        refresh();
        return view;
    }
    //刷新Fragment的内容
    public void refresh() {
        View linearLayout = view.findViewById(R.id.cakeFragment);
        linearLayout.setVisibility(View.VISIBLE);

        ImageView mySister = (ImageView) view.findViewById(R.id.mySister);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mySister.getLayoutParams();//获取当前控件的布局对象
        params.width= (int) (dm.widthPixels * 0.6);//设置当前控件布局的宽度
        params.height = (int) (dm.heightPixels * 0.75);//设置当前控件布局的高度
        mySister.setLayoutParams(params);//将设置好的布局参数应用到控件中
        Glide.with(getContext())
                .load(R.mipmap.test)
                .into(mySister);
    }
}
