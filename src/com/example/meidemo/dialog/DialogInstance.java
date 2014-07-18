package com.example.meidemo.dialog;

import com.example.meidemo.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

public class DialogInstance {
	private static DialogInstance dialogInstance;
	public Context context;
	private DialogInstance() {
		// TODO Auto-generated constructor stub
	}
	public synchronized static DialogInstance getInstance(){
		
		if (dialogInstance==null) {
			dialogInstance = new DialogInstance();
		}
		return dialogInstance;
	}
	public  Dialog getProgressDialog(Context context){
		this.context = context;
		return getProgressDialog(context,null);
	}
	public Dialog getProgressDialog(Context context,View contentView){
		Dialog dialog = new Dialog(context,R.style.progressDialog);
		
		if (contentView!=null) {
			dialog.setContentView(contentView);
		}else {
		}
		return dialog;
	}
}
