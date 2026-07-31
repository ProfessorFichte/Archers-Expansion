package com.archers_expansion.datagen;

import com.archers_expansion.items.Armors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.item.MRPGCItems;

import java.util.concurrent.CompletableFuture;

public class CraftingRecipeGenerator extends FabricRecipeProvider {

    public CraftingRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        generateArmorRecipes(exporter);
    }

    private void generateArmorRecipes(RecipeExporter exporter) {
        createShapedRecipe(exporter, Armors.deadeye_t1.armorSet().head,
                "ABA",
                "C C",
                null,
                'A', MRPGCItems.HARDENED_LEATHER,
                'B', net.minecraft.item.Items.SPIDER_EYE,
                'C', net.minecraft.item.Items.RABBIT_HIDE
        );

        createShapedRecipe(exporter, Armors.deadeye_t1.armorSet().chest,
                "A A",
                "BDB",
                "CBC",
                'A', net.minecraft.item.Items.GOLD_INGOT,
                'B', MRPGCItems.HARDENED_LEATHER,
                'C', net.minecraft.item.Items.RABBIT_HIDE,
                'D', net.minecraft.item.Items.SPIDER_EYE
        );

        createShapedRecipe(exporter, Armors.deadeye_t1.armorSet().legs,
                "AAA",
                "B B",
                "C C",
                'A', MRPGCItems.HARDENED_LEATHER,
                'B', net.minecraft.item.Items.GOLD_INGOT,
                'C', net.minecraft.item.Items.RABBIT_HIDE
        );

        createShapedRecipe(exporter, Armors.deadeye_t1.armorSet().feet,
                "ACA",
                "B B",
                null,
                'A', MRPGCItems.HARDENED_LEATHER,
                'B', net.minecraft.item.Items.RABBIT_HIDE,
                'C', net.minecraft.item.Items.GOLD_INGOT
        );

        createShapedRecipe(exporter, Armors.tundra_hunter_t1.armorSet().head,
                "AAA",
                "C C",
                null,
                'A', "more_rpg_classes:polar_bear_fur",
                'C', net.minecraft.item.Items.PRISMARINE_SHARD
        );

        createShapedRecipe(exporter, Armors.tundra_hunter_t1.armorSet().chest,
                "A A",
                "BAB",
                "ACA",
                'A', "more_rpg_classes:polar_bear_fur",
                'B', net.minecraft.item.Items.PRISMARINE_SHARD,
                'C', MRPGCItems.HARDENED_LEATHER
        );

        createShapedRecipe(exporter, Armors.tundra_hunter_t1.armorSet().legs,
                "AAA",
                "A A",
                "C C",
                'A', "more_rpg_classes:polar_bear_fur",
                'C', net.minecraft.item.Items.PRISMARINE_SHARD
        );

        createShapedRecipe(exporter, Armors.tundra_hunter_t1.armorSet().feet,
                "A A",
                "B B",
                null,
                'A', net.minecraft.item.Items.PRISMARINE_SHARD,
                'B', "more_rpg_classes:polar_bear_fur"
        );

        createShapedRecipe(exporter,Armors.war_archer_t1.armorSet().head,
                "ABA",
                "C C",
                null,
                'A', net.minecraft.item.Items.IRON_INGOT,
                'B', net.minecraft.item.Items.NETHERITE_SCRAP,
                'C', net.minecraft.item.Items.CHAIN
        );

        createShapedRecipe(exporter, Armors.war_archer_t1.armorSet().chest,
                "A A",
                "BAB",
                "ACA",
                'A', net.minecraft.item.Items.CHAIN,
                'B', net.minecraft.item.Items.LEATHER,
                'C', net.minecraft.item.Items.NETHERITE_SCRAP
        );

        createShapedRecipe(exporter, Armors.war_archer_t1.armorSet().legs,
                "AAA",
                "B B",
                "C C",
                'A', net.minecraft.item.Items.LEATHER,
                'B', net.minecraft.item.Items.NETHERITE_SCRAP,
                'C', net.minecraft.item.Items.CHAIN
        );

        createShapedRecipe(exporter, Armors.war_archer_t1.armorSet().feet,
                "ACA",
                "B B",
                null,
                'A', net.minecraft.item.Items.LEATHER,
                'B', net.minecraft.item.Items.CHAIN,
                'C', net.minecraft.item.Items.NETHERITE_SCRAP
        );
    }

    public static void createShapedRecipe(
            RecipeExporter exporter,
            Item result,
            String pattern1,
            String pattern2,
            String pattern3,
            Object... keyMappings
    ) {
        ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder
                .create(RecipeCategory.COMBAT, result, 1)
                .pattern(pattern1)
                .pattern(pattern2);

        if (pattern3 != null && !pattern3.isEmpty()) {
            builder.pattern(pattern3);
        }

        for (int i = 0; i < keyMappings.length; i += 2) {
            char key = (char) keyMappings[i];
            Object ingredient = keyMappings[i + 1];

            if (ingredient instanceof Item item) {
                builder.input(key, item);
            } else if (ingredient instanceof TagKey<?> tag) {
                builder.input(key, (TagKey<Item>) tag);
            } else if (ingredient instanceof String str) {
                TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, Identifier.of(str));
                builder.input(key, tag);
            }
        }

        builder.criterion(hasItem(result), conditionsFromItem(result))
                .showNotification(true)
                .offerTo(exporter);
    }

    public static void createShapelessRecipe(
            RecipeExporter exporter,
            Item result,
            Object... ingredients
    ) {
        ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder
                .create(RecipeCategory.MISC, result, 1);

        for (Object ingredient : ingredients) {
            if (ingredient instanceof Item item) {
                builder.input(item);
            } else if (ingredient instanceof TagKey<?> tag) {
                builder.input((TagKey<Item>) tag);
            } else if (ingredient instanceof String str) {
                TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, Identifier.of(str));
                builder.input(tag);
            }
        }

        builder.criterion(hasItem(result), conditionsFromItem(result))
                .offerTo(exporter);
    }

    @Override
    public String getName() {
        return "Crafting Recipes";
    }
}
