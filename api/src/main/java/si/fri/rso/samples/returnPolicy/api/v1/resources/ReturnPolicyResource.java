package si.fri.rso.samples.returnPolicy.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.samples.returnPolicy.models.ReturnPolicy;
import si.fri.rso.samples.returnPolicy.services.ReturnPolicyBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@RequestScoped
@Path("/returnPolicy")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class ReturnPolicyResource {

    @Inject
    private ReturnPolicyBean returnPolicyBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getReturnPolicy() {

        List<ReturnPolicy> returnPolicies = returnPolicyBean.getReturnPolicy(uriInfo);

        return Response.ok(returnPolicies).build();
    }

    @GET
    @Path("/{returnPolicyId}")
    public Response getReturnPolicy(@PathParam("returnPolicyId") String returnPolicyId) {

        ReturnPolicy returnPolicy = returnPolicyBean.getReturnPolicy(returnPolicyId);

        if (returnPolicy == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(returnPolicy).build();
    }

    @POST
    public Response createReturnPolicy(ReturnPolicy returnPolicy) {

        if ((returnPolicy.getRefund() == null || returnPolicy.getRefund().isEmpty()) ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            returnPolicy = returnPolicyBean.createReturnPolicy(returnPolicy);
        }

        if (returnPolicy.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(returnPolicy).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(returnPolicy).build();
        }
    }

    @PUT
    @Path("{returnPolicyId}")
    public Response putReturnPolicy(@PathParam("returnPolicyId") String returnPolicyId, ReturnPolicy returnPolicy) {

        returnPolicy = returnPolicyBean.putReturnPolicy(returnPolicyId, returnPolicy);

        if (returnPolicy == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (returnPolicy.getId() != null)
                return Response.status(Response.Status.OK).entity(returnPolicy).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{returnPolicyId}")
    public Response deleteReturnPolicy(@PathParam("returnPolicyId") String returnPolicyId) {

        boolean deleted = returnPolicyBean.deleteReturnPolicy(returnPolicyId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
