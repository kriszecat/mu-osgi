package com.hq.poc.activity.route;

import org.apache.camel.builder.RouteBuilder;

public class RestToServiceRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("cxfrs:bean:restServer?bindingStyle=SimpleConsumer")
			.id("activity-rest-endpoint")
			.choice()
				.when(header("operationName").isEqualTo("createActivity"))
					.to("direct:createActivity")
				.when(header("operationName").isEqualTo("getActivity"))
					.to("direct:getActivity")
				.when(header("operationName").isEqualTo("getAllActivities"))
					.to("direct:getAllActivities");	
	}

}
