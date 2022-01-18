package org.duckdns.fossilfind.scifi.network;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import org.duckdns.fossilfind.scifi.client.ClientAccess;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class RefineryUpdatePacket
{
	public final BlockPos pos;
	public final CompoundTag nbt;
	
	public RefineryUpdatePacket(BlockPos pos, CompoundTag nbt)
	{
		this.pos = pos;
		this.nbt = nbt;
	}
	
	public RefineryUpdatePacket(FriendlyByteBuf buffer)
	{
		this(buffer.readBlockPos(), null);
	}
	
	public void encode(FriendlyByteBuf buffer)
	{
		//buffer.writeNbt(nbt);
		buffer.writeBlockPos(pos);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx)
	{
		AtomicBoolean success = new AtomicBoolean(false);
		
		ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> success.set(ClientAccess.updateRefinery(pos, nbt))));
		ctx.get().setPacketHandled(true);
		
		return success.get();
	}
}