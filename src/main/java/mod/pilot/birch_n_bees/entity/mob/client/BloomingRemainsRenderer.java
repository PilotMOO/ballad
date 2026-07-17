package mod.pilot.birch_n_bees.entity.mob.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.pilot.birch_n_bees.entity.mob.BloomingRemainsEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.layer.ItemInHandGeoLayer;

public class BloomingRemainsRenderer<R extends LivingEntityRenderState & GeoRenderState>
        extends GeoEntityRenderer<BloomingRemainsEntity, R> {
    public BloomingRemainsRenderer(EntityRendererProvider.Context context,
                                   GeoModel<BloomingRemainsEntity> model) {
        super(context, model);
        //addRenderLayer(new BloomingRemainsInHandRenderLayer<>(this));
        addRenderLayer(new ItemInHandGeoLayer<>(this, "right_hand", "left_hand"));
    }


    public static DataTicket<Boolean> IS_AGGRESSIVE = DataTicket.create("is_aggressive", Boolean.class);
    public static DataTicket<Boolean> RIGHT_HANDED = DataTicket.create("right_handed", Boolean.class);
    public static DataTicket<ItemStack> MAIN_HAND_ITEM = DataTicket.create("main_hand_item", ItemStack.class);
    public static DataTicket<ItemStack> OFFHAND_ITEM = DataTicket.create("offhand_item", ItemStack.class);

    @Override
    public void addRenderData(BloomingRemainsEntity animatable, Void relatedObject, R renderState) {
        renderState.addGeckolibData(IS_AGGRESSIVE, animatable.isAggressive());
        ItemStack main = animatable.getMainHandItem(), offhand = animatable.getOffhandItem();
        renderState.addGeckolibData(MAIN_HAND_ITEM, main);
        renderState.addGeckolibData(OFFHAND_ITEM, offhand);

        boolean rightHanded = animatable.getMainArm() == HumanoidArm.RIGHT;
        renderState.addGeckolibData(RIGHT_HANDED, rightHanded);
    }

    @Override
    public void actuallyRender(R renderState, PoseStack poseStack, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int renderColor) {
        GeoModel<BloomingRemainsEntity> geoModel = getGeoModel();
        GeoBone rightArm = geoModel.getBone("right_arm").orElse(null);
        GeoBone leftArm = geoModel.getBone("left_arm").orElse(null);
        if (rightArm != null && leftArm != null) {
            //getGeckolibData can return null here so we need to wrap it in this weird ass way
            boolean aggressive = Boolean.TRUE.equals(renderState.getGeckolibData(IS_AGGRESSIVE));
            if (aggressive){
                boolean right = Boolean.TRUE.equals(renderState.getGeckolibData(RIGHT_HANDED));
                ItemStack main = renderState.getGeckolibData(MAIN_HAND_ITEM);
                if (main != null && main.getItem() instanceof BowItem) {
                    float mainRotY = 0.1F, offRotY = -0.5F;
                    float halfPi = (float)(Math.PI / 2f);

                    rightArm.setRotY(right ? mainRotY : offRotY);
                    leftArm.setRotY(right ? offRotY : mainRotY);
                    rightArm.setRotX(halfPi);
                    leftArm.setRotX(halfPi);
                }
            }
        }

        super.actuallyRender(renderState, poseStack, model, renderType, bufferSource, buffer, isReRender, packedLight, packedOverlay, renderColor);
    }
}
