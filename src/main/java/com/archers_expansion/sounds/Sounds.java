package com.archers_expansion.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class Sounds {
    public static final Identifier TRICK_SHOT_ID = Identifier.of(MOD_ID, "trick_shot");
    public static SoundEvent TRICK_SHOT_EVENT= SoundEvent.of(TRICK_SHOT_ID);
    public static final Identifier SPECIAL_SHOT_ID = Identifier.of(MOD_ID, "special_shot");
    public static SoundEvent SPECIAL_SHOT_EVENT= SoundEvent.of(SPECIAL_SHOT_ID);
    public static final Identifier CRYSTAL_ARROW_ID = Identifier.of(MOD_ID, "enchanted_crystal_arrow_impact");
    public static SoundEvent CRYSTAL_ARROW_EVENT= SoundEvent.of(CRYSTAL_ARROW_ID);
    public static final Identifier PIN_DOWN_ID = Identifier.of(MOD_ID, "pin_down");
    public static SoundEvent PIN_DOWN_EVENT= SoundEvent.of(PIN_DOWN_ID);
    public static final Identifier POINT_BLANK_SHOT_ID = Identifier.of(MOD_ID, "point_blank_shot");
    public static SoundEvent POINT_BLANK_SHOT_EVENT= SoundEvent.of(POINT_BLANK_SHOT_ID);
    public static final Identifier POISON_CLOUD_ID = Identifier.of(MOD_ID, "poison_cloud");
    public static SoundEvent POISON_CLOUD_EVENT= SoundEvent.of(POISON_CLOUD_ID);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, TRICK_SHOT_ID, TRICK_SHOT_EVENT);
        Registry.register(Registries.SOUND_EVENT, SPECIAL_SHOT_ID, SPECIAL_SHOT_EVENT);
        Registry.register(Registries.SOUND_EVENT, CRYSTAL_ARROW_ID, CRYSTAL_ARROW_EVENT);
        Registry.register(Registries.SOUND_EVENT, PIN_DOWN_ID, PIN_DOWN_EVENT);
        Registry.register(Registries.SOUND_EVENT, POINT_BLANK_SHOT_ID, POINT_BLANK_SHOT_EVENT);
        Registry.register(Registries.SOUND_EVENT, POISON_CLOUD_ID, POISON_CLOUD_EVENT);
    }
}
