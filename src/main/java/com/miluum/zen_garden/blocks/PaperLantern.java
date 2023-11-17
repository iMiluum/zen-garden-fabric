package com.miluum.zen_garden.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PaperLantern extends Block {
    public PaperLantern(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(IS_LIT, false));
    }

    public static BooleanProperty IS_LIT = BooleanProperty.of("is_lit");

    VoxelShape shape = VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.9375, 0.75);

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
}
