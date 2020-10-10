package awsome.realistech.data.generators;

import java.util.function.Consumer;

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

public class WeakSmeltingRecipeBuilder {
	
	private final Ingredient ingredient;
	private final Item result;
	private final float experience;

	public WeakSmeltingRecipeBuilder(IItemProvider resultIn, Ingredient ingredientIn, float experienceIn) {
		this.ingredient = ingredientIn;
		this.result = resultIn.asItem();
		this.experience = experienceIn;
	}
	
	public static WeakSmeltingRecipeBuilder weakSmeltingRecipe(Ingredient input, IItemProvider result, float experience) {
		return new WeakSmeltingRecipeBuilder(result, input, experience);
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.result));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save, boolean isRootFolder) {
		ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(this.result);
		if (!isRootFolder) {
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Weak Smelting Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(save));
			}
		}else{
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Weak Smelting Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(Reference.MODID, save + ForgeRegistries.ITEMS.getKey(this.result).getPath()));
			}
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new WeakSmeltingRecipeBuilder.Result(id, this.ingredient, this.result, this.experience));
	}
	
	
	public static class Result implements IFinishedRecipe {
		
		private final ResourceLocation id;
		private final Ingredient ingredient;
		private final Item result;
		private final float experience;
		
		public Result(ResourceLocation idIn, Ingredient ingredientIn, Item resultIn, float experienceIn) {
			this.id = idIn;
			this.ingredient = ingredientIn;
			this.result = resultIn;
			this.experience = experienceIn;
		}

		@Override
		public void serialize(JsonObject json) {
			json.add("ingredient", this.ingredient.serialize());
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
			
			json.add("output", jsonObject);
			json.addProperty("experience", this.experience);
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return Registration.WEAK_SMELTING_RECIPE_SERIALIZER.get();
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
