package soft.weac.birthdaygift;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Arcobaleno on 2017/7/7.
 */

/**
 * 【说明】
 * 在A创建的时候，调用 add方法把当前的A添加进去。
 * 当需要结束的时候，在B中调用 destoryActivity方法，指定添加A时的Key值来finish 掉A
 */

public class ActivityManagerApplication extends Application {

    private static Map<String, Activity> destroyMap = new HashMap<>();

    private ActivityManagerApplication() {
    }

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
