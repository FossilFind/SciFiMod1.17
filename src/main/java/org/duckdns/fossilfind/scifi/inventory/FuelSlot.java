package org.duckdns.fossilfind.scifi.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

public class FuelSlot extends Slot
{
	public FuelSlot(Container container, int index,int x,int y)
	{
		super(container, index, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack)
	{
		return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
	}
}