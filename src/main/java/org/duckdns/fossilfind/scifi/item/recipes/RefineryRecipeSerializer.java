package org.duckdns.fossilfind.scifi.item.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RefineryRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RefineryRecipe>
{
	@SuppressWarnings("deprecation")
	@Override
	public RefineryRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		Ingredient input;
		ItemStack output;
		
		if(!json.has("ingredient"))
			throw new JsonSyntaxException("Missing ingredient, expected to find a string or object");
		if(!json.has("result"))
			throw new JsonSyntaxException("Missing result, expected to find a string or object");
		
		if(json.get("ingredient").isJsonObject())
			input = Ingredient.fromJson(json.getAsJsonObject("ingredient"));
		else
		{
			String item = json.get("ingredient").getAsString();
			ResourceLocation location = new ResourceLocation(item);
			input = Ingredient.of(new ItemStack(Registry.ITEM.get(location)));
		}
		
		if(json.get("result").isJsonObject())
			output = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));
		else
		{
			String item = json.get("result").getAsString();
			ResourceLocation location = new ResourceLocation(item);
			output = new ItemStack(Registry.ITEM.get(location));
		}
		
		int experience = json.get("experience").getAsInt();
		int cookingTime = json.get("cookingtime").getAsInt();
		
		return new RefineryRecipe(id, input, output, experience, cookingTime);
	}
	
	@Override
	public RefineryRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
	{
		ItemStack output = buffer.readItem();
		Ingredient input = Ingredient.fromNetwork(buffer);
		int experience = buffer.readInt();
		int cookingTime = buffer.readInt();
		
		return new RefineryRecipe(id, input, output, experience, cookingTime);
	}
	
	@Override
	public void toNetwork(FriendlyByteBuf buffer, RefineryRecipe recipe)
	{
		buffer.writeItemStack(recipe.getInput().getItems()[0], false);
		buffer.writeItemStack(recipe.getResultItem(), false);
		buffer.writeInt(recipe.getCookingTime());
	}
}