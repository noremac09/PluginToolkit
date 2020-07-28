package io.github.socraticphoenix.plugintoolkit.database;

import org.bson.Document;

public interface ValueTranslator<T> {

    Class<T> type();

    Document serialize(T val);

    T deserialize(Document obj);

    default DocumentBuilder document() {
        return new DocumentBuilder();
    }

    class DocumentBuilder {
        private Document document = new Document();

        public DocumentBuilder add(String key, Object value) {
            this.document.append(key, value);
            return this;
        }

        public DocumentBuilder id(Object value) {
            return add("_id", value);
        }

        public Document get() {
            return this.document;
        }

    }

}
