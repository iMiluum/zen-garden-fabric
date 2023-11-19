package com.miluum.zen_garden;

import com.miluum.zen_garden.blocks.PaperLantern;
import com.miluum.zen_garden.items.PaperLanternItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

import static com.miluum.zen_garden.blocks.PaperLantern.IS_LIT;

public class Zen_garden implements ModInitializer {
	public static final String MOD_ID = "zen_garden";
	public static final String MOD_NAME = "Zen Garden";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Collection<ItemStack> allItems = new ArrayList<>();

	public static final AbstractBlock.Settings PAPER_LANTERN_SETTINGS = AbstractBlock.Settings.create()
			.emissiveLighting((state, world, pos) -> state.get(IS_LIT))
			.burnable()
			.luminance((state)-> state.get(IS_LIT) ? 15 : 0)
			.instrument(Instrument.FLUTE)
			.sounds(BlockSoundGroup.SCAFFOLDING)
			.hardness(0.1f);

	public static final Block PAPER_LANTERN = new PaperLantern(PAPER_LANTERN_SETTINGS, LOGGER);
	public static final Item PAPER_LANTERN_ITEM =  new PaperLanternItem(PAPER_LANTERN, new FabricItemSettings());

	public static final Block FINE_STONE = new Block(AbstractBlock.Settings.copy(Blocks.GRAVEL));
	public static final Item FINE_STONE_ITEM = new BlockItem(FINE_STONE, new FabricItemSettings());

	private static void registerBlockItem(String id, Block block, Item item) {
		Registry.register(Registries.BLOCK, id, block);
		Registry.register(Registries.ITEM, id, item);
		allItems.add(new ItemStack(item));
	}

	private static void registerItem(String id, Item item) {
		Registry.register(Registries.ITEM, id, item);
	}

	private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(PAPER_LANTERN_ITEM))
			.displayName(Text.translatable("itemGroup.zen_garden.main"))
			.entries((context, entries) -> {
				entries.addAll(allItems);
			})
			.build();



	public static void registerBuiltinPack(String namespace, String path) {
		ModContainer mod = FabricLoader.getInstance().getModContainer("zen_garden").orElseThrow();
		ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(namespace, path), mod, ResourcePackActivationType.NORMAL);
	}


	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Zen Garden!");

		Registry.register(Registries.ITEM_GROUP, new Identifier("zen_garden", "main"), ITEM_GROUP);

		registerBlockItem("zen_garden:paper_lantern", PAPER_LANTERN, PAPER_LANTERN_ITEM);
		registerBlockItem("zen_garden:fine_stone_block", FINE_STONE, FINE_STONE_ITEM);

		registerBuiltinPack("zen_garden", "pixel_garden");
	}
}