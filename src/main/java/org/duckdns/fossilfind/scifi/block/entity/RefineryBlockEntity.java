package org.duckdns.fossilfind.scifi.block.entity;

import java.util.List;
import java.util.Set;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.block.RefineryBlock;
import org.duckdns.fossilfind.scifi.init.SciFiBlockEntities;
import org.duckdns.fossilfind.scifi.init.SciFiRecipes;
import org.duckdns.fossilfind.scifi.inventory.RefineryMenu;
import org.duckdns.fossilfind.scifi.item.recipes.RefineryRecipe;
import org.duckdns.fossilfind.scifi.network.PacketHandler;
import org.duckdns.fossilfind.scifi.network.RefineryUpdatePacket;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class RefineryBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible
{
	private static final int[] SLOTS_UP = new int[] {0};
	private static final int[] SLOTS_DOWN = new int[] {2, 3, 1};
	private static final int[] SLOTS_HORIZANTAL = new int[] {1};
	private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
	public int cookTime = 0, cookTimeTotal, litTime = 0, litDuration;
	private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
	
	public RefineryBlockEntity(BlockPos pos, BlockState state)
	{
		super(SciFiBlockEntities.REFINERY.get(), pos, state);
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int windowID, Inventory inventory)
	{
		return new RefineryMenu(windowID, inventory, this);
	}
	
	@Override
	protected Component getDefaultName()
	{
		return new TranslatableComponent("container." + SciFi.MODID + ".refinery");
	}
	
	public void tick()
	{
		boolean burning = isBurning();
		boolean changed = false;
		
		if(isBurning())
			litTime--;
		
		ItemStack fuel = items.get(1);
		
		if(isBurning() || !fuel.isEmpty() && !items.get(0).isEmpty())
		{
			RefineryRecipe recipe = getRecipe(items.get(0));
			
			if(!isBurning() && canSmelt(recipe))
			{
				litTime = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
				litDuration = litTime;
				
				if(isBurning())
				{
					changed = true;
					if(fuel.hasContainerItem())
						items.set(1, fuel.getContainerItem());
					else if(!fuel.isEmpty())
					{
						fuel.shrink(1);
						if(fuel.isEmpty())
							items.set(1, fuel.getContainerItem());
					}
				}
			}
			
			if(isBurning() && canSmelt(recipe))
			{
				cookTime++;
				
				if(cookTime == cookTimeTotal)
				{
					cookTime = 0;
					cookTimeTotal = recipe.getCookingTime();
					smelt(recipe);
					changed = true;
				}
			}
			else
				cookTime = 0;
		}
		else if(!isBurning() && cookTime > 0)
			cookTime = Mth.clamp(cookTime - 2,  0, cookTimeTotal);
		
		if(burning != isBurning())
		{
			changed = true;
			level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(RefineryBlock.LIT, Boolean.valueOf(isBurning())), 3);
		}
		
		if(changed)
		{
			setChanged();
		}
		
		if(!level.isClientSide)
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(getBlockPos())), new RefineryUpdatePacket(worldPosition, save(new CompoundTag())));
	}
	
	public boolean isBurning()
	{
		return litTime > 0;
	}
	
	public boolean canSmelt(RefineryRecipe recipe)
	{
		if(!items.get(0).isEmpty() && recipe != null)
		{
			ItemStack output = recipe.getResultItem();
			
			if(!output.isEmpty())
			{
				ItemStack slot1 = items.get(2);
				ItemStack slot2 = items.get(3);
				
				boolean slot1good;
				boolean slot2good;
				
				if(slot1.isEmpty())
					slot1good = true;
				else if(!slot1.equals(output, false))
					slot1good = false;
				else if(slot1.getCount() + output.getCount() <= getMaxStackSize() && slot1.getCount() + output.getCount() <= slot1.getMaxStackSize())
					slot1good = true;
				else
					slot1good = slot1.getCount() + output.getCount() <= slot1.getMaxStackSize();
				
				if(slot2.isEmpty())
					slot2good = true;
				else if(!slot2.equals(output, false))
					slot2good = false;
				else if(slot2.getCount() + output.getCount() <= getMaxStackSize() && slot2.getCount() + output.getCount() <= slot2.getMaxStackSize())
					slot2good = true;
				else
					slot2good = slot2.getCount() + output.getCount() <= slot2.getMaxStackSize();
				
				return slot1good && slot2good;
			}
			else
				return false;
		}
		
		return false;
	}
	
	private void smelt(RefineryRecipe recipe)
	{
		if(recipe != null && canSmelt(recipe))
		{
			ItemStack input = items.get(0);
			ItemStack result = recipe.getResultItem();
			ItemStack output1 = items.get(2);
			ItemStack output2 = items.get(3);
			
			if(output1.isEmpty())
				items.set(2, result.copy());
			else if(output1.getItem() == result.getItem())
				output1.grow(result.getCount());
			
			if(output2.isEmpty())
				items.set(3, result.copy());
			else if(output2.getItem() == result.getItem())
				output2.grow(result.getCount());
			
			input.shrink(1);
		}
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		
		items = NonNullList.withSize(4, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, items);
		
		litTime = nbt.getInt("LitTime");
		cookTime = nbt.getInt("CookTime");
		cookTimeTotal = nbt.getInt("CookTimeTotal");
		litDuration = ForgeHooks.getBurnTime(items.get(1), RecipeType.SMELTING);
		
		CompoundTag recipeNBT = nbt.getCompound("RecipesUsed");
		for(String s : recipeNBT.getAllKeys())
			recipesUsed.put(new ResourceLocation(s), recipeNBT.getInt(s));
	}
	
	@Override
	public CompoundTag save(CompoundTag nbt)
	{
		super.save(nbt);
		
		ContainerHelper.saveAllItems(nbt, items);
		
		nbt.putInt("LitTime", litTime);
		nbt.putInt("CookTime", cookTime);
		nbt.putInt("CookTimeTotal", cookTimeTotal);
		
		CompoundTag recipeNBT = new CompoundTag();
		recipesUsed.forEach((location, recipe) -> { recipeNBT.putInt(location.toString(), recipe); });
		nbt.put("RecipesUsed", recipeNBT);
		
		return nbt;
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
	{
		load(pkt.getTag());
	}
	
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket()
	{
		return new ClientboundBlockEntityDataPacket(worldPosition, -1, save(new CompoundTag()));
	}
	
	@Override
	public int[] getSlotsForFace(Direction side)
	{
		switch(side)
		{
		case UP:
			return SLOTS_UP;
		case DOWN:
			return SLOTS_DOWN;
		default:
			return SLOTS_HORIZANTAL;
		}
	}
	
	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side)
	{
		return canPlaceItem(slot, stack);
	}
	
	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side)
	{
		if(side == Direction.DOWN && slot == 1)
			return stack.is(Items.BUCKET);
		else
			return true;
	}
	
	@Override
	public int getContainerSize()
	{
		return items.size();
	}
	
	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : items)
			if(!stack.isEmpty())
				return false;
		
		return true;
	}
	
	@Override
	public ItemStack getItem(int slot)
	{
		return items.get(slot);
	}
	
	@Override
	public ItemStack removeItem(int slot, int count)
	{
		return ContainerHelper.removeItem(items, slot, count);
	}
	
	@Override
	public ItemStack removeItemNoUpdate(int slot)
	{
		return ContainerHelper.takeItem(items, slot);
	}
	
	@Override
	public void setItem(int slot, ItemStack stack)
	{
		ItemStack slotStack = items.get(slot);
		boolean same = !stack.isEmpty() && stack.sameItem(slotStack) && ItemStack.tagMatches(stack, slotStack);
		
		items.set(slot, stack);
		
		if(stack.getCount() > getMaxStackSize())
			stack.setCount(getMaxStackSize());
		
		if(slot == 0 && !same)
		{
			cookTimeTotal = getRecipe(stack) == null ? 0 : getRecipe(stack).getCookingTime();
			cookTime = 0;
			setChanged();
		}
	}
	
	@Override
	public boolean stillValid(Player player)
	{
		if(level.getBlockEntity(worldPosition) != this)
			return false;
		else
			return player.distanceToSqr((double) worldPosition.getX() + 0.5d, (double) worldPosition.getY() + 0.5d, (double) worldPosition.getZ() + 0.5d) <= 64d;
	}
	
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack)
	{
		if(slot == 2 || slot == 3)
			return false;
		else if(slot != 1)
			return true;
		else
			return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
	}
	
	@Override
	public void clearContent()
	{
		items.clear();
	}
	
	@Override
	public void setRecipeUsed(Recipe<?> recipe)
	{
		if(recipe != null);
			recipesUsed.addTo(recipe.getId(), 1);
	}
	
	@Override
	public Recipe<?> getRecipeUsed()
	{
		return null;
	}
	
	@Override
	public void awardUsedRecipes(Player player)
	{
		if(!level.isClientSide)
			awardUsedRecipesAndPopExperience((ServerPlayer) player);
	}
	
	public void awardUsedRecipesAndPopExperience(ServerPlayer player)
	{
		List<Recipe<?>> list = getRecipesToAwardAndPopExperience(player.getLevel(), player.position());
		player.awardRecipes(list);
		recipesUsed.clear();
	}
	
	public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 vec)
	{
		List<Recipe<?>> list = Lists.newArrayList();
		
		for(Entry<ResourceLocation> entry : recipesUsed.object2IntEntrySet())
		{
			level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) ->
			{
				list.add(recipe);
				createExperience(level, vec, entry.getIntValue(), ((RefineryRecipe) recipe).getExperience());
			});
		}
		
		return list;
	}
	
	public void createExperience(ServerLevel level, Vec3 vec, int a, float b)
	{
		int i = Mth.floor((float) a * b);
		float f = Mth.frac((float) a * b);
		if(f != 0f && Math.random() < (double) f)
			i++;
		
		ExperienceOrb.award(level, vec, i);
	}
	
	@Override
	public void fillStackedContents(StackedContents contents)
	{
		for(ItemStack stack : items)
			contents.accountStack(stack);
	}
	
	private RefineryRecipe getRecipe(ItemStack stack)
	{
		if(stack == null)
			return null;
		
		Set<Recipe<?>> recipes = SciFiRecipes.findRecipesByType(SciFiRecipes.REFINERY_TYPE, level);
		for(Recipe<?> iRecipe : recipes)
		{
			RefineryRecipe recipe = (RefineryRecipe) iRecipe;
			if(recipe.matches(this, level))
				return recipe;
		}
		
		return null;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if(!remove && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if(side == Direction.UP)
				return handlers[0].cast();
			else if(side == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		
		return super.getCapability(cap, side);
	}
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		
		for(int i = 0; i < handlers.length; i++)
			handlers[i].invalidate();
	}
	
	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		
		handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
	}
}