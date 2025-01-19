package com.archers_expansion;

import com.archers_expansion.config.Default;
import com.archers_expansion.effect.Effects;
import com.archers_expansion.items.Group;
import com.archers_expansion.items.Items;
import com.archers_expansion.items.armors.Armors;
import com.archers_expansion.sounds.Sounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import com.archers_expansion.config.EffectsConfig;
import net.spell_engine.api.item.ItemConfig;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchersExpansionMod implements ModInitializer {
	public static final String MOD_ID = "archers_expansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ConfigManager<EffectsConfig> effectsConfig = new ConfigManager<EffectsConfig>
			("effects_v1", new EffectsConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	public static ConfigManager<ItemConfig> itemConfig = new ConfigManager<ItemConfig>
			("items_v1", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();


	private void registerItemGroup() {
		Group.ARCHERS_EXPANSION= FabricItemGroup.builder()
				.icon(() -> new ItemStack(Armors.deadeye_t1.head.asItem()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, Group.KEY, Group.ARCHERS_EXPANSION);
	}
	@Override
	public void onInitialize() {
		effectsConfig.refresh();
		itemConfig.refresh();
		Items.registerModItems();
		Group.registerItemGroups();
		registerItemGroup();
		Effects.register();
		Sounds.register();
		Armors.register(itemConfig.value.armor_sets);
		itemConfig.save();
	}

}