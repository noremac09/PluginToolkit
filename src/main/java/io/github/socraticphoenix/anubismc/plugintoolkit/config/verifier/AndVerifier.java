package io.github.socraticphoenix.anubismc.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;

import java.util.List;

public class AndVerifier implements ConfigVerifier {
    private List<ConfigVerifier> verifiers;

    public AndVerifier(List<ConfigVerifier> verifiers) {
        this.verifiers = verifiers;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        VerificationResult.Builder builder = VerificationResult.builder(node).success();

        for (ConfigVerifier verifier : this.verifiers) {
            VerificationResult result = verifier.verify(node);
            builder.child(result);

            if (!result.isSuccessful()) {
                builder.failure().message("One or more tests failed");
            }
        }

        return builder.build();
    }

}
