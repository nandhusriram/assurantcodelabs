package com.assurant.inc.codelabs.jersey.rs;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;

@Path("/stats")
public class MyApplicationMonitorResource {

	@Inject
    Provider<MonitoringStatistics> monitoringStatisticsProvider;
	
	@GET
    public String getStats() 
	{
        final MonitoringStatistics snapshot
            = monitoringStatisticsProvider.get().snapshot();
 
        final TimeWindowStatistics timeWindowStatistics
            = snapshot.getRequestStatistics()
              .getTimeWindowStatistics().get(0l);
 
        return "request count: " + timeWindowStatistics.getRequestCount()
            + ", average request processing [ms]: "
            + timeWindowStatistics.getAverageDuration();
    }
}
