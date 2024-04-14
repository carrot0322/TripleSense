package me.carrot0322.triplesense.features.modules.client;

import com.google.common.eventbus.Subscribe;
import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.event.impl.ClientEvent;
import me.carrot0322.triplesense.features.modules.Module;
import me.carrot0322.triplesense.features.settings.Setting;
import me.carrot0322.triplesense.util.chat.ChatUtil;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class ClickGui
        extends Module {
    private static ClickGui INSTANCE = new ClickGui();
    public Setting<String> prefix = this.register(new Setting<>("Prefix", "!"));
    public Setting<Integer> red = this.register(new Setting<>("Red", 255, 0, 255));
    public Setting<Integer> green = this.register(new Setting<>("Green", 120, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<>("Blue", 0, 0, 255));
    public Setting<Integer> hoverAlpha = this.register(new Setting<>("Alpha", 255, 0, 255));
    public Setting<Integer> topRed = this.register(new Setting<>("SecondRed", 255, 0, 255));
    public Setting<Integer> topGreen = this.register(new Setting<>("SecondGreen", 120, 0, 255));
    public Setting<Integer> topBlue = this.register(new Setting<>("SecondBlue", 0, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<>("HoverAlpha", 100, 0, 255));
    public Setting<Boolean> rainbow = this.register(new Setting<>("Rainbow", false));
    public Setting<Integer> rainbowHue = this.register(new Setting<>("Delay", 240, 0, 600, v -> this.rainbow.getValue()));
    public Setting<Float> rainbowBrightness = this.register(new Setting<>("Brightness ", 150.0f, 1.0f, 255.0f, v -> this.rainbow.getValue()));
    public Setting<Float> rainbowSaturation = this.register(new Setting<>("Saturation", 150.0f, 1.0f, 255.0f, v -> this.rainbow.getValue()));
    private me.carrot0322.triplesense.features.gui.ClickGui click;

    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
        setBind(GLFW.GLFW_KEY_Y);
        this.setInstance();
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Subscribe
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                TripleSense.commandManager.setPrefix(this.prefix.getPlannedValue());
                ChatUtil.sendInfo("Prefix set to " + Formatting.DARK_GRAY + TripleSense.commandManager.getPrefix());
            }
            TripleSense.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        mc.setScreen(me.carrot0322.triplesense.features.gui.ClickGui.getClickGui());
    }

    @Override
    public void onLoad() {
        TripleSense.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        TripleSense.commandManager.setPrefix(this.prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(me.carrot0322.triplesense.features.modules.client.ClickGui.mc.currentScreen instanceof me.carrot0322.triplesense.features.gui.ClickGui)) {
            this.disable();
        }
    }
}