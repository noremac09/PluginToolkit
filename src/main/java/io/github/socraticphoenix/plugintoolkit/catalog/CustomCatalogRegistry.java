package io.github.socraticphoenix.plugintoolkit.catalog;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public abstract class CustomCatalogRegistry<T extends CatalogType> implements AdditionalCatalogRegistryModule<T> {
    private Map<String, T> types = new LinkedHashMap<>();

    @Override
    public void registerAdditionalCatalog(T extraCatalog) {
        this.types.put(extraCatalog.getId(), extraCatalog);
    }

    @Override
    public Optional<T> getById(String id) {
        return Optional.ofNullable(this.types.get(id));
    }

    @Override
    public Collection<T> getAll() {
        return this.types.values();
    }

    public void remove(T catalog) {
        remove(catalog.getId());
    }

    public void remove(String id) {
        this.types.remove(id);
    }

}
