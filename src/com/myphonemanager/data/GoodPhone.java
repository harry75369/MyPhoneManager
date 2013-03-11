package com.myphonemanager.data;

import android.annotation.SuppressLint;

@SuppressLint("NewApi")
public class GoodPhone extends BadPhone {
	
	protected String msg;
	
	public GoodPhone(String name, String number, String msg) {
		super(name, number);
		if ( this.name.equals("垃圾") ) {
			this.name = "亲";
		}
		
		if ( msg.isEmpty() ) {
			msg = "现在有事，待会回你";
		}
		this.msg = msg;
	}
	
	public GoodPhone(int id, String name, String number, String msg) {
		super(id, name, number);
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
	
	@Override
	public boolean isValid() {
		return !(name.isEmpty() || number.isEmpty() || msg.isEmpty());
	}

}
