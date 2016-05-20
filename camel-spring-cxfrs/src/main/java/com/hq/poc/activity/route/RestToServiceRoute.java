package com.hq.poc.activity.route;

import org.apache.camel.builder.RouteBuilder;

public class RestToServiceRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
    	// Routing based on the operation invoked on the REST web service
		from("cxfrs:bean:restServer?bindingStyle=SimpleConsumer")
            .id("activity-api-restservice")
        	.toD("direct:${header.operationName}");

        // Service routing
        from("direct:createActivity").id("activity-service-create")
            .beanRef("activityService", "create");

        from("direct:getActivity").id("activity-service-findById")
            .beanRef("activityService", "findById(${header.id})");
        
        from("direct:getAllActivities").id("activity-service-findAll")
    		.beanRef("activityService", "findAll(${header.id})");

        from("direct:updateActivity").id("activity-service-update")
            .beanRef("activityService", "update");

        from("direct:cancelActivity").id("activity-service-delete")
        	.beanRef("activityService", "delete(${header.id})");
	}

}
