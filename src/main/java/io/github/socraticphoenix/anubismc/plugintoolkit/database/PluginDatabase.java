package io.github.socraticphoenix.anubismc.plugintoolkit.database;

import com.mongodb.client.MongoDatabase;

public class PluginDatabase {
    private MongoDatabase database;
    private DatabaseTranslatorRegistry registry;

    public PluginDatabase(MongoDatabase database, DatabaseTranslatorRegistry registry) {
        this.database = database;
        this.registry = registry;
    }

    public <T, K> PluginDatabaseCollection<T, K> collection(String name, Class<T> value, Class<K> key) {
        return new PluginDatabaseCollection<>(this.registry.getValueFor(value), this.registry.getKeyFor(key), this.database.getCollection(name));
    }

}
