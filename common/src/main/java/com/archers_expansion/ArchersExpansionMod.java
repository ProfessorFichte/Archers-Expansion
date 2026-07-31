package com.archers_expansion;

import com.archers_expansion.config.Default;
import com.archers_expansion.effect.ArchersExpansionEffects;
import com.archers_expansion.entity.ArcherExpansionSummons;
import com.archers_expansion.entity.ArchersTeamMatcher;
import com.archers_expansion.entity.ModEntitiesRegistry;
import com.archers_expansion.items.Group;
import com.archers_expansion.items.Items;
import com.archers_expansion.items.Armors;
import com.archers_expansion.sounds.Sounds;
import com.archers_expansion.config.TweaksConfig;
import com.archers_expansion.spell.CustomSpellImpacts;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.spell_engine.api.config.ConfigFile;
import net.spell_engine.api.spell.summon.SummonedEntityConfig;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.archers_expansion.compat.CompatLoadingCheck.armoryLoadCheck;

public class ArchersExpansionMod{
	public static final String MOD_ID = "archers_expansion";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ConfigManager<ConfigFile.Effects> effectsConfig = new ConfigManager<>
			("effects_v3", new ConfigFile.Effects())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	public static ConfigManager<ConfigFile.Equipment> itemConfig = new ConfigManager<ConfigFile.Equipment>
			("equipment_v4", Default.itemConfig)
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
	public static ConfigManager<SummonedEntityConfig> summonConfig = new ConfigManager<>
			("summoned_entities", seededSummonDefaults())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	private static SummonedEntityConfig seededSummonDefaults() {
		var config = new SummonedEntityConfig();
		config.entries.put(ModEntitiesRegistry.POLAR_BEAR_ID.toString(), ArcherExpansionSummons.defaults());
		return config;
	}

	public static void init() {
		CustomSpellImpacts.registerCustomImpacts();
		CustomSpellImpacts.registerCustomDeliveries();
		ArchersTeamMatcher.register();
		effectsConfig.refresh();
		itemConfig.refresh();
		tweaksConfig.refresh();
		summonConfig.refresh();
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			tweaksConfig.value.ignore_items_required_mods = true;
		}
	}
	public static void registerEffects() {
		ArchersExpansionEffects.register(effectsConfig.value);
		effectsConfig.save();
	}
	public static void registerSounds() {
		Sounds.register();
	}
	public static void registerItems() {
		Items.registerModItems();
		Group.registerItemGroups();
		Group.ARCHERS_EXPANSION= FabricItemGroup.builder()
				.icon(() -> new ItemStack(Armors.war_archer_t1.armorSet().head.asItem()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, Group.KEY, Group.ARCHERS_EXPANSION);
		Armors.register(itemConfig.value.armor_sets);
		if (armoryLoadCheck()) {
			FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "archers_expansion_armory_compat"),
						modContainer,
						ResourcePackActivationType.ALWAYS_ENABLED
				);
			});
		}
		itemConfig.save();
	}
	public static void registerEntities() {
		ModEntitiesRegistry.registerEntities();
	}

}