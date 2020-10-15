package awsome.realistech.network.packet;

import java.util.function.Supplier;

import awsome.realistech.inventory.container.CrucibleContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent;

public class SCrucibleTankUpdatePacket {
	
	private final NonNullList<FluidStack> inventoryContents;
	private final int capacity;
	
	public SCrucibleTankUpdatePacket(PacketBuffer buf) {
		
		int inventoryContentsSize = buf.readVarInt();
		NonNullList<FluidStack> stacks = NonNullList.withSize(inventoryContentsSize, FluidStack.EMPTY);
		
		for (int i = 0; i < stacks.size(); i++) {
			stacks.set(i, buf.readFluidStack());
		}
		
		this.capacity = buf.readVarInt();
		this.inventoryContents = stacks;
	}
	
	public SCrucibleTankUpdatePacket(NonNullList<FluidStack> stacksIn, int capacity) {
		this.inventoryContents = stacksIn;
		this.capacity = capacity;
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeVarInt(inventoryContents.size());
		for (FluidStack stack : inventoryContents) {
			buf.writeFluidStack(stack);
		}
		buf.writeVarInt(capacity);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = Minecraft.getInstance().player.openContainer;
			if (container instanceof CrucibleContainer) {
				CrucibleContainer container1 = (CrucibleContainer)container;
				container1.crucibleTanks = inventoryContents;
				container1.tankCapacity = capacity;
			}
		});
		return true;
	}
}
