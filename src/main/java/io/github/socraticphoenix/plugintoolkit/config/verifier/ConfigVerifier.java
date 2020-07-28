package io.github.socraticphoenix.plugintoolkit.config.verifier;

import com.google.common.reflect.TypeToken;
import io.github.socraticphoenix.plugintoolkit.config.ConfigPath;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.CatalogType;

import java.util.Arrays;

public interface ConfigVerifier {

    VerificationResult verify(ConfigurationNode node);

    static ConfigVerifier and(ConfigVerifier... verifiers) {
        return new AndVerifier(Arrays.asList(verifiers));
    }

    static ConfigVerifier or(ConfigVerifier... verifiers) {
        return new OrVerifier(Arrays.asList(verifiers));
    }

    static ConfigVerifier path(ConfigPath path, ConfigVerifier verifier) {
        return path().add(path, verifier).build();
    }

    static PathVerifier.Builder path() {
        return new PathVerifier.Builder();
    }

    static ConfigVerifier allMatch(ConfigVerifier verifier) {
        return new AndElementVerifier(verifier);
    }

    static ConfigVerifier anyMatch(ConfigVerifier verifier) {
        return new OrElementVerifier(verifier);
    }

    static ConfigVerifier type(Class<?> type) {
        return new TypeVerifier(type);
    }

    static ConfigVerifier type(TypeToken<?> token) {
        return new TypeVerifier(token);
    }

    static ConfigVerifier equal(Object val) {
        return new EqualsVerifier(val);
    }

    static ConfigVerifier range(long min, long max) {
        return new LongRangeVerifier(min, max);
    }

    static ConfigVerifier range(double min, double max) {
        return new DoubleRangeVerifier(min, max);
    }

    static ConfigVerifier catalogType(Class<? extends CatalogType> type) {
        return new CatalogTypeVerifier(type);
    }

    static ConfigVerifier enumType(Class<? extends Enum> type) {
        return new EnumTypeVerifier(type);
    }

    static ConfigVerifier oneOf(Object... val) {
        return or(Arrays.stream(val).map(ConfigVerifier::equal).toArray(ConfigVerifier[]::new));
    }

}
