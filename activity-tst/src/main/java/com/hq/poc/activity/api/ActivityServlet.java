package com.hq.poc.activity.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

import com.hq.poc.activity.dao.ActivityDaoService;
import com.hq.poc.activity.dao.ActivityDto;

public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = -7680000616447685425L;
	private transient ActivityDaoService activityDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();

		if (req.getParameter("add") != null) {
			String name = req.getParameter("name");
			Double duration = Double.valueOf(req.getParameter("duration"));
			addActivity(name, duration);
		} else if (req.getParameter("id") != null) {
			showActivity(writer, req.getParameter("id"));
		} else {
			showActivityList(writer);
		}
	}

	private void addActivity(String name, Double duration) {
		ActivityDto dto = new ActivityDto();
		dto.setName(name);
		dto.setDuration(duration);
		activityDao.create(dto);
	}

	private void showActivityList(PrintWriter writer) {
		writer.println("<h1>Activities</h1>");
		Collection<ActivityDto> activities = activityDao.findAll();
		for (ActivityDto activity : activities) {
			writer.println("<a href=\"?id=" + activity.getFKey() + "\">" + activity.getName() + "</a><BR/>");
		}
	}

	private void showActivity(PrintWriter writer, String id) {
		ActivityDto activity = activityDao.findById(new Integer(id));
		if (activity != null) {
			if (activity.getDuration() != null) {
				writer.println(activity.toString());
			}
		} else {
			writer.println("Activity with id " + id + " not found");
		}

	}

	@Reference
	public void setActivityDao(ActivityDaoService activityDao) {
		this.activityDao = activityDao;
	}

}
