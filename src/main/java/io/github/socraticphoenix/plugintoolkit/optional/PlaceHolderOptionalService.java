package io.github.socraticphoenix.plugintoolkit.optional;

import org.spongepowered.api.text.Text;

public interface PlaceHolderOptionalService {

    Text replacePlaceholders(Text text, Object source, Object observer);

}
