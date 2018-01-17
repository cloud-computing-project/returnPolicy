package si.fri.rso.samples.returnPolicy.services;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.returnPolicy.models.ReturnPolicy;
import si.fri.rso.samples.returnPolicy.services.config.RestProperties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@ApplicationScoped
public class ReturnPolicyBean {

    private Logger log = LogManager.getLogger(ReturnPolicyBean.class.getName());

    @Inject
    private EntityManager em;

    private Client httpClient;

    public List<ReturnPolicy> getReturnPolicy(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, ReturnPolicy.class, queryParameters);

    }

    public ReturnPolicy getReturnPolicy(String returnPolicyId) {

        ReturnPolicy returnPolicy = em.find(ReturnPolicy.class, returnPolicyId);

        if (returnPolicy == null){
            throw new NotFoundException();
        }

        return returnPolicy;
    }

    public ReturnPolicy createReturnPolicy(ReturnPolicy returnPolicy) {

        try {
            beginTx();
            em.persist(returnPolicy);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return returnPolicy;
    }

    public ReturnPolicy putReturnPolicy(String returnPolicyId, ReturnPolicy returnPolicy) {

        ReturnPolicy c = em.find(ReturnPolicy.class, returnPolicyId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            returnPolicy.setId(c.getId());
            returnPolicy = em.merge(returnPolicy);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return returnPolicy;
    }

    public boolean deleteReturnPolicy(String returnPolicyId) {

        ReturnPolicy returnPolicy = em.find(ReturnPolicy.class, returnPolicyId);

        if (returnPolicy != null) {
            try {
                beginTx();
                em.remove(returnPolicy);
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
