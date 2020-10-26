package awsome.realistech.util;

import java.util.function.Supplier;

import awsome.realistech.registry.Registration;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ModItemTier implements IItemTier {
	
	STONE(0, 60, 2.5f, 0.0f, 5, () -> {
		return Ingredient.fromItems(Items.STONE);
	}),
	COPPER(1, 212, 3.5f, 1.0f, 15, () -> {
		return Ingredient.fromItems(Registration.COPPER_INGOT.get());
	}),
	BRONZE(1, 300, 5.0f, 2.0f, 14, () -> {
		return Ingredient.fromItems(Registration.BRONZE_INGOT.get());
	}),
	IRON(2, 450, 6.0f, 3.0f, 10, () -> {
		return Ingredient.fromItems(Items.IRON_INGOT);
	});
	
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;
	
	private ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.repairMaterial = new LazyValue<>(repairMaterial);
		this.enchantability = enchantability;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public float getEfficiency() {
		return this.efficiency;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getMaxUses() {
		return this.maxUses;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

}
