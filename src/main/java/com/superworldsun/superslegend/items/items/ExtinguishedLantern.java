package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExtinguishedLantern extends Item
{

	public ExtinguishedLantern(Properties properties)
	{
		super(properties);
	}



	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.RED + "Lanturn has no fuel"));
		list.add(new StringTextComponent(TextFormatting.RED + "Right Click to refuel with oil"));
	}

	@Nonnull
	public ActionResult<ItemStack> use(@Nonnull World world, PlayerEntity player, @Nonnull Hand hand)
	{
		AtomicBoolean found = new AtomicBoolean(false);
		player.inventory.items.forEach(stack ->{
			if (!found.get()) {
				if (stack.getItem() instanceof Oil) {
					found.set(true);
					if (stack.getCount() > 1) {
						stack.setCount(stack.getCount() - 1);
					} else {
						stack.setCount(0);
					}
					player.setItemInHand(hand, new ItemStack(ItemInit.LANTERN.get()));
				}
			}
		});
		return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
	}


	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		return super.onEntityItemUpdate(stack, entity);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}



}