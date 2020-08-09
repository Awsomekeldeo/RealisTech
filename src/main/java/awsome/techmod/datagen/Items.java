package awsome.techmod.datagen;

import awsome.techmod.Reference;
import awsome.techmod.registry.Registration;
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
		withExistingParent("item/machines/" + Registration.FIREBOX_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/machines/firebox"));
		withExistingParent("item/machines/" + Registration.CRUCIBLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/machines/crucible"));
		
		//Ores
		withExistingParent("item/ores/" + Registration.COPPER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/copper_ore"));
		withExistingParent("item/ores/" + Registration.TIN_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/tin_ore"));
		withExistingParent("item/ores/" + Registration.NICKEL_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/nickel_ore"));
		withExistingParent("item/ores/" + Registration.SILVER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/silver_ore"));
		withExistingParent("item/ores/" + Registration.LEAD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/lead_ore"));
		withExistingParent("item/ores/" + Registration.ZINC_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/zinc_ore"));
		withExistingParent("item/ores/" + Registration.COBALT_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/cobalt_ore"));
		withExistingParent("item/ores/" + Registration.MOD_IRON_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_iron_ore"));
		withExistingParent("item/ores/" + Registration.MOD_GOLD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_gold_ore"));
		withExistingParent("item/ores/" + Registration.MOD_COAL_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_coal_ore"));
		withExistingParent("item/ores/" + Registration.MOD_DIAMOND_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_diamond_ore"));
		withExistingParent("item/ores/" + Registration.MOD_EMERALD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_emerald_ore"));
		withExistingParent("item/ores/" + Registration.MOD_REDSTONE_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_redstone_ore"));
		withExistingParent("item/ores/" + Registration.MOD_LAPIS_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/mod_lapis_ore"));
		
		//Ore Samples
		withExistingParent("item/ores/samples/" + Registration.COPPER_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/copper_sample"));
		withExistingParent("item/ores/samples/" + Registration.TIN_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/tin_sample"));
		withExistingParent("item/ores/samples/" + Registration.NICKEL_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/nickel_sample"));
		withExistingParent("item/ores/samples/" + Registration.SILVER_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/silver_sample"));
		withExistingParent("item/ores/samples/" + Registration.COBALT_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/cobalt_sample"));
		withExistingParent("item/ores/samples/" + Registration.LEAD_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/lead_sample"));
		withExistingParent("item/ores/samples/" + Registration.ZINC_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/zinc_sample"));
		withExistingParent("item/ores/samples/" + Registration.COAL_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/coal_sample"));
		withExistingParent("item/ores/samples/" + Registration.IRON_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/iron_sample"));
		withExistingParent("item/ores/samples/" + Registration.GOLD_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/gold_sample"));
		withExistingParent("item/ores/samples/" + Registration.DIAMOND_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/diamond_sample"));
		withExistingParent("item/ores/samples/" + Registration.EMERALD_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/emerald_sample"));
		withExistingParent("item/ores/samples/" + Registration.LAPIS_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/lapis_sample"));
		withExistingParent("item/ores/samples/" + Registration.REDSTONE_SAMPLE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/redstone_sample"));
		
		//Clusters
		singleTexture("item/ore_clusters/" + Registration.COPPER_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/copper"));
		singleTexture("item/ore_clusters/" + Registration.TIN_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/tin"));
		singleTexture("item/ore_clusters/" + Registration.ZINC_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/zinc"));
		singleTexture("item/ore_clusters/" + Registration.NICKEL_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/nickel"));
		singleTexture("item/ore_clusters/" + Registration.COBALT_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/cobalt"));
		singleTexture("item/ore_clusters/" + Registration.SILVER_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/silver"));
		singleTexture("item/ore_clusters/" + Registration.LEAD_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/lead"));
		singleTexture("item/ore_clusters/" + Registration.IRON_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/iron"));
		singleTexture("item/ore_clusters/" + Registration.GOLD_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/gold"));
		
		//Ingots
		singleTexture("item/ingots/" + Registration.COPPER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/copper"));
		singleTexture("item/ingots/" + Registration.TIN_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/tin"));
		singleTexture("item/ingots/" + Registration.NICKEL_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/nickel"));
		singleTexture("item/ingots/" + Registration.LEAD_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/lead"));
		singleTexture("item/ingots/" + Registration.SILVER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/silver"));
		singleTexture("item/ingots/" + Registration.COBALT_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/cobalt"));
		singleTexture("item/ingots/" + Registration.ZINC_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/zinc"));
	}
}