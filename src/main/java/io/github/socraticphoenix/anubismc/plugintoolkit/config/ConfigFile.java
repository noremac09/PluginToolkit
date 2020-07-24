package io.github.socraticphoenix.anubismc.plugintoolkit.config;

import com.google.inject.internal.cglib.proxy.$Callback;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
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
        return node;
    }

    public ConfigurationNode getNode(ConfigPath path) {
        return path.get(this.node);
    }

    public ConfigurationNode getNode(Object... path) {
        return getNode(ConfigPath.of(path));
    }

}
