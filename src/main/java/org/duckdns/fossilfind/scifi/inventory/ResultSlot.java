package org.duckdns.fossilfind.scifi.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ResultSlot extends Slot
{
	public ResultSlot(Container container, int index, int x, int y)
	{
		super(container, index, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack)
	{
		return false;
	}
}