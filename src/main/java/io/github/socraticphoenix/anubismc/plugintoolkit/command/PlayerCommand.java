package io.github.socraticphoenix.anubismc.plugintoolkit.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class PlayerCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            execute((Player) src, args);
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Only a player may execute this command!"));
        }
        return CommandResult.success();
    }

    protected abstract void execute(Player player, CommandContext args) throws CommandException;

    protected void error(Player player, String text) {
        player.sendMessage(Text.of(TextColors.RED, text));
    }

    protected void message(Player player, String text) {
        player.sendMessage(Text.of(TextColors.GREEN, text));
    }

}
