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

    public static void register() {
        for (var entry : entries) {
            Registry.register(Registries.SOUND_EVENT, entry.id(), entry.soundEvent());
        }
    }
}
