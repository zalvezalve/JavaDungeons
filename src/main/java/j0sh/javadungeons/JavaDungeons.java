package j0sh.javadungeons;

import net.forgemc.api.ModInitializer;
import net.forgemc.fabric.api.client.itemgroup.ForgeItemGroupBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.item.ItemGroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import j0sh.javadungeons.content.*;

public class JavaDungeons implements ModInitializer {

	public static final String MOD_ID = "dungeons";

	public static final Logger log = LogManager.getLogger(MOD_ID);

	public static final ItemGroup GENERIC = ForgecItemGroupBuilder.build(new Identifier(MOD_ID, "generic"), () -> new ItemStack(GenericBlocks.GREEN_LIT_BRAZIER));
	public static final ItemGroup CREEPER_WOODS = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "creeper_woods"), () -> new ItemStack(CreeperWoodsBlocks.CW_MOSSY_STONE_BRICKS));
	public static final ItemGroup DESERT_TEMPLE = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "desert_temple"), () -> new ItemStack(DesertTempleBlocks.DT_CHISELED_SANDY_STONE));
	public static final ItemGroup PUMPKIN_PASTURES = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "pumpkin_pastures"), () -> new ItemStack(PumpkinPasturesBlocks.PM_RED_AUTUMNAL_LEAVES));
	public static final ItemGroup SOGGY_SWAMP = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "soggy_swamp"), () -> new ItemStack(SoggySwampBlocks.SS_GRASS_BLOCK));
	public static final ItemGroup CACTI_CANYON = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "cacti_canyon"), () -> new ItemStack(CactiCanyonBlocks.CC_CACTUS));
	public static final ItemGroup REDSTONE_MINES = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "redstone_mines"), () -> new ItemStack(RedstoneMinesBlocks.RM_REDSTONE_CRYSTALS));
	public static final ItemGroup FIERY_FORGE = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "fiery_forge"), () -> new ItemStack(FieryForgeBlocks.FF_EMBLEMED_DARK_STONE));
	public static final ItemGroup CREEPING_WINTER = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "creeping_winter"), () -> new ItemStack(CreepingWinterBlocks.CI_ICE_BRICKS));
	public static final ItemGroup WEAPONS = ForgeItemGroupBuilder.build(new Identifier(MOD_ID, "weapons"), () -> new ItemStack(Weapons.FIREBRAND));

	@Override
	public void onInitialize() {
		// other stuff
		Tags.init();
		Sounds.init();
		Fluids.init();
		Particles.init();
		Properties.init();
		Recipes.init();
		Containers.init();
		Paintings.init();

		// blocks
		GenericBlocks.init();
		CreeperWoodsBlocks.init();
		SoggySwampBlocks.init();
		DesertTempleBlocks.init();
		PumpkinPasturesBlocks.init();
		CactiCanyonBlocks.init();
		RedstoneMinesBlocks.init();
		FieryForgeBlocks.init();
		CreepingWinterBlocks.init();

		// items
		Weapons.init();

		// worldgen
		SurfaceBuilders.init();
		Features.init();
		Biomes.init();

		log.info("JavaDungeons initialized!");
	}
}
