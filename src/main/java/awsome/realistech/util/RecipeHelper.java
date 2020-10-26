package awsome.realistech.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeHelper {
	
	public static FluidStack deserializeFluid(JsonObject object) {
		String s = JSONUtils.getString(object, "id");
		Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(s));
		if (fluid == null) {
			throw new JsonSyntaxException("Unknown fluid '" + s + "'");
		}
		
		if (!object.has("amount")) {
			throw new JsonSyntaxException("Missing fluid amount");
		}
		
		int i = JSONUtils.getInt(object, "amount");
		return new FluidStack(fluid, i);
	}
	
	public static MoldType getMoldType(JsonObject json) {
		String s = JSONUtils.getString(json, "mold_type");
		
		if (MoldType.getFromString(s) == null) {
			throw new JsonSyntaxException("Invalid mold type" + s + ", available mold types are: " + MoldType.values());
		}
		
		return MoldType.getFromString(s);
	}
	
}
