package io.github.socraticphoenix.plugintoolkit.config.verifier;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class DoubleRangeVerifier implements ConfigVerifier {
    private double min;
    private double max;

    public DoubleRangeVerifier(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        try {
            Double l = node.getValue(new TypeToken<Double>() {});
            if (l == null) {
                return VerificationResult.failure(node, "Expected type: decimal, got: null");
            } else if (l < min || l >= max) {
                return VerificationResult.failure(node, "Expected value in range [" + min + ", " + max + "), got: " + l);
            }
        } catch (ObjectMappingException e) {
            return VerificationResult.failure(node, "Expected type: decimal, got: null");
        }
        return VerificationResult.success(node);
    }

}
