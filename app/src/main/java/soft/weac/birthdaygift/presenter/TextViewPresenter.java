package soft.weac.birthdaygift.presenter;

import soft.weac.birthdaygift.fragment.MessageDialog;
import soft.weac.birthdaygift.view.SinglyTextView;
import soft.weac.birthdaygift.view.TextIndexListener;

/**
 * Created by Arcobaleno on 2017/7/10.
 */

public class TextViewPresenter {


//    public TextViewPresenter(Context context, SinglyTextView singlyTextView, TextIndexListener textIndexListener) {
//        if (textIndexListener.getIndex() == 1) {
//            singlyTextView.setText(context.getResources().getText(R.string.myMessage));
////            Log.i("PresenterIndex---", "I must stop!!");
//        }
//
//    }

    public TextViewPresenter(SinglyTextView singlyTextView) {
//        Log.i("SinglyTextView---", "I am ready!!");
//        singlyTextView.setText(context.getResources().getText(R.string.myMessage));
        singlyTextView.init();
        singlyTextView.start();
    }

    public TextViewPresenter(SinglyTextView singlyTextView, TextIndexListener textIndexListener) {
        if (textIndexListener.getIsMAXIndex()) {
            singlyTextView.setText(MessageDialog.mOriginalStrTemp);
//            Log.i("PresenterIndex---", "I must stop!!");
        }

    }

//    public TextViewPresenter(Context context, SinglyTextView singlyTextView, IPagerPositionListener pagerPositionListener) {
//        Log.i("PagerPosition---", String.valueOf(pagerPositionListener.getPagerPosition()));
//        if (pagerPositionListener.getPagerPosition() == 0) {
//            Log.i("PagerPosition---", "I am ready!!");
//            singlyTextView.setText(context.getResources().getText(R.string.myMessage));
//            singlyTextView.init();
//            singlyTextView.start();
//        }
//    }

}
