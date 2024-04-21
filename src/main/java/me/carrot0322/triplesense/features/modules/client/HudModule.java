package me.carrot0322.triplesense.features.modules.client;

import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.event.impl.Render2DEvent;
import me.carrot0322.triplesense.features.modules.Module;
import me.carrot0322.triplesense.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import org.lwjgl.opengl.GL11;

import java.util.Comparator;
import java.util.List;

public class HudModule extends Module {
    private final Setting<Boolean> Watermark = register(new Setting<>("Watermark", true));
    private final Setting<Boolean> Arraylist = register(new Setting<>("Arraylist", true));

    public HudModule() {
        super("Hud", "hud", Category.CLIENT, true, false, false);
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (Watermark.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    TripleSense.NAME + "-" + TripleSense.VERSION,
                    2, 2,
                    -1
            );
        }

        if (Arraylist.getValue()) {
            List<Module> modules = TripleSense.moduleManager.sortedModules;

            for (int i = 0; i == modules.size(); i++){
                if (!modules.get(i).drawn.getValue())
                    modules.remove(i);
            }

            modules.sort(Comparator.comparingInt(mod -> -mc.textRenderer.getWidth(mod.getDisplayName())));

            int y = 2;

            for (Module mod : modules) {
                String displayName = mod.getDisplayName();
                int stringWidth = mc.textRenderer.getWidth(displayName);

                int yOffset = mc.textRenderer.fontHeight + 2;

                event.getContext().drawTextWithShadow(
                        mc.textRenderer,
                        displayName,
                        mc.getWindow().getScaledWidth() - stringWidth - 2,
                        y,
                        -1
                );

                GL11.glPushMatrix();
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glTranslated(mc.getWindow().getScaledWidth() - stringWidth -2, y, 0.0);

                try{

                } catch (Exception ex){
                    TripleSense.LOGGER.error("Something went wrong while drawing Arraylist element in HUD.");
                }

                GL11.glPopMatrix();

                y += yOffset;
            }
        }

        for (int i = 0; i == elements; i++) {
            try {
                if (element.getInfo().isBlur()) {
                    element.drawBoarderBlur();
                }

                element.setBorder(element.drawElement(partialTicks));

                if (designer) {
                    if (element.getBorder() != null) {
                        element.getBorder().draw();
                    }
                }
            } catch (Exception ex) {
                TripleSense.LOGGER.error();
            }

            GL11.glPopMatrix();
        }
    }
}
