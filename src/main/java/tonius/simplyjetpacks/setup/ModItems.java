package tonius.simplyjetpacks.setup;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tonius.simplyjetpacks.SimplyJetpacks;
import tonius.simplyjetpacks.config.Config;
import tonius.simplyjetpacks.crafting.UpgradingRecipe;
import tonius.simplyjetpacks.integration.TEItems;
import tonius.simplyjetpacks.item.ItemMeta;
import tonius.simplyjetpacks.item.ItemMeta.MetaItem;
import tonius.simplyjetpacks.item.ItemPack.ItemJetpack;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ModItems {
    
    public static ItemJetpack jetpacksCommon = null;
    
    public static ItemJetpack jetpacksTE = null;
    
    public static ItemMeta components = null;
    public static ItemMeta armorPlatings = null;
    public static ItemMeta particleCustomizers = null;
    
    public static ItemStack jetpackTE1 = null;
    
    public static ItemStack leatherStrap = null;
    public static ItemStack jetpackIcon = null;
    public static ItemStack thrusterTE1 = null;
    
    public static ItemStack armorPlatingTE1 = null;
    
    public static ItemStack particleDefault = null;
    public static ItemStack particleNone = null;
    public static ItemStack particleSmoke = null;
    public static ItemStack particleRainbowSmoke = null;
    
    private static boolean teAvailable = false;
    
    public static void preInit() {
        teAvailable = ModType.THERMAL_EXPANSION.isLoaded() && Config.enableIntegrationTE;
        
        constructItems();
        registerItems();
    }
    
    public static void init() {
        if (teAvailable) {
            TEItems.init();
        }
        
        registerRecipes();
        doIMC();
    }
    
    private static void constructItems() {
        SimplyJetpacks.logger.info("Constructing items");
        
        jetpacksCommon = new ItemJetpack(ModType.SIMPLY_JETPACKS);
        
        if (teAvailable) {
            jetpacksTE = new ItemJetpack(ModType.THERMAL_EXPANSION);
            jetpackTE1 = jetpacksTE.putPack(1, Packs.jetpackTE1);
        }
       
        components = new ItemMeta("components");
        armorPlatings = new ItemMeta("armorPlatings");
        particleCustomizers = new ItemMeta("particleCustomizers");
        
        leatherStrap = components.addMetaItem(0, new MetaItem("leatherStrap", null, EnumRarity.common), true, false);
        jetpackIcon = components.addMetaItem(1, new MetaItem("jetpack.icon", null, EnumRarity.common, false, true), false, false);
        
        if (teAvailable) {
            thrusterTE1 = components.addMetaItem(11, new MetaItem("thruster.te.1", null, EnumRarity.common), true, false);
            
            armorPlatingTE1 = armorPlatings.addMetaItem(1, new MetaItem("armorPlating.te.1", null, EnumRarity.common), true, false);
        }
        
        particleDefault = particleCustomizers.addMetaItem(0, new MetaItem("particle.0", "particleCustomizers", EnumRarity.common), true, false);
        particleNone = particleCustomizers.addMetaItem(1, new MetaItem("particle.1", "particleCustomizers", EnumRarity.common), true, false);
        particleSmoke = particleCustomizers.addMetaItem(2, new MetaItem("particle.2", "particleCustomizers", EnumRarity.common), true, false);
        particleRainbowSmoke = particleCustomizers.addMetaItem(3, new MetaItem("particle.3", "particleCustomizers", EnumRarity.common), true, false);
    }
    
    private static void registerItems() {
        SimplyJetpacks.logger.info("Registering items");
        
        // For compatibility, do not change the following IDs until 1.8
        
        registerItem(jetpacksCommon, "jetpacksCommon");
        
        registerItem(jetpacksTE, "jetpacks");
        
        registerItem(components, "components");
        registerItem(armorPlatings, "armorPlatings");
        registerItem(particleCustomizers, "particleCustomizers");
    }
    
    private static void registerRecipes() {
        SimplyJetpacks.logger.info("Registering recipes");
        
        GameRegistry.addRecipe(new ShapedOreRecipe(leatherStrap, "LIL", "LIL", 'L', Items.leather, 'I', "ingotIron"));
        
        Object dustCoal = OreDictionary.getOres("dustCoal").size() > 0 ? "dustCoal" : new ItemStack(Items.coal);
        GameRegistry.addRecipe(new ShapedOreRecipe(particleDefault, " D ", "DCD", " D ", 'C', dustCoal, 'D', Blocks.torch));
        GameRegistry.addRecipe(new ShapedOreRecipe(particleNone, " D ", "DCD", " D ", 'C', dustCoal, 'D', "blockGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(particleSmoke, " C ", "CCC", " C ", 'C', dustCoal));
        GameRegistry.addRecipe(new ShapedOreRecipe(particleRainbowSmoke, " R ", " C ", "G B", 'C', dustCoal, 'R', "dyeRed", 'G', "dyeLime", 'B', "dyeBlue"));
        
        if (teAvailable) {
            GameRegistry.addRecipe(new ShapedOreRecipe(thrusterTE1, "ICI", "PGP", "DSD", 'I', "ingotLead", 'P', "blockGlass", 'C', TEItems.powerCoilGold, 'G', "gearCopper", 'D', TEItems.dynamoSteam, 'S', TEItems.pneumaticServo));
            
            GameRegistry.addRecipe(new ShapedOreRecipe(armorPlatingTE1, "TIT", "III", "TIT", 'I', "ingotIron", 'T', "ingotTin"));

            GameRegistry.addRecipe(new UpgradingRecipe(jetpackTE1, "IBI", "IJI", "T T", 'I', "ingotLead", 'B', TEItems.capacitorBasic, 'T', thrusterTE1, 'J', leatherStrap));
            
            GameRegistry.addRecipe(new UpgradingRecipe(jetpackTE1, "J", "P", 'J', jetpackTE1, 'P', new ItemStack(particleCustomizers, 1, OreDictionary.WILDCARD_VALUE)));
            
        }
    }
    
    private static void doIMC() {
        SimplyJetpacks.logger.info("Doing intermod communication");
    }
    
    private static void registerItem(Item item, String name) {
        if (item != null) {
            GameRegistry.registerItem(item, name);
        }
    }
    
}
