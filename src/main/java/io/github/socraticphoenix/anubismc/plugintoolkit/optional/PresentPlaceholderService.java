package io.github.socraticphoenix.anubismc.plugintoolkit.optional;

import me.rojo8399.placeholderapi.PlaceholderService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class PresentPlaceholderService implements PlaceHolderOptionalService {

    @Override
    public Text replacePlaceholders(Text text, Object source, Object observer) {
        Optional<PlaceholderService> serviceOptional = Sponge.getServiceManager().provide(PlaceholderService.class);
        if (serviceOptional.isPresent()) {
            return serviceOptional.get().replacePlaceholders(text, source, observer);
        } else {
            return text;
        }
    }

}
