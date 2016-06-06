package com.hq.poc.activity.persistence.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Test;

import com.hq.poc.activity.dao.ActivityDaoService;
import com.hq.poc.activity.dao.ActivityDto;
import com.hq.poc.activity.persistence.impl.ActivityDaoImpl;

public class ActivityDaoTest {
	@Test
	public void testPersistence() {
		// Make sure derby.log is in target
		System.setProperty("derby.stream.error.file", "target/derby.log");
		ActivityDaoImpl activityServiceImpl = new ActivityDaoImpl();
		ActivityDaoService activityService = activityServiceImpl;

		EntityManagerFactory emf = createTestEMF();
		final EntityManager em = emf.createEntityManager();
		final String name = "hq-audit";
		em.getTransaction().begin();
		{
			activityServiceImpl.em = em;

			ActivityDto dto = new ActivityDto();
			dto.setName(name);
			dto.setEmployeeId(100007);
			dto.setDuration(10.);
			activityService.create(dto);
		}
		em.getTransaction().commit();

		em.getTransaction().begin();
		{
			ActivityDto dto2 = activityService.findById(1);
			Assert.assertEquals(name, dto2.getName());
		}
		em.getTransaction().commit();
		em.close();
	}

	private EntityManagerFactory createTestEMF() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver",
				"org.apache.derby.jdbc.EmbeddedDriver");
		properties.put("javax.persistence.jdbc.url",
				"jdbc:derby:memory:TEST;create=true");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				"activity", properties);
		return emf;
	}
}
