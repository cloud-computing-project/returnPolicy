package si.fri.rso.samples.return_policy.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-properties")
public class RestProperties {

    @ConfigValue(value = "external-services.return_policy-service.enabled", watch = true)
    private boolean return_policyServiceEnabled;

    public boolean isReturn_policyServiceEnabled() {
        return return_policyServiceEnabled;
    }

    public void setreturn_policyServiceEnabled(boolean return_policyServiceEnabled) {
        this.return_policyServiceEnabled = return_policyServiceEnabled;
    }
}
