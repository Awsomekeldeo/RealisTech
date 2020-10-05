package awsome.realistech.compat.jei.categories;

import javax.annotation.Nullable;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class RealistechRecipeCategory<T> implements IRecipeCategory<T> {
	
	public final ResourceLocation uid;
	protected final IGuiHelper helper;
	private final Class<? extends T> recipeClass;
	public String localizedName;
	protected IDrawableStatic background;
	private IDrawable icon;
	
	public RealistechRecipeCategory(Class<? extends T> recipeClassIn, IGuiHelper helperIn, ResourceLocation uidIn, String localizationKey) {
		this.uid = uidIn;
		this.helper = helperIn;
		this.localizedName = I18n.format(localizationKey);
		this.recipeClass = recipeClassIn;
	}

	@Override
	public ResourceLocation getUid() {
		return this.uid;
	}

	@Override
	public Class<? extends T> getRecipeClass() {
		return this.recipeClass;
	}

	@Override
	public String getTitle() {
		return this.localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}
	
	@Nullable
	@Override
	public IDrawable getIcon() {
		return this.icon;
	}
	
	protected void setIcon(ItemStack stackIn) {
		this.setIcon(this.helper.createDrawableIngredient(stackIn));
	}
	
	protected void setIcon(IDrawable iconIn) {
		this.icon = iconIn;
	}
	
	protected void setBackground(IDrawableStatic backgroundIn) {
		this.background = backgroundIn;
	}
}
