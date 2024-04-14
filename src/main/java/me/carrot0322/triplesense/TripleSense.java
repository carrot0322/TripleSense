package me.carrot0322.triplesense;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TripleSense implements ModInitializer, ClientModInitializer {
    public static final String NAME = "TripleSense";
    public static final String VERSION = "1.0.0-dev";

    public static float TIMER = 1f;

    public static final Logger LOGGER = LogManager.getLogger("TripleSense");
    public static me.carrot0322.triplesense.manager.ServerManager serverManager;
    public static me.carrot0322.triplesense.manager.ColorManager colorManager;
    public static me.carrot0322.triplesense.manager.RotationManager rotationManager;
    public static me.carrot0322.triplesense.manager.PositionManager positionManager;
    public static me.carrot0322.triplesense.manager.HoleManager holeManager;
    public static me.carrot0322.triplesense.manager.EventManager eventManager;
    public static me.carrot0322.triplesense.manager.SpeedManager speedManager;
    public static me.carrot0322.triplesense.manager.CommandManager commandManager;
    public static me.carrot0322.triplesense.manager.FriendManager friendManager;
    public static me.carrot0322.triplesense.manager.ModuleManager moduleManager;
    public static me.carrot0322.triplesense.manager.ConfigManager configManager;
    public static me.carrot0322.triplesense.manager.PlayerManager playerManager;


    @Override public void onInitialize() {
        eventManager = new me.carrot0322.triplesense.manager.EventManager();
        serverManager = new me.carrot0322.triplesense.manager.ServerManager();
        rotationManager = new me.carrot0322.triplesense.manager.RotationManager();
        positionManager = new me.carrot0322.triplesense.manager.PositionManager();
        friendManager = new me.carrot0322.triplesense.manager.FriendManager();
        colorManager = new me.carrot0322.triplesense.manager.ColorManager();
        commandManager = new me.carrot0322.triplesense.manager.CommandManager();
        moduleManager = new me.carrot0322.triplesense.manager.ModuleManager();
        speedManager = new me.carrot0322.triplesense.manager.SpeedManager();
        holeManager = new me.carrot0322.triplesense.manager.HoleManager();
        playerManager = new me.carrot0322.triplesense.manager.PlayerManager();
    }


    @Override public void onInitializeClient() {
        LOGGER.info("[{}] Initializing Client", NAME);
        eventManager.init();
        moduleManager.init();

        configManager = new me.carrot0322.triplesense.manager.ConfigManager();
        configManager.load();
        colorManager.init();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> configManager.save()));
    }
}
