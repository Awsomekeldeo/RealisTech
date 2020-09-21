package awsome.realistech.datagen;

import java.util.function.Consumer;

import awsome.realistech.data.generators.HandworkRecipeBuilder;
import awsome.realistech.data.generators.NonConsumingShapelessRecipeBuilder;
import awsome.realistech.registry.Registration;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class Recipes extends RecipeProvider {
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		
		NonConsumingShapelessRecipeBuilder.nonConsumingShapelessRecipe(Registration.UNFIRED_KILN_BRICK.get()).addIngredient(Ingredient.fromItems(Registration.KILN_CLAY_BALL.get())).addIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).addNonConsumedIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).build(consumer);
		NonConsumingShapelessRecipeBuilder.nonConsumingShapelessRecipe(Registration.UNFIRED_CLAY_BRICK.get()).addIngredient(Ingredient.fromItems(Items.CLAY_BALL)).addIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).addNonConsumedIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).build(consumer);
		NonConsumingShapelessRecipeBuilder.nonConsumingShapelessRecipe(Registration.UNFIRED_KAOLINITE_BRICK.get()).addIngredient(Ingredient.fromItems(Registration.KAOLINITE_CLAY_BALL.get())).addIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).addNonConsumedIngredient(Ingredient.fromItems(Registration.PRIMITIVE_BRICK_MOLD.get())).build(consumer);
		
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Registration.UNFIRED_KILN_BRICK.get()), Registration.KILN_BRICK.get(), 0.3f, 200).addCriterion("kiln_clay_ball", InventoryChangeTrigger.Instance.forItems(Registration.KILN_CLAY_BALL.get())).build(consumer);
		
		HandworkRecipeBuilder.handworkRecipe(Registration.STONE_AXE_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine(" ##  ").patternLine("#### ").patternLine("#####").patternLine("#### ").patternLine(" ##  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.handworkRecipe(Registration.STONE_SHOVEL_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine("  #  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.handworkRecipe(Registration.STONE_CHISEL_HEAD.get()).recipeItem(Ingredient.fromItems(Registration.ROCK_ITEM.get())).patternLine("   # ").patternLine("  ###").patternLine(" ### ").patternLine(" ##  ").patternLine("#    ").build(consumer, "handworking/", true);
		
		HandworkRecipeBuilder.handworkRecipe(Registration.UNFIRED_CERAMIC_AXE_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine(" ##  ").patternLine("#### ").patternLine("#####").patternLine("#### ").patternLine(" ##  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.handworkRecipe(Registration.UNFIRED_CERAMIC_PICKAXE_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine(" ### ").patternLine("#   #").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.handworkRecipe(Registration.UNFIRED_CERAMIC_SHOVEL_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine(" ### ").patternLine("  #  ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.handworkRecipe(Registration.UNFIRED_CERAMIC_PROPICK_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine(" ####").patternLine("#   #").patternLine("    #").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.handworkRecipe(Registration.UNFIRED_CERAMIC_INGOT_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine("    ").patternLine(" ## ").patternLine(" ## ").patternLine(" ## ").patternLine("    ").build(consumer, "handworking/", true);
		HandworkRecipeBuilder.handworkRecipe(Registration.UNFIRED_CERAMIC_SWORD_MOLD.get(), true).recipeItem(Ingredient.fromItems(Items.CLAY_BALL)).patternLine("   ##").patternLine("  ###").patternLine(" ### ").patternLine(" ##  ").patternLine("#    ").build(consumer, "handworking/", true);
	}
}
