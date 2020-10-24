package awsome.realistech.api.recipe;

import java.util.ArrayList;

import awsome.realistech.Reference;
import awsome.realistech.api.capability.impl.CrucibleTankHandler;
import awsome.realistech.registry.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class AlloyRecipe implements IRecipe<RecipeWrapper> {
	
	public static final IRecipeType<MeltingRecipe> ALLOY_RECIPE = IRecipeType.register(new ResourceLocation(Reference.MODID, "alloying").toString());
	
	private final IRecipeType<?> type;
	protected final NonNullList<FluidStack> inputs;
	protected final FluidStack output;
	protected final ResourceLocation id;
	
	public AlloyRecipe(NonNullList<FluidStack> inputs, FluidStack output, ResourceLocation id) {
		this.inputs = inputs;
		this.output = output;
		this.id = id;
		type = ALLOY_RECIPE;
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		return true;
	}
	
	public boolean isValid(CrucibleTankHandler tanksIn) {
		
		ArrayList<Integer> inputTanks = new ArrayList<>();
		
		for (int i = 0; i < tanksIn.getTanks(); i++) {
			for (int j = 0; j < this.inputs.size(); j++) {
				if (tanksIn.getFluidInTank(i).containsFluid(this.inputs.get(j))) {
					inputTanks.add(i);
				}
			}
		}
		
		if (inputTanks.size() > 1) {
			return true;
		}
		
		return false;
	}
	
	public NonNullList<FluidStack> getInputs() {
		return this.inputs;
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
		return Registration.ALLOY_RECIPE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return this.type;
	}

}
