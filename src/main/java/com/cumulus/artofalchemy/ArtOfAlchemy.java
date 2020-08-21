package com.cumulus.artofalchemy;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ArtOfAlchemy implements ModInitializer {

	public static final String MODID = "artofalchemy";

	@Override
	public void onInitialize() {
		System.out.println("Art of Alchemy!");
	}

	public static Identifier id(String s) {
		return new Identifier(MODID, s);
	}
}
