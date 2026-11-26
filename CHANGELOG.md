# Changelog

All notable changes to Archers Expansion are documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [2.2.2+1.21.1]

### Fixed

- Sound effects are now mono and 48 kHz, so they come from the right direction and get quieter with distance like vanilla sounds

## [2.2.1+1.21.1]

### Changed

- Venom Cask and Frozen Fusillade models now shrink out smoothly at the real end of the cloud, also when skill tree nodes make the cloud last longer

### Fixed

- Fixed Barrel and Alter Ego decoy explosions ignoring range bonuses, they now use the boosted range for the actual hit area
- Fixed the Venom Cask cloud model disappearing about a second early, it now stays visible for the whole lifetime of the cloud
- Fixed Frozen Fusillade spamming "Failed to play sound" errors in the log on every tick, its cloud now uses a sound that actually exists

## [2.2.0+1.21.1]

### Changed

- Adopt Spell Engine 1.10.9 & More RPG Library 2.8.0
- **BREAKING for modpack/datapack developers:** More RPG Library's namespace was renamed `more_rpg_classes` -> `mrpg_lib` (and its advancement root moved to `mrpg_lib:root`). If you have any data files, loot tables, tags or advancements that reference the old `more_rpg_classes:`/`more_rpg_content:` ids from this mod or MRPGL, update them to `mrpg_lib:`
- Updated internal advancement parents, mod dependency declarations (fabric.mod.json, neoforge.mods.toml)
- Arches Expansion equipment is now affiliated loot: it drops more often for players carrying the matching spell book

## [2.1.1+1.21.1]

### Changed

- Drop Forgified Fabric API (FFAPI) as a required dependency
- Replace AzurelibArmor with ArmorModelAPI
- Updated All Spell Icon Textures (created by Slepykat)

## [2.1.0+1.21.1]

### Changed

- Adopt Spell Engine 1.10
- Thanks Daedelus!

## [2.0.0+1.21.1]

**Spell Expansion**
Deadeye:
Tundra hunter:
War Archer:
**Balancing & Internal Changes:**

### Added

- Added uk_ua localization- #18 - thx: Ch1sho

### Changed

- Adapt to Spell Engine 1.9.10+ API Changes
- T3 Venom Cask: Throws a cask of venom that shatters on impact, deals damage to the target. Also leaves a poisonous cloud on the ground.
- T4 Alter Ego: Creates decoys and grants invisibility to the caster.
- T3 Frozen Fusillade: Summons a barrage of ice around you, slowing nearby enemies and speeding your allies.
- T4 Polar Bearward: Summons a Polar Bear to fight by your side, empowered by your Ranged Damage. The Bear gets a short raging speed boost if its target is some distance away.
- T3 Explosive Barrel: Places an explosive barrel that detonates itself and other near barrels when struck or approached by an enemy, creating a huge explosion.
- T4 Scorched Earth: Fires a bolt that ignites a line of ground in front of you, dealing damage and burning enemies.
- Renamed the Spell Trick Shot (id: "trick_shot") to Bouncing Arrow (id: "bouncing_arrow")
- Enchanted Crystal Arrow can now be charged, that the projectile is bigger and hits harder
- Armory  Compat Equipment is now added to a separate Item Group
- Infiltrators Arrow, Winters Grip & Fan of Fire are removed and will soon be available in the LNE Archers Mod!

## [1.5.1+1.21.1]

### Fixed

- Fix Crash with Enchanted Crystal Arrow, due to wrong projectile model_id

## [1.5.0+1.21.1]

**Update to use Spell Engine 1.9.0**
**Internal Changes & Functional Additions**

### Added

- Added Entities & Custom Spell-Deliveries for future Spell Choices

### Changed

- DISCLAIMER: All spell books and spell scrolls will be reset, due to major API changes.
- All Archer Expansion Spell Books now offers 3 spells only, to match other classes
- **DEADEYE:** Fast Shot & Trick Shot are now T2-Spell Choices
- **TUNDRA HUNTER:** Frozen Shot & Frozen Pact are now T2-Spell Choices
- **WAR ARCHER:** Dual Shot & Smoldering Arrows are now T2-Spell Choices

## [1.4.6+1.21.1]

### Changed

- Update to newest MRPG Lib Version

## [1.4.5+1.21.1]

### Removed

- remove additional damage logic for choking gas if the entity is bleeding 

### Fixed

- fix missing model part for Bounty Hunter Armor Set (Epic Deadeye Armor Set)

## [1.4.4+1.21.1]

### Changed

- give Choking Gas Poison Effect better damage scaling

### Fixed

- fix wrong Upgrade Crystal -> General's Lost Crystal is the correct

## [1.4.3+1.21.1]

**Add T5 Armory Compat Sets for each Archer Class!**

### Changed

- Bounty Hunter Armor Set -> DeadEye
- Polar Stalker Armor Set -> Tundra Hunter
- Sentinel Archer Armor Set > War Archer
- Migrate Recipes To Datagen
- Small Fixes

## [1.4.2+1.21.1]

### Added

- add optional Critical Chance & Damage Attribute compat for War Archer and Deadeye Armor Sets

### Changed

- nerf some ranged damage & haste values

### Removed

- remove knockback resistance from war archer armor and go for armor toughness on T3

## [1.4.1+1.21.1]

### Changed

- update azurelib

## [1.4.0+1.21.1]

### Changed

- NeoForge Beta!
- Changed Spell Book Textures to be more in line with the RPG-Series Books (by SirGhaith)
- Improve Armor Set Models (by Slepykat)
- License changed to ARR

## [1.3.11+1.21.1]

### Added

- Add Infiltrator's Speed Effect for the Skill Tree Add On

### Changed

- forgot to add Invis Mixin's to Mixin file for Infiltrator's Stealth
- Delete Spell Schools here because they're now provided by MRPG Lib

## [1.3.10+1.21.1]

### Added

- add spell Datagen
- Add ru_ru lang file (Thanks Strelok656)

### Changed

- Infiltrators Arrow, Fan of Fire & Winters Grip are now T4 Spells (Only important if LNE-Archers is installed)
- Point Blank Shot & Disabling Shot now use Custom Spell Impacts instead of Status Effects
- Deadeye Armor Sets now give the new Evasion Attribute
- War Archer Netherite Armor Set now gives Ranged Velocity Attribute instead of Armor Toughness
- Choking Gas now uses Spell Engine Particles for the Poison Smoke
- Frozen Pact-, CrystalArrow- and WintersGrasp Effect now stack frozen ticks
- already added Armory Compat Code (just awaiting Armor Model & Texture for release)

### Fixed

- fix loot table console error (Again)

## [1.3.8+1.21.1]

### Changed

- implemented FROST_RANGED again and also FIRE_RANGED Spell Schools 
- they benefit from frost and fire spell power besides ranged weapon damage
- all tundra hunter spells are now FROST_RANGED
- all war archer spells are now FIRE_RANGED
- this re-balancing was made to make the two sub-classes more unique and support future content better

### Removed

- removed movement speed from the tundra hunter and added frost spell power

### Fixed

- fix smoldering arrow
- fix issue with a loot table

## [1.3.7+1.21.1]

### Changed

- increase fast shot cooldown a bit

### Fixed

- fix some target modifiers in Passive Spell Impact

## [1.3.6+1.21.1]

### Changed

- update to newest spell engine api
- change item group icon to war archer armor head

## [1.3.5+1.21.1]

### Fixed

- fix errors in structure tags & loot tables

## [1.3.4+1.21.1]

### Added

- Add All Assets, Data & Code for the LNE-Compat & Treasure Spells

### Changed

- Arctic Volley now acts like a actual volley

## [1.3.3+1.21.1]

### Added

- add es_ar translation
- Add armor meta type tags

### Fixed

- fix lang file because of some outdated description GitHub Issue #4

## [1.3.2+1.21.1]

### Added

- Add spell Scrolls
- Add smelting recipes for disassembling weapons and armor pieces
- Add datagen

## [1.3.1+1.21.1]

### Changed

- Spell Engine 1.6 Update

## [1.3.0+1.21.1]

### Changed

- Spell Engine 1.5 Update
- Update to Fabric Loom 1.9
- Buffed Smoldering Arrow
- Buffed Arctic Volley

## [1.2.1+1.21.1]

### Changed

- Disabling Shot, Pin Down & Point Blank Shot does not work on "#c:bosses"
- Trick Shots Bleeding does not work on "#minecraft:undead"
- Choking Gas does not work on"#minecraft:ignores_poison_and_regen"
- Arctic Volley, Enchanted Crystal Arrow, Frozen pact and Frozen Shot Status Effects do not work on  "#minecraft:freeze_immune_entity_types"

## [1.2.0+1.21.1]

**- Spell Engine 1.4 Update**
1.1.1 - 1.21.1
1.1.0 - 1.21.1
1.0.2 - 1.21.1
1.0.1 - 1.21.1
1.0.0 - 1.21.1

### Added

- Added Netherite Tier Armor

### Changed

- AzureLib Armor 3.0 Update
- Armor Rebalancing
- Fast Shot now only gives Ranged Haste and no Movement Speed
- The Movement Speed Buffs will be moved to the Deadeye´s Armor Parts
- All Tundra Hunter Spells are now PHYSICAL_RANGED like all the other Archery Spells
- "#minecraft:freeze_hurts_extra_types" get 30% more damage from Tundra Hunter Spells like the Wizard Frost Spells
- "#minecraft:freeze_immune_entity_types" get 30% less damage from Tundra Hunter Spells like the Wizard Frost Spells
- Item Config Changed to file "items_v1"

- Spell Scroll Update
- Renamed the Bounty List to Deadeye´s List ( Spell Book )

- Spell Engine 1.2 Update
- Smoldering Arrow, Frozen Shot, Fast Shot got reworked, they use the new SpellStash Feature
- all arrow spells now have special models & travel particle visuals
- Poison Immune Entites are now also immune to the Choking Gas Effect
- changed the Choking Gas Spell a bit

- #3 Not compatible with 1.21 while it says it is on Modrinth #3 fix
- forgot enchantment item tags for the armor sets

- forgot the netherite armor recipes (saved them in the 1.20.1 workspace)

- Official Release!

### Removed

- Removed the FROST_RANGED Spell School and all Frost Spell Power Attributes from the Tundra Hunters Armor

### Fixed

- fixed CrystalArrowEffect & FrozenPactEffect & ChokingGasEffect
