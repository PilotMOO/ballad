package mod.pilot.birch_n_bees.items;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import mod.pilot.birch_n_bees.blocks.BirchBlocks;
import mod.pilot.birch_n_bees.items.unique.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BirchItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ABalladofBirchandBees.MOD_ID);

    public static final DeferredItem<Item> WILDFLOWER_TWINE = ITEMS.registerItem("wildflower_twine",
            (properties) -> new Item(properties){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.wildflower_twine.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            });
    public static final DeferredItem<Item> BIRCH_BARK = ITEMS.registerItem("birch_bark",
            (properties) -> new Item(properties){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.birch_bark.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            });
    public static final DeferredItem<Item> SOGGY_BIRCH_BARK = ITEMS.registerItem("soggy_birch_bark",
            (properties) -> new Item(properties.food(BirchFoodProperties.BARK_FOOD)){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.soggy_birch_bark.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            });

    public static final DeferredItem<SplinterItem> SPLINTERS = ITEMS.registerItem("splinters", SplinterItem::new);
    public static final DeferredItem<Item> BUNDLE_OF_SPLINTERS = ITEMS.registerItem("bundle_of_splinters",
            (properties) -> new Item(properties.food(BirchFoodProperties.BARK_FOOD)){
                @Override
                public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
                    if (level instanceof ServerLevel server){
                        livingEntity.hurtServer(server, livingEntity.damageSources().cactus(), 5);
                        server.playSound(null, livingEntity.blockPosition(), SoundEvents.THORNS_HIT, SoundSource.PLAYERS);
                    }
                    return super.finishUsingItem(stack, level, livingEntity);
                }

                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.bundle_of_splinters.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            });
    public static final DeferredItem<Item> CLAY_BRICK = ITEMS.registerItem("clay_brick",
            (properties) -> new Item(properties){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.clay_brick.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            });

    public static final DeferredItem<WildflowerDressingItem> WILDFLOWER_DRESSING = ITEMS.registerItem("wildflower_dressing",
            (properties -> new WildflowerDressingItem(properties){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.wildflower_dressing.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            }));
    public static final DeferredItem<WildflowerBandageItem> WILDFLOWER_BANDAGE = ITEMS.registerItem("wildflower_bandage",
            (properties -> new WildflowerBandageItem(properties){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.wildflower_bandage.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            }));

    public static final DeferredItem<Item> THREE_SUGARCANE = ITEMS.registerItem("three_sugarcane",
            (properties) -> new Item(properties.stacksTo(7)){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                            @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                            @NotNull TooltipFlag flag) {
                    tooltipAdder.accept(Component.translatable("item.birch_n_bees.three_sugarcane.description"));
                    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
                }
            });

    public static final DeferredItem<Item> STONE_PEBBLE = ITEMS.registerItem("stone_pebble", Item::new);
    public static final DeferredItem<Item> ANDESITE_PEBBLE = ITEMS.registerItem("andesite_pebble", Item::new);
    public static final DeferredItem<Item> DIORITE_PEBBLE = ITEMS.registerItem("diorite_pebble", Item::new);
    public static final DeferredItem<Item> GRANITE_PEBBLE = ITEMS.registerItem("granite_pebble", Item::new);
    public static final DeferredItem<Item> TUFF_PEBBLE = ITEMS.registerItem("tuff_pebble", Item::new);
    public static final DeferredItem<Item> DEEPSLATE_PEBBLE = ITEMS.registerItem("deepslate_pebble", Item::new);


    public static final DeferredItem<HoneyAxeItem> HONEY_AXE = ITEMS.registerItem("honey_axe", HoneyAxeItem::new);
    public static final DeferredItem<HoneyShovelItem> HONEY_SHOVEL = ITEMS.registerItem("honey_shovel", HoneyShovelItem::new);

    public static final DeferredItem<CrudeCobblestonePickaxeItem> CRUDE_COBBLESTONE_PICKAXE = ITEMS.registerItem(
            "crude_cobblestone_pickaxe", CrudeCobblestonePickaxeItem::new);
    public static final DeferredItem<CrudeCobblestoneAxeItem> CRUDE_COBBLESTONE_AXE = ITEMS.registerItem(
            "crude_cobblestone_axe", CrudeCobblestoneAxeItem::new);
    public static final DeferredItem<CrudeCobblestoneSwordItem> CRUDE_COBBLESTONE_SWORD = ITEMS.registerItem(
            "crude_cobblestone_sword", CrudeCobblestoneSwordItem::new);
    public static final DeferredItem<FlintKnifeItem> FLINT_KNIFE = ITEMS.registerItem(
            "flint_knife", FlintKnifeItem::new);
    public static final DeferredItem<BirchShieldItem> BIRCH_SHIELD = ITEMS.registerItem(
            "birch_shield", BirchShieldItem::new);

    public static final DeferredItem<WildflowerPopperItem> WILDFLOWER_POPPER = ITEMS.registerItem(
            "wildflower_popper", WildflowerPopperItem::new);

    public static final DeferredItem<BlockItem> STICKY_PLANKS = ITEMS.registerSimpleBlockItem(BirchBlocks.STICKY_PLANKS);

    public static final DeferredItem<BlockItem> COBBLED_ANDESITE = ITEMS.registerSimpleBlockItem(BirchBlocks.COBBLED_ANDESITE);
    public static final DeferredItem<BlockItem> COBBLED_DIORITE = ITEMS.registerSimpleBlockItem(BirchBlocks.COBBLED_DIORITE);
    public static final DeferredItem<BlockItem> COBBLED_GRANITE = ITEMS.registerSimpleBlockItem(BirchBlocks.COBBLED_GRANITE);
    public static final DeferredItem<BlockItem> COBBLED_TUFF = ITEMS.registerSimpleBlockItem(BirchBlocks.COBBLED_TUFF);


    public static final DeferredItem<Item> CLAYFLINT = ITEMS.registerItem("clayflint", (Item::new));
    public static final DeferredItem<Item> MOON = ITEMS.registerItem("moon", (Item::new));
    public static final DeferredItem<Item> WITHER_HEART = ITEMS.registerItem("wither_heart", (Item::new));

    public static final DeferredItem<TestingToolBase> TESTING_TOOL_BASE = ITEMS.registerItem("buildable_tool",
            TestingToolBase::new);

/*    public static <I extends BuildableToolBase> DeferredItem<I> registerBuildableTool(String name, Supplier<I> supplier) {
        I tool = supplier.get();
        tool.fillValidHeads();
        return ITEMS.registerItem(name, (properties)->tool);
    }*/
}
