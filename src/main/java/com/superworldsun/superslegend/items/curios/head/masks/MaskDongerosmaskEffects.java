package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MaskDongerosmaskEffects extends Item implements ICurioItem {

    public MaskDongerosmaskEffects(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        World world = livingEntity.level;
        PlayerEntity player = (PlayerEntity) livingEntity;
        if (!world.isClientSide) {
            boolean isHelmeton = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.MASK_DONGEROSMASK);
            if (isHelmeton) player.addEffect(new EffectInstance(Effect.byId(8), 10, 1, false, false));

        }

    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "This Hood is Hoppin!"));
        list.add(new StringTextComponent(TextFormatting.GRAY + "Grants a jump boost"));
    }
}