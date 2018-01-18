package si.fri.rso.samples.returnPolicy.services;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
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
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import si.fri.rso.samples.returnPolicy.models.Product;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class ReturnPolicyBean {

    private Logger log = LogManager.getLogger(ReturnPolicyBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private ReturnPolicyBean returnPolicyBean;

    @Inject
    private RestProperties restProperties;

    @Inject
    @DiscoverService("products")
    private Optional<String> baseUrlProducts;

    private Client httpClient;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }


    public List<ReturnPolicy> getReturnPolicy() {

        TypedQuery<ReturnPolicy> query = em.createNamedQuery("ReturnPolicy.getAll", ReturnPolicy.class);

        return query.getResultList();

    }

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

        if (restProperties.isProductServiceEnabled()) {
            List<Product> products = returnPolicyBean.getProducts(returnPolicyId);
            returnPolicy.setProducts(products);
        }

        return returnPolicy;
    }

    public List<Product> getProducts(String returnPolicyId) {
        log.info("base url products " + baseUrlProducts);
        if (baseUrlProducts.isPresent()) {
            try {
                return httpClient
                        .target(baseUrlProducts.get() + "/v1/products?where=returnPolicyId:EQ:" + returnPolicyId)
                        .request().get(new GenericType<List<Product>>() {
                        });
            } catch (WebApplicationException | ProcessingException e) {
                log.error(e);
                throw new InternalServerErrorException(e);
            }
        }

        return new ArrayList<>();
    }

    public List<Product> getProductsFallback(String productId) {
        return new ArrayList<>();
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
