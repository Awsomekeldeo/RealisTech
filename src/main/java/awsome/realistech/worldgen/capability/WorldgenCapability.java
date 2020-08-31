package awsome.realistech.worldgen.capability;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import awsome.realistech.worldgen.api.BlockPosDim;
import awsome.realistech.worldgen.api.ChunkPosDim;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;

/*
 * Using a modified version of Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/capability/WorldgenCapability.java
 * Original source and credit goes to ohitsjustjose
 */
public class WorldgenCapability implements IWorldgenCapability {
	
	private Map<ChunkPosDim, Boolean> oreGenMap;
    private Map<ChunkPosDim, Boolean> stoneGenMap;
    private Map<ChunkPosDim, Boolean> clayGenMap;
    private Map<BlockPosDim, BlockState> pendingBlocks;
    private Map<UUID, Boolean> giveMap;

    public WorldgenCapability()
    {
        this.oreGenMap = new ConcurrentHashMap<>();
        this.stoneGenMap = new ConcurrentHashMap<>();
        this.clayGenMap = new ConcurrentHashMap<>();
        this.pendingBlocks = new ConcurrentHashMap<>();
        this.giveMap = new ConcurrentHashMap<>();
    }

    @Override
    public Map<ChunkPosDim, Boolean> getOreGenMap()
    {
        return this.oreGenMap;
    }
    
    public Map<ChunkPosDim, Boolean> getClayGenMap() {
    	return this.clayGenMap;
    }

    @Override
    public Map<ChunkPosDim, Boolean> getStoneGenMap()
    {
        return this.stoneGenMap;
    }

    @Override
    public Map<BlockPosDim, BlockState> getPendingBlocks()
    {
        return this.pendingBlocks;
    }

    @Override
    public void putPendingBlock(BlockPosDim pos, BlockState state)
    {
        this.pendingBlocks.put(pos, state);
    }

    @Override
    public BlockState getPendingBlock(BlockPosDim pos)
    {
        BlockState ret = this.pendingBlocks.get(pos);
        return ret;
    }

    @Override
    public void setOrePlutonGenerated(ChunkPosDim dim)
    {
        this.oreGenMap.put(dim, true);
    }

    @Override
    public boolean hasOrePlutonGenerated(ChunkPosDim pos)
    {
        return this.oreGenMap.containsKey(pos) && this.oreGenMap.get(pos);
    }
    
    @Override
    public void setSurfaceDepositGenerated(ChunkPosDim dim) {
    	this.clayGenMap.put(dim, true);
    }
    
    @Override
    public boolean hasSurfaceDepositGenerated(ChunkPosDim pos) {
    	return this.clayGenMap.containsKey(pos) && this.clayGenMap.get(pos);
    }

    @Override
    public void setStonePlutonGenerated(ChunkPosDim dim)
    {
        this.stoneGenMap.put(dim, true);
    }

    @Override
    public boolean hasStonePlutonGenerated(ChunkPosDim pos)
    {
        return this.stoneGenMap.containsKey(pos) && this.stoneGenMap.get(pos);
    }

    @Override
    public boolean hasPlayerReceivedManual(UUID uuid)
    {
        return this.giveMap.containsKey(uuid);
    }

    @Override
    public void setPlayerReceivedManual(UUID uuid)
    {
        this.giveMap.put(uuid, true);
    }

    @Override
    public Map<UUID, Boolean> getGivenMap()
    {
        return this.giveMap;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT compound = new CompoundNBT();
        compound.put("WorldOreDeposits", new CompoundNBT());
        compound.put("WorldStoneDeposits", new CompoundNBT());
        compound.put("WorldClayDeposits", new CompoundNBT());
        compound.put("PendingBlocks", new CompoundNBT());
        compound.put("PlayersGifted", new CompoundNBT());

        CompoundNBT oreDeposits = compound.getCompound("WorldOreDeposits");
        CompoundNBT clayDeposits = compound.getCompound("WorldClayDeposits");
        CompoundNBT stoneDeposits = compound.getCompound("WorldStoneDeposits");
        CompoundNBT pendingBlocks = compound.getCompound("PendingBlocks");
        CompoundNBT playersGifted = compound.getCompound("PlayersGifted");

        this.getOreGenMap().forEach((x, y) -> oreDeposits.putBoolean(x.toString(), y));
        this.getStoneGenMap().forEach((x, y) -> stoneDeposits.putBoolean(x.toString(), y));
        this.getClayGenMap().forEach((x, y) -> clayDeposits.putBoolean(x.toString(), y));
        this.getPendingBlocks().forEach((x, y) -> pendingBlocks.put(x.toString(), NBTUtil.writeBlockState(y)));
        this.getGivenMap().forEach((x, y) -> playersGifted.putBoolean(x.toString(), y));

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound)
    {
        CompoundNBT oreDeposits = compound.getCompound("WorldOreDeposits");
        CompoundNBT stoneDeposits = compound.getCompound("WorldStoneDeposits");
        CompoundNBT clayDeposits = compound.getCompound("WorldClayDeposits");
        CompoundNBT pendingBlocks = compound.getCompound("PendingBlocks");
        CompoundNBT playersGifted = compound.getCompound("PlayersGifted");

        oreDeposits.keySet().forEach(key -> this.setOrePlutonGenerated(new ChunkPosDim(key)));
        stoneDeposits.keySet().forEach(key -> this.setStonePlutonGenerated(new ChunkPosDim(key)));
        clayDeposits.keySet().forEach(key -> this.setSurfaceDepositGenerated(new ChunkPosDim(key)));
        pendingBlocks.keySet().forEach(key -> this.putPendingBlock(new BlockPosDim(key),
                NBTUtil.readBlockState((CompoundNBT) Objects.requireNonNull(pendingBlocks.get(key)))));
        playersGifted.keySet().forEach(key -> this.setPlayerReceivedManual(UUID.fromString(key)));
    }
}
