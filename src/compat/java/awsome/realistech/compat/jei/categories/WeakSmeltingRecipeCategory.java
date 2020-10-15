package awsome.realistech.compat.jei.categories;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.WeakSmeltingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class WeakSmeltingRecipeCategory extends RealistechRecipeCategory<WeakSmeltingRecipe> {
	
	private IDrawableAnimated arrow;
	private IDrawableAnimated flame;
	
	public static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "weak_smelting");

	public WeakSmeltingRecipeCategory(IGuiHelper helperIn) {
		super(WeakSmeltingRecipe.class, helperIn, UID, "screen.realistech.jei_compat.weak_smelting");
		setBackground(helper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 114, 82, 54));
		arrow = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
		flame = helper.createAnimatedDrawable(helper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14), 300, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public void setIngredients(WeakSmeltingRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}
	
	@Override
	public void draw(WeakSmeltingRecipe recipe, double mouseX, double mouseY) {
		arrow.draw(24, 18);
		flame.draw(1, 20);
		
		
		float experience = recipe.getExperience();
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		int stringWidth = fontRenderer.getStringWidth(I18n.format("gui.jei.category.smelting.experience", experience));
		fontRenderer.drawString(I18n.format("gui.jei.category.smelting.experience", experience), background.getWidth() - stringWidth, 0, 0xFF808080);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WeakSmeltingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		
		guiStacks.init(0, true, 0, 0);
		guiStacks.init(2, false, 60, 18);
		
		guiStacks.set(ingredients);
	}

}
