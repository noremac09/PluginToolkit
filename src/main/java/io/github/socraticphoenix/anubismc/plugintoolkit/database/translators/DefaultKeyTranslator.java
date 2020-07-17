package io.github.socraticphoenix.anubismc.plugintoolkit.database.translators;

import io.github.socraticphoenix.anubismc.plugintoolkit.database.KeyTranslator;

public class DefaultKeyTranslator implements KeyTranslator<Object> {
    public static final DefaultKeyTranslator INSTANCE = new DefaultKeyTranslator();

    @Override
    public Class<Object> type() {
        return Object.class;
    }

    @Override
    public Object translate(Object key) {
        return String.valueOf(key);
    }

}
