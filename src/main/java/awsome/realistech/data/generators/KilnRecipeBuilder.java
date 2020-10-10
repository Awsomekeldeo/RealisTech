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

public class KilnRecipeBuilder {
	
	private final Item output;
	private final Ingredient ingredient;
	private final int firingTemp;
	
	public KilnRecipeBuilder(IItemProvider resultIn, Ingredient ingredientIn, int firingTempIn) {
		this.ingredient = ingredientIn;
		this.output = resultIn.asItem();
		this.firingTemp = firingTempIn;
	}
	
	public static KilnRecipeBuilder kilnRecipe(Ingredient input, IItemProvider output, int firingTemp) {
		return new KilnRecipeBuilder(output, input, firingTemp);
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.output));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save, boolean isRootFolder) {
		ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(this.output);
		if (!isRootFolder) {
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Kiln Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(save));
			}
		}else{
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Kiln Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(Reference.MODID, save + ForgeRegistries.ITEMS.getKey(this.output).getPath()));
			}
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new KilnRecipeBuilder.Result(id, this.ingredient, this.output, this.firingTemp));
	}
	
	public static class Result implements IFinishedRecipe {
		
		private final ResourceLocation id;
		private final Item output;
		private final Ingredient input;
		private final int firingTemp;
		
		public Result(ResourceLocation idIn, Ingredient inputIn, Item outputIn, int firingTempIn) {
			this.id = idIn;
			this.input = inputIn;
			this.output = outputIn;
			this.firingTemp = firingTempIn;
		}

		@Override
		public void serialize(JsonObject json) {
			json.add("input", this.input.serialize());
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
			
			json.add("output", jsonObject);
			
			json.addProperty("firing_temperature", this.firingTemp);
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return Registration.KILN_RECIPE_SERIALIZER.get();
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
