package io.github.socraticphoenix.plugintoolkit.optional;

import org.spongepowered.api.text.Text;

public class MissingPlaceholderService implements PlaceHolderOptionalService {

    @Override
    public Text replacePlaceholders(Text text, Object source, Object observer) {
        return text;
    }

}
