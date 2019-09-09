package com.zhangku.qukandian.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class LayoutInflaterUtils {

	public static View inflateView(Context context, int resId){
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(resId, null);
	}
}
