package com.hq.poc.activity.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

import com.hq.poc.activity.dao.ActivityDto;

public class ActivityRestServiceTest extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		return "/OSGI-INF/blueprint/rest-activity.xml";
	}

	@Test
	public void testCreateActivity() throws Exception {
		String json = "{\"name\":\"poc-hq-dev\",\"duration\":0.25,\"employeeId\":111007}";

		log.info("Sending activity using json payload: {}", json);

		// Define mock route
		MockEndpoint mockEP = getMockEndpoint("mock:createActivity");
		ActivityDto dto = new ActivityDto();
		dto.setName("poc-hq-dev");
		dto.setDuration(0.25);
		dto.setEmployeeId(111007);
		mockEP.expectedMessageCount(1);
		mockEP.expectedBodiesReceived(dto);

		// Substitute former route definition
		context.getRouteDefinition("activityService-create")
			.adviceWith(context, new AdviceWithRouteBuilder() {
					@Override
					public void configure() throws Exception {
						interceptSendToEndpoint("direct:createActivity")
							.skipSendToOriginalEndpoint()
							.to("mock:createActivity");

					}

				});


		// Use restlet component to create invoke the rest API
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		String response = template.requestBodyAndHeaders(
				"restlet:http://localhost:8080/activities?restletMethod=POST",
				json, headers, String.class);
		log.info(String.format("Created new activity with response [%s]",
				response));

		mockEP.assertIsSatisfied();
	}

}
