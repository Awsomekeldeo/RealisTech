package awsome.realistech.api.recipe;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class AnvilRecipe implements IRecipe<RecipeWrapper> {
	
	public static final IRecipeType<AnvilRecipe> ANVIL_RECIPE = IRecipeType.register(new ResourceLocation(Reference.MODID, "anvil").toString());

	private final IRecipeType<?> type;
	protected final Ingredient ingredient1;
	protected final Ingredient ingredient2;
	protected final ItemStack result;
	protected final ResourceLocation id;
	
	public AnvilRecipe(ResourceLocation id, Ingredient ingredient1, Ingredient ingredient2, ItemStack output) {
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.result = output;
		this.id = id;
		this.type = ANVIL_RECIPE;
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		return (ingredient1.test(inv.getStackInSlot(0)) && ingredient2.test(inv.getStackInSlot(1))) 
				|| (ingredient1.test(inv.getStackInSlot(1)) && ingredient2.test(inv.getStackInSlot(0)));
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return this.result.copy();
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.result;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return Registration.ANVIL_RECIPE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return this.type;
	}

}
