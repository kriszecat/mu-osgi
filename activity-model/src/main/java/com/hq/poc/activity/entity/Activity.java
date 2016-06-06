package com.hq.poc.activity.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Activity {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private Double duration;
	private int employeeId;

	public Activity() {
		super();
	}

	public Activity(String name, Double duration, int employeeId) {
		this();
		this.name = name;
		this.duration = duration;
		this.employeeId = employeeId;
	}

	public Activity copy(Activity copy) {
		this.name = copy.name;
		this.duration = copy.duration;
		this.employeeId = copy.employeeId;
		return this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
