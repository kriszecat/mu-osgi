package com.hq.poc.activity.service;

import com.hq.poc.activity.entity.Activity;

public interface ActivityService {

	Activity getActivity(int activityId);

	void updateActivity(Activity activity);

	String createActivity(Activity activity);

	void cancelActivity(int activityId);
}
