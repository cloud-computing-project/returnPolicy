package si.fri.rso.samples.returnPolicy.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-properties")
public class RestProperties {

    @ConfigValue(value = "external-services.returnPolicy-service.enabled", watch = true)
    private boolean returnPolicyServiceEnabled;

    public boolean isreturnPolicyServiceEnabled() {
        return returnPolicyServiceEnabled;
    }

    public void setreturnPolicyServiceEnabled(boolean returnPolicyServiceEnabled) {
        this.returnPolicyServiceEnabled = returnPolicyServiceEnabled;
    }
}
