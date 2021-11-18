package org.duckdns.fossilfind.scifi.world;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.init.SciFiBlocks;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class OreGeneration
{
	public static final List<ConfiguredFeature<?, ?>> OVERWORLD_ORES = new ArrayList<>();
	
	public static void registerOres()
	{
		ConfiguredFeature<?, ?> bauxite = register("bauxite", Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, SciFiBlocks.BAUXITE.get().defaultBlockState())), 13)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(63)).squared().count(20));
		ConfiguredFeature<?, ?> rutile = register("rutile", Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, SciFiBlocks.RUTILE.get().defaultBlockState())), 10)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(30)).squared().count(15));
		ConfiguredFeature<?, ?> tantilite = register("tantilite", Feature.ORE.configured(new OreConfiguration(List.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, SciFiBlocks.TANTILITE.get().defaultBlockState())), 10)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(30)).squared().count(15));
		
		OVERWORLD_ORES.add(bauxite);
		OVERWORLD_ORES.add(rutile);
		OVERWORLD_ORES.add(tantilite);
	}
	
	private static <C extends FeatureConfiguration> ConfiguredFeature<C, ?> register(String name, ConfiguredFeature<C, ?> feature)
	{
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(SciFi.MODID, name), feature);
	}
	
	@Mod.EventBusSubscriber(modid = SciFi.MODID, bus = Bus.FORGE)
	public static class ForgeBusSubscriber
	{
		@SubscribeEvent
		public static void biomeLoading(BiomeLoadingEvent event)
		{
			List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES);
			
			switch(event.getCategory())
			{
				default -> OreGeneration.OVERWORLD_ORES.forEach(ore -> features.add(() -> ore));
			}
		}
	}
}