package soft.weac.birthdaygift;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Application基类
 * Created by shixiaoming on 16/12/6.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fresco.initialize(this);//Fresco初始化
        MyApplication.mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.mContext;
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(
                getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 【说明】
     * 在A创建的时候，调用 add方法把当前的A添加进去。
     * 当需要结束的时候，在B中调用 destoryActivity方法，指定添加A时的Key值来finish 掉A
     */
    private static Map<String, Activity> destroyMap = new HashMap<>();

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestroyActivity(Activity activity, String activityName) {
        destroyMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destroyActivity(String activityName) {
        Set<String> keySet = destroyMap.keySet();
        for (String key : keySet) {
            destroyMap.get(key).finish();
        }
    }

    public static Activity getActivity(String activityName) {
        Set<String> keySet = destroyMap.keySet();
        for (String key : keySet) {
            return destroyMap.get(key);
        }
        return null;
    }
}
