package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.blocks.RoyalTileBlock;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZeldasLullaby extends OcarinaSong
{
	public ZeldasLullaby()
	{
		super("lurlur");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.ZELDAS_LULLABY.get();
	}
	
	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		int radius = 5;
		BlockPos start = player.blockPosition().offset(-radius, -radius, -radius);
		BlockPos end = player.blockPosition().offset(radius, radius, radius);
		BlockPos.betweenClosed(start, end).forEach(pos ->
		{
			BlockState blockState = level.getBlockState(pos);
			Block block = blockState.getBlock();
			
			if (block instanceof RoyalTileBlock)
			{
				((RoyalTileBlock) block).activate(level, blockState, pos);
			}
		});
	}
}