package io.github.socraticphoenix.anubismc.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;

import java.util.Arrays;

public class EnumTypeVerifier implements ConfigVerifier {
    private Class<? extends Enum> type;

    public EnumTypeVerifier(Class<? extends Enum> type) {
        this.type = type;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        String target = node.getString();
        if (target == null) {
            return VerificationResult.failure(node, "Expected type: " + type.getSimpleName() + ", got: null");
        } else if (!matches(target)) {
            return VerificationResult.failure(node, "Expected one of: " + Arrays.toString(type.getEnumConstants()));
        }
        return VerificationResult.success(node);
    }

    private boolean matches(String target) {
        for (Enum en : this.type.getEnumConstants()) {
            if (en.name().equals(target)) {
                return true;
            }
        }
        return false;
    }

}
