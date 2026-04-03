package combat.ui;

import java.util.Scanner;

class ShieldBash implements Special_skill {

    public String getName() {
        return "Shield Bash";
    }

    public String getDescription() {
        return "Deal basic attack damage to one enemy and stun it for 2 turns.";
    }

    public void use() {
        System.out.println("Using Shield Bash...");
    }
}
interface Special_skill{
    String getName();
    String getDescription();
    void use();
}
class ArcaneBlast implements Special_skill {


    public String getName() {
        return "Arcane Blast";
    }

    public String getDescription() {
        return "Deal damage to all enemies. Gain +10 attack per enemy defeated.";
    }

    public void use() {
        // placeholder
        System.out.println("Using Arcane Blast...");
    }
}

interface Players{
    public void Special_skill();
    public void getinfo();

}
interface Items{
    public String getitemName();
    public void useitem(character chosen);
}
interface Enemy{
    public void getEnemyinfo();
}
class Goblin implements Enemy{
    int HP =55;
    int attack=35;
    int defense=15;
    int speed=25;
    public void getEnemyinfo(){
        System.out.println("The Goblin has the following attributes:");
        System.out.println("HP:" + this.HP);
        System.out.println("Attack:" + this.attack);
        System.out.println("Defense:" + this.defense);
        System.out.println("Speed:" + this.speed);
    }
}
class Wolf implements Enemy{
    int HP =40;
    int attack=45;
    int defense=5;
    int speed=35;
    public void getEnemyinfo(){
        System.out.println("The Wolf has the following attributes:");
        System.out.println("HP:" + this.HP);
        System.out.println("Attack:" + this.attack);
        System.out.println("Defense:" + this.defense);
        System.out.println("Speed:" + this.speed);
    }
}
class Warrior implements Players {
    int hp = 260;
    int attack = 40;
    int Defense = 20;
    int Speed = 30;

    public void Special_skill() {
        System.out.println("Special Skill: Shield Bash");
        System.out.println("Shield Bash Effect: Deal Basic Attack damage to selected enemy. Selected enemy is unable to take actions for the current turn and the next turn");
    }

    public void getinfo() {
        System.out.println("HP:" + this.hp);
        System.out.println("Attack:" + this.attack);
        System.out.println("Defense:" + this.Defense);
        System.out.println("Speed:" + this.Speed);
    }
}
class Wizard implements Players{
    int hp=200;
    int attack=50;
    int Defense=10;
    int Speed=20;
    public void Special_skill(){
        System.out.println("Special Skill: Arcane Blast");
        System.out.println("Arcane Blast Effect: Deal Basic Attack damage to all enemies currently in combat. Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until end of the level");
    }
    public void getinfo(){
        System.out.println("HP:" + this.hp);
        System.out.println("Attack:" + this.attack);
        System.out.println("Defense:" + this.Defense);
        System.out.println("Speed:" + this.Speed);
    }

}
class character {
    int hp;
    int attack;
    int Defense;
    int Speed;
    String cla;
    Special_skill special_skill;
    Items[] items=new Items[2];
    int Itemcount=0;
    int difficulty=0;
    public character(int hp, int attack, int Defense, int Speed,Special_skill skill){
        this.hp=hp;
        this.attack=attack;
        this.Defense=Defense;
        this.Speed=Speed;
        this.special_skill=skill;
    }
    public void setcla(String cla){
        this.cla=cla;
    }
    public String getcla(){
        return this.cla;
    }
    public void addItem(Items item) {
        if (Itemcount < 2) {
            items[Itemcount] = item;
            Itemcount++;
        }
    }
    public void showItems() {
        for (int i = 0; i < Itemcount; i++) {
            System.out.println((i+1) + ". " + items[i].getitemName());
        }
    }
    public void setDifficulty(int difficulty){
        this.difficulty=difficulty;
    }

}
class SmokeBomb implements Items {
    public String getitemName() {
        return "Smoke Bomb";
    }

    public void useitem(character user) {
        System.out.println("Enemies deal 0 damage for 2 turns!");
    }
}
class Powerstone implements Items {
    public String getitemName() {
        return "Powerstone";
    }
    public void useitem(character chosen){
        System.out.println("Special skill triggered without cooldown!");
        chosen.special_skill.use();
    }
}
class Potion implements Items {
    public String getitemName() {
        return "Potion";
    }

    public void useitem(character chosen) {
        if (chosen.Speed==30){
            chosen.hp = Math.min(chosen.hp + 100, 260);
            System.out.println("Healed 100 HP!");
        }
        else{
            chosen.hp = Math.min(chosen.hp + 100, 200);
            System.out.println("Healed 100 HP!");
        }

    }
}
public class Menu {
    public static void main(String[] arg) {
        Scanner scan = new Scanner(System.in);
        Warrior warrior = new Warrior();
        Wizard wizard = new Wizard();
        System.out.println("Welcome to Turn-based combat arena");
        System.out.println("-----------------------------");
        System.out.println("(1) for Warrior");
        warrior.getinfo();
        warrior.Special_skill();
        System.out.println("(2) for Wizard");
        wizard.getinfo();
        wizard.Special_skill();
        System.out.println("Please select your character, with 1 for Warrior and 2 for Wizard");
        int choice;
        character chosen;
        while (true) {
            System.out.print("Enter 1 or 2: ");

            choice = scan.nextInt();

            if (choice == 1 || choice == 2) {
                break; // valid input → exit loop
            }

            System.out.println("Invalid input, please try again.");
        }
        if (choice == 1) {
            System.out.println("You have chosen the Warrior Class");
            Special_skill skill = new ShieldBash();
            chosen = new character(260, 40, 20, 30, skill);
            chosen.setcla("Warrior");
        } else {
            System.out.println("You have chosen the Wizard Class");
            Special_skill skill = new ArcaneBlast();
            chosen = new character(200, 50, 10, 20, skill);
            chosen.setcla("Wizard");
        }
        System.out.println("Please choose 2 of the following items, (1) for Potion, (2) for Powerstone and (3) for Smoke bomb.");
        System.out.println("Potion: When used, Heal 100 HP");
        System.out.println("PowerStone: Trigger the special skill effect once without utilizing the cooldown timer");
        System.out.println("Smoke Bomb: When used, Enemy attacks do 0 damage in the current turn and the next turn");
        int times = 0;
        while (times < 2) {
            int choose = scan.nextInt();
            if (choose == 1) {
                System.out.println("You have chosen Potion!");
                chosen.addItem(new Potion());
                times = times + 1;
            } else if (choose == 2) {
                System.out.println("You have chosen PowerStone!");
                chosen.addItem(new Powerstone());
                times = times + 1;
            } else if (choose == 3) {
                System.out.println("You have chosen Smoke Bomb!");
                chosen.addItem(new SmokeBomb());
                times = times + 1;
            } else {
                System.out.println("Please enter a proper number");
            }
        }
        System.out.println("Choose your level of difficulty");
        System.out.println("---------------------------------");
        System.out.println("Level 1 (Easy), Initial Spawn: 3 Goblins");
        System.out.println("Level 2 (Medium), Initial Spawn: 1 Goblin 1 Wolf, Backup Spawn: 2 Wolves");
        System.out.println("Level 3 (Hard), Initial Spawn: 2 Goblins, Backup Spawn: 1 Goblin 2 Wolves");
        System.out.print("Choose 1 to list enemy attributes, 2 to choose level of difficulty");
        choice = scan.nextInt();
        while (choice < 0 || choice > 2) {
            System.out.println("Please choose again");
            choice = scan.nextInt();
        }
        if (choice == 1) {
            Enemy wolf = new Wolf();
            Enemy goblin = new Goblin();
            wolf.getEnemyinfo();
            goblin.getEnemyinfo();
        }
        System.out.print("Choose your level of difficulty:");
        int difficulty = scan.nextInt();
        while (difficulty < 0 || difficulty > 3) {
            System.out.println("Please choose a difficulty from 0 to 3");
        }
        chosen.setDifficulty(difficulty);
        System.out.println("You have chosen difficulty level: " + chosen.difficulty);
    }
}


