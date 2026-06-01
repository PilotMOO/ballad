package mod.pilot.birch_n_bees.items.unique;

import mod.pilot.birch_n_bees.items.BirchItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FlintKnifeItem extends Item {
    public FlintKnifeItem(Properties properties) {
        super(properties.sword(getMaterial(), 1f, -1.0f));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder,
                                @NotNull TooltipFlag flag) {
        tooltipAdder.accept(Component.translatable("item.birch_n_bees.flint_knife.description"));
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        ItemStack offhand = entity.getOffhandItem();
        if (stack == offhand) offhand = entity.getMainHandItem();
        return isSugarcane(offhand) ? 40 : 0;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BUNDLE;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack offhand;
        if (hand == InteractionHand.MAIN_HAND) offhand = player.getOffhandItem();
        else offhand = player.getMainHandItem();
        if (isSugarcane(offhand)){
            player.startUsingItem(hand);
            player.playSound(SoundEvents.SCAFFOLDING_PLACE, 1.0F, .5F);
            return InteractionResult.SUCCESS;
        } else{
            player.stopUsingItem();
            player.playSound(SoundEvents.BUBBLE_POP, 1.0F, .5F);
            player.displayClientMessage(Component.translatable(
                    offhand.isEmpty() ? "birch_n_bees.message.nothing_to_cut" : "birch_n_bees.message.not_cuttable"),
                    true);
            player.getCooldowns().addCooldown(player.getItemInHand(hand), 5);
            return InteractionResult.FAIL;
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, LivingEntity entity) {
        ItemStack offhand = entity.getOffhandItem();
        boolean fromOffhand = false;
        if (stack.equals(offhand)){
            offhand = entity.getMainHandItem();
            fromOffhand = true;
        }
        if (isSugarcane(offhand)) {
            offhand.shrink(1);
            entity.playSound(SoundEvents.BAMBOO_BREAK, .75f, 1.25f);
            stack.hurtAndBreak(5, entity, fromOffhand ? EquipmentSlot.OFFHAND :  EquipmentSlot.MAINHAND);
            if (entity instanceof Player p){
                p.addItem(BirchItems.PREPARED_SUGARCANE.toStack());
            } else {
                Vec3 pos = entity.position();
                ItemEntity itemEntity = new ItemEntity(level,pos.x, pos.y, pos.z,
                        BirchItems.PREPARED_SUGARCANE.toStack());
                level.addFreshEntity(itemEntity);
            }
        }
        return stack;
    }

    private static boolean isSugarcane(ItemStack stack) {
        return stack.is(Items.SUGAR_CANE);
    }

    private static ToolMaterial getMaterial(){
        return new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 64, 3.5F, 0.0F, 12, null);
    }
}
