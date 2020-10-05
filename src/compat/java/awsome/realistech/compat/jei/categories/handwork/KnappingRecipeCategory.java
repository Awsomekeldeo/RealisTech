package awsome.realistech.compat.jei.categories.handwork;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.KnappingRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.util.ResourceLocation;

public class KnappingRecipeCategory extends AbstractHandworkRecipeCategory<KnappingRecipe> {
	
	public static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "knapping");

	public KnappingRecipeCategory(IGuiHelper helperIn) {
		super(helperIn, UID, KnappingRecipe.class, "container.realistech.knapping");
	}
	
	@Override
	public void draw(KnappingRecipe recipe, double mouseX, double mouseY) {
		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			if (recipe.getIngredients().get(i).equals(recipe.getRecipeItem())) {
				helper.createDrawable(AbstractHandworkRecipeCategory.BUTTON_TEXTURE, 32, 0, 16, 16).draw((i % 5) * 16 + 1, (i / 5) * 16 + 1);
			}else{
				helper.createDrawable(AbstractHandworkRecipeCategory.BUTTON_TEXTURE, 48, 0, 16, 16).draw((i % 5) * 16 + 1, (i / 5) * 16 + 1);
			}
		}
	}
	
}
