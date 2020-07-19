package com.awsome.techmod.api.worldgen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.awsome.techmod.util.worldgen.OreGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.ChunkPos;
/*	Using Geolosys's worldgen code
 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/api/GeolosysAPI.java
 * 	Original source & credit goes to oitsjustjose
 */	

public class OreAPI {
	
	public static ArrayList<IOre> oreBlocks = new ArrayList<>();
	public static ArrayList<IBlockState> oreConverterBlacklist = new ArrayList<>();
    private static HashMap<ChunkPosSerializable, String> currentWorldDeposits = new HashMap<>();
    private static LinkedHashMap<ChunkPosSerializable, Boolean> regennedChunks = new LinkedHashMap<>();
    public static ArrayList<IBlockState> replacementMats = new ArrayList<>();
    
    public static void putWorldDeposit(ChunkPos pos, int dimension, String state) {
        currentWorldDeposits.put(new ChunkPosSerializable(pos, dimension), state);
    }
    
    public static void putWorldDeposit(ChunkPosSerializable pos, String state) {
        currentWorldDeposits.put(pos, state);
    }
    
    @SuppressWarnings("unchecked")
    public static HashMap<ChunkPosSerializable, String> getCurrentWorldDeposits() {
        return (HashMap<ChunkPosSerializable, String>) currentWorldDeposits.clone();
    }
    
    @SuppressWarnings("unchecked")
    public static HashMap<ChunkPosSerializable, Boolean> getRegennedChunks() {
        return (HashMap<ChunkPosSerializable, Boolean>) regennedChunks.clone();
    }
    
    public static void markChunkRegenned(String posAsString) {
        String[] parts = posAsString.replace("[", "").replace("]", "").split(",");
        markChunkRegenned(new ChunkPosSerializable(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])));
    }

    public static void markChunkRegenned(ChunkPos pos, int dimension) {
        markChunkRegenned(new ChunkPosSerializable(pos, dimension));
    }

    public static void markChunkRegenned(ChunkPosSerializable pos) {
        regennedChunks.put(pos, true);
    }

    public static boolean hasChunkRegenned(ChunkPos pos, int dimension) {
        return hasChunkRegenned(new ChunkPosSerializable(pos, dimension));
    }

    public static boolean hasChunkRegenned(ChunkPosSerializable pos) {
        for (ChunkPosSerializable c : regennedChunks.keySet()) {
            if (c.getX() == pos.getX() && c.getZ() == pos.getZ() && c.getDimension() == pos.getDimension()) {
                return regennedChunks.get(c);
            }
        }
        return false;
    }

    public static void registerMineralDeposit(IBlockState oreBlock, Block sampleBlock, int yMin, int yMax,
            int size, int chance, int[] dimBlacklist, List<IBlockState> blockStateMatchers, float density,
            @Nullable String customName) {
        Deposit tempDeposit = new Deposit(oreBlock, sampleBlock, yMin, yMax, size, chance, dimBlacklist,
                blockStateMatchers, density, customName);
        OreGenerator.addOreGen(tempDeposit);
        oreBlocks.add(tempDeposit);
    }
    
    
    
    public static class ChunkPosSerializable implements Serializable {
        private static final long serialVersionUID = 6006452707959877895L;
        private int x;
        private int z;
        private int dim;

        /**
         * @param pos A Mojang ChunkPos initializer for ChunkPosSerializable
         */
        public ChunkPosSerializable(ChunkPos pos, int dim) {
            this(pos.x, pos.z, dim);
        }

        /**
         * @param x The X position which the Chunk starts at
         * @param z The Z position which the Chunk starts at
         */
        public ChunkPosSerializable(int x, int z, int dim) {
            this.x = x;
            this.z = z;
            this.dim = dim;
        }

        /**
         * @return The X value at which the Chunk starts at
         */
        public int getX() {
            return this.x;
        }

        /**
         * @return The Z value at which the Chunk starts at
         */
        public int getZ() {
            return this.z;
        }

        /**
         * @return The dimension of the chunk
         */
        public int getDimension() {
            return this.dim;
        }

        /**
         * @return A Mojang ChunkPos variant of this object
         */
        public ChunkPos toChunkPos() {
            return new ChunkPos(this.x, this.z);
        }

        @Override
        public String toString() {
            return "[" + this.getX() + "," + this.getZ() + "," + this.getDimension() + "]";
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            } else if (other instanceof ChunkPosSerializable) {
                ChunkPosSerializable c = (ChunkPosSerializable) other;
                return c.getX() == this.getX() && c.getZ() == this.getZ() && c.getDimension() == this.getDimension();
            }
            return false;
        }
    }



	/**
     * @param posAsString The ChunkPosSerializable in its toString() form
     * @param state       The String to act as a value
     */
    public static void putWorldDeposit(String posAsString, String state) {
        String[] parts = posAsString.replace("[", "").replace("]", "").split(",");
        currentWorldDeposits.put(new ChunkPosSerializable(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])), state);
    }

}
