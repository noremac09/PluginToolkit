package io.github.socraticphoenix.plugintoolkit.database;

public interface KeyTranslator<K> {

    Class<K> type();

    Object translate(K key);

}
