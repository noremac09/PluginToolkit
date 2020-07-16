package io.github.socraticphoenix.anubismc.plugintoolkit.config.verifier;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Objects;

public class EqualsVerifier implements ConfigVerifier {
    private Object value;

    public EqualsVerifier(Object value) {
        this.value = value;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        if (Objects.equals(value, node.getValue())) {
            return VerificationResult.success(node);
        } else if (node.getValue() == null) {
            return VerificationResult.failure(node, "Expected: " + value + ", got: null");
        } else if (value == null) {
            return VerificationResult.failure(node, "Expected: null, got: " + node.getValue());
        } else {
            try {
                Object val = node.getValue(TypeToken.of(this.value.getClass()));

                if (Objects.equals(value, val)) {
                    return VerificationResult.success(node);
                } else {
                    return VerificationResult.failure(node, "Expected: " + value + ", got: " + val);
                }

            } catch (ObjectMappingException e) {
                return VerificationResult.failure(node, "Expected: " + value + ", got: " + node.getValue());
            }
        }
    }

}
