package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.loot.FishingLootModifier;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LootInit
{
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, SupersLegendMain.MOD_ID);
    
	public static final RegistryObject<FishingLootModifier.Serializer> FISHING = REGISTRY.register("fishing", FishingLootModifier.Serializer::new);
}
