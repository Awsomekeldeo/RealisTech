package awsome.realistech.network.packet;

import java.util.function.Supplier;

import awsome.realistech.inventory.container.AbstractHandworkContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SConsumeHandworkItemPacket {
	
	private final boolean buttonClicked;
	
	public SConsumeHandworkItemPacket(PacketBuffer buf) {
		this.buttonClicked = buf.readBoolean();
	}
	
	public SConsumeHandworkItemPacket(boolean clicked) {
		this.buttonClicked = clicked;
	}
	
	public void toBytes(PacketBuffer buf) {
		buf.writeBoolean(buttonClicked);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().openContainer;
			if (container instanceof AbstractHandworkContainer) {
				((AbstractHandworkContainer) container).setButtonClicked(buttonClicked);
			}
		});
		return true;
	}
	
}
