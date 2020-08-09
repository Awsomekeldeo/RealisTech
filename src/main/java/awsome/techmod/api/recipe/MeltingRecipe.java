package awsome.techmod.api.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import awsome.techmod.Reference;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MeltingRecipe implements IRecipe<IInventory> {
	
	public static final Serializer SERIALIZER = new Serializer();
	
	private final Ingredient input;
	private final FluidStack output;
	private final int meltTemp;
	private final ResourceLocation id;
	
	public MeltingRecipe(ResourceLocation id, Ingredient input, FluidStack output, int meltTemp) {
		this.id = id;
		this.input = input;
		this.output = output;
		this.meltTemp = meltTemp;
	}
	
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		return null;
	}

	@Override
	public boolean canFit(int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecipeType<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static FluidStack deserializeFluid(JsonObject object) {
		String s = JSONUtils.getString(object, "fluid");
		Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(s));
		if (fluid == null) {
			throw new JsonSyntaxException("Unknown fluid '" + s + "'");
		}
		int i = JSONUtils.getInt(object, "amount");
		return new FluidStack(fluid, i);
	}
	
	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MeltingRecipe> {
		
		Serializer() {
			this.setRegistryName(new ResourceLocation(Reference.MODID, "melting_recipe"));
		}
		
		@Override
		public MeltingRecipe read(ResourceLocation recipeId, JsonObject json) {
			//
			final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
			final Ingredient input = Ingredient.deserialize(inputElement);
			
			final FluidStack output = MeltingRecipe.deserializeFluid(json);
			final int temp = JSONUtils.getInt(json, "temperature");
			
			return new MeltingRecipe(recipeId, input, output, temp);
		}

		@Override
		public MeltingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			final Ingredient input = Ingredient.read(buffer);
			final FluidStack output = buffer.readFluidStack();
			final ResourceLocation id = buffer.readResourceLocation();
			final int temperature = buffer.readInt();
			
			return new MeltingRecipe(id, input, output, temperature);
		}

		@Override
		public void write(PacketBuffer buffer, MeltingRecipe recipe) {
			recipe.input.write(buffer);
			buffer.writeFluidStack(recipe.output);
			buffer.writeResourceLocation(recipe.id);
			buffer.writeInt(recipe.meltTemp);
		}
		
	}
}
