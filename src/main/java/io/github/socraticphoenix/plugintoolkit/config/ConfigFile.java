package io.github.socraticphoenix.plugintoolkit.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigFile {
    private Path path;
    private Asset asset;

    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationNode node;

    public static ConfigFile of(Path dir, String name, Object plugin) {
        return new ConfigFile(dir.resolve(name + ".conf"), Sponge.getAssetManager().getAsset(plugin, "default_" + name + ".conf").orElse(null));
    }

    public ConfigFile(Path path) {
        this(path, null);
    }

    public ConfigFile(Path path, Asset asset) {
        this.path = path;
        this.asset = asset;

        this.loader = HoconConfigurationLoader.builder()
                .setPath(this.path).build();
    }

    public String write() throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        
        HoconConfigurationLoader.builder()
                .setSink(() -> bw)
                .build().save(this.node);
        bw.close();
        sw.close();

        return sw.toString();
    }

    public void load() throws IOException {
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        if (asset != null) {
            asset.copyToFile(path, false, true);
        }

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        this.node = loader.load();
    }

    public void save() throws IOException {
        this.loader.save(this.node);
    }

    public ConfigurationNode getNode() {
        checkLoaded();
        return node;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
        return loader;
    }

    public ConfigurationNode getNode(ConfigPath path) {
        checkLoaded();
        return path.get(this.node);
    }

    public ConfigurationNode getNode(Object... path) {
        return getNode(ConfigPath.of(path));
    }

    private void checkLoaded() {
        if (this.node == null) {
            throw new IllegalStateException("Config not loaded");
        }
    }

}
