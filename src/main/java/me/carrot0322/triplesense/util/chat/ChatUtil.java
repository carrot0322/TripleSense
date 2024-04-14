package me.carrot0322.triplesense.util.chat;

import me.carrot0322.triplesense.TripleSense;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.carrot0322.triplesense.features.Feature.nullCheck;
import static me.carrot0322.triplesense.util.etc.Util.mc;

public class ChatUtil {
    public static String MsgPrefix(){
        return Formatting.WHITE + "[" + Formatting.GREEN + TripleSense.NAME + Formatting.WHITE + "]" + Formatting.RESET;
    }

    public static void sendInfo(String message) {
        sendSilentMessage(MsgPrefix() + " " + Formatting.GRAY + message);
    }

    public static void sendWarning(String message) {
        sendSilentMessage(MsgPrefix() + " " + Formatting.YELLOW + message);
    }

    public static void sendError(String message) {
        sendSilentMessage(MsgPrefix() + " " + Formatting.RED + message);
    }

    public static void sendSilentMessage(String message) {
        if (nullCheck())
            return;

        mc.inGameHud.getChatHud().addMessage(Text.literal(message));
    }
}