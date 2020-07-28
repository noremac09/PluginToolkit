package io.github.socraticphoenix.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ValueType;

public class OrElementVerifier implements ConfigVerifier {
    private ConfigVerifier verifier;

    public OrElementVerifier(ConfigVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        if (node.getValueType() == ValueType.LIST) {
            VerificationResult.Builder builder = VerificationResult.builder(node).failure().message("No child nodes are valid");
            for (ConfigurationNode child : node.getChildrenList()) {
                VerificationResult result = verifier.verify(child);
                builder.child(result);
                if (result.isSuccessful()) {
                    builder.success();
                }
            }
            return builder.build();
        } else if (node.getValueType() == ValueType.MAP) {
            VerificationResult.Builder builder = VerificationResult.builder(node).failure();
            for (ConfigurationNode child : node.getChildrenMap().values()) {
                VerificationResult result = verifier.verify(child);
                builder.child(result);
                if (result.isSuccessful()) {
                    builder.success();
                }
            }
            return builder.build();
        } else {
            return verifier.verify(node);
        }
    }

}
