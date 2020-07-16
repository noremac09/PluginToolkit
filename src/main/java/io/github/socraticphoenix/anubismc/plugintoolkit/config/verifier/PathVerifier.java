package io.github.socraticphoenix.anubismc.plugintoolkit.config.verifier;

import io.github.socraticphoenix.anubismc.plugintoolkit.config.ConfigPath;
import ninja.leaping.configurate.ConfigurationNode;

import java.util.ArrayList;
import java.util.List;

public class PathVerifier implements ConfigVerifier {
    private List<Entry> verifiers;

    private PathVerifier(List<Entry> verifiers) {
        this.verifiers = verifiers;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        if (verifiers.size() == 1) {
            Entry entry = this.verifiers.get(0);
            return entry.getVerifier().verify(entry.getPath().get(node));
        }

        VerificationResult.Builder builder = VerificationResult.builder(node).success();

        for (Entry entry : this.verifiers) {
            ConfigurationNode target = entry.getPath().get(node);
            VerificationResult result = entry.getVerifier().verify(target);
            builder.child(result);
            if (!result.isSuccessful()) {
                builder.failure().message("One or more child nodes are invalid");
            }
        }

        return builder.build();
    }

    public static class Builder {
        private List<Entry> verifiers = new ArrayList<>();

        public Builder add(ConfigPath path, ConfigVerifier verifier) {
            verifiers.add(new Entry(path, verifier));
            return this;
        }

        public PathVerifier build() {
            return new PathVerifier(verifiers);
        }

    }

    private static class Entry {
        private ConfigPath path;
        private ConfigVerifier verifier;

        public Entry(ConfigPath path, ConfigVerifier verifier) {
            this.path = path;
            this.verifier = verifier;
        }

        public ConfigPath getPath() {
            return path;
        }

        public ConfigVerifier getVerifier() {
            return verifier;
        }
    }

}
