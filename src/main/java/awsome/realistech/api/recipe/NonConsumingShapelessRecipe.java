package awsome.realistech.api.recipe;

import awsome.realistech.registry.Registration;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NonConsumingShapelessRecipe implements ICraftingRecipe {

	private final IRecipeType<?> type;
	protected final NonNullList<Ingredient> inputs;
	protected final NonNullList<Ingredient> nonConsumedInputs;
	protected final ItemStack output;
	protected final ResourceLocation id;
	protected final boolean isSimple;
	
	public NonConsumingShapelessRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, NonNullList<Ingredient> nonConsumedIngredients, ItemStack output) {
		this.inputs = ingredients;
		this.id = id;
		this.output = output;
		this.nonConsumedInputs = nonConsumedIngredients;
		this.type = IRecipeType.CRAFTING;
		this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int i = 0;
		
		for(int j = 0; j < inv.getSizeInventory(); ++j) {
			ItemStack itemstack = inv.getStackInSlot(j);
			if (!itemstack.isEmpty()) {
				++i;
	            if (isSimple)
	            recipeitemhelper.func_221264_a(itemstack, 1);
	            else inputs.add(itemstack);
			}
		}

	    return i == this.inputs.size() && (isSimple ? recipeitemhelper.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.inputs) != null);
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		return this.output.copy();
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= this.inputs.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return Registration.NON_CONSUMING_SHAPELESS_RECIPE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return this.type;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
		NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		
		for(Ingredient ingredient : this.nonConsumedInputs) {
			for(int i = 0; i < remainingItems.size(); ++i) {
				ItemStack item = inv.getStackInSlot(i);
				if (!item.isEmpty()) {
		            if (item.hasContainerItem()) {
		               remainingItems.set(i, item.getContainerItem());
		            } else if (ingredient.test(item)) {
		               ItemStack itemstack1 = item.copy();
		               itemstack1.setCount(1);
		               remainingItems.set(i, itemstack1);
		            }
		        }
			}
		}
		
		return remainingItems;
	}
}
