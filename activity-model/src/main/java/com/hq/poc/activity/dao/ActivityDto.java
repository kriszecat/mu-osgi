package com.hq.poc.activity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hq.poc.activity.entity.Activity;

@JsonPropertyOrder({ "id", "employeeId", "name", "duration" })
public class ActivityDto extends AbstractDto<Activity> {

	private String name;
	private Double duration;
	private int employeeId;

	@JsonIgnore
	@Override
	public Activity toEntity() {
		Activity activity = new Activity();

		// Simplistic id mapping
		if (fKey != null) {
			if (fKey.getClass().equals(String.class)) {
				activity.setId(Integer.valueOf((String)fKey));			
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		activity.setName(name);
		activity.setDuration(duration);
		activity.setEmployeeId(employeeId);
		return activity;
	}

	@JsonIgnore
	@Override
	public void fromEntity(Activity entity) {
		this.setFKey(entity.getId());
		this.name = entity.getName();
		this.duration = entity.getDuration();
		this.employeeId = entity.getEmployeeId();
	}

	@JsonIgnore
	public String toString() {
		return String.format("ActivityDto[%s, %f, %d]", name, duration, employeeId);
	};
	
	@JsonIgnore
	public static int toId(Object fKey) {
		// Simplistic id mapping
		if (fKey.getClass().equals(String.class)) {
			return Integer.valueOf((String)fKey);			
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void setFKey(Object id) {
		// Simplistic id mapping
		if (id.getClass().equals(Integer.class)) {
			this.fKey = Integer.toString((Integer)id);			
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

}
