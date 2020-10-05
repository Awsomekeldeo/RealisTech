package awsome.realistech.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

/* Modified Version of Immersive Engineering's IngredientStackListBuilder
 * Original Source:
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.15/src/main/java/blusunrize/immersiveengineering/common/util/compat/jei/IngredientStackListBuilder.java
 * Original source and credit goes to BluSunrize.
 */
/**
 * @author BluSunrize - 19.01.2020
 */
public class IngredientStackListBuilder {
	
	private final List<List<ItemStack>> list;

	private IngredientStackListBuilder()
	{
		this.list = new ArrayList<>();
	}
	
	public static IngredientStackListBuilder make(List<Ingredient> ingredientList) {
		
		IngredientStackListBuilder builder = new IngredientStackListBuilder();
		builder.add(ingredientList);
		return builder;
		
	}
	
	public IngredientStackListBuilder add(List<Ingredient> ingredientList) {
		
		for (Ingredient ingredient : ingredientList) {
			this.list.add(Arrays.asList(ingredient.getMatchingStacks()));
		}
		return this;
		
	}
	
	public static IngredientStackListBuilder make(Ingredient... ingredientStacks)
	{
		IngredientStackListBuilder builder = new IngredientStackListBuilder();
		builder.add(ingredientStacks);
		return builder;
	}

	public IngredientStackListBuilder add(Ingredient... ingredientStacks)
	{
		for(Ingredient ingr : ingredientStacks)
			this.list.add(Arrays.asList(ingr.getMatchingStacks()));
		return this;
	}

	public List<List<ItemStack>> build()
	{
		return this.list;
	}
}
