package com.miluum.zen_garden.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PaperLantern extends Block {
    public PaperLantern(Settings settings) {
        super(settings);
    }

    VoxelShape shape = VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.9375, 0.75);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return shape;
    }

}
