package si.fri.rso.samples.return_policy.api.v1.health;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.samples.return_policy.api.v1.configuration.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class Return_policyServiceHealthCheck implements HealthCheck{
    @Inject
    private RestProperties restProperties;

    @Override
    public HealthCheckResponse call() {

        if (restProperties.isHealthy()) {
            return HealthCheckResponse.named(Return_policyServiceHealthCheck.class.getSimpleName()).up().build();
        } else {
            return HealthCheckResponse.named(Return_policyServiceHealthCheck.class.getSimpleName()).down().build();
        }

    }
}
