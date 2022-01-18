package org.duckdns.fossilfind.scifi.init;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.inventory.RefineryMenu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SciFiContainers
{
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SciFi.MODID);
	
	public static final RegistryObject<MenuType<RefineryMenu>> REFINERY = CONTAINERS.register("refinery", () -> IForgeContainerType.create(RefineryMenu::new));
}