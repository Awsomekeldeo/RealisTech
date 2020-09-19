package awsome.realistech.api.recipe;

import java.util.Map;
import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class HandworkRecipeSerializer<T extends HandworkRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<HandworkRecipe> {
	
	private final HandworkRecipeSerializer.IFactory<T> factory;
	
	public HandworkRecipeSerializer(HandworkRecipeSerializer.IFactory<T> factory) {
		this.factory = factory;
	}
	
	@Override
	public HandworkRecipe read(ResourceLocation recipeId, JsonObject json) {
		final JsonElement inputElement = JSONUtils.getJsonObject(json, "recipe_item");
		final Ingredient input = Ingredient.deserialize(inputElement);
		String[] pattern = shrink(patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
		int i = pattern[0].length();
        int j = pattern.length;
        Map<String, Ingredient> keys = Maps.newHashMap();
        keys.put("#", input);
        keys.put(" ", Ingredient.EMPTY);
        NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, keys, i, j);
		final ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), false);
		
		return this.factory.create(recipeId, input, output, ingredients, i, j);
	}

	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		final int i = buffer.readVarInt();
		final int j = buffer.readVarInt();
		final Ingredient input = Ingredient.read(buffer);
		final ItemStack output = buffer.readItemStack();
		final ResourceLocation id = buffer.readResourceLocation();
		
		NonNullList<Ingredient> ingredients = NonNullList.withSize(i * j, Ingredient.EMPTY);

        for(int k = 0; k < ingredients.size(); ++k) {
           ingredients.set(k, Ingredient.read(buffer));
        }
		
		return this.factory.create(id, input, output, ingredients, i, j);
	}
	
	@VisibleForTesting
	static String[] shrink(String... toShrink) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;
		
		for(int i1 = 0; i1 < toShrink.length; ++i1) {
			String s = toShrink[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			if (j1 < 0) {
				if (k == i1) {
					++k;
				}

				++l;
			} else {
				l = 0;
			}
		}

		if (toShrink.length == l) {
			return new String[0];
		} else {
			String[] astring = new String[toShrink.length - l - k];

			for(int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = toShrink[k1 + k].substring(i, j + 1);
			}

			return astring;
		}
	}

	private static int firstNonSpace(String str) {
		int i;
		for(i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
			;
		}

		return i;
	}

	private static int lastNonSpace(String str) {
		int i;
		for(i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
			;
		}

		return i;
	}
	
	
	private static String[] patternFromJson(JsonArray jsonArr) {
		String[] astring = new String[jsonArr.size()];
		if (astring.length > 5) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, " + 5 + " is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for(int i = 0; i < astring.length; ++i) {
				String s = JSONUtils.getString(jsonArr.get(i), "pattern[" + i + "]");
				if (s.length() > 5) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, " + 5 + " is maximum");
				}

				if (i > 0 && astring[0].length() != s.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}

				astring[i] = s;
			}

			return astring;
		}
	}
	
	private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight) {
		NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(keys.keySet());
		set.remove(" ");

		for(int i = 0; i < pattern.length; ++i) {
			for(int j = 0; j < pattern[i].length(); ++j) {
				String s = pattern[i].substring(j, j + 1);
				Ingredient ingredient = keys.get(s);
				if (ingredient == null) {
					throw new JsonSyntaxException("Pattern can only contain the # character and spaces.");
				}

				set.remove(s);
				nonnulllist.set(j + patternWidth * i, ingredient);
			}
		}
		
		return nonnulllist;
	}
	
	@Override
	public void write(PacketBuffer buffer, HandworkRecipe recipe) {
		buffer.writeVarInt(recipe.recipeWidth);
		buffer.writeVarInt(recipe.recipeHeight);
		recipe.recipe_item.write(buffer);
		for(Ingredient ingredient : recipe.pattern) {
			ingredient.write(buffer);
		}
		buffer.writeItemStack(recipe.result);
		buffer.writeResourceLocation(recipe.id);
	}
	
	public interface IFactory<T extends HandworkRecipe> {
		T create(ResourceLocation resourceLocation, Ingredient ingredient, ItemStack output, NonNullList<Ingredient> ingredients, int recipeWidth, int recipeHeight);
	}
	
	public HandworkRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}
}
