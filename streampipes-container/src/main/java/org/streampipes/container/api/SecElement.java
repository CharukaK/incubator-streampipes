package org.streampipes.container.api;

import org.streampipes.container.declarer.InvocableDeclarer;
import org.streampipes.container.declarer.SemanticEventConsumerDeclarer;
import org.streampipes.container.init.DeclarersSingleton;
import org.streampipes.container.init.RunningInstances;
import org.streampipes.container.util.Util;
import org.streampipes.model.base.NamedStreamPipesEntity;
import org.streampipes.model.graph.DataSinkInvocation;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sec")
public class SecElement extends InvocableElement<DataSinkInvocation, SemanticEventConsumerDeclarer> {

    public SecElement() {
        super(DataSinkInvocation.class);
    }

    @Override
    protected List<SemanticEventConsumerDeclarer> getElementDeclarers() {
        return DeclarersSingleton.getInstance().getConsumerDeclarers();
    }

    @Override
    protected String getInstanceId(String uri, String elementId) {
        return Util.getInstanceId(uri, "sec", elementId);
    }

    @GET
    @Path("{elementId}/{runningInstanceId}")
    @Produces(MediaType.TEXT_HTML)
    public Response getHtml(@PathParam("elementId") String elementId, @PathParam("runningInstanceId") String runningInstanceId) {

        InvocableDeclarer runningInstance = RunningInstances.INSTANCE.getInvocation(runningInstanceId);
        NamedStreamPipesEntity description = RunningInstances.INSTANCE.getDescription(runningInstanceId);

        if (runningInstance != null && runningInstance instanceof SemanticEventConsumerDeclarer && description != null
                && description instanceof DataSinkInvocation) {

            SemanticEventConsumerDeclarer instanceDeclarer = (SemanticEventConsumerDeclarer) runningInstance;
            DataSinkInvocation desctionDeclarer = (DataSinkInvocation) description;

            // TODO was previous getHtml, do we still need the whole method?
            return getResponse("HTML removed");


        } else {
            return getResponse("Error in element " + elementId);
       }
    }

    private Response getResponse(String text) {
        return Response.ok() //200
                    .entity(text)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .header("Access-Control-Allow-Credentials", "false")
                    .header("Access-Control-Max-Age", "60")
                    .allow("OPTIONS").build();
    }
}
