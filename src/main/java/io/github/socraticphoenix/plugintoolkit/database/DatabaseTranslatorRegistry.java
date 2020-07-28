package io.github.socraticphoenix.plugintoolkit.database;

import io.github.socraticphoenix.plugintoolkit.database.translators.DefaultKeyTranslator;
import io.github.socraticphoenix.plugintoolkit.database.translators.IdentifiableTranslator;
import io.github.socraticphoenix.plugintoolkit.database.translators.UUIDTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseTranslatorRegistry {
    public static final DatabaseTranslatorRegistry GLOBAL = new DatabaseTranslatorRegistry();

    static {
        GLOBAL.register(
                new UUIDTranslator(),
                new IdentifiableTranslator()
        );
    }

    private List<ValueTranslator<?>> translators = new ArrayList<>();
    private List<KeyTranslator<?>> keyTranslators = new ArrayList<>();

    public DatabaseTranslatorRegistry register(ValueTranslator<?>... translators) {
        Collections.addAll(this.translators, translators);
        return this;
    }

    public DatabaseTranslatorRegistry register(KeyTranslator<?>... translators) {
        Collections.addAll(this.keyTranslators, translators);
        return this;
    }

    public <T> ValueTranslator<T> getValueFor(Class<T> type) {
        return (ValueTranslator<T>) this.translators.stream().filter(d -> d.type().isAssignableFrom(type)).findFirst().orElseThrow(() -> new IllegalArgumentException("No translator for: " + type.getName()));
    }

    public <K> KeyTranslator<K> getKeyFor(Class<K> type) {
        return (KeyTranslator<K>) this.keyTranslators.stream().filter(k -> k.type().isAssignableFrom(type)).findFirst().orElse(DefaultKeyTranslator.INSTANCE);
    }

}
