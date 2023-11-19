package com.miluum.zen_garden.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.slf4j.Logger;

public class PaperLantern extends FallingBlock {
    public PaperLantern(Settings settings, Logger logger) {
        super(settings);
        this.LOGGER = logger;
        setDefaultState(getStateManager().getDefaultState().with(IS_LIT, false));
    }

    private final Logger LOGGER;
    public static BooleanProperty IS_LIT = BooleanProperty.of("is_lit");
    public static BooleanProperty HOOKED = BooleanProperty.of("hooked");
    public static EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class);

    VoxelShape shape = VoxelShapes.cuboid(0.21875, 0, 0.21875, 0.78125, 0.9375, 0.78125);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    protected void appendProperties(net.minecraft.state.StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_LIT);
        builder.add(FACING);
        builder.add(HOOKED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockState(pos).get(IS_LIT)) {
            player.playSound(SoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF, 1, 1);
            world.setBlockState(pos, state.with(IS_LIT, false));
        }
        else {
            player.playSound(SoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, 1, 1);
            world.setBlockState(pos, state.with(IS_LIT, true));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        var facing = context.getSide();
        LOGGER.info("facing: " + facing);
        boolean isPlayerSneaking = context.getPlayer() != null && context.getPlayer().isSneaking();
        boolean isHooked = !isPlayerSneaking;
        return this.getDefaultState().with(HOOKED, isHooked).with(FACING, facing);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(HOOKED)) {
            super.scheduledTick(state, world, pos, random);
        }
    }
}
