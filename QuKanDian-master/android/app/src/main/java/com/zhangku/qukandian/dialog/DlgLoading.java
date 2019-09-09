package com.zhangku.qukandian.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.zhangku.qukandian.R;


public class DlgLoading {

	private Dialog mDlgLoading;
	private TextView _textViewLoading;
	
	private DlgLoading(){
	}
	
	public static DlgLoading createDlg(Context context, String loadingStr, boolean cancelable){
		DlgLoading dlgLoading = new DlgLoading();
		dlgLoading.mDlgLoading = new Dialog(context, R.style.zhangku_dialog);
		dlgLoading.mDlgLoading.setCancelable(cancelable);
		dlgLoading.mDlgLoading.setContentView(R.layout.dlg_loading);
		dlgLoading._textViewLoading = (TextView)dlgLoading.mDlgLoading.findViewById(R.id.txt_loading);
		dlgLoading._textViewLoading.setText(loadingStr);
		return dlgLoading;
	}
	
	public static DlgLoading createDlg(Context context, String loadingStr){
		DlgLoading dlgLoading = new DlgLoading();
		dlgLoading.mDlgLoading = new Dialog(context, R.style.zhangku_dialog);
		dlgLoading.mDlgLoading.setCancelable(false);
		dlgLoading.mDlgLoading.setContentView(R.layout.dlg_loading);
		dlgLoading._textViewLoading = (TextView)dlgLoading.mDlgLoading.findViewById(R.id.txt_loading);
		dlgLoading._textViewLoading.setText(loadingStr);
		return dlgLoading;
	}
	
	public static DlgLoading createDlg(Context context){
		DlgLoading dlgLoading = new DlgLoading();
		dlgLoading.mDlgLoading = new Dialog(context, R.style.zhangku_dialog);
		dlgLoading.mDlgLoading.setCancelable(false);
		dlgLoading.mDlgLoading.setContentView(R.layout.dlg_loading);
		return dlgLoading;
	}
	
	public void closeDlg(){
//		if(mDlgLoading != null && mDlgLoading.isShowing()){
		if(mDlgLoading != null){
			mDlgLoading.dismiss();
		}
	}
	
	public void showDlg(){
		if(mDlgLoading != null){
			mDlgLoading.show();
		}
	}
	
}
