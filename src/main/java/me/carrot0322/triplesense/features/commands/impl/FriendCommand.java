package me.carrot0322.triplesense.features.commands.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.features.commands.Command;
import me.carrot0322.triplesense.features.commands.args.FriendArgumentType;
import me.carrot0322.triplesense.features.commands.args.PlayerListEntryArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FriendCommand extends Command {
    public FriendCommand(){super("friend");}

    @Override
    public void executeBuild(@NotNull LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("add").then(arg("player", PlayerListEntryArgumentType.create()).executes(context -> {
            GameProfile profile = PlayerListEntryArgumentType.get(context).getProfile();

            if (!TripleSense.friendManager.isFriend(profile.getName())) {
                TripleSense.friendManager.addFriend(profile.getName());
                sendInfo("Added %s%s%s to friends.".formatted(Formatting.GREEN, profile.getName(), Formatting.GRAY));
            }
            else sendInfo("Already friends with that player.");

            return SINGLE_SUCCESS;
        })));

        builder.then(literal("del").then(arg("player", FriendArgumentType.create()).executes(context -> {
            String nickname = context.getArgument("player", String.class);

            TripleSense.friendManager.removeFriend(nickname);
            sendInfo(nickname + " has been unfriended");
            return SINGLE_SUCCESS;
        })));

        builder.executes(context -> {
            if (TripleSense.friendManager.getFriends().isEmpty()) {
                sendInfo("Friend list empty D:");
            } else {
                StringBuilder f = new StringBuilder("Friends: ");
                for (String friend : TripleSense.friendManager.getFriends()) {
                    try {
                        f.append(friend).append(", ");
                    } catch (Exception ignored) {
                    }
                }
                sendInfo(f.toString());
            }
            return SINGLE_SUCCESS;
        });
    }
}