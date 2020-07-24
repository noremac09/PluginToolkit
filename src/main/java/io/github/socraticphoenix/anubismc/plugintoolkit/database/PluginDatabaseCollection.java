package io.github.socraticphoenix.anubismc.plugintoolkit.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PluginDatabaseCollection<T, K> {
    private ValueTranslator<T> translator;
    private KeyTranslator<K> keyTranslator;
    private MongoCollection<Document> collection;

    public PluginDatabaseCollection(ValueTranslator<T> translator, KeyTranslator<K> keyTranslator, MongoCollection<Document> collection) {
        this.translator = translator;
        this.keyTranslator = keyTranslator;
        this.collection = collection;
    }

    public void insert(T value) {
        this.collection.insertOne(this.translator.serialize(value));
    }

    public void put(K key, T value) {
        this.collection.replaceOne(idQuery(key), this.translator.serialize(value));
    }

    public void remove(K key) {
        this.collection.deleteOne(idQuery(key));
    }

    public Optional<T> get(K key) {
        return Optional.ofNullable(this.collection.find(idQuery(key)).first()).map(this.translator::deserialize);
    }

    public Stream<T> getAll() {
        FindIterable<Document> find = this.collection.find();
        Spliterator<Document> spl = Spliterators.spliteratorUnknownSize(find.iterator(), 0);
        return StreamSupport.stream(spl, false).map(this.translator::deserialize);
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    private Bson idQuery(K key) {
        return Filters.eq("_id", this.keyTranslator.translate(key));
    }

}
