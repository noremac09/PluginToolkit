package io.github.socraticphoenix.anubismc.plugintoolkit.database.translators;

import io.github.socraticphoenix.anubismc.plugintoolkit.database.KeyTranslator;
import org.bson.Document;

public class DefaultKeyTranslator implements KeyTranslator<Object> {
    public static final DefaultKeyTranslator INSTANCE = new DefaultKeyTranslator();

    @Override
    public Class<Object> type() {
        return Object.class;
    }

    @Override
    public Object translate(Object key) {
        if (key instanceof Number || key instanceof Boolean || key instanceof Character || key instanceof Document) {
            return key;
        } else {
            return String.valueOf(key);
        }
    }

}
