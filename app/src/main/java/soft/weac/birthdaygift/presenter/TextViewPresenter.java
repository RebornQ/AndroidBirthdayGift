package soft.weac.birthdaygift.presenter;

import soft.weac.birthdaygift.fragment.MessageDialog;
import soft.weac.birthdaygift.view.SinglyTextView;
import soft.weac.birthdaygift.view.TextIndexListener;

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
