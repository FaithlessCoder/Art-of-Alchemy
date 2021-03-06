package com.cumulusmc.artofalchemy;

import com.cumulusmc.artofalchemy.block.AoABlocks;
import com.cumulusmc.artofalchemy.blockentity.AoABlockEntities;
import com.cumulusmc.artofalchemy.dispenser.AoADispenserBehavior;
import com.cumulusmc.artofalchemy.essentia.AoAEssentia;
import com.cumulusmc.artofalchemy.fluid.AoAFluids;
import com.cumulusmc.artofalchemy.gui.handler.AoAHandlers;
import com.cumulusmc.artofalchemy.item.AoAItems;
import com.cumulusmc.artofalchemy.network.AoANetworking;
import com.cumulusmc.artofalchemy.recipe.AoARecipes;
import com.cumulusmc.artofalchemy.transport.EssentiaNetworker;
import com.cumulusmc.artofalchemy.util.AoALoot;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArtOfAlchemy implements ModInitializer {

	public static final String MOD_ID = "artofalchemy";
	public static final String MOD_NAME = "Art of Alchemy";

	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static final ItemGroup ALCHEMY_GROUP = FabricItemGroupBuilder.create(id("alchemy"))
			.icon(() -> new ItemStack(AoAItems.MYSTERIOUS_SIGIL)).build();

	@Override
	public void onInitialize() {
		log(Level.INFO, "Humankind cannot gain anything without first giving something in return. "
				+ "To obtain, something of equal value must be lost.");

		AutoConfig.register(AoAConfig.class, GsonConfigSerializer::new);
		AoAEssentia.registerEssentia();
		AoAFluids.registerFluids();
		AoABlocks.registerBlocks();
		AoAItems.registerItems();
		AoABlockEntities.registerBlockEntities();
		AoAHandlers.registerHandlers();
		AoARecipes.registerRecipes();
		AoADispenserBehavior.registerDispenserBehavior();
		AoANetworking.initializeNetworking();
		AoALoot.initialize();
		WorldTickCallback.EVENT.register((world) -> {
			if (!world.isClient()) {
				EssentiaNetworker.get((ServerWorld) world).tick();
			}
		});
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static void log(Level level, String message){
		LOGGER.log(level, message);
	}

}
