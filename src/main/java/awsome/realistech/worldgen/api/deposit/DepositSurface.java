package awsome.realistech.worldgen.api.deposit;

import java.util.List;

import awsome.realistech.worldgen.api.PlutonType;
import awsome.realistech.worldgen.utils.Utils;
import net.minecraft.block.BlockState;

public class DepositSurface implements ISurfaceDeposit {
	
	private BlockState subsoilBlock;
	private BlockState topsoilBlock;
	private BlockState indicatorBlock;
	private int maxYSize;
	private int size;
	private int chance;
	private String[] dimensionBlacklist;
	private List<BlockState> subsoilStateMatchers;
	private List<BlockState> topsoilStateMatchers;
	private PlutonType plutonType;
	private float density;
	
	public DepositSurface(BlockState subsoilBlock, BlockState topsoilBlock, BlockState indicatorBlock, int size, int chance,
			int maxYSize, String[] dimBlacklist, List<BlockState> subsoilStateMatchers, List<BlockState> topsoilStateMatchers, PlutonType type, float density) {
		this.topsoilBlock = topsoilBlock;
		this.subsoilBlock = subsoilBlock;
		this.chance = chance;
		this.density = density;
		this.dimensionBlacklist = dimBlacklist;
		this.indicatorBlock = indicatorBlock;
		this.maxYSize = maxYSize;
		this.size = size;
		this.chance = chance;
		this.topsoilStateMatchers = topsoilStateMatchers;
		this.subsoilStateMatchers = subsoilStateMatchers;
		this.plutonType = type;
	}
	
	@Override
	public BlockState getSubsoil() {
		return this.subsoilBlock;
	}

	@Override
	public BlockState getTopsoil() {
		return this.topsoilBlock;
	}

	@Override
	public BlockState getIndicator() {
		return this.indicatorBlock;
	}

	@Override
	public String getFriendlyName() {
		return Utils.blockStateToName(this.getSubsoil());
	}

	@Override
	public int getMaxYSize() {
		return this.maxYSize;
	}

	@Override
	public int getChance() {
		return this.chance;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public String[] getDimensionBlacklist() {
		return this.dimensionBlacklist;
	}

	@Override
	public boolean canReplace(BlockState state) {
		if (this.topsoilStateMatchers == null || this.subsoilStateMatchers == null) {
			return true;
		}
		for (BlockState s : this.topsoilStateMatchers)
        {
            if (Utils.doStatesMatch(s, state))
            {
                return true;
            }
        }
		for (BlockState s : this.subsoilStateMatchers)
        {
            if (Utils.doStatesMatch(s, state))
            {
                return true;
            }
        }
		return false;
	}

	@Override
	public boolean subsoilMatches(BlockState other) {
		return Utils.doStatesMatch(this.subsoilBlock, other);
	}

	@Override
	public boolean topsoilMatches(BlockState other) {
		return Utils.doStatesMatch(this.topsoilBlock, other);
	}

	@Override
	public boolean indicatorMatches(BlockState other) {
		return Utils.doStatesMatch(this.indicatorBlock, other);
	}

	@Override
	public List<BlockState> getSubsoilStateMatchers() {
		return this.subsoilStateMatchers;
	}

	@Override
	public PlutonType getPlutonType() {
		return this.plutonType;
	}

	@Override
	public float getDensity() {
		return this.density;
	}

	@Override
	public List<BlockState> getTopsoilStateMatchers() {
		return this.topsoilStateMatchers;
	}

}
