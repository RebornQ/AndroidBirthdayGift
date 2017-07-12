package soft.weac.birthdaygift.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.weac.birthdaygift.R;

/**
 * Created by Arcobaleno on 2017/6/7.
 */

public class AlbumFragmentBak extends Fragment {

    private View view;

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
    }
}