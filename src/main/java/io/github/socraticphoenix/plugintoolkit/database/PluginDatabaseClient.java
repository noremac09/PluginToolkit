package io.github.socraticphoenix.plugintoolkit.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class PluginDatabaseClient {
    private MongoClient client;
    private DatabaseTranslatorRegistry registry;

    public PluginDatabaseClient(MongoClient client, DatabaseTranslatorRegistry registry) {
        this.client = client;
        this.registry = registry;
    }

    public PluginDatabaseClient(String client, DatabaseTranslatorRegistry registry) {
        this.client = MongoClients.create(client);
        this.registry = registry;
    }

    public PluginDatabaseClient(String client) {
        this(client, DatabaseTranslatorRegistry.GLOBAL);
    }

    public PluginDatabase database(String name) {
        return new PluginDatabase(this.client.getDatabase(name), this.registry);
    }

    public void close() {
        this.client.close();
    }

}
