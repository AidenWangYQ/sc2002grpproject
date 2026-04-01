package com.sc2002.arena.engine;

import com.sc2002.arena.model.combatant.Player;
 
/**
 * Manages special-skill cooldown ticking for player combatants.
 *
 * SRP  – sole responsibility: tick cooldowns at the correct time.

 *   - Cooldown ticks ONLY when the player successfully takes their turn.
 *   - Stunned players who skip their turn do NOT tick down their cooldown.
 *   - BattleEngine calls tickCooldown() immediately after the player acts,
 *     NOT at end of round.
 */

public class CooldownManager {

    public void tickCooldown(Player player) {
        player.tickCooldown();
    }

    public void startCooldown(Player player) {
        player.setSkillCooldown(Player.SKILL_COOLDOWN);
    }

    public boolean isSkillReady(Player player) {
        return player.isSkillReady();
    }

    public void printCooldownStatus(Player player) {
        if (player.isSkillReady()) {
            System.out.printf("    [%s special skill: READY]%n", player.getName());
        } else {
            System.out.printf("    [%s special skill cooldown: %d round(s)]%n",
                    player.getName(), player.getSkillCooldown());
        }
    }
}
 