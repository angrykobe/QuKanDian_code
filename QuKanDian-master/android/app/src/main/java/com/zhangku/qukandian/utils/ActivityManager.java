package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ActivityManager {

    public static Stack<Activity> activityStack;
//    private static ActivityManager instance;
//
//    private ActivityManager() {
//
//    }
//
//    /**
//     * 单一实例
//     */
//    public static ActivityManager getAppManager() {
//        if (instance == null) {
//            instance = new ActivityManager();
//        }
//        return instance;
//    }

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        if(activityStack.size()>2){
            try{
                activityStack.get(0).finish();
                activityStack.remove(0);
            }catch (Exception e){
            }
        }
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 回到某个页面
     *
     * @param cls
     */
    public static void goActivity(Class<?> cls) {
        try {
            List<Activity> activities = new ArrayList<Activity>();
            for (int i = activityStack.size() - 1; i > 0; i--) {
                Activity activity = activityStack.get(i);
                if (activity != null && !activity.getClass().equals(cls)) {
                    activities.add(activity);
                    activity.finish();
                    activity = null;
                } else {
                    break;
                }
            }
            for (int i = 0; i < activities.size(); i++) {
                activityStack.remove(activities.get(i));
            }
            activities.clear();
            activities = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goActivity2(Class<?> cls) {
        try {
            List<Activity> activities = new ArrayList<Activity>();
            for (int i = activityStack.size() - 1; i > 0; i--) {
                Activity activity = activityStack.get(i);
                if (activity != null && !activity.getClass().equals(cls)) {
                    activities.add(activity);
                    activity.finish();
                    activity = null;
                } else {
                    break;
                }
            }
            for (int i = 0; i < activities.size(); i++) {
                activityStack.remove(activities.get(i));
            }
            activities.clear();
            activities = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭其他activity 只剩下一个
     *
     * @param only 不关闭的activity名称
     */
    public static void finishOtherActivity(Class<?> only) {
        try {
            List<Activity> activities = new ArrayList<Activity>();
            for (int i = 0; i < activityStack.size(); i++) {
                Activity activity = activityStack.get(i);
                if (activity != null && activity.getClass().equals(only)) {
                    break;
                } else {
                    activities.add(activity);
                    activity.finish();
                    activity = null;
                }
            }
            for (int i = 0; i < activities.size(); i++) {
                activityStack.remove(activities.get(i));
            }
            activities.clear();
            activities = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        try {
            if (activity != null) {
                try {
                    activityStack.remove(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                activity.finish();
                activity = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 移除指定的Activity 并未进行finish操作
     */
    public static void removeActivity(Activity activity) {
        try {
            if (activity != null) {
                activityStack.remove(activity);
                activity = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void finishActivityAll(String className){
        Iterator<Activity> it = activityStack.iterator();
        while(it.hasNext()){
            Activity x = it.next();
            if(x.getClass().getName().equals(className)){
                it.remove();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static void setActivityStack(Stack<Activity> activityStack) {
        ActivityManager.activityStack = activityStack;
    }


    public static Activity findActivity(String name) {
        if (activityStack == null)
            return null;
        Iterator iter = activityStack.iterator();
        while (iter.hasNext()) {
            Activity abs = (Activity) iter.next();
            if (name.equals(abs.getClass().getName()))
                return abs;
        }
        return null;
    }

//    /**
//     * BookDetailsAct，该BookDetailsAct2是否是相同BookDetailsAct2最后一个
//     * @return
//     */
//    public static boolean hasBookDetailsAct2() {
//        int i=0;
//        if (activityStack == null)
//            return false;
//        Iterator iter = activityStack.iterator();
//        while (iter.hasNext()) {
//            Activity abs = (Activity) iter.next();
//            if (abs instanceof BookDetailsAct)
//                i++;
//        }
//        return i==1;
//    }

}