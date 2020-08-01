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
		withExistingParent(Registration.FIREBOX_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/firebox"));
		
		//Ores
		withExistingParent(Registration.COPPER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/copper_ore"));
		withExistingParent(Registration.TIN_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/tin_ore"));
		withExistingParent(Registration.NICKEL_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/nickel_ore"));
		withExistingParent(Registration.SILVER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/silver_ore"));
		withExistingParent(Registration.LEAD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/lead_ore"));
		withExistingParent(Registration.ZINC_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/zinc_ore"));
		withExistingParent(Registration.COBALT_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/cobalt_ore"));
		withExistingParent(Registration.MOD_IRON_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/mod_iron_ore"));
		withExistingParent(Registration.MOD_GOLD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/mod_gold_ore"));
		withExistingParent(Registration.MOD_COAL_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/mod_coal_ore"));
		withExistingParent(Registration.MOD_DIAMOND_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/mod_diamond_ore"));
		withExistingParent(Registration.MOD_EMERALD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/mod_emerald_ore"));
		withExistingParent(Registration.MOD_REDSTONE_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/mod_redstone_ore"));
		withExistingParent(Registration.MOD_LAPIS_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/mod_lapis_ore"));
		
		//Clusters
		singleTexture(Registration.COPPER_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/copper"));
		singleTexture(Registration.TIN_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/tin"));
		singleTexture(Registration.ZINC_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/zinc"));
		singleTexture(Registration.NICKEL_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/nickel"));
		singleTexture(Registration.COBALT_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/cobalt"));
		singleTexture(Registration.SILVER_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/silver"));
		singleTexture(Registration.LEAD_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/lead"));
		singleTexture(Registration.IRON_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/iron"));
		singleTexture(Registration.GOLD_CLUSTER.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/clusters/gold"));
		
		//Ingots
		singleTexture(Registration.COPPER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/copper"));
		singleTexture(Registration.TIN_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/tin"));
		singleTexture(Registration.NICKEL_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/nickel"));
		singleTexture(Registration.LEAD_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/lead"));
		singleTexture(Registration.SILVER_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/silver"));
		singleTexture(Registration.COBALT_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/cobalt"));
		singleTexture(Registration.ZINC_INGOT.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Reference.MODID, "items/ingots/zinc"));
	}
}