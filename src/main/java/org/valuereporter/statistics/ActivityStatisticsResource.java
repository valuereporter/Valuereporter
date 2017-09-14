package org.valuereporter.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by baardl on 02.03.16.
 */
@Component
@Path("/statistics/{prefix}")
public class ActivityStatisticsResource {
    private static final Logger log = getLogger(ActivityStatisticsResource.class);

    private final ObjectMapper mapper;
    private final ActivityStatisticsService statisticsService;

    @Autowired
    public ActivityStatisticsResource(ObjectMapper mapper, ActivityStatisticsService statisticsService) {
        this.mapper = mapper;
        this.statisticsService = statisticsService;
    }

    //Available at http://localhost:4901/reporter/observe/statistics/{prefix}/{activityName}
    @GET
    @Path("/{activityName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listLogon(@PathParam("prefix") String prefix,@PathParam("activityName") String activityName, @QueryParam("startTime") Long startTime, @QueryParam("endTime") Long endTime) {

        ActivityStatistics activityStatistics = null;
        if (activityName != null && activityName.equalsIgnoreCase("userlogon"))
       activityStatistics = statisticsService.findUserLogons(startTime, endTime);
        String resultJson = "";
        try {
            resultJson = mapper.writeValueAsString(activityStatistics);
        } catch (JsonProcessingException e) {
            log.trace("Failed to bulild json from {}. Reason {}", activityStatistics, e.getMessage());
        }
        return Response.ok(resultJson).build();

    }

}
