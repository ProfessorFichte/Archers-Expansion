package com.archers_expansion;

import com.archers_expansion.effect.Effects;
import com.archers_expansion.items.Group;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import com.archers_expansion.config.EffectsConfig;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.archers_expansion.items.Items.registerModItems;

public class ArchersExpansionMod implements ModInitializer {
	public static final String MOD_ID = "archers_expansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ConfigManager<EffectsConfig> effectsConfig = new ConfigManager<EffectsConfig>
			("effects", new EffectsConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();


	private void registerItemGroup() {
		Group.ARCHERS_EXPANSION= FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, Group.KEY, Group.ARCHERS_EXPANSION);
	}
	@Override
	public void onInitialize() {
		effectsConfig.refresh();
		Group.registerItemGroups();
		registerModItems();
		registerItemGroup();
		Effects.register();
	}

}