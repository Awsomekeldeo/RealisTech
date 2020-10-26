package awsome.realistech.api.recipe;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import awsome.realistech.util.MoldType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class SolidificationRecipe implements IRecipe<RecipeWrapper> {
	
	public static final IRecipeType<SolidificationRecipe> SOLIDIFICATION_RECIPE = IRecipeType.register(new ResourceLocation(Reference.MODID, "solidification").toString());
	
	private final IRecipeType<?> type;
	private final FluidStack input;
	private final ItemStack output;
	private final ResourceLocation id;
	private final MoldType moldType;
	
	public SolidificationRecipe(ResourceLocation id, FluidStack input, ItemStack output, MoldType moldType) {
		this.input = input;
		this.output = output;
		this.id = id;
		this.moldType = moldType;
		this.type = SOLIDIFICATION_RECIPE;
	}
	
	public FluidStack getInput() {
		return this.input;
	}
	
	public ItemStack getOutput() {
		return this.output;
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		return true;
	}
	
	public boolean isValid(IFluidHandlerItem tanksIn, MoldType moldType) {
		
		if (tanksIn.getFluidInTank(0).containsFluid(this.input) && moldType == this.moldType) {
			return true;
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return this.output.copy();
	}

	@Override
	public boolean canFit(int width, int height) {
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
	public IRecipeSerializer<?> getSerializer() {
		return Registration.SOLIDIFICATION_RECIPE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return this.type;
	}

	public MoldType getMoldType() {
		return this.moldType;
	}

}
