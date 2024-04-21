package me.carrot0322.triplesense.features.commands.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.features.commands.Command;
import me.carrot0322.triplesense.features.commands.args.ModuleArgumentType;
import me.carrot0322.triplesense.features.modules.Module;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.NotNull;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "t");
    }

    @Override
    public void executeBuild(@NotNull LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(arg("module", ModuleArgumentType.create()).executes(context -> {
            final Module module = context.getArgument("module", Module.class);

            TripleSense.moduleManager.toggleModule(module.getName());

            return SINGLE_SUCCESS;
        }));
    }
}