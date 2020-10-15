package awsome.realistech.listeners;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import awsome.realistech.api.recipe.AnvilRecipe;
import awsome.realistech.api.recipe.KilnRecipe;
import awsome.realistech.api.recipe.KnappingRecipe;
import awsome.realistech.api.recipe.MoldingRecipe;
import awsome.realistech.api.recipe.WeakSmeltingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

public class RecipeReloadListener implements ISelectiveResourceReloadListener {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		if (EffectiveSide.get().isServer()) {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			RecipeManager recipeManager = server.getRecipeManager();
			buildRecipeLists(recipeManager);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onRecipesUpdated(RecipesUpdatedEvent event) {
		buildRecipeLists(event.getRecipeManager());
	}

	static void buildRecipeLists(RecipeManager managerIn) {
		Collection<IRecipe<?>> recipes = managerIn.getRecipes();
		
		if(recipes.size()==0)
			return;
		
		KnappingRecipe.recipeList = filterRecipes(recipes, KnappingRecipe.class, KnappingRecipe.KNAPPING);
		MoldingRecipe.recipeList = filterRecipes(recipes, MoldingRecipe.class, MoldingRecipe.MOLDING);
		KilnRecipe.recipeList = filterRecipes(recipes, KilnRecipe.class, KilnRecipe.KILN_RECIPE);
		AnvilRecipe.recipeList = filterRecipes(recipes, AnvilRecipe.class, AnvilRecipe.ANVIL_RECIPE);
		WeakSmeltingRecipe.recipeList = filterRecipes(recipes, WeakSmeltingRecipe.class, WeakSmeltingRecipe.WEAK_SMELTING_RECIPE);
	}
	
	static <R extends IRecipe<?>> Map<ResourceLocation, R> filterRecipes(Collection<IRecipe<?>> recipes, Class<R> recipeClass, IRecipeType<R> recipeType)
	{
		return recipes.stream()
				.filter(iRecipe -> iRecipe.getType()==recipeType)
				.flatMap(r -> {
						return Stream.of(r);
				})
				.map(recipeClass::cast)
				.collect(Collectors.toMap(recipe -> recipe.getId(), recipe -> recipe));
	}
}
