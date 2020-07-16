package io.github.socraticphoenix.anubismc.plugintoolkit.database.translators;

import io.github.socraticphoenix.anubismc.plugintoolkit.database.KeyTranslator;

import java.util.UUID;

public class UUIDTranslator implements KeyTranslator<UUID> {

    @Override
    public Class<UUID> type() {
        return UUID.class;
    }

    @Override
    public Object translate(UUID key) {
        return key.toString();
    }

}
