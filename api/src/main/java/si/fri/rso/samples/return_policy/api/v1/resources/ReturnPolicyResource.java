package si.fri.rso.samples.return_policy.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.samples.return_policy.models.Return_policy;
import si.fri.rso.samples.return_policy.services.ReturnPolicyBean;

import javax.enterprise.context.RequestScoped;
import org.eclipse.microprofile.metrics.annotation.Metered;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@RequestScoped
@Path("/return_policy")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class ReturnPolicyResource {

    @Inject
    private ReturnPolicyBean returnPolicyBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Metered
    public Response getReturn_policy() {

        List<Return_policy> return_policy = returnPolicyBean.getReturn_policy(uriInfo);

        return Response.ok(return_policy).build();
    }


    @GET
    @Path("{return_policyId}")
    public Response getReturn_policy(@PathParam("return_policyId") String return_policyId) {

        Return_policy return_policy = returnPolicyBean.getReturn_policy(return_policyId);

        if (return_policy == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(return_policy).build();
    }

    @POST
    public Response createReturn_policy(Return_policy return_policy) {

        if (return_policy.getContactSellerWithin() == null || return_policy.getContactSellerWithin().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            return_policy = returnPolicyBean.createReturn_policy(return_policy);
        }

        if (return_policy.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(return_policy).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(return_policy).build();
        }
    }

    @PUT
    @Path("{return_policyId}")
    public Response putReturn_policy(@PathParam("return_policyId") String return_policyId, Return_policy return_policy) {

        return_policy = returnPolicyBean.putReturn_policy(return_policyId, return_policy);

        if (return_policy == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (return_policy.getId() != null)
                return Response.status(Response.Status.OK).entity(return_policy).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{return_policyId}")
    public Response deleteReturn_policy(@PathParam("return_policyId") String return_policyId) {

        boolean deleted = returnPolicyBean.deleteReturn_policy(return_policyId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
