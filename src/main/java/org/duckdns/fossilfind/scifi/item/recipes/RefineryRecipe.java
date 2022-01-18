package org.duckdns.fossilfind.scifi.item.recipes;

import org.duckdns.fossilfind.scifi.init.SciFiRecipes;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;

public class RefineryRecipe implements IRefineryRecipe
{
	private final ResourceLocation id;
	private Ingredient input;
	private final ItemStack output;
	private int experience;
	private int cookingTime;
	
	public RefineryRecipe(ResourceLocation id, Ingredient input, ItemStack output, int experience, int cookingTime)
	{
		this.id = id;
		this.input = input;
		this.output = output;
		this.experience = experience;
		this.cookingTime = cookingTime;
	}

	@Override
	public boolean matches(BaseContainerBlockEntity inventory, Level level)
	{
		if(input.test(inventory.getItem(0)))
			return true;
		return false;
	}

	@Override
	public ItemStack assemble(BaseContainerBlockEntity inventory)
	{
		return output;
	}

	@Override
	public ItemStack getResultItem()
	{
		return output;
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return SciFiRecipes.REFINERY_SERIALIZER.get();
	}

	@Override
	public Ingredient getInput()
	{
		return input;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(null, input);
	}
	
	public int getExperience()
	{
		return experience;
	}
	
	public int getCookingTime()
	{
		return cookingTime;
	}
}