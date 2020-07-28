package io.github.socraticphoenix.plugintoolkit.config.verifier;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class TypeVerifier implements ConfigVerifier {
    private TypeToken<?> type;

    public TypeVerifier(TypeToken<?> type) {
        this.type = type;
    }

    public TypeVerifier(Class<?> type) {
        this(TypeToken.of(type));
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        try {
            if (node.getValue(this.type) != null) {
                return VerificationResult.success(node);
            } else {
                return VerificationResult.failure(node, "Required type: " + type.toString());
            }
        } catch (ObjectMappingException e) {
            return VerificationResult.failure(node, "Required type: " + type.toString());
        }
    }
}
