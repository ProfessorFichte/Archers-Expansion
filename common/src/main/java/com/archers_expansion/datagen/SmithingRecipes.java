package com.archers_expansion.datagen;

import com.archers_expansion.items.Armors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.datagen.SmithingRecipeGenerator;

public class SmithingRecipes extends SmithingRecipeGenerator {

    public SmithingRecipes(FabricDataOutput output) {
        super(output, "archers_expansion");
    }

    @Override
    public void generate() {
        Identifier templateItem = Identifier.of("armory_rpgs", "epic_armor_upgrade");
        Identifier generalCrystal = Identifier.of("more_rpg_classes", "general_upgrade_crystal");
        Identifier ravagerCrystal = Identifier.of("more_rpg_classes", "ravager_upgrade_crystal");

        // ==========================================
        // BOUNTY HUNTER UPGRADES (Deadeye -> Bounty Hunter)
        // ==========================================
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

        // ==========================================
        // POLAR STALKER UPGRADES (Tundra Hunter -> Polar Stalker)
        // ==========================================
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

        // ==========================================
        // SENTINEL ARCHER UPGRADES (War Archer -> Sentinel Archer)
        // ==========================================
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

        // ==========================================
        // NETHERITE UPGRADES (T1 -> Netherite) - WITHOUT Load Conditions
        // ==========================================
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
}
