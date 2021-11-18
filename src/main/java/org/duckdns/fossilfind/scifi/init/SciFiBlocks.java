package org.duckdns.fossilfind.scifi.init;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SciFiBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SciFi.MODID);
	
	//Aluminum
	public static final RegistryObject<Block> ALUMINUM_BLOCK = BLOCKS.register("aluminum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> BAUXITE_BLOCK = BLOCKS.register("bauxite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	public static final RegistryObject<Block> BAUXITE = BLOCKS.register("bauxite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
	//Titanium
	public static final RegistryObject<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> RUTILE_BLOCK = BLOCKS.register("rutile_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	public static final RegistryObject<Block> RUTILE = BLOCKS.register("rutile", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
	//Tantalum
	public static final RegistryObject<Block> TANTALUM_BLOCK = BLOCKS.register("tantalum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> TANTILITE_BLOCK = BLOCKS.register("tantilite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	public static final RegistryObject<Block> TANTILITE = BLOCKS.register("tantilite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
}