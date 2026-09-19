package com.archers_expansion.datagen;

import com.archers_expansion.items.Armors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/// Smithing-transform recipe generator.
///
/// This is a local 1.20.1 re-implementation of `net.more_rpg_classes.datagen.SmithingRecipeGenerator`
/// (same call surface, same recipe bodies). The library version could not be reused as-is on this line:
/// it still writes the 1.21 shapes — the `data/<mod>/recipe/` directory (1.20.1 wants `recipes/`),
/// a `"result": {"id": ..}` object (1.20.1 `SmithingTransformRecipe` reads `"item"`), and
/// `"neoforge:conditions"` (Forge 47 reads a **top-level** `"conditions"` array, see
/// `RecipeManager` -> `CraftingHelper.processConditions(json, "conditions", ...)`).
/// `fabric:load_conditions` is unchanged and already an array, which Fabric API 0.92 requires.
public class SmithingRecipes implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final FabricDataOutput output;
    private final String modId;
    private final List<RecipeData> recipes = new ArrayList<>();

    public SmithingRecipes(FabricDataOutput output) {
        this.output = output;
        this.modId = "archers_expansion";
    }

    public void generate() {
        Identifier templateItem = new Identifier("armory_rpgs", "epic_armor_upgrade");
        Identifier generalCrystal = new Identifier("more_rpg_classes", "general_upgrade_crystal");
        Identifier ravagerCrystal = new Identifier("more_rpg_classes", "ravager_upgrade_crystal");

        createArmorSetUpgrade(
                "smithing_from_deadeye",
                Armors.deadeye_t1.armorSet(),
                templateItem,
                generalCrystal,
                Armors.bountyHunterArmorSet.armorSet(),
                "armory_rpgs"
        );
        createArmorSetUpgrade(
                "smithing_from_netherite_deadeye",
                Armors.netherite_deadeye.armorSet(),
                templateItem,
                generalCrystal,
                Armors.bountyHunterArmorSet.armorSet(),
                "armory_rpgs"
        );

        createArmorSetUpgrade(
                "smithing_from_tundra_hunter",
                Armors.tundra_hunter_t1.armorSet(),
                templateItem,
                ravagerCrystal,
                Armors.polarStalkerArmorSet.armorSet(),
                "armory_rpgs"
        );
        createArmorSetUpgrade(
                "smithing_from_netherite_tundra_hunter",
                Armors.netherite_tundra_hunter.armorSet(),
                templateItem,
                ravagerCrystal,
                Armors.polarStalkerArmorSet.armorSet(),
                "armory_rpgs"
        );

        createArmorSetUpgrade(
                "smithing_from_war_archer",
                Armors.war_archer_t1.armorSet(),
                templateItem,
                generalCrystal,
                Armors.sentinelArcherArmorSet.armorSet(),
                "armory_rpgs"
        );
        createArmorSetUpgrade(
                "smithing_from_netherite_war_archer",
                Armors.netherite_war_archer.armorSet(),
                templateItem,
                generalCrystal,
                Armors.sentinelArcherArmorSet.armorSet(),
                "armory_rpgs"
        );

        createSimpleArmorSetUpgrade(
                "netherite_deadeye",
                Armors.deadeye_t1.armorSet(),
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                Items.NETHERITE_INGOT,
                Armors.netherite_deadeye.armorSet()
        );
        createSimpleArmorSetUpgrade(
                "netherite_tundra_hunter",
                Armors.tundra_hunter_t1.armorSet(),
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                Items.NETHERITE_INGOT,
                Armors.netherite_tundra_hunter.armorSet()
        );
        createSimpleArmorSetUpgrade(
                "netherite_war_archer",
                Armors.war_archer_t1.armorSet(),
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                Items.NETHERITE_INGOT,
                Armors.netherite_war_archer.armorSet()
        );
    }

    // MARK: recipe building

    private void createArmorSetUpgrade(String recipeBaseName, net.spell_engine.rpg_series.item.Armor.Set baseSet,
                                       Object template, Object addition,
                                       net.spell_engine.rpg_series.item.Armor.Set resultSet, String requiredMod) {
        var name = armorSetName(resultSet);
        add(recipeBaseName + "_" + name + "_head", (Item) baseSet.head, template, addition, (Item) resultSet.head, requiredMod);
        add(recipeBaseName + "_" + name + "_chest", (Item) baseSet.chest, template, addition, (Item) resultSet.chest, requiredMod);
        add(recipeBaseName + "_" + name + "_legs", (Item) baseSet.legs, template, addition, (Item) resultSet.legs, requiredMod);
        add(recipeBaseName + "_" + name + "_feet", (Item) baseSet.feet, template, addition, (Item) resultSet.feet, requiredMod);
    }

    private void createSimpleArmorSetUpgrade(String recipeBaseName, net.spell_engine.rpg_series.item.Armor.Set baseSet,
                                             Object template, Object addition,
                                             net.spell_engine.rpg_series.item.Armor.Set resultSet) {
        var name = armorSetName(resultSet);
        add(recipeBaseName + "_" + name + "_head", (Item) baseSet.head, template, addition, (Item) resultSet.head, null);
        add(recipeBaseName + "_" + name + "_chest", (Item) baseSet.chest, template, addition, (Item) resultSet.chest, null);
        add(recipeBaseName + "_" + name + "_legs", (Item) baseSet.legs, template, addition, (Item) resultSet.legs, null);
        add(recipeBaseName + "_" + name + "_feet", (Item) baseSet.feet, template, addition, (Item) resultSet.feet, null);
    }

    private void add(String name, Item base, Object template, Object addition, Item result, String requiredMod) {
        recipes.add(new RecipeData(name, base, template, addition, result, requiredMod));
    }

    private static String armorSetName(net.spell_engine.rpg_series.item.Armor.Set armorSet) {
        var path = Registries.ITEM.getId((Item) armorSet.head).getPath();
        return path.endsWith("_head") ? path.substring(0, path.length() - 5) : path;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        recipes.clear();
        generate();
        return CompletableFuture.allOf(recipes.stream().map(data -> {
            JsonObject recipe = buildRecipeJson(data);
            // 1.20.1 datapack directory is `recipes`, not the 1.21 `recipe`.
            Path path = output.getResolver(DataOutput.OutputType.DATA_PACK, "recipes")
                    .resolveJson(new Identifier(modId, data.name));
            return DataProvider.writeToPath(writer, recipe, path);
        }).toArray(CompletableFuture[]::new));
    }

    private JsonObject buildRecipeJson(RecipeData data) {
        JsonObject recipe = new JsonObject();

        if (data.requiredMod != null) {
            JsonArray fabricConditions = new JsonArray();
            JsonObject fabricCondition = new JsonObject();
            fabricCondition.addProperty("condition", "fabric:all_mods_loaded");
            JsonArray values = new JsonArray();
            values.add(data.requiredMod);
            fabricCondition.add("values", values);
            fabricConditions.add(fabricCondition);
            recipe.add("fabric:load_conditions", fabricConditions);

            JsonArray forgeConditions = new JsonArray();
            JsonObject forgeCondition = new JsonObject();
            forgeCondition.addProperty("type", "forge:mod_loaded");
            forgeCondition.addProperty("modid", data.requiredMod);
            forgeConditions.add(forgeCondition);
            recipe.add("conditions", forgeConditions);
        }

        recipe.addProperty("type", "minecraft:smithing_transform");

        JsonObject templateObj = new JsonObject();
        templateObj.addProperty("item", itemId(data.template));
        recipe.add("template", templateObj);

        JsonObject baseObj = new JsonObject();
        baseObj.addProperty("item", Registries.ITEM.getId(data.base).toString());
        recipe.add("base", baseObj);

        JsonObject additionObj = new JsonObject();
        additionObj.addProperty("item", itemId(data.addition));
        recipe.add("addition", additionObj);

        // 1.20.1 `SmithingTransformRecipe` reads the result through `ShapedRecipe.outputFromJson`: `item` + `count`.
        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("item", Registries.ITEM.getId(data.result).toString());
        resultObj.addProperty("count", 1);
        recipe.add("result", resultObj);

        return recipe;
    }

    /// Cross-mod ingredients are passed as `Identifier`s so an absent mod does not silently resolve to air.
    private String itemId(Object itemOrId) {
        if (itemOrId instanceof Identifier id) {
            return id.toString();
        } else if (itemOrId instanceof String str) {
            return str;
        } else if (itemOrId instanceof Item item) {
            var id = Registries.ITEM.getId(item);
            if (id.equals(Registries.ITEM.getId(Items.AIR))) {
                throw new IllegalStateException("Item resolved to minecraft:air - use an Identifier for cross-mod items");
            }
            return id.toString();
        }
        throw new IllegalArgumentException("Template/Addition must be Item, Identifier or String, got: " + itemOrId.getClass());
    }

    @Override
    public String getName() {
        return "Smithing Recipes (" + modId + ")";
    }

    private record RecipeData(String name, Item base, Object template, Object addition, Item result, String requiredMod) {}
}
