package org.duckdns.fossilfind.scifi.block;

import java.util.List;
import java.util.Random;

import org.duckdns.fossilfind.scifi.block.entity.RefineryBlockEntity;
import org.duckdns.fossilfind.scifi.init.SciFiBlockEntities;
import org.duckdns.fossilfind.scifi.util.helpers.KeyboardHelpers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RefineryBlock extends Block implements EntityBlock
{
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	
	public RefineryBlock(BlockBehaviour.Properties properties)
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return SciFiBlockEntities.REFINERY.get().create(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
	{
		return level.isClientSide ? null : ($0, $1, $2, blockEntity) -> ((RefineryBlockEntity) blockEntity).tick();
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if(level.isClientSide)
		{
			return InteractionResult.SUCCESS;
		}
		else
		{
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if(blockEntity instanceof RefineryBlockEntity)
				player.openMenu((MenuProvider) blockEntity);
			
			return InteractionResult.CONSUME;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack)
	{
		if(stack.hasCustomHoverName())
		{
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if(blockEntity instanceof RefineryBlockEntity)
				((RefineryBlockEntity) blockEntity).setCustomName(stack.getHoverName());
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if(!state.is(newState.getBlock()))
		{
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if(blockEntity instanceof RefineryBlockEntity)
			{
				if(level instanceof ServerLevel)
				{
					Containers.dropContents(level, pos, (RefineryBlockEntity) blockEntity);
					((RefineryBlockEntity) blockEntity).getRecipesToAwardAndPopExperience((ServerLevel) level, Vec3.atCenterOf(pos));
				}
				
				level.updateNeighbourForOutputSignal(pos, this);
			}
			
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
	{
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.MODEL;
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation)
	{
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror)
	{
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING, LIT);
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random rand)
	{
		if(state.getValue(LIT))
		{
			double x = (double) pos.getX() + 0.5d;
			double y = (double) pos.getY();
			double z = (double) pos.getZ() + 0.5d;
			
			if(rand.nextDouble() < 0.1d)
				level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1f, 1f, false);
			
			Direction direction = state.getValue(FACING);
			Direction.Axis axis = direction.getAxis();
			
			double xzModifier = rand.nextDouble() * 0.6d - 0.3d;
			double xOffset = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52d : xzModifier;
			double yOffset = rand.nextDouble() * 6d /16d;
			double zOffset = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52d : xzModifier;
			
			level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0, 0, 0);
			level.addParticle(ParticleTypes.FLAME, x + xOffset, y + yOffset, z + zOffset, 0, 0, 0);
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, BlockGetter getter, List<Component> tooltip, TooltipFlag flag)
	{
		if(KeyboardHelpers.shift())
		{
			tooltip.add(new TextComponent("\\u00A7aSmelts ores in a more pure way"));
			tooltip.add(new TextComponent("\\u00A7athan the furnace to double the output"));
		}
		else
			tooltip.add(new TextComponent("\\u00A75Hold SHIFT"));
	}
}