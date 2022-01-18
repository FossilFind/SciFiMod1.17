package org.duckdns.fossilfind.scifi.item.recipes;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;

public interface IRefineryRecipe extends Recipe<BaseContainerBlockEntity>
{
	ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(SciFi.MODID, "refining");
	
	@Override
	default RecipeType<?> getType()
	{
		return Registry.RECIPE_TYPE.get(RECIPE_TYPE_ID);
	}
	
	@Override
	default boolean canCraftInDimensions(int width, int height)
	{
		return false;
	}
	
	Ingredient getInput();
}