package org.duckdns.fossilfind.scifi.init;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SciFiItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SciFi.MODID);
	
	//Aluminum
	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> BAUXITE_CHUNK = ITEMS.register("bauxite_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	//Titanium
	public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> RUTILE_CHUNK = ITEMS.register("rutile_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	//Tantulum
	public static final RegistryObject<Item> TANTALUM_INGOT = ITEMS.register("tantalum_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> TANTILITE_CHUNK = ITEMS.register("tantilite_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	
	//BlockItems
	//Aluminum
	public static final RegistryObject<BlockItem> ALUMINUM_BLOCK = ITEMS.register("aluminum_block", () -> new BlockItem(SciFiBlocks.ALUMINUM_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<BlockItem> BAUXITE_BLOCK = ITEMS.register("bauxite_block", () -> new BlockItem(SciFiBlocks.BAUXITE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<BlockItem> BAUXITE = ITEMS.register("bauxite", () -> new BlockItem(SciFiBlocks.BAUXITE.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	//Titanium
	public static final RegistryObject<BlockItem> TITANIUM_BLOCK = ITEMS.register("titanium_block", () -> new BlockItem(SciFiBlocks.TITANIUM_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<BlockItem> RUTILE_BLOCK = ITEMS.register("rutile_block", () -> new BlockItem(SciFiBlocks.RUTILE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<BlockItem> RUTILE = ITEMS.register("rutile", () -> new BlockItem(SciFiBlocks.RUTILE.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	//Tantinite
	public static final RegistryObject<BlockItem> TANTALUM_BLOCK = ITEMS.register("tantalum_block", () -> new BlockItem(SciFiBlocks.TANTALUM_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<BlockItem> TANTILITE_BLOCK = ITEMS.register("tantilite_block", () -> new BlockItem(SciFiBlocks.TANTILITE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<BlockItem> TANTILITE = ITEMS.register("tantilite", () -> new BlockItem(SciFiBlocks.TANTILITE.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}