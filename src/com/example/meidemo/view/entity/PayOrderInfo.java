package com.example.meidemo.view.entity;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class PayOrderInfo implements Serializable{
	public String paymoney;
	public String state;
	public String result;
	public String orderid;
	@Override
	public String toString() {
		return "PayOrderInfo [paymoney=" + paymoney + ", state=" + state
				+ ", result=" + result + ", orderid=" + orderid + "]";
	}
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
}
