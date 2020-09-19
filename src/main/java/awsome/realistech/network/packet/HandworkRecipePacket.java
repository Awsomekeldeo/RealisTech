package awsome.realistech.network.packet;

import java.util.function.Supplier;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

public class HandworkRecipePacket {
	
	private final ResourceLocation id;
	private final NonNullList<ItemStack> inventoryContents;
	
	public HandworkRecipePacket(PacketBuffer buf) {
		this.id = buf.readResourceLocation();
		
		int inventoryContentsSize = buf.readVarInt();
		NonNullList<ItemStack> stacks = NonNullList.withSize(inventoryContentsSize, ItemStack.EMPTY);
		
		for (int i = 0; i < stacks.size(); i++) {
			stacks.set(i, buf.readItemStack());
		}
		
		this.inventoryContents = stacks;
	}
	
	public HandworkRecipePacket(ResourceLocation id, NonNullList<ItemStack> stacksIn) {
		this.id = id;
		this.inventoryContents = stacksIn;
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeResourceLocation(id);
		buf.writeVarInt(inventoryContents.size());
		for (ItemStack stack : inventoryContents) {
			buf.writeItemStack(stack);
		}
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().openContainer;
			int i = 37;
			for (ItemStack stack : inventoryContents) {
				container.putStackInSlot(i, stack);
				i++;
			}
		});
		return true;
	}

}
