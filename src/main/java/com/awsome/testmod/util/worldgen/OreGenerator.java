package com.awsome.testmod.util.worldgen;

import java.util.HashMap;
import java.util.Random;

import com.awsome.techmod.Techmod;
import com.awsome.techmod.api.worldgen.IOre;
import com.awsome.techmod.api.worldgen.OreAPI;
import com.awsome.testmod.util.TechmodSaveData;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Loader;

/*	Using Geolosys's worldgen code:
 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/world/OreGenerator.java,
 * 	which is a modified version of:
 *  https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/world/IEWorldGen.java
 * 	Original source & credit goes to BluSunrize
 */	


public class OreGenerator implements IWorldGenerator {
	
	private static final String dataID = "techmodOreGeneratorPending";
	private static int last;
	private static HashMap<Integer, OreGen> oreSpawnWeights = new HashMap<>();
	
	public static void addOreGen(IOre ore) {
		OreGen gen = new OreGen(ore);
		for (int i = last; i < last + ore.getChance(); i++) {
            oreSpawnWeights.put(i, gen);
        }
		last = last + ore.getChance();
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (oreSpawnWeights.keySet().size() > 0) {
            int rng = random.nextInt(oreSpawnWeights.keySet().size());
                oreSpawnWeights.get(rng).generate(world, random, (chunkX * 16), (chunkZ * 16));
        }
		
	}
	
	public static class OreGen {
		WorldGenMineableSafe pluton;
		IOre ore;
		
		public OreGen(IOre ore) {
            this.pluton = new WorldGenMineableSafe(ore, dataID);
            this.ore = ore;
        }
		
		public void generate(World world, Random rand, int x, int z) {
			if (!Techmod.getInstance().chunkOreGen.canGenerateInChunk(world, new ChunkPos(x / 16, z / 16),
                    world.provider.getDimension())) {
                return;
            }
            boolean lastState = ForgeModContainer.logCascadingWorldGeneration;
            ForgeModContainer.logCascadingWorldGeneration = false;
            for (int d : this.ore.getDimBlacklist()) {
                if (d == world.provider.getDimension()) {
                    return;
                }
            }
            if (rand.nextInt(100) < this.ore.getChance()) {
                int y = this.ore.getYMin() != this.ore.getYMax()
                        ? this.ore.getYMin() + rand.nextInt(this.ore.getYMax() - this.ore.getYMin())
                        : this.ore.getYMin();
                if (Loader.isModLoaded("twilightforest") && world.provider.getDimension() == 7) {
                    y /= 2;
                    y /= 2;
                }
                // If the pluton placed any ores at all
                if (pluton.generate(world, rand, new BlockPos(x, y, z))) {
                    IBlockState tmp = this.ore.getOre();
                    OreAPI.putWorldDeposit(new ChunkPos(x / 16, z / 16), world.provider.getDimension(),
                            tmp.getBlock().getRegistryName() + ":" + tmp.getBlock().getMetaFromState(tmp));
                    TechmodSaveData.get(world).markDirty();
                    Techmod.getInstance().chunkOreGen.addChunk(new ChunkPos(x / 16, z / 16), world, y, this.ore);
                }
            }
            ForgeModContainer.logCascadingWorldGeneration = lastState;
        }
	}
}

