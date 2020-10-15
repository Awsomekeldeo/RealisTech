package awsome.realistech.compat.jei;

import java.util.ArrayList;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.AnvilRecipe;
import awsome.realistech.api.recipe.KilnRecipe;
import awsome.realistech.api.recipe.KnappingRecipe;
import awsome.realistech.api.recipe.MoldingRecipe;
import awsome.realistech.api.recipe.WeakSmeltingRecipe;
import awsome.realistech.client.gui.containter.screen.KilnScreen;
import awsome.realistech.client.gui.containter.screen.KnappingScreen;
import awsome.realistech.client.gui.containter.screen.MediumHeatFurnaceScreen;
import awsome.realistech.client.gui.containter.screen.MoldingScreen;
import awsome.realistech.compat.jei.categories.AnvilRecipeCategory;
import awsome.realistech.compat.jei.categories.KilnRecipeCategory;
import awsome.realistech.compat.jei.categories.WeakSmeltingRecipeCategory;
import awsome.realistech.compat.jei.categories.handwork.KnappingRecipeCategory;
import awsome.realistech.compat.jei.categories.handwork.MoldingRecipeCategory;
import awsome.realistech.registry.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;


@JeiPlugin
public class RealistechJEIPlugin implements IModPlugin {
	
	public final ResourceLocation UID = new ResourceLocation(Reference.MODID, "main");
	public static IDrawableStatic slotDrawable;
	

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(
			new KnappingRecipeCategory(guiHelper),
			new MoldingRecipeCategory(guiHelper),
			new KilnRecipeCategory(guiHelper),
			new AnvilRecipeCategory(guiHelper),
			new WeakSmeltingRecipeCategory(guiHelper)
		);
		slotDrawable = guiHelper.getSlotDrawable();
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(new ArrayList<>(KnappingRecipe.recipeList.values()), KnappingRecipeCategory.UID);
		registration.addRecipes(new ArrayList<>(MoldingRecipe.recipeList.values()), MoldingRecipeCategory.UID);
		registration.addRecipes(new ArrayList<>(KilnRecipe.recipeList.values()), KilnRecipeCategory.UID);
		registration.addRecipes(new ArrayList<>(AnvilRecipe.recipeList.values()), AnvilRecipeCategory.UID);
		registration.addRecipes(new ArrayList<>(WeakSmeltingRecipe.recipeList.values()), WeakSmeltingRecipeCategory.UID);
		registration.addIngredientInfo(new ItemStack(Registration.PLANT_FIBER.get()), VanillaTypes.ITEM, "gui.realistech.grass_fiber_description");
	}
	
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(KnappingScreen.class, 109, 48, 22, 15, KnappingRecipeCategory.UID);
		registration.addRecipeClickArea(MoldingScreen.class, 109, 48, 22, 15, MoldingRecipeCategory.UID);
		registration.addRecipeClickArea(MediumHeatFurnaceScreen.class, 78, 32, 28, 23, WeakSmeltingRecipeCategory.UID, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
		registration.addRecipeClickArea(KilnScreen.class, 71, 52, 22, 15, KilnRecipeCategory.UID, VanillaRecipeCategoryUid.FUEL);
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(Registration.ROCK_ITEM.get()), KnappingRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(Items.CLAY_BALL), MoldingRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(Registration.KILN_ITEM.get()), KilnRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(Registration.STONE_ANVIL.get()), AnvilRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(Registration.WEAK_FURNACE.get()), WeakSmeltingRecipeCategory.UID, VanillaRecipeCategoryUid.FURNACE);
	}
}
