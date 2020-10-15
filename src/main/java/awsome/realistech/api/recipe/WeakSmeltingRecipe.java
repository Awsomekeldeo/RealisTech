package awsome.realistech.api.recipe;

import java.util.Collections;
import java.util.Map;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class WeakSmeltingRecipe implements IRecipe<RecipeWrapper> {
	
	public static final IRecipeType<WeakSmeltingRecipe> WEAK_SMELTING_RECIPE = IRecipeType.register(new ResourceLocation(Reference.MODID, "kiln").toString());
	
	private final IRecipeType<?> recipeType;
	protected final Ingredient input;
	protected final ItemStack output;
	protected final float xp;
	protected final ResourceLocation id;
	
	public WeakSmeltingRecipe(ResourceLocation idIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn) {
		this.recipeType = WEAK_SMELTING_RECIPE;
		this.input = ingredientIn;
		this.output = resultIn;
		this.xp = experienceIn;
		this.id = idIn;
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		return this.input.test(inv.getStackInSlot(0));
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return this.output.copy();
	}

	@Override
	public boolean canFit(int width, int height) {
		if (width > 1 && height > 1) {
			return false;
		}
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.from(input, input);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return Registration.WEAK_SMELTING_RECIPE_SERIALIZER.get();
	}
	
	public static Map<ResourceLocation, WeakSmeltingRecipe> recipeList = Collections.emptyMap();

	@Override
	public IRecipeType<?> getType() {
		return this.recipeType;
	}

	public float getExperience() {
		return this.xp;
	}

}
