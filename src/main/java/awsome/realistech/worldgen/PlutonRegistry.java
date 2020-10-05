package awsome.realistech.worldgen;

import java.util.ArrayList;
import java.util.Random;

import awsome.realistech.worldgen.api.deposit.DepositBiomeRestricted;
import awsome.realistech.worldgen.api.deposit.DepositMultiOreBiomeRestricted;
import awsome.realistech.worldgen.api.deposit.DepositStone;
import awsome.realistech.worldgen.api.deposit.IDeposit;
import awsome.realistech.worldgen.api.deposit.ISurfaceDeposit;
import awsome.realistech.worldgen.feature.PlutonOreFeature;
import awsome.realistech.worldgen.feature.PlutonStoneFeature;
import awsome.realistech.worldgen.feature.SurfaceDepositFeature;
import awsome.realistech.worldgen.feature.SurfaceRockFeature;
import awsome.realistech.worldgen.feature.SurfaceStickFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/PlutonRegistry.java
 * Original source and credit goes to ohitsjustjose
 */
public class PlutonRegistry
{
    private ArrayList<IDeposit> ores;
    private ArrayList<IDeposit> oreWeightList;
    private ArrayList<DepositStone> stones;
    private ArrayList<DepositStone> stoneWeightList;
    private ArrayList<ISurfaceDeposit> surfaceDeposits;
    private ArrayList<ISurfaceDeposit> surfaceDepositWeightList;

    public PlutonRegistry()
    {
        this.ores = new ArrayList<>();
        this.oreWeightList = new ArrayList<>();
        this.stones = new ArrayList<>();
        this.stoneWeightList = new ArrayList<>();
        this.surfaceDeposits = new ArrayList<>();
        this.surfaceDepositWeightList = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<IDeposit> getOres()
    {
        return (ArrayList<IDeposit>) this.ores.clone();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<IDeposit> getStones()
    {
        return (ArrayList<IDeposit>) this.stones.clone();
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<ISurfaceDeposit> getSurfaceDeposits() {
    	return (ArrayList<ISurfaceDeposit>) this.surfaceDeposits.clone();
    }

    public boolean addOrePluton(IDeposit ore)
    {
        if (ore instanceof DepositStone)
        {
            return addStonePluton((DepositStone) ore);
        }

        for (int i = 0; i < ore.getChance(); i++)
        {
            oreWeightList.add(ore);
        }

        return this.ores.add(ore);
    }

    public boolean addStonePluton(DepositStone stone)
    {
        for (int i = 0; i < stone.getChance(); i++)
        {
            stoneWeightList.add(stone);
        }
        return this.stones.add(stone);
    }
    
    public boolean addSurfaceDeposit(ISurfaceDeposit deposit)
    {
        for (int i = 0; i < deposit.getChance(); i++)
        {
            surfaceDepositWeightList.add(deposit);
        }
        return this.surfaceDeposits.add(deposit);
    }

    public IDeposit pickPluton(IWorld world, BlockPos pos, Random rand)
    {
        if (this.oreWeightList.size() > 0)
        {
            // Sometimes bias towards specific biomes where applicable
            if (rand.nextBoolean())
            {
                Biome b = world.getBiome(pos);
                ArrayList<IDeposit> forBiome = new ArrayList<>();
                for (IDeposit d : this.ores)
                {
                    if (d instanceof DepositBiomeRestricted)
                    {
                        if (((DepositBiomeRestricted) d).canPlaceInBiome(b))
                        {
                            forBiome.add(d);
                        }
                    }
                    else if (d instanceof DepositMultiOreBiomeRestricted)
                    {
                        if (((DepositMultiOreBiomeRestricted) d).canPlaceInBiome(b))
                        {
                            forBiome.add(d);
                        }
                    }
                }
                if (forBiome.size() > 0)
                {
                    int pick = rand.nextInt(forBiome.size());
                    return forBiome.get(pick);
                }
            }
            int pick = rand.nextInt(this.oreWeightList.size());
            return this.oreWeightList.get(pick);

        }
        return null;
    }

    public DepositStone pickStone()
    {
        if (this.stoneWeightList.size() > 0)
        {
            Random random = new Random();
            int pick = random.nextInt(this.stoneWeightList.size());
            return this.stoneWeightList.get(pick);
        }
        return null;
    }
    
    public ISurfaceDeposit pickSurfaceDeposit() {
    	
    	if (this.surfaceDepositWeightList.size() > 0) {
    		Random random = new Random();
    		int pick = random.nextInt(this.surfaceDepositWeightList.size());
    		return this.surfaceDepositWeightList.get(pick);
    	}
    	return null;
    }

    public void registerAsOreGenerator()
    {
        for (Biome biome : ForgeRegistries.BIOMES.getValues())
        {
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    new ConfiguredFeature<>(new PlutonOreFeature(NoFeatureConfig::deserialize), new NoFeatureConfig()));
        }
        for (Biome biome : ForgeRegistries.BIOMES.getValues())
        {
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ConfiguredFeature<>(
                    new PlutonStoneFeature(NoFeatureConfig::deserialize), new NoFeatureConfig()));
        }
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ConfiguredFeature<>(
					new SurfaceDepositFeature(NoFeatureConfig::deserialize), new NoFeatureConfig()));
		}
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
        	biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ConfiguredFeature<>(
        			new SurfaceRockFeature(NoFeatureConfig::deserialize), new NoFeatureConfig()));
        }
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
        	biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ConfiguredFeature<>(
        			new SurfaceStickFeature(NoFeatureConfig::deserialize), new NoFeatureConfig()));
        }
    }
}