package awsome.worldgen.capability;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/capability/WorldgenCapStorage.java
 * Original source and credit goes to ohitsjustjose
 */
public class WorldgenCapStorage implements Capability.IStorage<IWorldgenCapability> {
	@Override
    public void readNBT(Capability<IWorldgenCapability> capability, IWorldgenCapability instance, Direction side,
            INBT nbt)
    {
        if (nbt instanceof CompoundNBT)
        {
            instance.deserializeNBT(((CompoundNBT) nbt));
        }
    }

    @Nullable
    @Override
    public INBT writeNBT(Capability<IWorldgenCapability> capability, IWorldgenCapability instance, Direction side)
    {
        // Initialize the Compound with WorldDeposits and RetroGen:
        return instance.serializeNBT();
    }
}
