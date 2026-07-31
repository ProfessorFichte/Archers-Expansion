package com.archers_expansion.entity;

import net.spell_engine.internals.target.EntityRelations;

public class ArchersTeamMatcher {
    public static void register() {

        EntityRelations.registerTeamMatcher("archers_expansion_alter_ego", (entity1, entity2) -> {
            if (entity2 instanceof AlterEgoEntity alterEgo) {
                var owner = alterEgo.getOwner();
                if (owner != null && owner == entity1) {
                    return new EntityRelations.TeamRelation(true, false);
                }
            }
            return null;
        });
    }
}
