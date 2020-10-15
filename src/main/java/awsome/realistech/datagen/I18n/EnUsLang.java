package awsome.realistech.datagen.I18n;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraftforge.common.data.LanguageProvider;

public class EnUsLang extends LanguageProvider {

	public EnUsLang(DataGenerator gen) {
		super(gen, Reference.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		
		//Items Start
		
		//Buckets
		add(Registration.MOLTEN_COPPER.asItem(), "Molten Copper Bucket");
		add(Registration.MOLTEN_TIN.asItem(), "Molten Tin Bucket");
		add(Registration.MOLTEN_NICKEL.asItem(), "Molten Nickel Bucket");
		add(Registration.MOLTEN_SILVER.asItem(), "Molten Silver Bucket");
		add(Registration.MOLTEN_LEAD.asItem(), "Molten Lead Bucket");
		add(Registration.MOLTEN_COBALT.asItem(), "Molten Cobalt Bucket");
		add(Registration.MOLTEN_ZINC.asItem(), "Molten Zinc Bucket");
		add(Registration.MOLTEN_IRON.asItem(), "Molten Iron Bucket");
		add(Registration.MOLTEN_GOLD.asItem(), "Molten Gold Bucket");
		
		//Ingots
		add(Registration.COPPER_INGOT.get(), "Copper Ingot");
		add(Registration.TIN_INGOT.get(), "Tin Ingot");
		add(Registration.SILVER_INGOT.get(), "Silver Ingot");
		add(Registration.LEAD_INGOT.get(), "Lead Ingot");
		add(Registration.ZINC_INGOT.get(), "Zinc Ingot");
		add(Registration.NICKEL_INGOT.get(), "Nickel Ingot");
		add(Registration.COBALT_INGOT.get(), "Cobalt Ingot");
		add(Registration.CRUDE_COPPER_INGOT.get(), "Crude Copper Ingot");
		
		//Clusters
		add(Registration.COPPER_CLUSTER.get(), "Malachite Cluster");
		add(Registration.TIN_CLUSTER.get(), "Cassiterite Cluster");
		add(Registration.LEAD_CLUSTER.get(), "Galena Cluster");
		add(Registration.NICKEL_CLUSTER.get(), "Garnierite Cluster");
		add(Registration.SILVER_CLUSTER.get(), "Native Silver Cluster");
		add(Registration.COBALT_CLUSTER.get(), "Cobaltite Cluster");
		add(Registration.ZINC_CLUSTER.get(), "Sphalerite Cluster");
		add(Registration.IRON_CLUSTER.get(), "Hematite Cluster");
		add(Registration.GOLD_CLUSTER.get(), "Native Gold Cluster");
		
		//Plates
		add(Registration.COPPER_PLATE.get(), "Copper Plate");
		add(Registration.TIN_PLATE.get(), "Tin Plate");
		add(Registration.LEAD_PLATE.get(), "Lead Plate");
		add(Registration.NICKEL_PLATE.get(), "Nickel Plate");
		add(Registration.COBALT_PLATE.get(), "Cobalt Plate");
		add(Registration.ZINC_PLATE.get(), "Zinc Plate");
		add(Registration.SILVER_PLATE.get(), "Silver Plate");
		
		//Chunks
		add(Registration.COPPER_CHUNK.get(), "Copper Chunk");
		
		//Ceramics
			
			//Molds
			add(Registration.UNFIRED_CERAMIC_AXE_MOLD.get(), "Unfired Axe Mold");
			add(Registration.UNFIRED_CERAMIC_PICKAXE_MOLD.get(), "Unfired Pickaxe Mold");
			add(Registration.UNFIRED_CERAMIC_SHOVEL_MOLD.get(), "Unfired Shovel Mold");
			add(Registration.UNFIRED_CERAMIC_PROPICK_MOLD.get(), "Unfired Prospector's Pickaxe Mold");
			add(Registration.UNFIRED_CERAMIC_SWORD_MOLD.get(), "Unfired Sword Mold");
			add(Registration.UNFIRED_CERAMIC_INGOT_MOLD.get(), "Unfired Ingot Mold");
			add(Registration.FIRED_CERAMIC_AXE_MOLD.get(), "Axe Mold");
			add(Registration.FIRED_CERAMIC_PICKAXE_MOLD.get(), "Pickaxe Mold");
			add(Registration.FIRED_CERAMIC_SHOVEL_MOLD.get(), "Shovel Mold");
			add(Registration.FIRED_CERAMIC_PROPICK_MOLD.get(), "Prospector's Pickaxe Mold");
			add(Registration.FIRED_CERAMIC_SWORD_MOLD.get(), "Sword Mold");
			add(Registration.FIRED_CERAMIC_INGOT_MOLD.get(), "Ingot Mold");
			
			//Bricks
			add(Registration.UNFIRED_CLAY_BRICK.get(), "Unfired Clay Brick");
			add(Registration.UNFIRED_KAOLINITE_BRICK.get(), "Unfired Kaolin Brick");
			add(Registration.UNFIRED_KILN_BRICK.get(), "Unfired Kiln Brick");
			add(Registration.KILN_BRICK.get(), "Kiln Brick");
			add(Registration.FIREBRICK.get(), "Firebrick");
			
			//Clay
			add(Registration.KILN_CLAY_BALL.get(), "Kiln Clay");
			add(Registration.KAOLINITE_CLAY_BALL.get(), "Kaolin Clay");
			
		//Tool Heads
			
			//Stone
			add(Registration.STONE_AXE_HEAD.get(), "Stone Axe Head");
			add(Registration.STONE_SHOVEL_HEAD.get(), "Stone Shovel Head");
			add(Registration.STONE_PICKAXE_HEAD.get(), "Stone Pickaxe Head");
			add(Registration.STONE_HAMMER_HEAD.get(), "Stone Hammer Head");
			add(Registration.STONE_CHISEL_HEAD.get(), "Stone Chisel Head");
		
		//Tools
			
			//Stone
			add(Registration.STONE_AXE.get(), "Stone Axe");
			add(Registration.STONE_SHOVEL.get(), "Stone Shovel");
			add(Registration.STONE_PICKAXE.get(), "Stone Pickaxe");
			add(Registration.STONE_HAMMER.get(), "Stone Hammer");
			add(Registration.STONE_CHISEL.get(), "Stone Chisel");
			add(Registration.STONE_MORTAR_AND_PESTLE.get(), "Stone Mortar and Pestle");
		
		//Misc Items
		add(Registration.PLANT_FIBER.get(), "Plant Fiber");
		add(Registration.PLANT_FIBER_CORDAGE.get(), "Plant Fiber Cordage");
		add(Registration.ROCK_ITEM.get(), "Rock");
		add(Registration.PRIMITIVE_BRICK_MOLD.get(), "Primitive Brick Mold");
		add(Registration.KILN_CLAY_MIXTURE.get(), "Kiln-Ready Compound");
		
		//Items End
		
		//Blocks Start
		
		//Vanilla Overrides
		add(Items.FURNACE, "Weak Furnace");
		
		//Machines
		add(Registration.FIREBOX.get(), "Firebox");
		add(Registration.CRUCIBLE.get(), "Crucible");
		add(Registration.KILN.get(), "Kiln");
		add(Registration.WEAK_FURNACE.get(), "Furnace");
		
		//Anvils
		add(Registration.STONE_ANVIL.get(), "Stone Anvil");
		
		//Ores
		add(Registration.COPPER_ORE.get(), "Malachite");
		add(Registration.TIN_ORE.get(), "Cassiterite");
		add(Registration.LEAD_ORE.get(), "Galena");
		add(Registration.NICKEL_ORE.get(), "Garnierite");
		add(Registration.SILVER_ORE.get(), "Native Silver");
		add(Registration.COBALT_ORE.get(), "Cobaltite");
		add(Registration.ZINC_ORE.get(), "Sphalerite");
		add(Registration.MOD_IRON_ORE.get(), "Hematite");
		add(Registration.MOD_GOLD_ORE.get(), "Native Gold");
		add(Registration.MOD_DIAMOND_ORE.get(), "Diamondiferous Kimberlite");
		add(Registration.MOD_EMERALD_ORE.get(), "Beryl");
		add(Registration.MOD_LAPIS_ORE.get(), "Lazurite");
		add(Registration.MOD_REDSTONE_ORE.get(), "Cinnabar");
		add(Registration.MOD_COAL_ORE.get(), "Bituminous Coal");
		
		//Samples
		add(Registration.COPPER_SAMPLE.get(), "Malachite Sample");
		add(Registration.TIN_SAMPLE.get(), "Cassiterite Sample");
		add(Registration.LEAD_SAMPLE.get(), "Galena Sample");
		add(Registration.NICKEL_SAMPLE.get(), "Garnierite Sample");
		add(Registration.SILVER_SAMPLE.get(), "Native Silver Sample");
		add(Registration.COBALT_SAMPLE.get(), "Cobaltite Sample");
		add(Registration.ZINC_SAMPLE.get(), "Sphalerite Sample");
		add(Registration.IRON_SAMPLE.get(), "Hematite Sample");
		add(Registration.GOLD_SAMPLE.get(), "Native Gold Sample");
		add(Registration.DIAMOND_SAMPLE.get(), "Diamondiferous Kimberlite Sample");
		add(Registration.EMERALD_SAMPLE.get(), "Beryl Sample");
		add(Registration.LAPIS_SAMPLE.get(), "Lazurite Sample");
		add(Registration.REDSTONE_SAMPLE.get(), "Cinnabar Sample");
		add(Registration.COAL_SAMPLE.get(), "Bituminous Coal Sample");
		
		//Flowers
		add(Registration.KAOLINITE_LILY.get(), "Kaolinite Lily");
		add(Registration.GOLDENROD.get(), "Goldenrod");
		
		//Misc Blocks
		add(Registration.KAOLINITE_CLAY.get(), "Kaolin Clay");
		add(Registration.KAOLINITE_CLAY_GRASS.get(), "Grassy Kaolin Clay");
		add(Registration.VANILLA_CLAY_GRASS.get(), "Grassy Clay");
		add(Registration.ROCK.get(), "Rock");
		add(Registration.FIREBRICKS.get(), "Firebricks");
		add(Registration.STICK.get(), "Stick");
		
		//Blocks End
		
		//Fluids Start
		
		add("fluid.realistech.molten_copper", "Molten Copper");
		add("fluid.realistech.molten_tin", "Molten Tin");
		add("fluid.realistech.molten_nickel", "Molten Nickel");
		add("fluid.realistech.molten_lead", "Molten Lead");
		add("fluid.realistech.molten_silver", "Molten Silver");
		add("fluid.realistech.molten_cobalt", "Molten Cobalt");
		add("fluid.realistech.molten_zinc", "Molten Zinc");
		add("fluid.realistech.molten_iron", "Molten Iron");
		add("fluid.realistech.molten_gold", "Molten Gold");
		
		//Fluids End
		
		//Creative Tabs Start
		
		add("itemGroup.realistech.machines", "RealisTech Machines");
		add("itemGroup.realistech.ores", "RealisTech Ores");
		add("itemGroup.realistech.tools", "RealisTech Tools");
		add("itemGroup.realistech.materials", "RealisTech Materials");
		add("itemGroup.realistech.misc", "RealisTech Misc. Items");
		
		//Creative Tabs End
		
		//Other Translation Entries Start
		
		//Vanilla Overrides
		add("container.furnace", "Weak Furnace");
		
		//Mod Overrides
		add("gui.jei.category.smelting", "Weak Smelting");
		
		add("container.realistech.firebox", "Firebox");
		add("container.realistech.crucible", "Crucible");
		add("container.realistech.kiln", "Kiln");
		add("container.realistech.knapping", "Knapping");
		add("container.realistech.molding", "Molding");
		add("container.realistech.weak_furnace", "Furnace");
		add("screen.realistech.jei_compat.kiln", "Kiln Firing");
		add("screen.realistech.jei_compat.anvil", "Anvil Working");
		add("realistech.info.temperature", "%s°C");
		add("message.realistech.noBreak", "Your fists break!");
		add("tooltip.realistech.brick", "\"I'm a brick!\"");
		add("tooltip.realistech.fireclay", "Can be used as fireclay.");
		
		
		//Other Translation Entries End
	}
}
