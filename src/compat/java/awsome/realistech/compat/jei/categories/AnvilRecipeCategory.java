package awsome.realistech.compat.jei.categories;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.AnvilRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;

public class AnvilRecipeCategory extends RealistechRecipeCategory<AnvilRecipe> {
	
	public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/jei_integration/anvil_crafting.png");
	public static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "anvil_crafting");

	public AnvilRecipeCategory(IGuiHelper helperIn) {
		super(AnvilRecipe.class, helperIn, UID, "screen.realistech.jei_compat.anvil");
		setBackground(helper.createDrawable(GUI_TEXTURE, 0, 0, 92, 52));
	}

	@Override
	public void setIngredients(AnvilRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AnvilRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		
		guiStacks.init(0, true, 0, 0);
		guiStacks.init(1, true, 18, 0);
		
		guiStacks.init(2, false, 70, 23);
		
		guiStacks.set(ingredients);
	}

}
