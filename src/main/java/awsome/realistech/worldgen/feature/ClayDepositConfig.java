package awsome.realistech.worldgen.feature;

import java.util.List;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ClayDepositConfig implements IFeatureConfig {
	
	public final BlockState state;
	public final BlockState topsoilState;
	public final int size;
	public final int maxYSize;
	public final List<BlockState> replaceStates;
	public final List<BlockState> topsoilReplaceStates;
	
	public ClayDepositConfig(BlockState state, BlockState topsoilState, int size, int maxYSize, List<BlockState> replaceStates, List<BlockState> topsoilReplaceStates) {
		this.state = state;
		this.topsoilState = topsoilState;
		this.size = size;
		this.replaceStates = replaceStates;
		this.maxYSize = maxYSize;
		this.topsoilReplaceStates = topsoilReplaceStates;
	}

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		
		Builder<T, T> builder = new Builder<>();
		
		return new Dynamic<>(ops, ops.createMap(builder
				.put(ops.createString("state"), BlockState.serialize(ops, this.state).getValue())
				.put(ops.createString("topsoilState"), BlockState.serialize(ops, this.topsoilState).getValue())
				.put(ops.createString("size"), ops.createInt(this.size))
				.put(ops.createString("maxYSize"), ops.createInt(this.maxYSize))
				//Unchecked Cast
				.put(ops.createString("replaceStates"), (T) this.replaceStates.stream().map((state) -> {
					return BlockState.serialize(ops, state).getValue();
				}))
				//Unchecked Cast
				.put(ops.createString("topsoilReplaceStates"), (T) this.replaceStates.stream().map(state -> {
					return BlockState.serialize(ops, state);
				}))
				.build()));
		
//		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("state"), BlockState.serialize(ops, this.state).getValue(), ops.createString("topsoilState"), BlockState.serialize(ops, this.topsoilState), ops.createString("size"), ops.createInt(this.size), ops.createString("maxYSize"), ops.createInt(this.maxYSize), ops.createString("replaceStates"), this.replaceStates.stream().map((state) -> {
//			return BlockState.serialize(ops, state).getValue();
//		}))));
	}
	
	public static <T> ClayDepositConfig deserialize(Dynamic<T> datafixer) {
		BlockState state = datafixer.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		BlockState topsoilState = datafixer.get("topsoilState").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		int size = datafixer.get("size").asInt(0);
		int maxYSize = datafixer.get("maxYSize").asInt(0);
		List<BlockState> stateList = datafixer.get("replaceStates").asList(BlockState::deserialize);
		List<BlockState> topsoilStateList = datafixer.get("topsoilReplaceStates").asList(BlockState::deserialize);
		return new ClayDepositConfig(state, topsoilState, size, maxYSize, stateList, topsoilStateList);
	}
}
