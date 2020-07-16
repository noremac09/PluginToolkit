package io.github.socraticphoenix.anubismc.plugintoolkit.database;

public interface KeyTranslator<K> {

    Class<K> type();

    Object translate(K key);

}
