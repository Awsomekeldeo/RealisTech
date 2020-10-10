package awsome.realistech.api.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class WeakSmeltingRecipeSerializer<T extends WeakSmeltingRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<WeakSmeltingRecipe> {
	
	private final WeakSmeltingRecipeSerializer.IFactory<T> factory;
	
	public WeakSmeltingRecipeSerializer(WeakSmeltingRecipeSerializer.IFactory<T> factory) {
		this.factory = factory;
	}
	
	@Override
	public WeakSmeltingRecipe read(ResourceLocation recipeId, JsonObject json) {
		JsonElement inputElement = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
		Ingredient ingredient = Ingredient.deserialize(inputElement);
		
		ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), false);
		float exp = JSONUtils.getFloat(json, "experience");
		
		return this.factory.create(recipeId, ingredient, result, exp);
	}

	@Override
	public WeakSmeltingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		final Ingredient input = Ingredient.read(buffer);
		final ItemStack output = buffer.readItemStack();
		final ResourceLocation id = buffer.readResourceLocation();
		final float experience = buffer.readFloat();
		
		return this.factory.create(id, input, output, experience);
	}

	@Override
	public void write(PacketBuffer buffer, WeakSmeltingRecipe recipe) {
		recipe.input.write(buffer);
		buffer.writeItemStack(recipe.output);
		buffer.writeResourceLocation(recipe.id);
		buffer.writeFloat(recipe.xp);
	}
	
	public interface IFactory<T extends WeakSmeltingRecipe> {
		T create(ResourceLocation resourceLocation, Ingredient ingredient, ItemStack output, float experience);
	}
	
	public WeakSmeltingRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}

}
