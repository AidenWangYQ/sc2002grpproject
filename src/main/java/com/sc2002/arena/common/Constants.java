package com.sc2002.arena.common;

public final class Constants {

    private Constants() {
        // Prevent instantiation
    }

    public static final class PlayerStats {
        public static final int WARRIOR_HP = 260;
        public static final int WARRIOR_ATTACK = 40;
        public static final int WARRIOR_DEFENSE = 20;
        public static final int WARRIOR_SPEED = 30;

        public static final int WIZARD_HP = 200;
        public static final int WIZARD_ATTACK = 50;
        public static final int WIZARD_DEFENSE = 10;
        public static final int WIZARD_SPEED = 20;

        private PlayerStats() {}
    }

    public static final class EnemyStats {
        public static final int GOBLIN_HP = 55;
        public static final int GOBLIN_ATTACK = 35;
        public static final int GOBLIN_DEFENSE = 15;
        public static final int GOBLIN_SPEED = 25;

        public static final int WOLF_HP = 40;
        public static final int WOLF_ATTACK = 45;
        public static final int WOLF_DEFENSE = 5;
        public static final int WOLF_SPEED = 35;

        private EnemyStats() {}
    }

    public static final class Effects {
        public static final int DEFEND_BONUS = 10;
        public static final int DEFEND_DURATION_TURNS = 2;
        public static final int STUN_DURATION_TURNS = 2;
        public static final int SMOKE_BOMB_DURATION_TURNS = 2;
        public static final int ARCANE_BLAST_KILL_BONUS = 10;

        private Effects() {}
    }

    public static final class Items {
        public static final int POTION_HEAL_AMOUNT = 100;

        private Items() {}
    }

    public static final class Skills {
        public static final int SPECIAL_COOLDOWN_TURNS = 3;

        private Skills() {}
    }
}