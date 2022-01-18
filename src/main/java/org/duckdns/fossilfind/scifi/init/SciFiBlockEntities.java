package org.duckdns.fossilfind.scifi.init;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.block.entity.RefineryBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SciFiBlockEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SciFi.MODID);
	
	public static final RegistryObject<BlockEntityType<RefineryBlockEntity>> REFINERY = BLOCK_ENTITIES.register("refinery", () -> BlockEntityType.Builder.of(RefineryBlockEntity::new, SciFiBlocks.REFINERY.get()).build(null));
}