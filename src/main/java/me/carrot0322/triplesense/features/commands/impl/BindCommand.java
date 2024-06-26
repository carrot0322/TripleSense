package me.carrot0322.triplesense.features.commands.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.features.commands.Command;
import me.carrot0322.triplesense.features.commands.args.ModuleArgumentType;
import me.carrot0322.triplesense.features.modules.Module;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind");
    }

    @Override
    public void executeBuild(@NotNull LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(arg("module", ModuleArgumentType.create())
                .then(arg("key", StringArgumentType.word()).executes(context -> {
                    final Module module = context.getArgument("module", Module.class);
                    final String stringKey = context.getArgument("key", String.class);

                    if (stringKey == null) {
                        sendInfo(module.getName() + " is bound to " + Formatting.GRAY + module.getBind());
                        return SINGLE_SUCCESS;
                    }

                    int key;
                    if (stringKey.equalsIgnoreCase("none") || stringKey.equalsIgnoreCase("null")) {
                        key = -1;
                    } else {
                        try {
                            key = InputUtil.fromTranslationKey("key.keyboard." + stringKey.toLowerCase()).getCode();
                        } catch (NumberFormatException e) {
                            sendError("There is no such button");
                            return SINGLE_SUCCESS;
                        }
                    }


                    if (key == 0) {
                        sendWarning("Unknown key '" + stringKey + "'!");
                        return SINGLE_SUCCESS;
                    }
                    module.setBind(key);

                    sendInfo("Bind for " + Formatting.GREEN + module.getName() + Formatting.GRAY + " set to " + Formatting.GRAY + stringKey.toUpperCase());

                    return SINGLE_SUCCESS;
                }))
        );

        builder.then(literal("list").executes(context -> {
            StringBuilder binds = new StringBuilder("Binds: ");
            for (Module feature : TripleSense.moduleManager.modules) {
                /*
                if (!Objects.equals(feature.getBind().getBind(), "None")) {
                    binds.append("\n- ").append(feature.getName()).append(" -> ").append(getShortKeyName(feature)).append(feature.getBind().isHold() ? "[hold]" : "");
                }
                 */
            }
            sendInfo(binds.toString());
            return SINGLE_SUCCESS;
        }));

        builder.then(literal("reset").executes(context -> {
            for (Module mod : TripleSense.moduleManager.modules) mod.setBind(-1);
            sendInfo("Done!");
            return SINGLE_SUCCESS;
        }));
    }
}