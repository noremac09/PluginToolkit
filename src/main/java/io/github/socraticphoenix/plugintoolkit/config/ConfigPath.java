package io.github.socraticphoenix.plugintoolkit.config;

import ninja.leaping.configurate.ConfigurationNode;

import java.util.Arrays;
import java.util.Collection;

public class ConfigPath {
    public static final ConfigPath EMPTY = ConfigPath.of();

    private Object[] path;

    public ConfigPath(Object[] path) {
        this.path = path;
    }

    public static ConfigPath of(Object... path) {
        return new ConfigPath(path);
    }

    public static ConfigPath of(Collection<Object> path) {
        return new ConfigPath(path.toArray());
    }

    public ConfigurationNode get(ConfigurationNode node) {
        return node.getNode(this.path);
    }

    public ConfigPath cutPrefix(int len) {
        if (len >= path.length) {
            return EMPTY;
        } else if (len <= 0) {
            return this;
        } else {
            Object[] newPath = new Object[path.length - len];
            System.arraycopy(path, len, newPath, 0, newPath.length);
            return ConfigPath.of(newPath);
        }
    }

    public boolean exists(ConfigurationNode node) {
        return !get(node).isVirtual();
    }

    public Object[] getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigPath)) return false;
        ConfigPath that = (ConfigPath) o;
        return Arrays.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(path);
    }
}
