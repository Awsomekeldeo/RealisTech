package awsome.realistech.worldgen.capability;

import java.util.Map;
import java.util.UUID;

import awsome.realistech.worldgen.api.BlockPosDim;
import awsome.realistech.worldgen.api.ChunkPosDim;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/capability/IWorldgenCapability.java
 * Original source and credit goes to ohitsjustjose
 */
public interface IWorldgenCapability extends INBTSerializable<CompoundNBT> {
	boolean hasOrePlutonGenerated(ChunkPosDim pos);

    void setOrePlutonGenerated(ChunkPosDim pos);

    boolean hasStonePlutonGenerated(ChunkPosDim pos);

    void setStonePlutonGenerated(ChunkPosDim pos);

    void putPendingBlock(BlockPosDim pos, BlockState state);

    BlockState getPendingBlock(BlockPosDim pos);

    Map<ChunkPosDim, Boolean> getOreGenMap();

    Map<ChunkPosDim, Boolean> getStoneGenMap();

    Map<BlockPosDim, BlockState> getPendingBlocks();

    boolean hasPlayerReceivedManual(UUID uuid);

    void setPlayerReceivedManual(UUID uuid);

    Map<UUID, Boolean> getGivenMap();
}
