package com.miluum.zen_garden.items;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.slf4j.Logger;

public class PaperLanternItem extends BlockItem {
    public PaperLanternItem(Block block, Settings settings) {
        super(block, settings);
    }
}
