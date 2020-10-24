package awsome.realistech.network.packet;

import java.util.function.Supplier;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MoldCapsUpdatePacket {
	
	private final CompoundNBT tag;
	private final int slot;
	
	public MoldCapsUpdatePacket(PacketBuffer buf) {
		this.tag = buf.readCompoundTag();
		this.slot = buf.readInt();
	}
	
	public MoldCapsUpdatePacket(CompoundNBT nbt, int slot) {
		if (nbt.contains("realistech:heatData")) {
			this.tag = nbt.getCompound("realistech:heatData");
			this.slot = slot;
		}else{
			throw new IllegalStateException("An invalid mold capability update packet was sent, tag was missing realistech:heatData tag!");
		}
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeCompoundTag(this.tag);
		buf.writeInt(this.slot);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = Minecraft.getInstance().player.openContainer;
			if (container.inventorySlots.get(this.slot).getStack().getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
				IHeat h = container.inventorySlots.get(this.slot).getStack().getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
				
				HeatCapability.HEAT_CAPABILITY.readNBT(h, null, this.tag);
			}
		});
		return true;
	}
}
