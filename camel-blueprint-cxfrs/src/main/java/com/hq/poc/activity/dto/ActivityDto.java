package com.hq.poc.activity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hq.poc.activity.entity.Activity;

@JsonPropertyOrder({ "id", "employeeId", "name", "duration" })
public class ActivityDto extends AbstractDto<Activity> {

	public String name;
	public Double duration;
	public int employeeId;

	@JsonIgnore
	@Override	
	public Activity getEntity() {
		Activity activity = new Activity();
		activity.setName(name);
		activity.setDuration(duration);
		activity.setEmployeeId(employeeId);
		return activity;
	}

	@JsonIgnore
	@Override
	public String getFKey(Activity entity) {
		return entity.getName();
	}

}
