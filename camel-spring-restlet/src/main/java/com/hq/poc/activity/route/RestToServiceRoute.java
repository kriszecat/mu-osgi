package com.hq.poc.activity.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.hq.poc.activity.dto.ActivityDto;

/**
 * A Camel Java DSL Router
 */
public class RestToServiceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	restConfiguration()
    		.component("restlet").bindingMode(RestBindingMode.json).port(8080);
    	
        rest("/activities/").produces("application/json")
        	.post().id("activity-api-create")
        		.consumes("application/json").type(ActivityDto.class).to("direct:createActivity")
        	.get("/{id}").id("activity-api-get")
        		.to("direct:readActivity")
        	.get().id("activity-api-getAll")
        		.to("direct:readAllActivity")
        	.put().id("activity-api-update")
        		.consumes("application/json").type(ActivityDto.class).to("direct:updateActivity")
        	.delete("/{id}").id("activity-api-cancel")
        		.to("direct:deleteActivity");

        from("direct:createActivity").id("activity-service-create")
        	.beanRef("activityService", "create");

        from("direct:readActivity").id("activity-service-findById")
        	.beanRef("activityService", "findById(${header.id})");
        
        from("direct:readAllActivity").id("activity-service-findAll")
    		.beanRef("activityService", "findAll()");

        from("direct:updateActivity").id("activity-service-update")
        	.beanRef("activityService", "update");

        from("direct:deleteActivity").id("activity-service-delete")
    		.beanRef("activityService", "delete(${header.id})");
    }
}
