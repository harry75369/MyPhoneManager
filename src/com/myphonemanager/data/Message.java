package com.myphonemanager.data;

public class Message {
	
	protected int id;
	protected String from;
	protected String body;
	protected String date;
	
	public Message(String from, String body, String date)
	{
		this.from = from;
		this.body = body;
		this.date = date;
	}
	
	public Message(int id, String from, String body, String date)
	{
		this(from, body, date);
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getDate() {
		return date;
	}

}
