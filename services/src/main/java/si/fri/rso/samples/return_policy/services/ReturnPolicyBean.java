package si.fri.rso.samples.return_policy.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.return_policy.models.Return_policy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class ReturnPolicyBean {

    @Inject
    private EntityManager em;

    public List<Return_policy> getReturn_policy(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Return_policy.class, queryParameters);

    }

    public Return_policy getReturn_policy(String return_policyId) {

        Return_policy return_policy = em.find(Return_policy.class, return_policyId);

        if (return_policy == null) {
            throw new NotFoundException();
        }

        return return_policy;
    }

    public Return_policy createReturn_policy(Return_policy return_policy) {

        try {
            beginTx();
            em.persist(return_policy);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return return_policy;
    }

    public Return_policy putReturn_policy(String return_policyId, Return_policy return_policy) {

        Return_policy c = em.find(Return_policy.class, return_policyId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            return_policy.setId(c.getId());
            return_policy = em.merge(return_policy);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return return_policy;
    }

    public boolean deleteReturn_policy(String return_policyId) {

        Return_policy return_policy = em.find(Return_policy.class, return_policyId);

        if (return_policy != null) {
            try {
                beginTx();
                em.remove(return_policy);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
