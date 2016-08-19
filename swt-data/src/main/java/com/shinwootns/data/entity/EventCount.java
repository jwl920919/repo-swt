package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventCount implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer count;
	private Timestamp time;

	public EventCount() {
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}