package awsome.realistech.datagen;

import java.util.function.Consumer;

import awsome.realistech.data.generators.AlloyRecipeBuilder;
import awsome.realistech.data.generators.AnvilRecipeBuilder;
import awsome.realistech.data.generators.HandworkRecipeBuilder;
import awsome.realistech.data.generators.KilnRecipeBuilder;
import awsome.realistech.data.generators.MeltingRecipeBuilder;
import awsome.realistech.data.generators.NonConsumingShapelessRecipeBuilder;
import awsome.realistech.data.generators.SolidificationRecipeBuilder;
import awsome.realistech.data.generators.WeakSmeltingRecipeBuilder;
import awsome.realistech.data.tags.ModTags;
import awsome.realistech.registry.Registration;
import awsome.realistech.util.MoldType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class Recipes extends RecipeProvider {
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		
		//Alloying Recipes
		AlloyRecipeBuilder.alloyRecipe(new FluidStack(Registration.MOLTEN_BRONZE.get(), 4)).addInput(new FluidStack(Registration.MOLTEN_COPPER.get(), 3)).addInput(new FluidStack(Registration.MOLTEN_TIN.get(), 1)).build(consumer, "alloying/", true);
		
		//Melting Recipes
			
			//Clusters
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COPPER_CLUSTER.get()), new FluidStack(Registration.MOLTEN_COPPER.get(), 144), 1085.0f).build(consumer, "melting/clusters/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.TIN_CLUSTER.get()), new FluidStack(Registration.MOLTEN_TIN.get(), 144), 231.0f).build(consumer, "melting/clusters/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.NICKEL_CLUSTER.get()), new FluidStack(Registration.MOLTEN_NICKEL.get(), 144), 1455.0f).build(consumer, "melting/clusters/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.SILVER_CLUSTER.get()), new FluidStack(Registration.MOLTEN_SILVER.get(), 144), 961.0f).build(consumer, "melting/clusters/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.LEAD_CLUSTER.get()), new FluidStack(Registration.MOLTEN_LEAD.get(), 144), 621.0f).build(consumer, "melting/clusters/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COBALT_CLUSTER.get()), new FluidStack(Registration.MOLTEN_COBALT.get(), 144), 1495.0f).build(consumer, "melting/clusters/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.ZINC_CLUSTER.get()), new FluidStack(Registration.MOLTEN_ZINC.get(), 144), 419.0f).build(consumer, "melting/clusters/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.GOLD_CLUSTER.get()), new FluidStack(Registration.MOLTEN_GOLD.get(), 144), 419.0f).build(consumer, "melting/clusters/", true);
			
			//Ingots
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COPPER_INGOT.get()), new FluidStack(Registration.MOLTEN_COPPER.get(), 144), 1085.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.TIN_INGOT.get()), new FluidStack(Registration.MOLTEN_TIN.get(), 144), 231.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.NICKEL_INGOT.get()), new FluidStack(Registration.MOLTEN_NICKEL.get(), 144), 1455.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.SILVER_INGOT.get()), new FluidStack(Registration.MOLTEN_SILVER.get(), 144), 961.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.LEAD_INGOT.get()), new FluidStack(Registration.MOLTEN_LEAD.get(), 144), 621.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COBALT_INGOT.get()), new FluidStack(Registration.MOLTEN_COBALT.get(), 144), 1495.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.ZINC_INGOT.get()), new FluidStack(Registration.MOLTEN_ZINC.get(), 144), 419.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Items.IRON_INGOT), new FluidStack(Registration.MOLTEN_IRON.get(), 144), 1538.0f).build(consumer, "melting/ingots/", true);
			MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Items.GOLD_INGOT), new FluidStack(Registration.MOLTEN_GOLD.get(), 144), 1064.0f).build(consumer, "melting/ingots/", true);
			
			//Tool Heads
			
				//Copper
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COPPER_AXE_HEAD.get()), new FluidStack(Registration.MOLTEN_COPPER.get(), 432), 1085.0f).build(consumer, "melting/tool_heads/copper_axe/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COPPER_SHOVEL_HEAD.get()), new FluidStack(Registration.MOLTEN_COPPER.get(), 144), 1085.0f).build(consumer, "melting/tool_heads/copper_shovel/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COPPER_PICKAXE_HEAD.get()), new FluidStack(Registration.MOLTEN_COPPER.get(), 432), 1085.0f).build(consumer, "melting/tool_heads/copper_pickaxe/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COPPER_SWORD_HEAD.get()), new FluidStack(Registration.MOLTEN_COPPER.get(), 288), 1085.0f).build(consumer, "melting/tool_heads/copper_sword/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.COPPER_PROPICK_HEAD.get()), new FluidStack(Registration.MOLTEN_COPPER.get(), 288), 1085.0f).build(consumer, "melting/tool_heads/copper_propick/", true);
				
				//Bronze
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.BRONZE_AXE_HEAD.get()), new FluidStack(Registration.MOLTEN_BRONZE.get(), 432), 950.0f).build(consumer, "melting/tool_heads/copper_axe/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.BRONZE_SHOVEL_HEAD.get()), new FluidStack(Registration.MOLTEN_BRONZE.get(), 144), 950.0f).build(consumer, "melting/tool_heads/copper_shovel/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.BRONZE_PICKAXE_HEAD.get()), new FluidStack(Registration.MOLTEN_BRONZE.get(), 432), 950.0f).build(consumer, "melting/tool_heads/copper_pickaxe/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.BRONZE_SWORD_HEAD.get()), new FluidStack(Registration.MOLTEN_BRONZE.get(), 288), 950.0f).build(consumer, "melting/tool_heads/copper_sword/", true);
				MeltingRecipeBuilder.meltingRecipe(Ingredient.fromItems(Registration.BRONZE_PROPICK_HEAD.get()), new FluidStack(Registration.MOLTEN_BRONZE.get(), 288), 950.0f).build(consumer, "melting/tool_heads/copper_propick/", true);
		
		//Solidifying Recipes
		
			//Ingots
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_COPPER.get(), 144), Registration.COPPER_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_TIN.get(), 144), Registration.TIN_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_NICKEL.get(), 144), Registration.NICKEL_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_SILVER.get(), 144), Registration.SILVER_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_LEAD.get(), 144), Registration.LEAD_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_COBALT.get(), 144), Registration.COBALT_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_ZINC.get(), 144), Registration.ZINC_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_GOLD.get(), 144), Items.GOLD_INGOT, MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_IRON.get(), 144), Items.IRON_INGOT, MoldType.INGOT).build(consumer, "solidifying/", true);
			SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_BRONZE.get(), 144), Registration.BRONZE_INGOT.get(), MoldType.INGOT).build(consumer, "solidifying/", true);
			
			//Tool Heads
			
				//Copper
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_COPPER.get(), 432), Registration.COPPER_AXE_HEAD.get(), MoldType.AXE).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_COPPER.get(), 144), Registration.COPPER_SHOVEL_HEAD.get(), MoldType.SHOVEL).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_COPPER.get(), 432), Registration.COPPER_PICKAXE_HEAD.get(), MoldType.PICKAXE).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_COPPER.get(), 288), Registration.COPPER_SWORD_HEAD.get(), MoldType.SWORD).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_COPPER.get(), 288), Registration.COPPER_PROPICK_HEAD.get(), MoldType.PROPICK).build(consumer, "solidifying/", true);
				
				//Bronze
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_BRONZE.get(), 432), Registration.BRONZE_AXE_HEAD.get(), MoldType.AXE).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_BRONZE.get(), 144), Registration.BRONZE_SHOVEL_HEAD.get(), MoldType.SHOVEL).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_BRONZE.get(), 432), Registration.BRONZE_PICKAXE_HEAD.get(), MoldType.PICKAXE).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_BRONZE.get(), 288), Registration.BRONZE_SWORD_HEAD.get(), MoldType.SWORD).build(consumer, "solidifying/", true);
				SolidificationRecipeBuilder.solidificationRecipe(new FluidStack(Registration.MOLTEN_BRONZE.get(), 288), Registration.BRONZE_PROPICK_HEAD.get(), MoldType.PROPICK).build(consumer, "solidifying/", true);
			
		//Shapeless Recipes
		
			
			
			//Tools
			
				//Stone
				ShapelessRecipeBuilder.shapelessRecipe(Registration.STONE_AXE.get()).addIngredient(Registration.PLANT_FIBER_CORDAGE.get()).addIngredient(Registration.STONE_AXE_HEAD.get()).addIngredient(Items.STICK).addCriterion("stick", InventoryChangeTrigger.Instance.forItems(Items.STICK)).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.STONE_SHOVEL.get()).addIngredient(Registration.PLANT_FIBER_CORDAGE.get()).addIngredient(Registration.STONE_SHOVEL_HEAD.get()).addIngredient(Items.STICK).addCriterion("stick", InventoryChangeTrigger.Instance.forItems(Items.STICK)).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.STONE_PICKAXE.get()).addIngredient(Registration.PLANT_FIBER_CORDAGE.get()).addIngredient(Registration.STONE_PICKAXE_HEAD.get()).addIngredient(Items.STICK).addCriterion("stick", InventoryChangeTrigger.Instance.forItems(Items.STICK)).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.STONE_CHISEL.get()).addIngredient(Registration.PLANT_FIBER_CORDAGE.get()).addIngredient(Registration.STONE_CHISEL_HEAD.get()).addIngredient(Items.STICK).addCriterion("stick", InventoryChangeTrigger.Instance.forItems(Items.STICK)).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.STONE_HAMMER.get()).addIngredient(Registration.PLANT_FIBER_CORDAGE.get()).addIngredient(Registration.STONE_HAMMER_HEAD.get()).addIngredient(Items.STICK).addCriterion("stick", InventoryChangeTrigger.Instance.forItems(Items.STICK)).build(consumer);
				
				//Copper
				ShapelessRecipeBuilder.shapelessRecipe(Registration.COPPER_AXE.get()).addIngredient(Registration.COPPER_AXE_HEAD.get()).addIngredient(Items.STICK).addCriterion("copper_obtained", InventoryChangeTrigger.Instance.forItems(Registration.COPPER_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.COPPER_PICKAXE.get()).addIngredient(Registration.COPPER_PICKAXE_HEAD.get()).addIngredient(Items.STICK).addCriterion("copper_obtained", InventoryChangeTrigger.Instance.forItems(Registration.COPPER_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.COPPER_SHOVEL.get()).addIngredient(Registration.COPPER_SHOVEL_HEAD.get()).addIngredient(Items.STICK).addCriterion("copper_obtained", InventoryChangeTrigger.Instance.forItems(Registration.COPPER_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.COPPER_SWORD.get()).addIngredient(Registration.COPPER_SWORD_HEAD.get()).addIngredient(Items.STICK).addCriterion("copper_obtained", InventoryChangeTrigger.Instance.forItems(Registration.COPPER_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.COPPER_PROPICK.get()).addIngredient(Registration.COPPER_PROPICK_HEAD.get()).addIngredient(Items.STICK).addCriterion("copper_obtained", InventoryChangeTrigger.Instance.forItems(Registration.COPPER_INGOT.get())).build(consumer);
				
				//Bronze
				ShapelessRecipeBuilder.shapelessRecipe(Registration.BRONZE_AXE.get()).addIngredient(Registration.BRONZE_AXE_HEAD.get()).addIngredient(Items.STICK).addCriterion("bronze_obtained", InventoryChangeTrigger.Instance.forItems(Registration.BRONZE_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.BRONZE_PICKAXE.get()).addIngredient(Registration.BRONZE_PICKAXE_HEAD.get()).addIngredient(Items.STICK).addCriterion("bronze_obtained", InventoryChangeTrigger.Instance.forItems(Registration.BRONZE_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.BRONZE_SHOVEL.get()).addIngredient(Registration.BRONZE_SHOVEL_HEAD.get()).addIngredient(Items.STICK).addCriterion("bronze_obtained", InventoryChangeTrigger.Instance.forItems(Registration.BRONZE_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.BRONZE_SWORD.get()).addIngredient(Registration.BRONZE_SWORD_HEAD.get()).addIngredient(Items.STICK).addCriterion("bronze_obtained", InventoryChangeTrigger.Instance.forItems(Registration.BRONZE_INGOT.get())).build(consumer);
				ShapelessRecipeBuilder.shapelessRecipe(Registration.BRONZE_PROPICK.get()).addIngredient(Registration.BRONZE_PROPICK_HEAD.get()).addIngredient(Items.STICK).addCriterion("bronze_obtained", InventoryChangeTrigger.Instance.forItems(Registration.BRONZE_INGOT.get())).build(consumer);
			
			//Misc
			ShapelessRecipeBuilder.shapelessRecipe(Registration.PRIMITIVE_BRICK_MOLD.get()).addIngredient(Registration.STONE_HAMMER.get()).addIngredient(Registration.STONE_CHISEL.get()).addIngredient(ItemTags.PLANKS).addCriterion("planks", InventoryChangeTrigger.Instance.forItems(Registration.STONE_CHISEL.get())).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(Registration.KILN_CLAY_MIXTURE.get()).addIngredient(Registration.STONE_MORTAR_AND_PESTLE.get()).addIngredient(Items.SAND).addIngredient(Items.FLINT).addCriterion("flint", InventoryChangeTrigger.Instance.forItems(Items.FLINT)).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(Registration.KILN_CLAY_BALL.get(), 8).addIngredient(Registration.KILN_CLAY_MIXTURE.get()).addIngredient(Items.CLAY_BALL, 8).addCriterion("kiln_clay_mixture_obtained", InventoryChangeTrigger.Instance.forItems(Registration.KILN_CLAY_MIXTURE.get())).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(Registration.FIREBRICKS_ITEM.get()).addIngredient(Registration.FIREBRICK.get(), 4).addCriterion("fireclay_obtained", InventoryChangeTrigger.Instance.forItems(Registration.KAOLINITE_CLAY_BALL.get())).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(Registration.PLANT_FIBER_CORDAGE.get()).addIngredient(Registration.PLANT_FIBER.get(), 4).addCriterion("plant_fiber", InventoryChangeTrigger.Instance.forItems(Registration.PLANT_FIBER.get())).build(consumer);
			
		//Shaped Recipes
		
		ShapedRecipeBuilder.shapedRecipe(Registration.KILN.get()).patternLine("###").patternLine("# #").patternLine("###").key('#', Registration.KILN_BRICK.get()).addCriterion("kiln_brick_obtained", InventoryChangeTrigger.Instance.forItems(Registration.KILN_BRICK.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(Registration.WEAK_FURNACE.get()).patternLine("BBB").patternLine("BFB").patternLine("BBB").key('B', Registration.FIREBRICK.get()).key('F', Items.FURNACE).addCriterion("furnace_obtained", InventoryChangeTrigger.Instance.forItems(Items.FURNACE)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(Registration.FIREBOX.get()).patternLine("PPP").patternLine("B B").patternLine("BBB").key('P', ModTags.Items.COPPER_PLATE).key('B', Registration.FIREBRICKS_ITEM.get()).addCriterion("firebrick_obtained", InventoryChangeTrigger.Instance.forItems(Registration.FIREBRICK.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(Registration.CRUCIBLE.get()).patternLine("# #").patternLine("# #").patternLine("###").key('#', Registration.FIREBRICK.get()).addCriterion("firebrick_obtained", InventoryChangeTrigger.Instance.forItems(Registration.FIREBRICK.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(Registration.STONE_ANVIL.get()).patternLine("###").patternLine(" # ").patternLine("SSS").key('#', Items.STONE).key('S', Items.SMOOTH_STONE).addCriterion("stone_obtained", InventoryChangeTrigger.Instance.forItems(Items.STONE)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(Registration.BLOOMERY_CONTROLLER.get()).patternLine("###").patternLine("# #").patternLine("###").key('#', ModTags.Items.BRONZE_PLATE).addCriterion("bronze_obtained", InventoryChangeTrigger.Instance.forItems(Registration.BRONZE_INGOT.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(Registration.BOILER.get()).patternLine("###").patternLine("# #").patternLine("###").key('#', ModTags.Items.IRON_PLATE).addCriterion("iron_obtained", InventoryChangeTrigger.Instance.forItems(Items.IRON_INGOT)).build(consumer);
		
		//Shapeless Recipes With a Catalyst
		
		NonConsumingShapelessRecipeBuilder.nonConsumingShapelessRecipe(Registration.UNFIRED_KILN_BRICK.get()).addIngredient(Ingredient.fromItems(Registration.KILN_CLAY_BALL.get())).addIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).addNonConsumedIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).build(consumer);
		NonConsumingShapelessRecipeBuilder.nonConsumingShapelessRecipe(Registration.UNFIRED_CLAY_BRICK.get()).addIngredient(Ingredient.fromItems(Items.CLAY_BALL)).addIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).addNonConsumedIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).build(consumer);
		NonConsumingShapelessRecipeBuilder.nonConsumingShapelessRecipe(Registration.UNFIRED_KAOLINITE_BRICK.get()).addIngredient(Ingredient.fromItems(Registration.KAOLINITE_CLAY_BALL.get())).addIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).addNonConsumedIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).build(consumer);
		
		//Vanilla Furnace Smelting Recipes
		
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Registration.UNFIRED_KILN_BRICK.get()), Registration.KILN_BRICK.get(), 0.3f, 200).addCriterion("kiln_clay_ball", InventoryChangeTrigger.Instance.forItems(Registration.KILN_CLAY_BALL.get())).build(consumer);
		
		//Mod Furnace Smelting Recipes
		
		WeakSmeltingRecipeBuilder.weakSmeltingRecipe(Ingredient.fromItems(Registration.COPPER_CLUSTER.get()), Registration.COPPER_CHUNK.get(), 0.3f).build(consumer, "weak_smelting/", true);
		
		//Kiln Recipes
		
		KilnRecipeBuilder.kilnRecipe(Ingredient.fromItems(Registration.UNFIRED_CERAMIC_AXE_MOLD.get()), Registration.FIRED_CERAMIC_AXE_MOLD.get(), 1000).build(consumer, "kiln/", true);
		KilnRecipeBuilder.kilnRecipe(Ingredient.fromItems(Registration.UNFIRED_CERAMIC_INGOT_MOLD.get()), Registration.FIRED_CERAMIC_INGOT_MOLD.get(), 1000).build(consumer, "kiln/", true);
		KilnRecipeBuilder.kilnRecipe(Ingredient.fromItems(Registration.UNFIRED_CERAMIC_PICKAXE_MOLD.get()), Registration.FIRED_CERAMIC_PICKAXE_MOLD.get(), 1000).build(consumer, "kiln/", true);
		KilnRecipeBuilder.kilnRecipe(Ingredient.fromItems(Registration.UNFIRED_CERAMIC_SHOVEL_MOLD.get()), Registration.FIRED_CERAMIC_SHOVEL_MOLD.get(), 1000).build(consumer, "kiln/", true);
		KilnRecipeBuilder.kilnRecipe(Ingredient.fromItems(Registration.UNFIRED_CERAMIC_SWORD_MOLD.get()), Registration.FIRED_CERAMIC_SWORD_MOLD.get(), 1000).build(consumer, "kiln/", true);
		KilnRecipeBuilder.kilnRecipe(Ingredient.fromItems(Registration.UNFIRED_CERAMIC_PROPICK_MOLD.get()), Registration.FIRED_CERAMIC_PROPICK_MOLD.get(), 1000).build(consumer, "kiln/", true);
		KilnRecipeBuilder.kilnRecipe(Ingredient.fromItems(Registration.UNFIRED_KAOLINITE_BRICK.get()), Registration.FIREBRICK.get(), 1000).build(consumer, "kiln/", true);
		
		//Knapping Recipes
		
		HandworkRecipeBuilder.knappingRecipe(Registration.STONE_AXE_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine(" ##  ").patternLine("#### ").patternLine("#####").patternLine("#### ").patternLine(" ##  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.knappingRecipe(Registration.STONE_SHOVEL_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine("  #  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.knappingRecipe(Registration.STONE_CHISEL_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine("   # ").patternLine("  ###").patternLine(" ### ").patternLine(" ##  ").patternLine("#    ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.knappingRecipe(Registration.STONE_PICKAXE_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine("     ").patternLine(" ### ").patternLine("#   #").patternLine("     ").patternLine("     ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.knappingRecipe(Registration.STONE_HAMMER_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine("     ").patternLine("#####").patternLine("#####").patternLine("#####").patternLine("  #  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.knappingRecipe(Registration.STONE_MORTAR_AND_PESTLE.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine("  #  ").patternLine("  #  ").patternLine("# # #").patternLine("#   #").patternLine(" ### ").build(consumer, "handworking/", true);
		
		HandworkRecipeBuilder.moldingRecipe(Registration.UNFIRED_CERAMIC_AXE_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine(" ##  ").patternLine("#### ").patternLine("#####").patternLine("#### ").patternLine(" ##  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.moldingRecipe(Registration.UNFIRED_CERAMIC_PICKAXE_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine("     ").patternLine(" ### ").patternLine("#   #").patternLine("     ").patternLine("     ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.moldingRecipe(Registration.UNFIRED_CERAMIC_SHOVEL_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine("  #  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.moldingRecipe(Registration.UNFIRED_CERAMIC_PROPICK_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine("     ").patternLine(" ####").patternLine("#   #").patternLine("    #").patternLine("     ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.moldingRecipe(Registration.UNFIRED_CERAMIC_INGOT_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine("     ").patternLine(" ##  ").patternLine(" ##  ").patternLine(" ##  ").patternLine("     ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.moldingRecipe(Registration.UNFIRED_CERAMIC_SWORD_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine("   ##").patternLine("  ###").patternLine(" ### ").patternLine(" ##  ").patternLine("#    ").build(consumer, "handworking/", true);
		
		//Anvil Recipes
		
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromItems(Registration.COPPER_CHUNK.get()), Ingredient.fromItems(Registration.COPPER_CHUNK.get()), Registration.CRUDE_COPPER_INGOT.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromItems(Registration.CRUDE_COPPER_INGOT.get()), Ingredient.fromItems(Registration.CRUDE_COPPER_INGOT.get()), Registration.COPPER_PLATE.get()).build(consumer, "realistech:anvil/plates/copper_plate_from_crude_copper", false);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(ModTags.Items.COPPER_INGOT), Ingredient.fromTag(ModTags.Items.COPPER_INGOT), Registration.COPPER_PLATE.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(ModTags.Items.TIN_INGOT), Ingredient.fromTag(ModTags.Items.TIN_INGOT), Registration.TIN_PLATE.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(ModTags.Items.NICKEL_INGOT), Ingredient.fromTag(ModTags.Items.NICKEL_INGOT), Registration.NICKEL_PLATE.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(ModTags.Items.SILVER_INGOT), Ingredient.fromTag(ModTags.Items.SILVER_INGOT), Registration.SILVER_PLATE.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(ModTags.Items.LEAD_INGOT), Ingredient.fromTag(ModTags.Items.LEAD_INGOT), Registration.LEAD_PLATE.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(ModTags.Items.COBALT_INGOT), Ingredient.fromTag(ModTags.Items.COBALT_INGOT), Registration.COBALT_PLATE.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(ModTags.Items.ZINC_INGOT), Ingredient.fromTag(ModTags.Items.ZINC_INGOT), Registration.ZINC_PLATE.get()).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromItems(Registration.IRON_BLOOM.get()), Ingredient.EMPTY, Items.IRON_INGOT).build(consumer, "anvil/", true);
		AnvilRecipeBuilder.anvilRecipe(Ingredient.fromTag(Tags.Items.INGOTS_IRON), Ingredient.fromTag(Tags.Items.INGOTS_IRON), Registration.WROUGHT_IRON_PLATE.get()).build(consumer, "anvil/", true);
	}
}
