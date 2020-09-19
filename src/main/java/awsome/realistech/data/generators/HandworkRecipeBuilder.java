package awsome.realistech.data.generators;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class HandworkRecipeBuilder {
	
	private final Item output;
	private final List<String> pattern = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final boolean invertedPattern;
	
	public HandworkRecipeBuilder(IItemProvider output, boolean isInvertedPattern) {
		this.output = output.asItem();
		this.invertedPattern = isInvertedPattern;
	}
	
	public static HandworkRecipeBuilder handworkRecipe(IItemProvider output) {
		return handworkRecipe(output, false);
	}
	
	public static HandworkRecipeBuilder handworkRecipe(IItemProvider output, boolean isInvertedPattern) {
		return new HandworkRecipeBuilder(output, isInvertedPattern);
	}
	
	public HandworkRecipeBuilder recipeItem(Ingredient ingredientIn) {
		this.key.put('#', ingredientIn);
		return this;
	}
	
	public HandworkRecipeBuilder patternLine(String patternIn) {
		if (!this.pattern.isEmpty() && patternIn.length() != this.pattern.get(0).length()) {
			throw new IllegalArgumentException("Pattern must be the same width for every line!");
		}else{
			this.pattern.add(patternIn);
			return this;
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.output));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save, boolean isRootFolder) {
		ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(this.output);
		if (!isRootFolder) {
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Handworking Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(save));
			}
		}else{
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Handworking Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(Reference.MODID, save + ForgeRegistries.ITEMS.getKey(this.output).getPath()));
			}
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		this.validate(id);
		consumerIn.accept(new HandworkRecipeBuilder.HandworkResult(id, this.output, this.pattern, this.key.get('#'), this.invertedPattern));
	}
	
	private void validate(ResourceLocation id) {
		if (this.pattern.isEmpty()) {
			throw new IllegalStateException("No pattern is defined for handworking recipe " + id + "!");
		} else {
			Set<Character> set = Sets.newHashSet(this.key.keySet());
			set.remove(' ');

			for(String s : this.pattern) {
				for(int i = 0; i < s.length(); ++i) {
					char c0 = s.charAt(i);
					if (!this.key.containsKey(c0) && c0 != ' ') {
						throw new IllegalStateException("Pattern in handworking recipe " + id + " uses symbol '" + c0 + "', only '#' and ' ' are allowed!");
					}

					set.remove(c0);
				}
			}
		}
	}
	
	public class HandworkResult implements IFinishedRecipe {
		
		private final ResourceLocation id;
		private final Item output;
		private final Ingredient recipeItem;
		private final List<String> pattern;
		private final boolean invertedPattern;
		
		public HandworkResult(ResourceLocation idIn, Item resultIn, List<String> patternIn, Ingredient recipeItemIn, boolean isInverted) {
			this.id = idIn;
			this.recipeItem = recipeItemIn;
			this.output = resultIn;
			this.pattern = patternIn;
			this.invertedPattern = isInverted;
		}
		
		@Override
		public void serialize(JsonObject json) {
			json.add("recipe_item", this.recipeItem.serialize());
			json.addProperty("inverted_pattern", this.invertedPattern);
			JsonArray jsonArray = new JsonArray();
			
			for (String s : this.pattern) {
				jsonArray.add(s);
			}
			
			json.add("pattern", jsonArray);
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
			json.add("result", jsonObject);
		}
		
		@Override
		public ResourceLocation getAdvancementID() {
			return null;
		}
		
		@Override
		public JsonObject getAdvancementJson() {
			return null;
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return Registration.HANDWORK_RECIPE_SERIALIZER.get();
		}
	}
}
