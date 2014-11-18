package com.example.meidemo.view;

/** 
 * 带清除按钮的EditText
 */
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.meidemo.R;

public class ClearTextEditView extends EditText {
	private Drawable dLeft;
	private Drawable dRight;
	private Drawable dTop;
	private Drawable dBottom;

	private Rect rBounds;

	public ClearTextEditView(Context context) {
		super(context);
		initTemplate();
	}
	/**
	 * 初始化模板，设置背景，设置右边的图片
	 */
	public void initTemplate() {
		setSingleLine(true);
//		setBackgroundResource(R.drawable.input_bg);
		Drawable right = getResources().getDrawable(R.drawable.edit_delete);
		//设置输入框右边的图片
		setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);
		change("", false);// 不让显示图片
	}
	/**
	 * 初始化text,<p/>
	 * 如果defaultValue以“share”开头，则从SharedPreUtil中获得key为code的value，再将value以&
	 * 分割，将最后一段内容设置到输入框内
	 * @param defaultValue
	 * @param code
	 *//*
	public void initText(String defaultValue, String code) {
		if (defaultValue.startsWith("share")) {
			// 获得SharedPreferences对象
			SharedPreUtil spUtil = new SharedPreUtil(getContext(),
					Constants.SHAREDPRE_MEMORY);
			String pre = spUtil.getString(code);
			if (!"".equals(pre)) {
				String[] values = pre.split("&");
				setText(values[values.length - 1]);
			}
		}
	}
*/
	public void setMaxLength(Integer maxLength) {
		setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
	}

	public ClearTextEditView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initTemplate();
	}

	public ClearTextEditView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		initTemplate();
	}
//	此方法会先于change(String,Boolean)方法执行，所以dRight不会为null。
	public void setCompoundDrawables(Drawable left, Drawable top,
			Drawable right, Drawable bottom) {
		if (left != null) {
			dLeft = left;
		}
		if (right != null) {
			dRight = right;
		}
		if (top != null) {
			dTop = top;
		}
		if (bottom != null) {
			dBottom = bottom;
		}
		
		super.setCompoundDrawables(left, top, right, bottom);
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if (this.dRight != null
				&& paramMotionEvent.getAction() == MotionEvent.ACTION_UP) {
			this.rBounds = this.dRight.getBounds();
			int x = (int) paramMotionEvent.getX();
			int y = (int) paramMotionEvent.getY();

			if (x >= (this.getWidth() - getPaddingRight() - rBounds.width())
					&& x <= (this.getWidth() - this.getPaddingRight())
					&& y >= this.getPaddingTop()
					&& y <= (this.getHeight() - this.getPaddingBottom())) {
				setText("");
				paramMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
			}
		}
		return super.onTouchEvent(paramMotionEvent);
	}

	protected void change(String paramString, boolean paramBoolean) {
		if (this.dRight != null) {
			if ((paramString.length() > 0) && (paramBoolean)) {
				setCompoundDrawables(this.dLeft, this.dTop, this.dRight, this.dBottom);
			} else {
				setCompoundDrawables(this.dLeft, this.dTop,  null, this.dBottom);
			}
		}
	}

	protected void onFocusChanged(boolean paramBoolean, int paramInt,
			Rect paramRect) {
		this.change(getText().toString(), paramBoolean);
		super.onFocusChanged(paramBoolean, paramInt, paramRect);
	}

	protected void onTextChanged(CharSequence paramCharSequence, int paramInt1,
			int paramInt2, int paramInt3) {
		this.change(paramCharSequence.toString(), isFocused());
		super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
	}
}
