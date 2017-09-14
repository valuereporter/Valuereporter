package org.valuereporter.activity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by baardl on 02.03.16.
 */
@Component
@Path("/activities")
public class ActivitiesResource {
    private static final Logger log = getLogger(ActivitiesResource.class);

    private final ObjectMapper mapper;
    private final ActivitiesService activitiesService;

    @Autowired
    public ActivitiesResource(ObjectMapper mapper, ActivitiesService activitiesService) {
        this.mapper = mapper;
        this.activitiesService = activitiesService;
    }

    //Available at http://localhost:4901/reporter/observe/activities/{prefix}
    @POST
    @Path("/{prefix}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addObservationActivity(@PathParam("prefix") String prefix, String jsonBody){
        log.trace("addObservationMethod prefix {} , jsonBody {}.", prefix, jsonBody);
        List<ObservedActivity> observedActivities = null;
        try {
            observedActivities = mapper.readValue(jsonBody, new TypeReference<ArrayList<ObservedActivityJson>>(){ });
            if (observedActivities != null) {
                for (ObservedActivity observedActivity : observedActivities) {
                    observedActivity.setPrefix(prefix);
                }
            }
        } catch (IOException e) {
            log.warn("Unexpected error trying to produce list of ObservedActivity from \n prefix {} \n json {}, \n Reason {}",prefix, jsonBody, e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Error converting to requested format.").build();
        }

//        long updatedCount = observedActivities.size(); //writeOperations.addObservations(prefix,observedMethods);
        long updatedCount = activitiesService.updateActivities(prefix,observedActivities);
        String message =  "added " + updatedCount + " observedMethods.";
        Writer strWriter = new StringWriter();
        try {
            mapper.writeValue(strWriter, message);
        } catch (IOException e) {
            log.error("Could not convert {} to JSON.", updatedCount, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error converting to requested format.").build();
        }
        return Response.ok(strWriter.toString()).build();
    }
}
