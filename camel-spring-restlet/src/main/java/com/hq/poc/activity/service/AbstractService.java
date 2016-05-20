package com.hq.poc.activity.service;

import java.util.Collection;

import com.hq.poc.activity.dto.AbstractDto;

public interface AbstractService<T> {
	public AbstractDto<T> findById(final int id);

	public Collection<AbstractDto<T>> findAll();

	public Integer create(final AbstractDto<T> dto);

	public void update(final AbstractDto<T> dto);

	public void delete(final int id);

}
