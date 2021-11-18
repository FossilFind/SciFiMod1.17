package org.duckdns.fossilfind.scifi;

import org.duckdns.fossilfind.scifi.init.SciFiBlocks;
import org.duckdns.fossilfind.scifi.init.SciFiItems;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SciFi.MODID)
public class SciFi
{
	public static final String MODID = "scifi";
	
	public SciFi()
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		SciFiBlocks.BLOCKS.register(bus);
		SciFiItems.ITEMS.register(bus);
	}
}