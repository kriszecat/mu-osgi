package com.hq.poc.activity.service;

import com.hq.poc.activity.dto.AbstractDto;
import com.hq.poc.activity.dto.ActivityDto;
import com.hq.poc.activity.entity.Activity;

public class MockActivityService extends MockService<Activity> {
	
	@Override
	AbstractDto<Activity> createDto(Activity entity) {
		ActivityDto dto = new ActivityDto();
		dto.name = entity.getName();
		dto.employeeId = entity.getEmployeeId();
		dto.duration = entity.getDuration();
		return dto;
	}

	public void setupMockData() {
		ActivityDto dto = new ActivityDto();
		dto.name = "hq-audit";
		dto.employeeId = 100007;
		dto.duration = 10.;
		create(dto);

		dto = new ActivityDto();
		dto.name = "hq-poc";
		dto.employeeId = 100007;
		dto.duration = 15.25;
		create(dto);
	}
}
