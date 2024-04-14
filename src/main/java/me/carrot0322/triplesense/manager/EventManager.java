package me.carrot0322.triplesense.manager;

import com.google.common.eventbus.Subscribe;
import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.event.Stage;
import me.carrot0322.triplesense.event.impl.*;
import me.carrot0322.triplesense.features.Feature;
import me.carrot0322.triplesense.features.commands.Command;
import me.carrot0322.triplesense.util.models.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.Formatting;

public class EventManager extends Feature {
    private final Timer logoutTimer = new Timer();

    public void init() {
        EVENT_BUS.register(this);
    }

    public void onUnload() {
        EVENT_BUS.unregister(this);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        // mc.getWindow().setTitle("OyVey 0.0.3");
        if (!fullNullCheck()) {
//            OyVey.inventoryManager.update();
            TripleSense.moduleManager.onUpdate();
            TripleSense.moduleManager.sortModules(true);
            onTick();
//            if ((HUD.getInstance()).renderingMode.getValue() == HUD.RenderingMode.Length) {
//                OyVey.moduleManager.sortModules(true);
//            } else {
//                OyVey.moduleManager.sortModulesABC();
//            }
        }
    }

    public void onTick() {
        if (fullNullCheck())
            return;
        TripleSense.moduleManager.onTick();
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == null || player.getHealth() > 0.0F)
                continue;
            EVENT_BUS.post(new DeathEvent(player));
//            PopCounter.getInstance().onDeath(player);
        }
    }

    @Subscribe
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
            return;
        if (event.getStage() == Stage.PRE) {
            TripleSense.speedManager.updateValues();
            TripleSense.rotationManager.updateRotations();
            TripleSense.positionManager.updatePosition();
        }
        if (event.getStage() == Stage.POST) {
            TripleSense.rotationManager.restoreRotations();
            TripleSense.positionManager.restorePosition();
        }
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.Receive event) {
        TripleSense.serverManager.onPacketReceived();
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket)
            TripleSense.serverManager.update();
    }

    @Subscribe
    public void onWorldRender(Render3DEvent event) {
        TripleSense.moduleManager.onRender3D(event);
    }

    @Subscribe public void onRenderGameOverlayEvent(Render2DEvent event) {
        TripleSense.moduleManager.onRender2D(event);
    }

    /*
    @Subscribe public void onKeyInput(KeyEvent event) {
        TripleSense.moduleManager.onKeyPressed(event.getKey());
    }

     */

    /*
    @Subscribe public void onChatSent(ChatEvent event) {
        if (event.getMessage().startsWith(TripleSense.commandManager.getPrefix())) {
            event.cancel();
            try {
                if (event.getMessage().length() > 1) {
                    TripleSense.commandManager.executeCommand(event.getMessage().substring(TripleSense.commandManager.getPrefix().length() - 1));
                } else {
                    Command.sendMessage("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(Formatting.RED + "An error occurred while running this command. Check the log!");
            }
        }
    }

     */
}