package io.github.socraticphoenix.anubismc.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ValueType;

public class AndElementVerifier implements ConfigVerifier {
    private ConfigVerifier verifier;

    public AndElementVerifier(ConfigVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        if (node.getValueType() == ValueType.LIST) {
            VerificationResult.Builder builder = VerificationResult.builder(node).success();
            for (ConfigurationNode child : node.getChildrenList()) {
                VerificationResult result = verifier.verify(child);
                builder.child(result);
                if (!result.isSuccessful()) {
                    builder.failure().message("One or more child nodes are invalid");
                }
            }
            return builder.build();
        } else if (node.getValueType() == ValueType.MAP) {
            VerificationResult.Builder builder = VerificationResult.builder(node).success();
            for (ConfigurationNode child : node.getChildrenMap().values()) {
                VerificationResult result = verifier.verify(child);
                builder.child(result);
                if (!result.isSuccessful()) {
                    builder.failure().message("One or more child nodes are invalid");
                }
            }
            return builder.build();
        } else {
            return verifier.verify(node);
        }
    }

}