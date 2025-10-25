package com.archers_expansion;

import com.archers_expansion.config.Default;
import com.archers_expansion.effect.Effects;
import com.archers_expansion.entity.WintersGripEntity;
import com.archers_expansion.items.Group;
import com.archers_expansion.items.Items;
import com.archers_expansion.items.armors.Armors;
import com.archers_expansion.sounds.Sounds;
import com.archers_expansion.config.TweaksConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import com.archers_expansion.config.EffectsConfig;
import net.minecraft.util.Identifier;
import net.spell_engine.api.config.ConfigFile;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchersExpansionMod implements ModInitializer {
	public static final String MOD_ID = "archers_expansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ConfigManager<EffectsConfig> effectsConfig = new ConfigManager<EffectsConfig>
			("effects_v2", new EffectsConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	public static ConfigManager<ConfigFile.Equipment> itemConfig = new ConfigManager<>
			("equipment_v2", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<TweaksConfig> tweaksConfig = new ConfigManager<>
			("tweaks", new TweaksConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();


	private void registerItemGroup() {
		Group.ARCHERS_EXPANSION= FabricItemGroup.builder()
				.icon(() -> new ItemStack(Armors.war_archer_t1.head.asItem()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, Group.KEY, Group.ARCHERS_EXPANSION);
	}
	@Override
	public void onInitialize() {
		effectsConfig.refresh();
		itemConfig.refresh();
		tweaksConfig.refresh();
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			tweaksConfig.value.ignore_items_required_mods = true;
		}
		Items.registerModItems();
		Group.registerItemGroups();
		registerItemGroup();
		Effects.register();
		Sounds.register();
		Armors.register(itemConfig.value.armor_sets);
		/*
		if (FabricLoader.getInstance().isModLoaded("armory_rpgs") || ArchersExpansionMod.tweaksConfig.value.ignore_items_required_mods) {
			ArmoryCompat.register(itemConfig.value.armor_sets);
			FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "archers_expansion_armory_compat"),
						modContainer,
						ResourcePackActivationType.ALWAYS_ENABLED
				);
			});
		}
		*/
		itemConfig.save();
		effectsConfig.save();
	}
	static{
		WintersGripEntity.ENTITY_TYPE = Registry.register(
				Registries.ENTITY_TYPE,
				Identifier.of(MOD_ID, "winters_grip"),
				FabricEntityTypeBuilder.<WintersGripEntity>create(SpawnGroup.MISC, WintersGripEntity::new)
						.dimensions(EntityDimensions.changing(6F, 0.5F))
						.fireImmune()
						.trackRangeBlocks(128)
						.trackedUpdateRate(20)
						.build()
		);
	}
}