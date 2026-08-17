package com.archers_expansion.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class Sounds {
    public record Entry(Identifier id, SoundEvent soundEvent, int variants) {}

    public static final List<Entry> entries = new ArrayList<>();

    private static Entry add(String name, int variants) {
        var id = Identifier.of(MOD_ID, name);
        var soundEvent = SoundEvent.of(id);
        var entry = new Entry(id, soundEvent, variants);
        entries.add(entry);
        return entry;
    }

    public static final Entry TRICK_SHOT = add("trick_shot", 1);
    public static final Entry SPECIAL_SHOT = add("special_shot", 1);
    public static final Entry CRYSTAL_ARROW_IMPACT = add("enchanted_crystal_arrow_impact", 1);
    public static final Entry PIN_DOWN = add("pin_down", 1);
    public static final Entry POINT_BLANK_SHOT = add("point_blank_shot", 1);
    public static final Entry POISON_CLOUD = add("poison_cloud", 1);
    public static final Entry INFILTRATOR_VANISH = add("infiltrator_vanish", 1);
    public static final Entry SCORCHED_EARTH_IGNITE = add("scorched_earth_ignite", 1);
    public static final Entry POLARBEAR_SPAWN = add("polarbear_spawn", 1);
    public static final Entry POLARBEAR_IDLE = add("polarbear_idle", 1);
    public static final Entry POLARBEAR_SWING_IMPACT = add("polarbear_swing_impact", 1);
    public static final Entry POLARBEAR_SWING = add("polarbear_swing", 1);
    public static final Entry POLARBEAR_DEATH = add("polarbear_death", 1);
    public static final Entry BARREL_EXPLOSION = add("barrel_explosion", 1);
    public static final Entry ALTER_EGO_VANISH = add("alter_ego_vanish", 1);
    public static final Entry ALTER_EGO_EXPLOSION = add("alter_ego_explosion", 1);
    public static final Entry VENOM_CASK_THROW = add("venom_cask_throw", 1);
    public static final Entry VENOM_CASK_LAND = add("venom_cask_land", 1);

    public static void register() {
        for (var entry : entries) {
            Registry.register(Registries.SOUND_EVENT, entry.id(), entry.soundEvent());
        }
    }
}
