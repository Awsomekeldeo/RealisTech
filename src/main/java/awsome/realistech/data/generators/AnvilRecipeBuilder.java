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

public class AnvilRecipeBuilder {
	
	private final Item output;
	private final Ingredient input1;
	private final Ingredient input2;
	
	public AnvilRecipeBuilder(IItemProvider resultIn, Ingredient ingredient1, Ingredient ingredient2) {
		this.output = resultIn.asItem();
		this.input1 = ingredient1;
		this.input2 = ingredient2;
	}
	
	public static AnvilRecipeBuilder anvilRecipe(Ingredient ingredient1, Ingredient ingredient2, IItemProvider output) {
		return new AnvilRecipeBuilder(output, ingredient1, ingredient2);
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.output));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save, boolean isRootFolder) {
		ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(this.output);
		if (!isRootFolder) {
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Anvil Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(save));
			}
		}else{
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Anvil Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(Reference.MODID, save + ForgeRegistries.ITEMS.getKey(this.output).getPath()));
			}
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new AnvilRecipeBuilder.Result(id, this.input1, this.input2, this.output));
	}
	
	public static class Result implements IFinishedRecipe {
		
		private final ResourceLocation id;
		private final Item result;
		private final Ingredient input1;
		private final Ingredient input2;
		
		public Result(ResourceLocation id, Ingredient input1, Ingredient input2, Item output) {
			this.id = id;
			this.input1 = input1;
			this.input2 = input2;
			this.result = output;
		}

		@Override
		public void serialize(JsonObject json) {
			json.add("first_input", this.input1.serialize());
			json.add("second_input", this.input2.serialize());
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
			json.add("result", jsonObject);
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return Registration.ANVIL_RECIPE_SERIALIZER.get();
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
