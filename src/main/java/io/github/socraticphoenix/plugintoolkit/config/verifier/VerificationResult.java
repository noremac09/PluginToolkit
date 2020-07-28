package io.github.socraticphoenix.plugintoolkit.config.verifier;

import ninja.leaping.configurate.ConfigurationNode;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VerificationResult {
    private ConfigurationNode node;

    private boolean successful;
    private String message;
    private List<VerificationResult> children;

    public VerificationResult(ConfigurationNode node, boolean successful, String message, List<VerificationResult> children) {
        this.node = node;
        this.successful = successful;
        this.message = message;
        this.children = children;
    }

    public static VerificationResult success(ConfigurationNode node) {
        return new VerificationResult(node, true, "Valid", new ArrayList<>());
    }

    public static VerificationResult success(ConfigurationNode node, List<VerificationResult> children) {
        return new VerificationResult(node, true, "Valid", children);
    }

    public static VerificationResult success(ConfigurationNode node, String message, List<VerificationResult> children) {
        return new VerificationResult(node, true, message, children);
    }

    public static VerificationResult failure(ConfigurationNode node) {
        return new VerificationResult(node, false, "Invalid", new ArrayList<>());
    }

    public static VerificationResult failure(ConfigurationNode node, String message) {
        return new VerificationResult(node, false, message, new ArrayList<>());
    }

    public static VerificationResult failure(ConfigurationNode node, String message, List<VerificationResult> children) {
        return new VerificationResult(node, false, message, children);
    }

    public static VerificationResult.Builder builder(ConfigurationNode node) {
        return new VerificationResult.Builder(node);
    }

    public void print(Logger logger) {
        print(logger, true);
    }

    public void print(Logger logger, boolean hideSuccess) {
        if (successful) {
            generateMessage(hideSuccess).forEach(logger::info);
        } else {
            logger.error("Failed config validation!");
            generateMessage(hideSuccess).forEach(logger::error);
        }
    }

    public List<String> generateMessage() {
        return generateMessage(true);
    }

    public List<String> generateMessage(boolean hideSuccess) {
        List<String> str = new ArrayList<>();
        generateMessage(str, 0, hideSuccess);
        return str;
    }

    public void generateMessage(List<String> msgs, int indent, boolean hideSuccess) {
        if (!hideSuccess || !this.successful) {
            msgs.add(indent(indent) + String.join(".", Arrays.stream(this.node.getPath()).map(String::valueOf).toArray(String[]::new)) + ":" + message + ", caused by:");
            this.children.forEach(v -> v.generateMessage(msgs, indent + 1, hideSuccess));
        }
    }

    private String indent(int indent) {
        StringBuilder k = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            k.append(" ");
        }
        return k.toString();
    }

    public ConfigurationNode getNode() {
        return node;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }

    public List<VerificationResult> getChildren() {
        return children;
    }


    public static class Builder {
        private ConfigurationNode node;
        private boolean success;
        private String message;
        private List<VerificationResult> children = new ArrayList<>();

        public Builder(ConfigurationNode node) {
            this.node = node;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder message() {
            this.message = null;
            return this;
        }

        public Builder success() {
            this.success = true;
            return this;
        }

        public Builder failure() {
            this.success = false;
            return this;
        }

        public Builder child(VerificationResult... results) {
            Collections.addAll(this.children, results);
            return this;
        }

        public Builder child(Collection<VerificationResult> children) {
            this.children.addAll(children);
            return this;
        }

        public VerificationResult build() {
            if (message == null) {
                message = success ? "Valid" : "Invalid";
            }

            return new VerificationResult(node, success, message, children);
        }

    }

}
