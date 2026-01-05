package mod.pilot.birch_n_bees;

import mod.pilot.birch_n_bees.achievements.BirchCriteriaRegistry;
import mod.pilot.birch_n_bees.blocks.BirchBlocks;
import mod.pilot.birch_n_bees.effects.BirchEffects;
import mod.pilot.birch_n_bees.entity.BirchEntities;
import mod.pilot.birch_n_bees.items.BirchCreativeTabs;
import mod.pilot.birch_n_bees.items.BirchItems;
import mod.pilot.birch_n_bees.systems.HotBrickWatcher;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ABalladofBirchandBees.MOD_ID)
public class ABalladofBirchandBees
{
    public static final String MOD_ID = "birch_n_bees";

    public ABalladofBirchandBees(IEventBus modEventBus, ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.STARTUP, Config.SERVER_SPEC, "ballad_config.toml");
        configLoaded = true;
        HotBrickWatcher.init(Config.SERVER.BrickCookTime.get());

        BirchBlocks.BLOCKS.register(modEventBus);
        BirchItems.ITEMS.register(modEventBus);
        BirchEffects.EFFECTS.register(modEventBus);
        BirchCreativeTabs.TABS.register(modEventBus);
        BirchEntities.ENTITIES.register(modEventBus);
        BirchCriteriaRegistry.TRIGGERS.register(modEventBus);
    }
    public static boolean configLoaded = false;
}
