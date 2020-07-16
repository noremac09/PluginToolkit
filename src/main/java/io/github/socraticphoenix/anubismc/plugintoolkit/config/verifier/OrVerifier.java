package io.github.socraticphoenix.anubismc.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;

import java.util.List;

public class OrVerifier implements ConfigVerifier {
    private List<ConfigVerifier> verifiers;

    public OrVerifier(List<ConfigVerifier> verifiers) {
        this.verifiers = verifiers;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        VerificationResult.Builder builder = VerificationResult.builder(node).failure().message("All tests failed");

        for (ConfigVerifier verifier : this.verifiers) {
            VerificationResult result = verifier.verify(node);
            builder.child(result);

            if (result.isSuccessful()) {
                builder.success().message();
            }
        }

        return builder.build();
    }

}
