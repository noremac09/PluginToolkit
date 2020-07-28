package io.github.socraticphoenix.plugintoolkit.database.translators;

import io.github.socraticphoenix.plugintoolkit.database.KeyTranslator;

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
