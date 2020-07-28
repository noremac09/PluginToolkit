package io.github.socraticphoenix.plugintoolkit.database;

import com.mongodb.client.MongoDatabase;
import ninja.leaping.configurate.ConfigurationNode;

public class PluginDatabase {
    private MongoDatabase database;
    private DatabaseTranslatorRegistry registry;

    public PluginDatabase(MongoDatabase database, DatabaseTranslatorRegistry registry) {
        this.database = database;
        this.registry = registry;
    }

    public static PluginDatabase from(ConfigurationNode node, DatabaseTranslatorRegistry registry) {
        return new PluginDatabaseClient(node.getNode("uri").getString("mongodb://localhost:27017"), registry).database(node.getNode("name").getString("database"));
    }

    public static PluginDatabase from(ConfigurationNode node) {
        return from(node, DatabaseTranslatorRegistry.GLOBAL);
    }

    public <T, K> PluginDatabaseCollection<T, K> collection(String name, Class<T> value, Class<K> key) {
        return new PluginDatabaseCollection<>(this.registry.getValueFor(value), this.registry.getKeyFor(key), this.database.getCollection(name));
    }

}
