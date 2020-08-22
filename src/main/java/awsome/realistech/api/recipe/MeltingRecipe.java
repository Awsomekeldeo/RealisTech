package awsome.realistech.api.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import awsome.realistech.registry.Registration;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;

public class MeltingRecipe implements IRecipe<RecipeWrapper> {
	
	public static final IRecipeType<MeltingRecipe> melting_recipe = IRecipeType.register("techmod:melting");
	
	private final IRecipeType<?> type;
	protected final Ingredient input;
	protected final FluidStack output;
	protected final int meltTemp;
	protected final ResourceLocation id;
	
	public MeltingRecipe(ResourceLocation id, Ingredient input, FluidStack output, int meltTemp) {
		this.id = id;
		this.input = input;
		this.output = output;
		this.meltTemp = meltTemp;
		type = melting_recipe;
	}
	
	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		return this.input.test(inv.getStackInSlot(0));
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return null;
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
		return null;
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
	
	public static FluidStack deserializeFluid(JsonObject object) {
		String s = JSONUtils.getString(object, "fluid");
		Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(s));
		if (fluid == null) {
			throw new JsonSyntaxException("Unknown fluid '" + s + "'");
		}
		int i = JSONUtils.getInt(object, "amount");
		return new FluidStack(fluid, i);
	}
}
