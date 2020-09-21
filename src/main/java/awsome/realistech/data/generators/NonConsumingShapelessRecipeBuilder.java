package awsome.realistech.data.generators;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import awsome.realistech.registry.Registration;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class NonConsumingShapelessRecipeBuilder {
	
	private final Item result;
	private final int count;
	private final List<Ingredient> inputs = Lists.newArrayList();
	private final List<Ingredient> nonConsumedInputs = Lists.newArrayList();
	
	public NonConsumingShapelessRecipeBuilder(IItemProvider resultIn, int countIn) {
		this.result = resultIn.asItem();
		this.count = countIn;
	}
	
	public static NonConsumingShapelessRecipeBuilder nonConsumingShapelessRecipe(IItemProvider resultIn) {
		return new NonConsumingShapelessRecipeBuilder(resultIn, 1);
	}
	
	public static NonConsumingShapelessRecipeBuilder nonConsumingShapelessRecipe(IItemProvider resultIn, int countIn) {
		return new NonConsumingShapelessRecipeBuilder(resultIn, 1);
	}
	
	public NonConsumingShapelessRecipeBuilder addIngredient(Tag<Item> tagIn) {
		return this.addIngredient(Ingredient.fromTag(tagIn));
	}
	
	public NonConsumingShapelessRecipeBuilder addIngredient(IItemProvider itemIn) {
		return this.addIngredient(itemIn, 1);
	}
	
	public NonConsumingShapelessRecipeBuilder addIngredient(IItemProvider itemIn, int qtyIn) {
		for(int i = 0; i < qtyIn; ++i) {
			this.addIngredient(Ingredient.fromItems(itemIn));
		}
		
		return this;
	}
	
	public NonConsumingShapelessRecipeBuilder addIngredient(Ingredient ingredientIn) {
		return this.addIngredient(ingredientIn, 1);
	}
	
	public NonConsumingShapelessRecipeBuilder addIngredient(Ingredient ingredientIn, int qtyIn) {
		for(int i = 0; i < qtyIn; ++i) {
			this.inputs.add(ingredientIn);
		}
		
		return this;
	}
	
	public NonConsumingShapelessRecipeBuilder addNonConsumedIngredient(Tag<Item> tagIn) {
		return this.addNonConsumedIngredient(Ingredient.fromTag(tagIn));
	}
	
	public NonConsumingShapelessRecipeBuilder addNonConsumedIngredient(IItemProvider itemIn) {
		return this.addNonConsumedIngredient(itemIn, 1);
	}
	
	public NonConsumingShapelessRecipeBuilder addNonConsumedIngredient(IItemProvider itemIn, int qtyIn) {
		for(int i = 0; i < qtyIn; ++i) {
			this.addNonConsumedIngredient(Ingredient.fromItems(itemIn));
		}
		
		return this;
	}
	
	public NonConsumingShapelessRecipeBuilder addNonConsumedIngredient(Ingredient ingredientIn) {
		return this.addNonConsumedIngredient(ingredientIn, 1);
	}
	
	public NonConsumingShapelessRecipeBuilder addNonConsumedIngredient(Ingredient ingredientIn, int qtyIn) {
		for(int i = 0; i < qtyIn; ++i) {
			this.nonConsumedInputs.add(ingredientIn);
		}
		
		return this;
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.result));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
		ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
		if ((new ResourceLocation(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Non-Consuming Shapeless Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(consumerIn, new ResourceLocation(save));
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new NonConsumingShapelessRecipeBuilder.Result(id, this.result, this.count, this.inputs, this.nonConsumedInputs));
	}
	
	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final Item output;
		private final int count;
		private final List<Ingredient> inputs;
		private final List<Ingredient> nonConsumedInputs;
		
		public Result(ResourceLocation idIn, Item output, int countIn, List<Ingredient> ingredientsIn, List<Ingredient> nonConsumedIngredientsIn) {
			this.id = idIn;
			this.count = countIn;
			this.inputs = ingredientsIn;
			this.nonConsumedInputs = nonConsumedIngredientsIn;
			this.output = output;
		}

		@Override
		public void serialize(JsonObject json) {
			//Inputs
			JsonArray jsonArray = new JsonArray();
			
			for (Ingredient ingredient : this.inputs) {
				jsonArray.add(ingredient.serialize());
			}
			
			json.add("inputs", jsonArray);
			
			//Non-consumed inputs
			JsonArray jsonArray2 = new JsonArray();
			for (Ingredient ingredient : this.nonConsumedInputs) {
				jsonArray2.add(ingredient.serialize());
			}
			
			json.add("non_consumed_inputs", jsonArray2);
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
			if (this.count > 1) {
				jsonObject.addProperty("count", this.count);
			}
			
			json.add("output", jsonObject);
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return Registration.NON_CONSUMING_SHAPELESS_RECIPE_SERIALIZER.get();
		}

		@Override
		public JsonObject getAdvancementJson() {
			return null;
		}

		@Override
		public ResourceLocation getAdvancementID() {
			return null;
		}
	}
}
