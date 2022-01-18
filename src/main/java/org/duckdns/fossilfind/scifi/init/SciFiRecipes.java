package org.duckdns.fossilfind.scifi.init;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.item.recipes.RefineryRecipe;
import org.duckdns.fossilfind.scifi.item.recipes.RefineryRecipeSerializer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SciFiRecipes
{
	public static final RecipeType<RefineryRecipe> REFINERY_TYPE = registerType(RefineryRecipe.RECIPE_TYPE_ID);
	
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SciFi.MODID);
	
	public static final RegistryObject<RecipeSerializer<?>> REFINERY_SERIALIZER = RECIPE_SERIALIZERS.register("refining", () -> new RefineryRecipeSerializer());
	
	public static class RecipeTypeClass<T extends Recipe<?>> implements RecipeType<T>
	{
		@Override
		public String toString()
		{
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T extends RecipeType> T registerType(ResourceLocation typeID)
	{
		return (T) Registry.register(Registry.RECIPE_TYPE, typeID, new RecipeTypeClass<>());
	}
	
	public static Set<Recipe<?>> findRecipesByType(RecipeType<?> type, Level level)
	{
		return level != null ? level.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
	}
	
	public static Set<Recipe<?>> findRecipesByType(RecipeType<?> type)
	{
		@SuppressWarnings("resource")
		ClientLevel level = Minecraft.getInstance().level;
		return level != null ? level.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
	}
	
	public static Set<ItemStack> getAllRecipeInputs(RecipeType<?> type, Level level)
	{
		Set<ItemStack> inputs = new HashSet<ItemStack>();
		Set<Recipe<?>> recipes = findRecipesByType(type, level);
		
		for(Recipe<?> recipe : recipes)
		{
			NonNullList<Ingredient> ingredients = recipe.getIngredients();
			ingredients.forEach(ingredient ->
			{
				for(ItemStack stack : ingredient.getItems())
					inputs.add(stack);
			});
		}
		
		return inputs;
	}
}