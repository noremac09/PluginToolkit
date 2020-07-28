package io.github.socraticphoenix.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;

public class CatalogTypeVerifier implements ConfigVerifier {
    private Class<? extends CatalogType> type;

    public CatalogTypeVerifier(Class<? extends CatalogType> type) {
        this.type = type;
    }

    @Override
    public VerificationResult verify(ConfigurationNode node) {
        String target = node.getString();
        if (target == null) {
            return VerificationResult.failure(node, "Expected type: " + type.getSimpleName() + ", got: null");
        } else if (!Sponge.getRegistry().getType(this.type, target).isPresent()) {
            return VerificationResult.failure(node, "No " + type.getSimpleName() + " with id: " + target);
        }
        return VerificationResult.success(node);
    }

}
