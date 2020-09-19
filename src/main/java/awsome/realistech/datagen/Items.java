package awsome.realistech.datagen;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public class Items extends ItemModelProvider {

	public Items(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, Reference.MODID, helper);
	}

	@Override
	protected void registerModels() {
		//Machines
		withExistingParent("item/" + Registration.FIREBOX_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/machines/firebox"));
		withExistingParent("item/" + Registration.CRUCIBLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/machines/crucible"));
		withExistingParent("item/" + Registration.KILN_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/machines/kiln"));
		
		//Ores
		withExistingParent("item/" + Registration.COPPER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/copper_ore"));
		withExistingParent("item/" + Registration.TIN_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/tin_ore"));
		withExistingParent("item/" + Registration.NICKEL_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/nickel_ore"));
		withExistingParent("item/" + Registration.SILVER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/silver_ore"));
		withExistingParent("item/" + Registration.LEAD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/lead_ore"));
		withExistingParent("item/" + Registration.ZINC_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/zinc_ore"));
		withExistingParent("item/" + Registration.COBALT_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/cobalt_ore"));
		withExistingParent("item/" + Registration.MOD_IRON_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_iron_ore"));
		withExistingParent("item/" + Registration.MOD_GOLD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_gold_ore"));
		withExistingParent("item/" + Registration.MOD_COAL_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_coal_ore"));
		withExistingParent("item/" + Registration.MOD_DIAMOND_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_diamond_ore"));
		withExistingParent("item/" + Registration.MOD_EMERALD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_emerald_ore"));
		withExistingParent("item/" + Registration.MOD_REDSTONE_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_redstone_ore"));
		withExistingParent("item/" + Registration.MOD_LAPIS_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_lapis_ore"));
		
		//Ore Samples
		withExistingParent("item/" + Registration.COPPER_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/copper_sample"));
		withExistingParent("item/" + Registration.TIN_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/tin_sample"));
		withExistingParent("item/" + Registration.NICKEL_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/nickel_sample"));
		withExistingParent("item/" + Registration.SILVER_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/silver_sample"));
		withExistingParent("item/" + Registration.COBALT_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/cobalt_sample"));
		withExistingParent("item/" + Registration.LEAD_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/lead_sample"));
		withExistingParent("item/" + Registration.ZINC_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/zinc_sample"));
		withExistingParent("item/" + Registration.COAL_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/coal_sample"));
		withExistingParent("item/" + Registration.IRON_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/iron_sample"));
		withExistingParent("item/" + Registration.GOLD_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/gold_sample"));
		withExistingParent("item/" + Registration.DIAMOND_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/diamond_sample"));
		withExistingParent("item/" + Registration.EMERALD_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/emerald_sample"));
		withExistingParent("item/" + Registration.LAPIS_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/lapis_sample"));
		withExistingParent("item/" + Registration.REDSTONE_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/redstone_sample"));
		withExistingParent("item/" + Registration.ROCK_ITEMBLOCK.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/rock"));
		
		//Clusters
		singleTexture("item/" + Registration.COPPER_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/copper"));
		singleTexture("item/" + Registration.TIN_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/tin"));
		singleTexture("item/" + Registration.ZINC_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/zinc"));
		singleTexture("item/" + Registration.NICKEL_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/nickel"));
		singleTexture("item/" + Registration.COBALT_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/cobalt"));
		singleTexture("item/" + Registration.SILVER_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/silver"));
		singleTexture("item/" + Registration.LEAD_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/lead"));
		singleTexture("item/" + Registration.IRON_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/iron"));
		singleTexture("item/" + Registration.GOLD_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/gold"));
		
		//Clay Grasses
		withExistingParent("item/" + Registration.VANILLA_CLAY_GRASS_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/clay_grass"));
		withExistingParent("item/" + Registration.KAOLINITE_CLAY_GRASS_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/kaolinite_clay_grass"));
		
		//Clay Blocks
		withExistingParent("item/" + Registration.KAOLINITE_CLAY_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/kaolinite_clay"));
		
		//Ceramics
		singleTexture("item/" + Registration.UNFIRED_CLAY_BRICK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/unfired_clay_brick"));
		singleTexture("item/" + Registration.UNFIRED_KILN_BRICK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/unfired_kiln_brick"));
		singleTexture("item/" + Registration.PRIMITIVE_BRICK_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/primitive_brick_mold"));
		singleTexture("item/" + Registration.KILN_BRICK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/kiln_brick"));
		singleTexture("item/" + Registration.KILN_CLAY_BALL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/kiln_clay_ball"));
		singleTexture("item/" + Registration.KAOLINITE_CLAY_BALL.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/kaolinite_clay_ball"));
		singleTexture("item/" + Registration.UNFIRED_KAOLINITE_BRICK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/unfired_kaolinite_brick"));
		singleTexture("item/" + Registration.FIREBRICK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/firebrick"));
		
		//Flowers
		singleTexture("item/" + Registration.GOLDENROD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/goldenrod"));
		singleTexture("item/" + Registration.KAOLINITE_LILY_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/kaolinite_lily"));
		
		//Misc
		singleTexture("item/" + Registration.ROCK.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/rock"));
		
		//Ingots
		singleTexture("item/" + Registration.COPPER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/copper"));
		singleTexture("item/" + Registration.TIN_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/tin"));
		singleTexture("item/" + Registration.NICKEL_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/nickel"));
		singleTexture("item/" + Registration.LEAD_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/lead"));
		singleTexture("item/" + Registration.SILVER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/silver"));
		singleTexture("item/" + Registration.COBALT_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/cobalt"));
		singleTexture("item/" + Registration.ZINC_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/zinc"));
		
		//Unfired Molds
		singleTexture("item/" + Registration.UNFIRED_CERAMIC_AXE_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/unfired/axe_mold"));
		singleTexture("item/" + Registration.UNFIRED_CERAMIC_PICKAXE_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/unfired/pickaxe_mold"));
		singleTexture("item/" + Registration.UNFIRED_CERAMIC_SHOVEL_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/unfired/shovel_mold"));
		singleTexture("item/" + Registration.UNFIRED_CERAMIC_SWORD_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/unfired/sword_mold"));
		singleTexture("item/" + Registration.UNFIRED_CERAMIC_PROPICK_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/unfired/propick_mold"));
		singleTexture("item/" + Registration.UNFIRED_CERAMIC_INGOT_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/unfired/ingot_mold"));
		
		//Fired Molds
		singleTexture("item/" + Registration.FIRED_CERAMIC_AXE_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/fired/axe_mold"));
		singleTexture("item/" + Registration.FIRED_CERAMIC_PICKAXE_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/fired/pickaxe_mold"));
		singleTexture("item/" + Registration.FIRED_CERAMIC_SHOVEL_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/fired/shovel_mold"));
		singleTexture("item/" + Registration.FIRED_CERAMIC_SWORD_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/fired/sword_mold"));
		singleTexture("item/" + Registration.FIRED_CERAMIC_PROPICK_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/fired/propick_mold"));
		singleTexture("item/" + Registration.FIRED_CERAMIC_INGOT_MOLD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ceramic_molds/fired/ingot_mold"));
		
		//Tool Heads
			
			//Stone
			singleTexture("item/" + Registration.STONE_AXE_HEAD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/tool_heads/stone_axe"));
			singleTexture("item/" + Registration.STONE_SHOVEL_HEAD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/tool_heads/stone_shovel"));
			singleTexture("item/" + Registration.STONE_CHISEL_HEAD.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/tool_heads/stone_chisel"));
	}
}