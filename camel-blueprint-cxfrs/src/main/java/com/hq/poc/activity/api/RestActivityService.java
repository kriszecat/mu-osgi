package com.hq.poc.activity.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.hq.poc.activity.dto.ActivityDto;

@Path("/activities/")
@Produces(value = "application/json")
@Consumes(value = "application/json")
public interface RestActivityService {

    @POST
    String createActivity(ActivityDto activity);

    @GET
    @Path("/{id}")
    ActivityDto getActivity(@PathParam("id") int id);

    @GET
    Collection<ActivityDto> getAllActivities();

    @PUT
    void updateActivity(ActivityDto activity);

    @DELETE
    @Path("/{id}")
    void cancelActivity(@PathParam("id") int id);
}
