package awsome.techmod.inventory.container;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;

public interface IModContainer extends INamedContainerProvider {
	public void openGUI(ServerPlayerEntity player);
}
