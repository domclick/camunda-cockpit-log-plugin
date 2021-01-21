package ru.domclick.cockpit.plugin.log.resources;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginRootResource;
import ru.domclick.cockpit.plugin.log.LogPlugin;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("plugin/" + LogPlugin.ID)
public class LogPluginRootResource extends AbstractCockpitPluginRootResource {

    public LogPluginRootResource() {
        super(LogPlugin.ID);
    }


    public LogPluginRootResource(String pluginName) {
        super(pluginName);
    }

    @Path("{engineName}/log")
    public LogResource logs(
            @PathParam("engineName") String engineName,
            @QueryParam("procDefKey") String procDefKey,
            @QueryParam("activityId") String activityId,
            @QueryParam("businessKey") String businessKey) {
        return subResource(new LogResource(engineName, procDefKey, businessKey, activityId), engineName);
    }
}
