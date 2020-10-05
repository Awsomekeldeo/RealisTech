package awsome.realistech.inventory.container;

import awsome.realistech.api.recipe.MoldingRecipe;
import awsome.realistech.registry.Registration;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;

public class MoldingContainer extends AbstractHandworkContainer {

	public MoldingContainer(int windowId, PlayerInventory inventory) {
		super(Registration.MOLDING_CRAFTING_CONTAINER.get(), windowId, inventory, MoldingRecipe.MOLDING, 0, 0, 16, 16, Items.CLAY_BALL, 5, false);
	}

}
