package awsome.realistech.data.generators;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class SolidificationRecipeBuilder {
	
	private final FluidStack input;
	private final Item output;
	private final int count;
	
	public SolidificationRecipeBuilder(FluidStack inputIn, IItemProvider outputIn, int countIn) {
		this.input = inputIn;
		this.output = outputIn.asItem();
		this.count = countIn;
	}
	
	public static SolidificationRecipeBuilder solidificationRecipe(FluidStack inputIn, IItemProvider outputIn) {
		return new SolidificationRecipeBuilder(inputIn, outputIn, 1);
	}
	
	public static SolidificationRecipeBuilder solidificationRecipe(FluidStack inputIn, IItemProvider outputIn, int countIn) {
		return new SolidificationRecipeBuilder(inputIn, outputIn, countIn);
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.output));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save, boolean isRootFolder) {
		ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(this.output);
		if (!isRootFolder) {
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Melting Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(save));
			}
		}else{
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Melting Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(Reference.MODID, save + ForgeRegistries.ITEMS.getKey(this.output).getPath()));
			}
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new SolidificationRecipeBuilder.Result(id, this.input, this.output, this.count));
	}
	
	public static class Result implements IFinishedRecipe {
		
		private final ResourceLocation id;
		private final FluidStack input;
		private final Item output;
		private final int count;
		
		public Result(ResourceLocation id, FluidStack input, Item output, int count) {
			this.id = id;
			this.input = input;
			this.output = output;
			this.count = count;
		}

		@Override
		public void serialize(JsonObject json) {
			
			JsonObject fluid = new JsonObject();
			fluid.addProperty("id", ForgeRegistries.FLUIDS.getKey(this.input.getFluid()).toString());
			fluid.addProperty("amount", this.input.getAmount());
			
			json.add("input", fluid);
			
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
			return Registration.SOLIDIFICATION_RECIPE_SERIALIZER.get();
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
