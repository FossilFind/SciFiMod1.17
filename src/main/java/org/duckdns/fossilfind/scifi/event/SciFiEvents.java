package org.duckdns.fossilfind.scifi.event;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.world.OreGeneration;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SciFi.MODID, bus = Bus.MOD)
public class SciFiEvents
{
	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event)
	{
		event.enqueueWork(OreGeneration::registerOres);
	}
}