package awsome.techmod.worldgen.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import awsome.techmod.worldgen.api.OreAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/capability/WorldgenCapProvider.java
 * Original source and credit goes to ohitsjustjose
 */
public class WorldgenCapProvider implements ICapabilitySerializable<CompoundNBT>{
	
	private final IWorldgenCapability impl = new WorldgenCapability();
    private final LazyOptional<IWorldgenCapability> cap = LazyOptional.of(() -> impl);

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capIn, final @Nullable Direction side)
    {
        if (capIn == OreAPI.TECHMOD_WORLDGEN_CAPABILITY)
        {
            return cap.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return impl.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        impl.deserializeNBT(nbt);
    }
}
