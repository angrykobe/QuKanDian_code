package com.zhangku.qukandian.observer;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * activity退出时view类调用 
 * @author yuzuoning
 */
public class ExitActivityObserver {

	private static ExitActivityObserver mInst = null;

	private HashMap<Context, List<ExitActivityObserverAction>> mObserverActions;
	
	private ExitActivityObserver(){
		mObserverActions = new HashMap<Context, List<ExitActivityObserverAction>>();
	}
	
	public static ExitActivityObserver getInst(){
		if(mInst == null){
			synchronized(ExitActivityObserver.class){
				if(mInst == null){
					mInst = new ExitActivityObserver();
				}
			}
		}
		return mInst;
	}
	
	public void addExitActivityObserverAction(Context context, ExitActivityObserverAction observer){
		List<ExitActivityObserverAction> mActions = mObserverActions.get(context);
		if(mActions == null){
			mActions = new ArrayList<ExitActivityObserverAction>();
			mObserverActions.put(context, mActions);
		}
		
		if(!mActions.contains(observer)){
			mActions.add(observer);
		}
	}
	
	public void removeExitActivityObserverAction(Context context, ExitActivityObserverAction observer){
		List<ExitActivityObserverAction> mActions = mObserverActions.get(context);
		if(mActions != null){
			mActions.remove(observer);
		}
	}
	
	public void onActivityDestory(Context context){
		List<ExitActivityObserverAction> mActions = mObserverActions.get(context);
		if(mActions != null){
			int size = mActions.size();
			for (int i = size - 1; i >= 0; i--) {
				try {
					mActions.get(i).onActivityDestory();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mActions.clear();
		}
		mObserverActions.remove(context);
	}

	public interface ExitActivityObserverAction {
		
		void onActivityDestory();
	}
}
