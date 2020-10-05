package awsome.realistech.api.recipe;

import java.util.Collections;
import java.util.Map;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class MoldingRecipe extends AbstractHandworkRecipe {
	
	public static final IRecipeType<MoldingRecipe> MOLDING = IRecipeType.register(new ResourceLocation(Reference.MODID, "molding").toString());
	
	public MoldingRecipe(ResourceLocation id, Ingredient input, ItemStack output,
			NonNullList<Ingredient> pattern, int recipeWidth, int recipeHeight, boolean isInvertedPattern,
			NonNullList<Ingredient> jeiPattern) {
		super(MOLDING, id, input, output, pattern, recipeWidth, recipeHeight, isInvertedPattern, jeiPattern);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return Registration.KNAPPING_RECIPE_SERIALIZER.get();
	}
	
	public static Map<ResourceLocation, MoldingRecipe> recipeList = Collections.emptyMap();
	
}
