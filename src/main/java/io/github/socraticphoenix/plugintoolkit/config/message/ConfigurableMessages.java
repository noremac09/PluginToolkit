package io.github.socraticphoenix.plugintoolkit.config.message;

import com.google.common.reflect.TypeToken;
import io.github.socraticphoenix.plugintoolkit.PluginToolkitPlugin;
import io.github.socraticphoenix.plugintoolkit.config.ConfigPath;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ValueType;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ConfigurableMessages {
    private Map<ConfigPath, Text> messages = new LinkedHashMap<>();

    private ConfigurationNode node;
    private Set<ConfigPath> paths = new HashSet<>();
    private Logger logger = LoggerFactory.getLogger(ConfigurableMessages.class);

    public static ConfigurableMessages create() {
        return new ConfigurableMessages();
    }

    public static ConfigurableMessages create(ConfigurationNode node) {
        return new ConfigurableMessages().node(node);
    }

    public static ConfigurableMessages create(ConfigurationNode node, Logger logger) {
        return new ConfigurableMessages().node(node).logger(logger);
    }

    public ConfigurableMessages reset() {
        this.paths.clear();
        return this;
    }

    public ConfigurableMessages node(ConfigurationNode node) {
        this.node = node;
        return this;
    }

    public ConfigurableMessages add(Object... path) {
        this.paths.add(ConfigPath.of(path));
        return this;
    }

    public ConfigurableMessages logger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public ConfigurableMessages addAllEndpoints() {
        addAllEndpoints(this.node);
        return this;
    }

    private void addAllEndpoints(ConfigurationNode node) {
        if (node.getValueType() == ValueType.SCALAR) {
            this.paths.add(cut(ConfigPath.of(node.getPath())));
        } else if (node.getValueType() == ValueType.LIST) {
            node.getChildrenList().forEach(this::addAllEndpoints);
        } else if (node.getValueType() == ValueType.MAP) {
            node.getChildrenMap().values().forEach(this::addAllEndpoints);
        }
    }

    private ConfigPath cut(ConfigPath path) {
        int nulls = 0;
        for (int i = 0; i < this.node.getPath().length; i++) {
            Object obj = this.node.getPath()[i];
            if (obj == null) {
                nulls++;
            } else {
                break;
            }
        }

        return path.cutPrefix(this.node.getPath().length - nulls);
    }

    public ConfigurableMessages load() {
        this.messages.clear();
        paths.forEach(p -> {
            ConfigurationNode msg = p.get(node);
            if (msg.getValueType().canHaveChildren()) {
                try {
                    this.messages.put(p, msg.getValue(new TypeToken<Text>() {}, (Supplier<Text>) () -> {
                        logger.error("Failed to load message at " + Arrays.toString(node.getPath()) + ":" + Arrays.toString(p.getPath()));
                        return Text.of("<message unavailable: not an object>");
                    }));
                } catch (ObjectMappingException e) {
                    logger.error("Failed to load message at " + Arrays.toString(node.getPath()) + ":" + Arrays.toString(p.getPath()), e);
                    this.messages.put(p, Text.of("<message unavailable: " + e.getMessage() + ">"));
                }
            } else {
                String str = msg.getString();
                if (str == null) {
                    logger.error("Failed to load message at " + Arrays.toString(node.getPath()) + ":" + Arrays.toString(p.getPath()));
                    this.messages.put(p, Text.of("<message unavailable: null>"));
                } else {
                    this.messages.put(p, TextSerializers.FORMATTING_CODE.deserialize(str));
                }
            }
        });
        return this;
    }

    public MessageBuilder get(ConfigPath key, Text def) {
        Text text = this.messages.get(key);
        if (text == null) {
            text = def;
        }
        return new MessageBuilder(text);
    }

    public MessageBuilder get(ConfigPath key) {
        return get(key, Text.of("<message unavailable: null>"));
    }

    public MessageBuilder get(Object... key) {
        return get(ConfigPath.of(key));
    }

    public Text getText(ConfigPath key, Text def) {
        return get(key, def).build();
    }

    public Text getText(ConfigPath key) {
        return get(key).build();
    }

    public Text getText(ConfigPath key, Object source) {
        return get(key).source(source).build();
    }

    public Text getText(ConfigPath key, Object source, Object observer) {
        return get(key).source(source).observer(observer).build();
    }

    public static class MessageBuilder {
        private Map<String, Text> replace = new HashMap<>();
        private Text text;
        private Object source;
        private Object observer;

        public MessageBuilder(Text text) {
            this.text = text;
        }

        public MessageBuilder arg(String name, Text value) {
            this.replace.put(name, value);
            return this;
        }

        public MessageBuilder source(Object source) {
            this.source = source;
            return this;
        }

        public MessageBuilder observer(Object observer) {
            this.observer = observer;
            return this;
        }

        public MessageBuilder arg(String name, String value) {
            return this.arg(name, Text.of(value));
        }

        public Text build() {
            Text text = this.text;
            for (Map.Entry<String, Text> entry : this.replace.entrySet()) {
                text = text.replace("%" + entry.getKey() + "%", entry.getValue());
            }

            return PluginToolkitPlugin.get().getPlaceHolderOptionalService().replacePlaceholders(text, this.source, this.observer);
        }

        public MessageBuilder send(MessageReceiver... receivers) {
            Text text = build();
            for (MessageReceiver receiver : receivers) {
                receiver.sendMessage(text);
            }
            return this;
        }

    }

}
