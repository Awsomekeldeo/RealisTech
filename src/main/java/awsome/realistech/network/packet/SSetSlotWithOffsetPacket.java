package awsome.realistech.network.packet;

import java.util.function.Supplier;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SSetSlotWithOffsetPacket {
	
	private final int slotIndex;
	private final int slotIndexOffset;
	private final ItemStack stack;
	
	public SSetSlotWithOffsetPacket(PacketBuffer buf) {
		this.slotIndex = buf.readInt();
		this.slotIndexOffset = buf.readInt();
		this.stack = buf.readItemStack();
	}
	
	public SSetSlotWithOffsetPacket(int slotIndex, ItemStack stackIn, int slotIndexOffset) {
		this.slotIndex = slotIndex;
		this.slotIndexOffset = slotIndexOffset;
		this.stack = stackIn;
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeInt(slotIndex);
		buf.writeInt(slotIndexOffset);
		buf.writeItemStack(stack);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().openContainer;
			container.putStackInSlot(slotIndex + slotIndexOffset, stack);
		});
		return true;
	}
}
