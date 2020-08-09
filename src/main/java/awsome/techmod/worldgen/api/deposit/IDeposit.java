package awsome.techmod.worldgen.api.deposit;

import java.util.List;

import awsome.techmod.worldgen.api.PlutonType;
import net.minecraft.block.BlockState;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/api/world/IDeposit.java
 * Original source and credit goes to ohitsjustjose
 */
public interface IDeposit {
	BlockState getOre();

    BlockState getSample();

    String getFriendlyName();

    int getYMin();

    int getYMax();

    int getChance();

    int getSize();

    String[] getDimensionBlacklist();

    boolean canReplace(BlockState state);

    boolean oreMatches(BlockState other);

    boolean sampleMatches(BlockState other);

    List<BlockState> getBlockStateMatchers();

    PlutonType getPlutonType();

    float getDensity();
}
