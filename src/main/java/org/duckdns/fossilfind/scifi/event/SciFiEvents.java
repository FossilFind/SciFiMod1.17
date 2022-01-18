package org.duckdns.fossilfind.scifi.event;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.client.gui.screens.inventory.RefineryScreen;
import org.duckdns.fossilfind.scifi.init.SciFiContainers;
import org.duckdns.fossilfind.scifi.network.PacketHandler;
import org.duckdns.fossilfind.scifi.world.OreGeneration;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SciFi.MODID, bus = Bus.MOD)
public class SciFiEvents
{
	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event)
	{
		event.enqueueWork(OreGeneration::registerOres);
		event.enqueueWork(PacketHandler::init);
	}
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event)
	{
		MenuScreens.register(SciFiContainers.REFINERY.get(), RefineryScreen::new);
	}
}