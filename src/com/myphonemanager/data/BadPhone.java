package com.myphonemanager.data;

import android.annotation.SuppressLint;

@SuppressLint("NewApi")
public class BadPhone {
	
	protected int id;
	protected String name;
	protected String number;

	public BadPhone(String name, String number) {
		if ( name.isEmpty() ) {
			name = "垃圾";
		}
		
		this.id = 0;
		this.name = name;
		this.number = number;
	}
	
	public BadPhone(int id, String name, String number) {
		this(name, number);
		this.id = id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNumber() {
		return number;
	}

	public boolean isValid() {
		return !(name.isEmpty() || number.isEmpty());
	}

}
