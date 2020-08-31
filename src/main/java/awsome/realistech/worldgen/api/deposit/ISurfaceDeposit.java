package awsome.realistech.worldgen.api.deposit;

import java.util.List;

import awsome.realistech.worldgen.api.PlutonType;
import net.minecraft.block.BlockState;

public interface ISurfaceDeposit {
	
	BlockState getSubsoil();
	
	BlockState getTopsoil();

    BlockState getIndicator();

    String getFriendlyName();
    
    int getMaxYSize();

    int getChance();

    int getSize();

    String[] getDimensionBlacklist();

    boolean canReplace(BlockState state);

    boolean subsoilMatches(BlockState other);
    
    boolean topsoilMatches(BlockState other);

    boolean indicatorMatches(BlockState other);

    List<BlockState> getSubsoilStateMatchers();
    
    List<BlockState> getTopsoilStateMatchers();

    PlutonType getPlutonType();

    float getDensity();
}
