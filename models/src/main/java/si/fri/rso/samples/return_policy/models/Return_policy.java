package si.fri.rso.samples.return_policy.models;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "return_policy")
@NamedQueries(value =
{
        @NamedQuery(name = "return_policy.getAll", query = "SELECT c FROM return_policy c"),
        @NamedQuery(name = "return_policy.findByProduct", query = "SELECT o FROM return_policy o WHERE o.productId = " +
                ":productId")
})
@UuidGenerator(name = "idGenerator")
public class Return_policy {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "contact_seller_within")
    private String contactSellerWithin;

    private Boolean refund;

    @Column(name = "restocking_fee")
    private String restockingFee;

    @Column(name = "product_id")
    private String productId;

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

    public Boolean getRefund() {
        return refund;
    }

    public void setRefund(Boolean refund) {
        this.refund = refund;
    }

    public String getRestockingFee() {
        return restockingFee;
    }

    public void setRestockingFee(String restockingFee) {
        this.restockingFee = restockingFee;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


}