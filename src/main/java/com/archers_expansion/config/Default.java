package com.archers_expansion.config;

import com.archers_expansion.items.armors.Armors;
import net.spell_engine.api.config.ConfigFile;

public class Default {
    public static final ConfigFile.Equipment itemConfig;
    static {
        itemConfig = new ConfigFile.Equipment();
        for (var armorSet : Armors.entries) {
            itemConfig.armor_sets.put(armorSet.name(), armorSet.defaults());
        }
    }
}
