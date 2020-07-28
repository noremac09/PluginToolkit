package io.github.socraticphoenix.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ValueType;

public class NodeTypeVerifier implements ConfigVerifier {
    private ValueType type;

    public NodeTypeVerifier(ValueType type) {
        this.type = type;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        if (node.getValueType() == this.type) {
            return VerificationResult.success(node);
        } else {
            return VerificationResult.failure(node, "expected type: " + type + ", got: " + node.getValueType());
        }
    }
}
