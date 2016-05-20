package com.hq.poc.activity.dto;

public abstract class AbstractDto<T> {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract T getEntity();

	public abstract String getFKey(T entity);
}
