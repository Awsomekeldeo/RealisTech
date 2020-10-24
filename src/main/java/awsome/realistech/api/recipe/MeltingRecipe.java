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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MeltingRecipe implements IRecipe<RecipeWrapper> {
	
	public static final IRecipeType<MeltingRecipe> MELTING_RECIPE = IRecipeType.register(new ResourceLocation(Reference.MODID, "melting").toString());
	
	private final IRecipeType<?> type;
	protected final Ingredient input;
	protected final FluidStack output;
	protected final float meltTemp;
	protected final ResourceLocation id;
	
	public MeltingRecipe(ResourceLocation id, Ingredient input, FluidStack output, float meltTemp) {
		this.id = id;
		this.input = input;
		this.output = output;
		this.meltTemp = meltTemp;
		type = MELTING_RECIPE;
	}
	
	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		return this.input.test(inv.getStackInSlot(0));
	}
	
	public float getMeltTemp() {
		return this.meltTemp;
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return new ItemStack(this.output.getFluid().getFilledBucket()).copy();
	}
	
	public FluidStack getResult() {
		return this.output;
	}

	@Override
	public boolean canFit(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(this.output.getFluid().getFilledBucket());
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return Registration.MELTING_RECIPE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return type;
	}
}
