package io.github.yuanseen.stone.client.render.entity.layers;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.yuanseen.stone.client.model.entity.CapybaraModel;
import io.github.yuanseen.stone.entity.CapybaraEntity;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

// 使用@OnlyIn注解标记该类仅在客户端（Dist.CLIENT）中可用
@OnlyIn(Dist.CLIENT)
// 定义一个名为CapybaraCarryingItemLayer的类，它继承自RenderLayer，专门用于渲染CapybaraEntity手持物品的效果
public class CapybaraCarryingItemLayer extends RenderLayer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {
    // 声明一个ItemInHandRenderer类型的成员变量，用于渲染手持物品
    private final ItemInHandRenderer itemInHandRenderer;

    // 构造方法，接收一个RenderLayerParent类型的父渲染器和一个ItemInHandRenderer类型的手持物品渲染器
    public CapybaraCarryingItemLayer(RenderLayerParent<CapybaraEntity, CapybaraModel<CapybaraEntity>> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        // 调用父类的构造方法，传入父渲染器
        super(pRenderer);
        // 初始化itemInHandRenderer成员变量
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    // 重写render方法，用于渲染手持物品的效果
    @Override
    public void render(
            PoseStack pPoseStack, // 姿态堆栈，用于记录渲染时的变换
            MultiBufferSource pBuffer, // 缓冲区源，用于绘制
            int pPackedLight, // 打包的光照信息
            CapybaraEntity pLivingEntity, // 要渲染的水豚实体
            float pLimbSwing, // 摆动的角度
            float pLimbSwingAmount, // 摆动幅度
            float pPartialTicks, // 插值时间，用于平滑动画
            float pAgeInTicks, // 年龄，以刻为单位
            float pNetHeadYaw, // 头部偏转角度（网络值）
            float pHeadPitch // 头部俯仰角度
    ) {
        // 根据水豚实体的主手（右手或左手）来判断
        boolean flag = pLivingEntity.getMainArm() == HumanoidArm.RIGHT;
        // 压入一个新的姿态到姿态堆栈中，以便后续操作可以在这个新姿态的基础上进行
        pPoseStack.pushPose();
        pPoseStack.scale(0.25F, 0.25F, 0.25F);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(180));
        pPoseStack.translate(0.0F, -3.65F , 0.71F );
//        pPoseStack.translate(0.0F, -1.15F , 0.25F );//75x

        // 根据主手（右手或左手）获取水豚实体手持的物品
        ItemStack itemstack = flag ? pLivingEntity.getMainHandItem() : pLivingEntity.getOffhandItem();
        // 使用手持物品渲染器渲染手持物品
        this.itemInHandRenderer.renderItem(pLivingEntity, itemstack, ItemDisplayContext.GROUND, false, pPoseStack, pBuffer, pPackedLight);
        // 弹出姿态堆栈，恢复到渲染手持物品之前的姿态
        pPoseStack.popPose();
    }

}
