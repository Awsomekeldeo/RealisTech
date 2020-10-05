package awsome.realistech.inventory.container;

import awsome.realistech.api.recipe.KnappingRecipe;
import awsome.realistech.registry.Registration;
import net.minecraft.entity.player.PlayerInventory;

public class KnappingContainer extends AbstractHandworkContainer {

	public KnappingContainer(int windowId, PlayerInventory inventory) {
		super(Registration.KNAPPING_CRAFTING_CONTAINER.get(), windowId, inventory, KnappingRecipe.KNAPPING, 32, 0, 16, 16, Registration.ROCK_ITEM.get(), 1, true);
	}
	
	
}
