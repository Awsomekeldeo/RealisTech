package awsome.realistech.api.recipe;

import com.google.gson.JsonObject;

import awsome.realistech.util.MoldType;
import awsome.realistech.util.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SolidificationRecipeSerializer<T extends SolidificationRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SolidificationRecipe> {
	
	private final SolidificationRecipeSerializer.IFactory<T> factory;
	
	public SolidificationRecipeSerializer(SolidificationRecipeSerializer.IFactory<T> factoryIn) {
		this.factory = factoryIn;
	}
	
	@Override
	public SolidificationRecipe read(ResourceLocation recipeId, JsonObject json) {
		final JsonObject inputElement = JSONUtils.getJsonObject(json, "input");
		final FluidStack input = RecipeHelper.deserializeFluid(inputElement);
		
		final JsonObject outputElement = JSONUtils.getJsonObject(json, "output");
		final ItemStack output = CraftingHelper.getItemStack(outputElement, false);
		
		final MoldType moldType = RecipeHelper.getMoldType(json);
		
		return this.factory.create(recipeId, input, output, moldType);
	}

	@Override
	public SolidificationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		final FluidStack input = buffer.readFluidStack();
		final ItemStack output = buffer.readItemStack();
		final ResourceLocation id = buffer.readResourceLocation();
		final MoldType moldType = MoldType.getFromString(buffer.readString());
		
		if (moldType == null) {
			throw new IllegalStateException("Packet buffer for solidification recipe: " + id + " contained an invalid mold type!");
		}else{
			return this.factory.create(id, input, output, moldType);
		}
	}

	@Override
	public void write(PacketBuffer buffer, SolidificationRecipe recipe) {
		buffer.writeFluidStack(recipe.getInput());
		buffer.writeItemStack(recipe.getOutput());
		buffer.writeResourceLocation(recipe.getId());
		buffer.writeString(recipe.getMoldType().getTypeForString());
	}
	
	public interface IFactory<T extends SolidificationRecipe> {
		T create(ResourceLocation id, FluidStack input, ItemStack output, MoldType moldType);
	}
	
	public SolidificationRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}

}
