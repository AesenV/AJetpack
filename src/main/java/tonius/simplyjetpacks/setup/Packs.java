package tonius.simplyjetpacks.setup;

import net.minecraft.item.EnumRarity;
import tonius.simplyjetpacks.config.PackDefaults;
import tonius.simplyjetpacks.item.meta.Jetpack;

/**
 * for default pack tuning refer to {@link PackDefaults}
 */
public class Packs {
    
    public static Jetpack jetpackTE1;
    
    public static void preInit() {
        if (ModType.THERMAL_EXPANSION.isLoaded()) {
            jetpackTE1 = new Jetpack(1, EnumRarity.common, "jetpackTE1");
        }
    }
    
}
