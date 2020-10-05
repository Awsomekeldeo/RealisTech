package awsome.realistech.compat.jei.categories.handwork;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.MoldingRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.util.ResourceLocation;

public class MoldingRecipeCategory extends AbstractHandworkRecipeCategory<MoldingRecipe> {
	
	public static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "molding");
	
	public MoldingRecipeCategory(IGuiHelper helperIn) {
		super(helperIn, UID, MoldingRecipe.class, "container.realistech.molding");
	}
	
	@Override
	public void draw(MoldingRecipe recipe, double mouseX, double mouseY) {
		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			if (recipe.isInvertedPattern) {
				if (recipe.getIngredients().get(i).equals(recipe.getRecipeItem())) {
					helper.createDrawable(AbstractHandworkRecipeCategory.BUTTON_TEXTURE, 16, 0, 16, 16).draw((i % 5) * 16 + 1, (i / 5) * 16 + 1);
				}else{
					helper.createDrawable(AbstractHandworkRecipeCategory.BUTTON_TEXTURE, 0, 0, 16, 16).draw((i % 5) * 16 + 1, (i / 5) * 16 + 1);
				}
			}else{
				if (recipe.getIngredients().get(i).equals(recipe.getRecipeItem())) {
					helper.createDrawable(AbstractHandworkRecipeCategory.BUTTON_TEXTURE, 0, 0, 16, 16).draw((i % 5) * 16 + 1, (i / 5) * 16 + 1);
				}else{
					helper.createDrawable(AbstractHandworkRecipeCategory.BUTTON_TEXTURE, 16, 0, 16, 16).draw((i % 5) * 16 + 1, (i / 5) * 16 + 1);
				}
			}
		}
	}
	
}
