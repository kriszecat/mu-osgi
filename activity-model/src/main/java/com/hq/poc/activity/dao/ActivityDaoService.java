package com.hq.poc.activity.dao;

import java.util.Collection;

public interface ActivityDaoService {

	public ActivityDto findById(int id);

	public Collection<ActivityDto> findAll();

	public ActivityDto create(ActivityDto dto);

	public void update(ActivityDto dto);

	public void delete(int id);

}
