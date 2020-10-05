package awsome.realistech;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import awsome.realistech.inventory.container.KnappingContainer;
import awsome.realistech.inventory.container.MoldingContainer;
import awsome.realistech.listeners.RecipeReloadListener;
import awsome.realistech.registry.Registration;
import awsome.realistech.setup.ClientSetup;
import awsome.realistech.setup.ModSetup;
import awsome.realistech.worldgen.capability.WorldgenCapProvider;
import awsome.realistech.worldgen.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;

@Mod(Reference.MODID)
public class Realistech {
	
	public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);
	
	public Realistech() {
		Registration.init();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<World> event) {
		event.addCapability(new ResourceLocation(Reference.MODID, "pluton"), new WorldgenCapProvider());
		LOGGER.info("Worldgen capability attached for " + Utils.dimensionToString(event.getObject().getDimension()));
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void addItemTooltips(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() == Items.BRICK) {
			event.getToolTip().add(1, new TranslationTextComponent("tooltip.realistech.brick").applyTextStyle(TextFormatting.GRAY).applyTextStyle(TextFormatting.ITALIC));
		}else if(stack.getItem() == Items.NETHER_BRICK) {
			event.getToolTip().add(1, new TranslationTextComponent("tooltip.realistech.brick").applyTextStyle(TextFormatting.GRAY).applyTextStyle(TextFormatting.OBFUSCATED).applyTextStyle(TextFormatting.ITALIC));
		}
	}
	
	@SubscribeEvent
	public void itemRightClickHandler(PlayerInteractEvent.RightClickItem event) {
		ItemStack stack = event.getItemStack();
		PlayerEntity player = event.getPlayer();
		World world = event.getWorld();
		if (!world.isRemote) {
			if (stack.getItem() == Registration.ROCK_ITEM.get() && stack.getCount() >= 2) {
				INamedContainerProvider containerProvider = new INamedContainerProvider() {

					@Override
					public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
						return new KnappingContainer(i, playerInv);
					}

					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("container.realistech.knapping");
					}

				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, (buf) -> {
					buf.writeInt(32);
					buf.writeInt(0);
					buf.writeInt(16);
					buf.writeInt(16);
					buf.writeResourceLocation(new ResourceLocation(Reference.MODID, "rock"));
					buf.writeInt(1);
					buf.writeBoolean(true);
				});
			} else if (stack.getItem() == Items.CLAY_BALL && stack.getCount() >= 5) {
				INamedContainerProvider containerProvider = new INamedContainerProvider() {

					@Override
					public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
						return new MoldingContainer(i, playerInv);
					}

					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("container.realistech.molding");
					}

				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, (buf) -> {
					buf.writeInt(0);
					buf.writeInt(0);
					buf.writeInt(16);
					buf.writeInt(16);
					buf.writeResourceLocation(new ResourceLocation("minecraft:clay_ball"));
					buf.writeInt(5);
					buf.writeBoolean(false);
				});
			}
		}
	}
	
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		event.getServer().getResourceManager().addReloadListener(new RecipeReloadListener());
	}
	
	@SubscribeEvent
	public void slowWoodBreak(PlayerEvent.BreakSpeed event) {
		BlockState blockTargeted = event.getState();
		if (blockTargeted.getBlock().getTags().contains(new ResourceLocation("minecraft:logs"))) {
			if (event.getPlayer().getHeldItemMainhand() == ItemStack.EMPTY) {
				event.setNewSpeed(event.getOriginalSpeed() / 5.0f);
			}
		}else{
			if (event.getPlayer().getHeldItemMainhand() == ItemStack.EMPTY) {
				event.setNewSpeed(event.getOriginalSpeed() / 2.0f);
			}
		}
	}
	
	@SubscribeEvent
	public void onDropsHarvested(BreakEvent event) {
		BlockState blockTargeted = event.getState();
		LOGGER.info(blockTargeted.getBlock().toString());
		if (blockTargeted.getBlock().getTags().contains(new ResourceLocation("minecraft:logs"))) {
			if (event.getPlayer().getHeldItemMainhand() == ItemStack.EMPTY) {
				if(!event.getPlayer().world.isRemote) {
					event.getPlayer().attackEntityFrom(DamageSource.GENERIC, 3.0f);
					event.getPlayer().sendMessage(new TranslationTextComponent("message.realistech.noBreak")
							.applyTextStyle(TextFormatting.RED)
							.applyTextStyle(TextFormatting.ITALIC));
				}
				event.setCanceled(true);
			}
		}
	}
}