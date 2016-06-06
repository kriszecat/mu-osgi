package com.hq.poc.activity.dao;

public abstract class AbstractDto<T> {

	protected Object fKey;

	public Object getFKey() {
		return this.fKey;
	};

	public abstract void setFKey(Object id);

	public abstract T toEntity();

	public abstract void fromEntity(T entity);

}
