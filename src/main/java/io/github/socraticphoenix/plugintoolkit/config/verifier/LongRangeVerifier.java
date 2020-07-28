package io.github.socraticphoenix.plugintoolkit.config.verifier;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class LongRangeVerifier implements ConfigVerifier {
    private long min;
    private long max;

    public LongRangeVerifier(long min, long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        try {
            Long l = node.getValue(new TypeToken<Long>() {});
            if (l == null) {
                return VerificationResult.failure(node, "Expected type: integer, got: null");
            } else if (l < min || l >= max) {
                return VerificationResult.failure(node, "Expected value in range [" + min + ", " + max + "), got: " + l);
            }
        } catch (ObjectMappingException e) {
            return VerificationResult.failure(node, "Expected type: integer, got: null");
        }
        return VerificationResult.success(node);
    }
}
