package org.duckdns.fossilfind.scifi.client;

import org.duckdns.fossilfind.scifi.block.entity.RefineryBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClientAccess
{
	public static boolean updateRefinery(BlockPos pos, CompoundTag nbt)
	{
		@SuppressWarnings("resource")
		BlockEntity entity = Minecraft.getInstance().level.getBlockEntity(pos);
		
		if(entity instanceof RefineryBlockEntity)
		{
			((RefineryBlockEntity) entity).tick();
			return true;
		}
		
		return false;
	}
}