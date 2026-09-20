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

- Thanks to Daedelus for the PR!
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
