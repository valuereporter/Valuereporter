package org.valuereporter.sla;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
@Component
@Path("/sla/observations")
public class ObservedIntervalsResource {
    private static final Logger log = LoggerFactory.getLogger(ObservedIntervalsResource.class);

    ObservedIntervalsService service;
    private final ObjectMapper mapper;

    @Autowired
    public ObservedIntervalsResource(ObservedIntervalsService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * http://localhost:4901/reporter/observe/sla/observations/{prefix}?filter={methodName}&from=datetimeinmillis&to=datetimeinmillis
     * @param prefix
     * @param filter
     * @return
     */
    @GET
    @Path("/{prefix}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findValuableMethods(@PathParam("prefix") String prefix, @QueryParam("filter") String filter , @QueryParam("from") Long from, @QueryParam("to") Long to) {
        DateTime toDate = null;
        DateTime fromDate = null;
        if (to == null) {
            to = System.currentTimeMillis();
        }
        toDate = new DateTime(to);
        if (from != null) {
            fromDate = new DateTime(from);
        }else {
             fromDate = toDate.minusDays(7);
        }
        List<UsageStatistics> usages = service.findUsage(prefix, filter, fromDate, toDate);
        Writer strWriter = new StringWriter();
        try {
            mapper.writeValue(strWriter, usages);
        } catch (IOException e) {
            log.error("Could not convert {} UsageStatistics to JSON.", usages.size(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error converting to requested format.").build();
        }
        return Response.ok(strWriter.toString()).build();
    }
}
