package org.duckdns.fossilfind.scifi;

import org.duckdns.fossilfind.scifi.init.SciFiBlockEntities;
import org.duckdns.fossilfind.scifi.init.SciFiBlocks;
import org.duckdns.fossilfind.scifi.init.SciFiContainers;
import org.duckdns.fossilfind.scifi.init.SciFiItems;
import org.duckdns.fossilfind.scifi.init.SciFiRecipes;

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
		SciFiBlockEntities.BLOCK_ENTITIES.register(bus);
		SciFiContainers.CONTAINERS.register(bus);
		SciFiItems.ITEMS.register(bus);
		SciFiRecipes.RECIPE_SERIALIZERS.register(bus);
	}
}