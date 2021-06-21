package awsome.realistech.network.packet;

import java.util.function.Supplier;

import awsome.realistech.inventory.container.BoilerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent;

public class STankUpdatePacket {
	
	private final NonNullList<FluidStack> stacks;
	
	public STankUpdatePacket(PacketBuffer buf) {
		
		int tankAmount = buf.readVarInt();
		
		NonNullList<FluidStack> fluids = NonNullList.withSize(tankAmount, FluidStack.EMPTY);
		
		for (int i = 0; i < fluids.size(); i++) {
			fluids.set(i, buf.readFluidStack());
		}
		
		this.stacks = fluids;
	}
	
	public STankUpdatePacket(NonNullList<FluidStack> tanks) {
		this.stacks = tanks;
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeVarInt(stacks.size());
		for (FluidStack stack : stacks) {
			buf.writeFluidStack(stack);
		}
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = Minecraft.getInstance().player.openContainer;
			if (container instanceof BoilerContainer) {
				BoilerContainer container1 = (BoilerContainer)container;
				container1.boilerTanks = stacks;
			}
		});
		return true;
	}
	
}
