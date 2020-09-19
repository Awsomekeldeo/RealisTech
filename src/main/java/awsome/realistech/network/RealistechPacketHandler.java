package awsome.realistech.network;

import awsome.realistech.Reference;
import awsome.realistech.network.packet.SSetSlotWithOffsetPacket;
import awsome.realistech.network.packet.HandworkRecipePacket;
import awsome.realistech.network.packet.SConsumeHandworkItemPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class RealistechPacketHandler {
	
	public static SimpleChannel INSTANCE;
	public static int id = 0;
	
	public static int nextID() {
		return id++;
	}
	
	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Reference.MODID), 
				() -> "1.0", 
				s -> true, 
				s -> true);
		
		INSTANCE.messageBuilder(HandworkRecipePacket.class, nextID())
				.encoder(HandworkRecipePacket::toBytes)
				.decoder(HandworkRecipePacket::new)
				.consumer(HandworkRecipePacket::handle)
				.add();
		INSTANCE.messageBuilder(SSetSlotWithOffsetPacket.class, nextID())
				.encoder(SSetSlotWithOffsetPacket::toBytes)
				.decoder(SSetSlotWithOffsetPacket::new)
				.consumer(SSetSlotWithOffsetPacket::handle)
				.add();
		INSTANCE.messageBuilder(SConsumeHandworkItemPacket.class, nextID())
			.encoder(SConsumeHandworkItemPacket::toBytes)
			.decoder(SConsumeHandworkItemPacket::new)
			.consumer(SConsumeHandworkItemPacket::handle)
			.add();
	}
	
	public static void sendToClient(Object packet, ServerPlayerEntity player) {
		INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}
	
	public static void sendToServer(Object packet) {
		INSTANCE.sendToServer(packet);
	}
}
