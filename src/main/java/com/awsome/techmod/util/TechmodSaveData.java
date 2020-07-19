package com.awsome.techmod.util;

import java.util.Map;

import com.awsome.techmod.Reference;
import com.awsome.techmod.api.worldgen.OreAPI;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

/*	Using a modified version of:
 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/util/GeolosysSaveData.java
 * 	Original source & credit goes to oitsjustjose
 */	

public class TechmodSaveData extends WorldSavedData {
	
	public TechmodSaveData() {
        super(Reference.MODID);
        this.markDirty();
    }

    public TechmodSaveData(String s) {
        super(s);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("currentWorldDeposits")) {
            NBTTagCompound compDeposits = compound.getCompoundTag("currentWorldDeposits");
            for (String s : compDeposits.getKeySet()) {
                OreAPI.putWorldDeposit(s, compDeposits.getString(s));
            }
        }
        if (compound.hasKey("regennedChunks")) {
            NBTTagCompound compRegenned = compound.getCompoundTag("regennedChunks");
            for (String s : compRegenned.getKeySet()) {
                if (compRegenned.getBoolean(s)) {
                    OreAPI.markChunkRegenned(s);
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (!compound.hasKey("currentWorldDeposits")) {
            compound.setTag("currentWorldDeposits", new NBTTagCompound());
        }
        NBTTagCompound compDeposits = compound.getCompoundTag("currentWorldDeposits");
        for (Map.Entry<OreAPI.ChunkPosSerializable, String> e : OreAPI.getCurrentWorldDeposits().entrySet()) {
            compDeposits.setString(e.getKey().toString(), e.getValue());
        }

        if (!compound.hasKey("regennedChunks")) {
            compound.setTag("regennedChunks", new NBTTagCompound());
        }
        NBTTagCompound regenDeposits = compound.getCompoundTag("regennedChunks");
        for (Map.Entry<OreAPI.ChunkPosSerializable, Boolean> e : OreAPI.getRegennedChunks().entrySet()) {
            regenDeposits.setBoolean(e.getKey().toString(), e.getValue());
        }

        return compound;
    }

    public static TechmodSaveData get(World world) {
        // The IS_GLOBAL constant is there for clarity, and should be simplified into
        // the right branch.
        MapStorage storage = world.getPerWorldStorage();
        TechmodSaveData instance = (TechmodSaveData) storage.getOrLoadData(TechmodSaveData.class, Reference.MODID);

        if (instance == null) {
            instance = new TechmodSaveData();
            storage.setData(Reference.MODID, instance);
        }
        return instance;
    }
}
