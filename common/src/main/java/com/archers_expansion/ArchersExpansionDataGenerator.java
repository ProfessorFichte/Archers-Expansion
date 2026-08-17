package com.archers_expansion;

import com.archers_expansion.datagen.ArchersExpansionAdvancementDatagen;
import com.archers_expansion.datagen.CraftingRecipeGenerator;
import com.archers_expansion.datagen.SmithingRecipes;
import com.archers_expansion.effect.ArchersExpansionEffects;
import com.archers_expansion.items.Armors;
import com.archers_expansion.sounds.Sounds;
import com.archers_expansion.spell.ArchersExpansionSpells;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SimpleSoundGeneratorV2;
import net.spell_engine.api.datagen.SpellGenerator;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.api.tags.SpellTags;
import net.spell_engine.rpg_series.item.Armor;
import net.spell_engine.rpg_series.datagen.RPGSeriesDataGen;
import net.spell_engine.rpg_series.tags.RPGSeriesItemTags;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ItemTagGenerator::new);
		pack.addProvider(UnsmeltGenerator::new);
		pack.addProvider(SpellGen::new);
		pack.addProvider(SpellTagGenerator::new);
		pack.addProvider(ModelProvider::new);
		pack.addProvider(CraftingRecipeGenerator::new);
		pack.addProvider(SmithingRecipes::new);
		pack.addProvider(LangGenerator::new);
		pack.addProvider(SoundGen::new);
		pack.addProvider(ArchersExpansionAdvancementDatagen::new);
	}

	public static class SoundGen extends SimpleSoundGeneratorV2 {
		public SoundGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateSounds(Builder builder) {
			builder.entries.add(new Entry(MOD_ID,
					Sounds.entries.stream()
							.map(entry -> SoundEntry.withVariants(entry.id().getPath(), entry.variants()))
							.toList()
					)
			);
		}
	}

	public static class ModelProvider extends FabricModelProvider {
		public ModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			Armors.entries.forEach(entry -> {
				for (var piece: entry.armorSet().pieces()) {
					itemModelGenerator.register((Item) piece, Models.GENERATED);
				}
			});
		}
	}

	public static class SpellGen extends SpellGenerator {
		public SpellGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateSpells(Builder builder) {
			for (var entry: ArchersExpansionSpells.entries) {
				builder.add(entry.id(), entry.spell());
			}
		}
	}

	public static class ItemTagGenerator extends RPGSeriesDataGen.ItemTagGenerator {
		public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}
		public void armoryTags(List<Armor.Entry> armors) {
			this.armoryTags(armors, EnumSet.noneOf(RPGSeriesItemTags.ArmorMetaType.class));
		}

		public void armoryTags(List<Armor.Entry> armors, RPGSeriesItemTags.ArmorMetaType metaType) {
			this.armoryTags(armors, EnumSet.of(metaType));
		}

		public void armoryTags(List<Armor.Entry> armors, EnumSet<RPGSeriesItemTags.ArmorMetaType> metaTypes) {
			Iterator var3 = armors.iterator();

			while(var3.hasNext()) {
				Armor.Entry armor = (Armor.Entry)var3.next();
				Armor.Set set = armor.armorSet();
				FabricTagProvider<Item>.FabricTagBuilder headTag = this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR);
				headTag.addOptional(set.idOf(set.head));
				FabricTagProvider<Item>.FabricTagBuilder chestTag = this.getOrCreateTagBuilder(ItemTags.CHEST_ARMOR);
				chestTag.addOptional(set.idOf(set.chest));
				FabricTagProvider<Item>.FabricTagBuilder legsTag = this.getOrCreateTagBuilder(ItemTags.LEG_ARMOR);
				legsTag.addOptional(set.idOf(set.legs));
				FabricTagProvider<Item>.FabricTagBuilder feetTag = this.getOrCreateTagBuilder(ItemTags.FOOT_ARMOR);
				feetTag.addOptional(set.idOf(set.feet));
				Iterator var12;

				String lootTheme = armor.lootProperties().theme();
				if (lootTheme != null && !lootTheme.isEmpty()) {
					FabricTagProvider<Item>.FabricTagBuilder themeTag = this.getOrCreateTagBuilder(RPGSeriesItemTags.LootThemes.get(lootTheme));
					Iterator var19 = armor.armorSet().pieceIds().iterator();

					while(var19.hasNext()) {
						Object id = var19.next();
						themeTag.addOptional((Identifier)id);
					}
				}

				var12 = metaTypes.iterator();

				while(var12.hasNext()) {
					RPGSeriesItemTags.ArmorMetaType metaType = (RPGSeriesItemTags.ArmorMetaType)var12.next();
					FabricTagProvider<Item>.FabricTagBuilder metaTag = this.getOrCreateTagBuilder(RPGSeriesItemTags.ArmorType.get(metaType));
					Iterator var15 = armor.armorSet().pieceIds().iterator();

					while(var15.hasNext()) {
						Object id = var15.next();
						metaTag.addOptional((Identifier)id);
					}
				}
			}

		}
		List<String> armoryKeywords = List.of("sentinel","polar_stalker","bounty_hunter");
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			var armorTagOptions1 = new ArmorOptions(false, true);
			var armorTagOptions2 = new ArmorOptions(true, true);
			armoryTags(
					Armors.entries.stream().filter(entry -> armoryKeywords.stream().anyMatch(entry.name()::contains)).toList(),
					RPGSeriesItemTags.ArmorMetaType.ARCHERY
			);
			generateArmorTags(
					Armors.entries.stream().filter(entry -> armoryKeywords.stream().noneMatch(entry.name()::contains)).toList(),
					RPGSeriesItemTags.ArmorMetaType.ARCHERY,
					armorTagOptions2
			);
		}
	}

	public static class UnsmeltGenerator extends FabricRecipeProvider {
		public UnsmeltGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		public static int UNSMELT_TIME = 300;

		@Override
		public void generate(RecipeExporter exporter) {
			disassembleArmor(exporter, Armors.deadeye_t1.armorSet(), Items.LEATHER);
			disassembleArmor(exporter, Armors.war_archer_t1.armorSet(), Items.IRON_NUGGET);
			disassembleArmor(exporter, Armors.tundra_hunter_t1.armorSet(), Items.LEATHER);
			disassembleArmor(exporter, Armors.netherite_deadeye.armorSet(), Items.NETHERITE_SCRAP);
			disassembleArmor(exporter, Armors.netherite_war_archer.armorSet(), Items.NETHERITE_SCRAP);
			disassembleArmor(exporter, Armors.netherite_tundra_hunter.armorSet(), Items.NETHERITE_SCRAP);
		}

		@Override
		public String getName() {
			return "Unsmelt Recipes";
		}

		private static void disassembleArmor(RecipeExporter exporter, Armor.Set armorSet, Item output) {
			FabricRecipeProvider.offerSmelting(exporter,
					armorSet.pieces(),
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME,
					"disassemble"
			);
			FabricRecipeProvider.offerBlasting(exporter,
					armorSet.pieces(),
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME / 2,
					"disassemble"
			);
		}
	}

	public static class LangGenerator extends FabricLanguageProvider {
		public LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, "en_us", registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
			builder.add("itemGroup.archers_expansion.general", "Archers Expansion");

			builder.add("item.archers_expansion.spell_book/deadeye", "Deadeye's List");
			builder.add("item.archers_expansion.spell_book/deadeye.spell_binding.description",
					"Spell Book of Rogue Archers, using fast ranged weapons and tricky archery skills to slowly defeat enemies.\n- Strengths: Inflicting harmful effects and making sure not to be caught.\n- Weaknesses: Heavily armored enemies\n- Equipment: Moderately armored");
			builder.add("item.archers_expansion.spell_scroll/deadeye", "Deadeye Scroll");

			builder.add("item.archers_expansion.spell_book/tundra_hunter", "Tundric Hunting");
			builder.add("item.archers_expansion.spell_book/tundra_hunter.spell_binding.description",
					"Spell Book of Tundra Hunters, using balanced ranged weapons and freezing projectiles.\n- Strengths: Slowing and freezing enemies.\n- Weaknesses: Heavily armored and CC immune targets.\n- Equipment: Moderately armored");
			builder.add("item.archers_expansion.spell_scroll/tundra_hunter", "Tundra Hunter Scroll");

			builder.add("item.archers_expansion.spell_book/war_archer", "War Archery Instructions");
			builder.add("item.archers_expansion.spell_book/war_archer.spell_binding.description",
					"Spell Book of War Archers, using fast high ranged damage weapons to cause explosions and heavy damage.\n- Strengths: High Burst Damage.\n- Weaknesses: Fast and tricky Enemies\n- Equipment: Heavily armored");
			builder.add("item.archers_expansion.spell_scroll/war_archer", "War Archer Scroll");

			Armors.entries.forEach(entry -> {
				var translations = new LinkedHashMap<String, String>();
				translations.put(((Item)entry.armorSet().head).getTranslationKey(), entry.armorSet().headTranslation);
				translations.put(((Item)entry.armorSet().chest).getTranslationKey(), entry.armorSet().chestTranslation);
				translations.put(((Item)entry.armorSet().legs).getTranslationKey(), entry.armorSet().legsTranslation);
				translations.put(((Item)entry.armorSet().feet).getTranslationKey(), entry.armorSet().feetTranslation);
				for (var armorEntry: translations.entrySet()) {
					builder.add(armorEntry.getKey(), armorEntry.getValue());
				}
			});

			ArchersExpansionEffects.entries.forEach(entry -> {
				builder.add("effect." + MOD_ID + "." + entry.id.getPath(), entry.title);
				builder.add("effect." + MOD_ID + "." + entry.id.getPath() + ".description", entry.description);
			});

			ArchersExpansionSpells.entries.forEach(entry -> {
				if (entry.title() != null && !entry.title().isEmpty()) {
					builder.add("spell." + MOD_ID + "." + entry.id().getPath() + ".name", entry.title());
					builder.add("spell." + MOD_ID + "." + entry.id().getPath() + ".description", entry.description());
				}
			});

			for (var entry : ArchersExpansionAdvancementDatagen.getEntries()) {
				builder.add(entry.titleKey(), entry.title());
				builder.add(entry.descriptionKey(), entry.description());
			}
			builder.add("entity.archers_expansion.explosive_barrel", "Explosive Barrel");
			builder.add("entity.archers_expansion.alter_ego", "Alter Ego");
			builder.add("entity.archers_expansion.spell_polar_bear", "Polar Bear");

			builder.add("equipment_set.archers_expansion.bounty_hunter", "Bounty Hunting");
			builder.add("equipment_set.archers_expansion.polar_stalker", "Polar Prey");
			builder.add("equipment_set.archers_expansion.sentinel_archer", "Castle Guard");
		}
	}

	public static class SpellTagGenerator extends FabricTagProvider<Spell> {
		public SpellTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, SpellRegistry.KEY, registriesFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			var namespace = MOD_ID;
			var treasureTagBuilder = getOrCreateTagBuilder(SpellTags.TREASURE);
			var processedBooks = new HashSet<ArchersExpansionSpells.Book>();
			ArchersExpansionSpells.entries.forEach(entry -> {
				if (entry.book() != null) {
					var bookTagKey = SpellTags.spellBook(namespace, entry.book().toString().toLowerCase());
					var bookTag = getOrCreateTagBuilder(bookTagKey);
					bookTag.addOptional(entry.id());
					var scrollTagKey = SpellTags.spellScroll(namespace, entry.book().toString().toLowerCase());
					var scrollTag = getOrCreateTagBuilder(scrollTagKey);
					scrollTag.addOptional(entry.id());
					if (processedBooks.add(entry.book())) {
						treasureTagBuilder.addOptionalTag(scrollTagKey);
					}
				}
			});
		}
	}
}
