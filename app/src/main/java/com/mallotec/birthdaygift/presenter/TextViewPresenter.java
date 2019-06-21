package com.mallotec.birthdaygift.presenter;

import com.mallotec.birthdaygift.fragment.MessageDialog;
import com.mallotec.birthdaygift.view.SinglyTextView;
import com.mallotec.birthdaygift.view.TextIndexListener;

/**
 * Created by Arcobaleno on 2017/7/10.
 */

public class TextViewPresenter {

    public TextViewPresenter(SinglyTextView singlyTextView, TextIndexListener textIndexListener) {
        if (textIndexListener.getIsMAXIndex()) {
            singlyTextView.setText(MessageDialog.mOriginalStrTemp);
//            Log.i("PresenterIndex---", "I must stop!!");
        }

    }
}
