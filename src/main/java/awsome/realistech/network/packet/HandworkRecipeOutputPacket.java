package awsome.realistech.network.packet;

import java.util.function.Supplier;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class HandworkRecipeOutputPacket {
	
	private final int slotIndex;
	private final ItemStack stack;
	
	public HandworkRecipeOutputPacket(PacketBuffer buf) {
		this.slotIndex = buf.readInt();
		this.stack = buf.readItemStack();
	}
	
	public HandworkRecipeOutputPacket(int slotIndex, ItemStack stackIn) {
		this.slotIndex = slotIndex;
		this.stack = stackIn;
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeInt(slotIndex);
		buf.writeItemStack(stack);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().openContainer;
			container.putStackInSlot(slotIndex, stack);
		});
		return true;
	}
}
