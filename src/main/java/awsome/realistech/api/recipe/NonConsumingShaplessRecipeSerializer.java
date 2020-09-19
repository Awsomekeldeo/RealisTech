package awsome.realistech.api.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class NonConsumingShaplessRecipeSerializer<T extends NonConsumingShapelessRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<NonConsumingShapelessRecipe> {
	
	private final NonConsumingShaplessRecipeSerializer.IFactory<T> factory;
	
	public NonConsumingShaplessRecipeSerializer(NonConsumingShaplessRecipeSerializer.IFactory<T> factory) {
		this.factory = factory;
	}

	@Override
	public NonConsumingShapelessRecipe read(ResourceLocation recipeId, JsonObject json) {
		final NonNullList<Ingredient> ingredients = readIngredients(JSONUtils.getJsonArray(json, "inputs"));
		final NonNullList<Ingredient> nonConsumedIngredients = readIngredients(JSONUtils.getJsonArray(json, "non_consumed_inputs"));
		final ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), false);
		
		if (ingredients.isEmpty() || nonConsumedIngredients.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
         } else if ((ingredients.size() + nonConsumedIngredients.size()) > 3 * 3) {
            throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (3 * 3));
         } else {
            return this.factory.create(recipeId, ingredients, nonConsumedIngredients, output);
         }
	}
	
	private static NonNullList<Ingredient> readIngredients(JsonArray json) {
		NonNullList<Ingredient> ingredientList = NonNullList.create();
		
		for (int i = 0; i < json.size(); i++) {
			Ingredient ingredient = Ingredient.deserialize(json.get(i));
			if (!ingredient.hasNoMatchingItems()) {
				ingredientList.add(ingredient);
			}
		}
		
		return ingredientList;
	}

	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		int i = buffer.readVarInt();
		NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);
		
		for (int j = 0; j < ingredients.size(); j++) {
			ingredients.set(j, Ingredient.read(buffer));
		}
		
		int k = buffer.readVarInt();
		NonNullList<Ingredient> nonConsumedIngredients = NonNullList.withSize(k, Ingredient.EMPTY);
		
		for (int l = 0; l < nonConsumedIngredients.size(); l++) {
			nonConsumedIngredients.set(l, Ingredient.read(buffer));
		}
		
		ItemStack stack = buffer.readItemStack();
		
		return this.factory.create(recipeId, ingredients, nonConsumedIngredients, stack);
	}

	@Override
	public void write(PacketBuffer buffer, NonConsumingShapelessRecipe recipe) {
		buffer.writeVarInt(recipe.inputs.size());
		for(Ingredient ingredient : recipe.inputs) {
            ingredient.write(buffer);
        }
		buffer.writeVarInt(recipe.nonConsumedInputs.size());
		for(Ingredient ingredient : recipe.nonConsumedInputs) {
            ingredient.write(buffer);
        }
		buffer.writeItemStack(recipe.output);
	}
	
	public interface IFactory<T extends NonConsumingShapelessRecipe> {
		T create(ResourceLocation resourceLocation, NonNullList<Ingredient> ingredients, NonNullList<Ingredient> nonConsumedIngredients, ItemStack output);
	}
	
	public NonConsumingShaplessRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}
}
