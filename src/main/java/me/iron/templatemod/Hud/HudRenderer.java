package me.iron.templatemod.Hud;

import me.iron.templatemod.TemplateMod;
import me.iron.templatemod.config.Settings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HudRenderer {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        if (Settings.isEnabled) {
            TemplateMod.renderDisplayItem();
        }
    }

}
