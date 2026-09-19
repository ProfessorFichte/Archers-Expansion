# 3.0.0+1.20.1

> ### ⚠️ Read this before updating
>
> This release is a **major technical overhaul and is not backwards compatible.**
>
> - **Requires the matching Spell Engine and More RPG Library releases.** This version will not run
>   on Spell Engine **0.9.x**, and mods built against 0.9.x will not work alongside it.
> - **Update the whole set together.** Spell Engine, More RPG Library and every RPG Series mod must
>   be on matching versions. Mixing in an older add-on will break at startup or misbehave in play.
> - **Spell books must be re-obtained.** Spell books from an older world no longer carry valid
>   spell data. Re-craft them, or re-bind their spells at the Spell Binding Table.
>
> **Back up your world before updating.**

- Ported to Minecraft 1.20.1 (Fabric + Forge 47, no Forgified Fabric API). NeoForge is replaced by Forge on
  this line; the same Forge jar also loads on NeoForge 1.20.1.
- Requires the matching 1.20.1 releases of Spell Engine (1.10.5), Spell Power (1.6.0), More RPG Library
  (2.7.2), Armor Model API (1.0.0) and Ranged Weapon API (2.3.4), plus Archers itself.
- Every registry write goes through Forge's `RegisterEvent` window, so the mod also boots on Forge 47.0-47.3
  and on NeoForge 1.20.1, which never unlock the vanilla registries.
- The Bounty Hunter, Polar Stalker and Sentinel Archer armor is now always registered, so a server without
  Armory RPGs starts (their set bonuses used to fail to load and abort the startup). Crafting these sets
  still requires Armory RPGs.

### Accepted 1.20.1 limitations

- Choking Gas no longer reads `#minecraft:ignores_poison_and_regen` (a 1.21-only tag) to decide immunity; it
  uses the undead check vanilla's own Poison uses on this game version. Identical for vanilla mobs - only a
  modded mob that opts into that tag without being undead loses its immunity.
- Armor opts into `#minecraft:trimmable_armor` directly, replacing the 1.21-only
  `#minecraft:{head,chest,leg,foot}_armor` tags, so every set stays trimmable at a smithing table.
- 1.20.1 has no item data components: equipment-set membership is carried as item NBT instead.
  Functionally identical, but not readable by 1.21-era component tooling.
- The Armory compat sets' smithing recipes are gated with Forge's own `conditions` key rather than
  `neoforge:conditions`; a datapack that overrides those recipe files needs the same key.

# 2.1.1 - 1.21.1
- Drop Forgified Fabric API (FFAPI) as a required dependency
- Replace AzurelibArmor with ArmorModelAPI
- Updated All Spell Icon Textures (created by Slepykat)

# 2.1.0 - 1.21.1
- Adopt Spell Engine 1.10
- Thanks Daedelus!

# 2.0.0 - 1.21.1
- Adapt to Spell Engine 1.9.10+ API Changes
**Spell Expansion**
Deadeye:
- T3 Venom Cask: Throws a cask of venom that shatters on impact, deals damage to the target. Also leaves a poisonous cloud on the ground.
- T4 Alter Ego: Creates decoys and grants invisibility to the caster.
Tundra hunter:
- T3 Frozen Fusillade: Summons a barrage of ice around you, slowing nearby enemies and speeding your allies.
- T4 Polar Bearward: Summons a Polar Bear to fight by your side, empowered by your Ranged Damage. The Bear gets a short raging speed boost if its target is some distance away.
War Archer:
- T3 Explosive Barrel: Places an explosive barrel that detonates itself and other near barrels when struck or approached by an enemy, creating a huge explosion.
- T4 Scorched Earth: Fires a bolt that ignites a line of ground in front of you, dealing damage and burning enemies.
**Balancing & Internal Changes:**
- Renamed the Spell Trick Shot (id: "trick_shot") to Bouncing Arrow (id: "bouncing_arrow")
- Enchanted Crystal Arrow can now be charged, that the projectile is bigger and hits harder
- Added uk_ua localization- #18 - thx: Ch1sho
- Armory  Compat Equipment is now added to a separate Item Group
- Infiltrators Arrow, Winters Grip & Fan of Fire are removed and will soon be available in the LNE Archers Mod!

# 1.5.1 - 1.21.1
- Fix Crash with Enchanted Crystal Arrow, due to wrong projectile model_id

# 1.5.0 - 1.21.1
**Update to use Spell Engine 1.9.0**
- DISCLAIMER: All spell books and spell scrolls will be reset, due to major API changes.
- All Archer Expansion Spell Books now offers 3 spells only, to match other classes
- **DEADEYE:** Fast Shot & Trick Shot are now T2-Spell Choices
- **TUNDRA HUNTER:** Frozen Shot & Frozen Pact are now T2-Spell Choices
- **WAR ARCHER:** Dual Shot & Smoldering Arrows are now T2-Spell Choices
**Internal Changes & Functional Additions**
- Added Entities & Custom Spell-Deliveries for future Spell Choices

# 1.4.6 - 1.21.1
- Update to newest MRPG Lib Version

# 1.4.5 - 1.21.1
- fix missing model part for Bounty Hunter Armor Set (Epic Deadeye Armor Set)
- remove additional damage logic for choking gas if the entity is bleeding 

# 1.4.4 - 1.21.1
- give Choking Gas Poison Effect better damage scaling
- fix wrong Upgrade Crystal -> General's Lost Crystal is the correct

# 1.4.3 - 1.21.1
**Add T5 Armory Compat Sets for each Archer Class!**
- Bounty Hunter Armor Set -> DeadEye
- Polar Stalker Armor Set -> Tundra Hunter
- Sentinel Archer Armor Set > War Archer
- Migrate Recipes To Datagen
- Small Fixes

# 1.4.2 - 1.21.1
- nerf some ranged damage & haste values
- remove knockback resistance from war archer armor and go for armor toughness on T3
- add optional Critical Chance & Damage Attribute compat for War Archer and Deadeye Armor Sets

# 1.4.1 - 1.21.1
- update azurelib

# 1.4.0 - 1.21.1
- NeoForge Beta!
- Changed Spell Book Textures to be more in line with the RPG-Series Books (by SirGhaith)
- Improve Armor Set Models (by Slepykat)
- License changed to ARR

# 1.3.11 - 1.21.1
- forgot to add Invis Mixin's to Mixin file for Infiltrator's Stealth
- Delete Spell Schools here because they're now provided by MRPG Lib
- Add Infiltrator's Speed Effect for the Skill Tree Add On

# 1.3.10 - 1.21.1
- fix loot table console error (Again)
- Infiltrators Arrow, Fan of Fire & Winters Grip are now T4 Spells (Only important if LNE-Archers is installed)
- add spell Datagen
- Point Blank Shot & Disabling Shot now use Custom Spell Impacts instead of Status Effects
- Add ru_ru lang file (Thanks Strelok656)
- Deadeye Armor Sets now give the new Evasion Attribute
- War Archer Netherite Armor Set now gives Ranged Velocity Attribute instead of Armor Toughness
- Choking Gas now uses Spell Engine Particles for the Poison Smoke
- Frozen Pact-, CrystalArrow- and WintersGrasp Effect now stack frozen ticks
- already added Armory Compat Code (just awaiting Armor Model & Texture for release)

# 1.3.8 - 1.21.1
- implemented FROST_RANGED again and also FIRE_RANGED Spell Schools 
- they benefit from frost and fire spell power besides ranged weapon damage
- fix smoldering arrow
- removed movement speed from the tundra hunter and added frost spell power
- all tundra hunter spells are now FROST_RANGED
- all war archer spells are now FIRE_RANGED
- this re-balancing was made to make the two sub-classes more unique and support future content better
- fix issue with a loot table

# 1.3.7 - 1.21.1
- fix some target modifiers in Passive Spell Impact
- increase fast shot cooldown a bit

# 1.3.6 - 1.21.1
- update to newest spell engine api
- change item group icon to war archer armor head

# 1.3.5 - 1.21.1
- fix errors in structure tags & loot tables

# 1.3.4 - 1.21.1
- Arctic Volley now acts like a actual volley
- Add All Assets, Data & Code for the LNE-Compat & Treasure Spells

# 1.3.3 - 1.21.1
- fix lang file because of some outdated description GitHub Issue #4
- add es_ar translation
- Add armor meta type tags

# 1.3.2 - 1.21.1
- Add spell Scrolls
- Add smelting recipes for disassembling weapons and armor pieces
- Add datagen

# 1.3.1 - 1.21.1
- Spell Engine 1.6 Update

# 1.3.0 - 1.21.1
- Spell Engine 1.5 Update
- Update to Fabric Loom 1.9
- Buffed Smoldering Arrow
- Buffed Arctic Volley

# 1.2.1 - 1.21.1
- Disabling Shot, Pin Down & Point Blank Shot does not work on "#c:bosses"
- Trick Shots Bleeding does not work on "#minecraft:undead"
- Choking Gas does not work on"#minecraft:ignores_poison_and_regen"
- Arctic Volley, Enchanted Crystal Arrow, Frozen pact and Frozen Shot Status Effects do not work on  "#minecraft:freeze_immune_entity_types"

# 1.2.0 - 1.21.1
**- Spell Engine 1.4 Update**
- AzureLib Armor 3.0 Update
- Armor Rebalancing
- fixed CrystalArrowEffect & FrozenPactEffect & ChokingGasEffect
- Fast Shot now only gives Ranged Haste and no Movement Speed
- The Movement Speed Buffs will be moved to the Deadeye´s Armor Parts
- Removed the FROST_RANGED Spell School and all Frost Spell Power Attributes from the Tundra Hunters Armor
- All Tundra Hunter Spells are now PHYSICAL_RANGED like all the other Archery Spells
- "#minecraft:freeze_hurts_extra_types" get 30% more damage from Tundra Hunter Spells like the Wizard Frost Spells
- "#minecraft:freeze_immune_entity_types" get 30% less damage from Tundra Hunter Spells like the Wizard Frost Spells
- Item Config Changed to file "items_v1"

1.1.1 - 1.21.1
- Spell Scroll Update
- Renamed the Bounty List to Deadeye´s List ( Spell Book )

1.1.0 - 1.21.1
- Spell Engine 1.2 Update
- Smoldering Arrow, Frozen Shot, Fast Shot got reworked, they use the new SpellStash Feature
- all arrow spells now have special models & travel particle visuals
- Poison Immune Entites are now also immune to the Choking Gas Effect
- changed the Choking Gas Spell a bit

1.0.2 - 1.21.1
- #3 Not compatible with 1.21 while it says it is on Modrinth #3 fix
- forgot enchantment item tags for the armor sets

1.0.1 - 1.21.1
- forgot the netherite armor recipes (saved them in the 1.20.1 workspace)

1.0.0 - 1.21.1
- Official Release!
- Added Netherite Tier Armor