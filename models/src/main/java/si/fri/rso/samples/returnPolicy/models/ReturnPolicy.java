package si.fri.rso.samples.returnPolicy.models;

import org.eclipse.persistence.annotations.UuidGenerator;
import si.fri.rso.samples.returnPolicy.models.Product;

import javax.persistence.*;
import java.util.List;

@Entity(name = "returnPolicy")
@NamedQueries(value =
{
    @NamedQuery(name = "ReturnPolicy.getAll", query = "SELECT c FROM returnPolicy c"),
    @NamedQuery(name = "ReturnPolicy.findByProduct", query = "SELECT o FROM returnPolicy o WHERE o.productId = " +
        ":productId")
})
@UuidGenerator(name = "idGenerator")
public class ReturnPolicy {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "contact_seller_within")
    private String contactSellerWithin;

    private String refund;

    @Column(name = "restocking_fee")
    private String restockingFee;

    @Transient
    private List<Product> products;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactSellerWithin() {
        return contactSellerWithin;
    }

    public void setContactSellerWithin(String contactSellerWithin) {
        this.contactSellerWithin = contactSellerWithin;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getRestockingFee() {
        return restockingFee;
    }

    public void setRestockingFee(String restockingFee) {
        this.restockingFee = restockingFee;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}