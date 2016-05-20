package com.hq.poc.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Before;
import org.junit.Test;

import com.hq.poc.activity.service.MockActivityService;

public class ActivityRestServiceTest extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		return "/OSGI-INF/blueprint/rest-activity.xml";
	}

	@Before
	public void setup() throws Exception {
		log.info("ActivityRestServiceTest.setup()");
		MockActivityService activityService = context().getRegistry()
				.lookupByNameAndType("activityService",
						MockActivityService.class);
		activityService.setupMockData();
	}

	@Test
	public void testCreateActivity() throws Exception {
		String json = "{\"name\":\"poc-hq-dev\",\"duration\":0.25,\"employeeId\":111007}";

		log.info("Sending activity using json payload: {}", json);

		// Use restlet component to create the activity
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		String response = template.requestBodyAndHeaders(
				"restlet:http://localhost:8080/activities?restletMethod=POST",
				json, headers, String.class);
		log.info(String.format("Created new activity with response [%s]",
				response));
		assertNotNull(response);
		assertEquals("3", response);

		response = template
				.requestBodyAndHeaders(
						"restlet:http://localhost:8080/activities/3?restletMethod=DELETE",
						json, headers, String.class);
		log.info(String.format("Deleted new activity with response [%s]",
				response));
	}

	@Test
	public void testCreateAndGetActivity() throws Exception {
		String json = "{\"name\":\"poc-hq-dev\",\"duration\":0.25,\"employeeId\":111007}";

		log.info("Sending activity using json payload: {}", json);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		String response = template.requestBodyAndHeaders(
				"restlet:http://localhost:8080/activities?restletMethod=POST",
				json, headers, String.class);
		log.info(String.format("Created new activity with response [%s]",
				response));
		assertNotNull(response);
		assertEquals("3", response);

		response = template.requestBodyAndHeader(
				"restlet:http://localhost:8080/activities/1?restletMethod=GET",
				null, "Accept", "application/json", String.class);
		log.info(String.format("Got activity with response [%s]", response));

		response = template
				.requestBodyAndHeaders(
						"restlet:http://localhost:8080/activities/3?restletMethod=DELETE",
						json, headers, String.class);
		log.info(String.format("Deleted new activity with response [%s]",
				response));

	}

}
