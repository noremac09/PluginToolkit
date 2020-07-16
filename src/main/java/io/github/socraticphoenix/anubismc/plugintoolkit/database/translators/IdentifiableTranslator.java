package io.github.socraticphoenix.anubismc.plugintoolkit.database.translators;

import io.github.socraticphoenix.anubismc.plugintoolkit.database.KeyTranslator;
import org.spongepowered.api.util.Identifiable;

public class IdentifiableTranslator implements KeyTranslator<Identifiable> {

    @Override
    public Class<Identifiable> type() {
        return Identifiable.class;
    }

    @Override
    public Object translate(Identifiable key) {
        return key.getUniqueId().toString();
    }

}
