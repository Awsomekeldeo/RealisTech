package awsome.realistech.compat.jei.categories.handwork;

import java.util.Arrays;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.AbstractHandworkRecipe;
import awsome.realistech.compat.jei.categories.RealistechRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractHandworkRecipeCategory<T extends AbstractHandworkRecipe> extends RealistechRecipeCategory<T> {
	
	public static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/widgets/handwork_icons.png");

	public AbstractHandworkRecipeCategory(IGuiHelper helperIn, ResourceLocation uidIn, Class<T> classIn, String unlocalizedNameIn) {
		super(classIn, helperIn, uidIn, unlocalizedNameIn);
		setBackground(helper.createDrawable(new ResourceLocation(Reference.MODID, "textures/gui/molding.png"), 20, 16, 136, 82));
	}

	@Override
	public void setIngredients(AbstractHandworkRecipe recipe, IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(recipe.getRecipeItem().getMatchingStacks()));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AbstractHandworkRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		guiStacks.init(36, false, 118, 32);
		guiStacks.set(36, recipe.getRecipeOutput());
	}
	
}
