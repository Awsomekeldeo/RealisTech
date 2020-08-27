package awsome.realistech.datagen;

import java.util.function.Consumer;

import awsome.realistech.registry.Registration;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class Recipes extends RecipeProvider {
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapelessRecipe(Registration.UNFIRED_CLAY_BRICK.get()).addIngredient(Registration.PRIMITIVE_BRICK_MOLD.get()).addIngredient(Items.CLAY_BALL);
		ShapelessRecipeBuilder.shapelessRecipe(Registration.UNFIRED_KILN_BRICK.get()).addIngredient(Registration.PRIMITIVE_BRICK_MOLD.get()).addIngredient(Registration.KILN_CLAY_BALL.get());
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Registration.UNFIRED_KILN_BRICK.get()), Registration.KILN_BRICK.get(), 0.3f, 200);
	}
	
}
