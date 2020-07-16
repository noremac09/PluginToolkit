package io.github.socraticphoenix.anubismc.plugintoolkit.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigFile {
    private Path path;
    private Asset asset;

    private ConfigurationLoader<CommentedConfigurationNode> loader;

    private ConfigurationNode node;

    public ConfigFile(Path path) {
        this(path, null);
    }

    public ConfigFile(Path path, Asset asset) {
        this.path = path;
        this.asset = asset;

        this.loader = HoconConfigurationLoader.builder()
                .setPath(this.path).build();
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

    public ConfigurationNode getNode() {
        return node;
    }
    
}
