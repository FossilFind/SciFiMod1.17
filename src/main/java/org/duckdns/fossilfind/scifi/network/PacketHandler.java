package org.duckdns.fossilfind.scifi.network;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class PacketHandler
{	
	private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(SciFi.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static void init()
	{
		int index = 0;
		
		INSTANCE.messageBuilder(RefineryUpdatePacket.class, index++, NetworkDirection.PLAY_TO_CLIENT).encoder(RefineryUpdatePacket::encode).decoder(RefineryUpdatePacket::new).consumer(RefineryUpdatePacket::handle).add();
	}
}