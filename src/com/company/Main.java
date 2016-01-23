package com.company;

//import my things


import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

//Open main class
public class Main
{
    //TODO:  Consider switching toFile to a PrintStream

    //Define global things and things used in functions other than main.

    public static final int SIDES_PER_DIE = 6;
    //Defines the number of sides on the "die" rolled to determine things.
    public static final int CRITICAL_HIT_THRESHOLD = 6;
    //If an attack roll is greater than or equal to this number, that attack is a critical hit.
    public static final int CRITICAL_MISS_THRESHOLD = 1;
    //If an attack roll is less than or equal to this number, that attack is a critical miss.

    protected static final int CRITICAL_HIT_FLAG = -1;
    protected static final int CRITICAL_MISS_FLAG = -2;
    //Unrollable numbers used internally by the dice roller, to represent critical hits/misses.

    public static final Scanner console = new Scanner(System.in);

    private static boolean userReturned = false;

    public static Map<String, Character> charactersMap = new HashMap<>();
    @NotNull
    protected static Character playerCharacter;
    @NotNull
    private static Character GaryOak;

    //Main function
    public static void main(String[] args)
    {
        //Define my File
        File storeCharacters;

        //Define/initialize my PrintWriter and initialize my map.
        PrintWriter toFile;

        //Made in the try/catch because java made me and it should stop errors? I hope?
        try
        {
            //Initialize my file, create my file, and initialize my Scanner + PrintWriter
            storeCharacters = new File("..\\characters.txt");
            if (!storeCharacters.exists())
            {
                //noinspection ResultOfMethodCallIgnored
                storeCharacters.createNewFile();
                GaryOak = new Character(4, 4, 4, "none", "Adversary");
                charactersMap.put("Adversary", GaryOak);
            }
            toFile = new PrintWriter(new BufferedWriter(new FileWriter(storeCharacters, true)));
            readFile(storeCharacters);
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        GaryOak = charactersMap.get("Adversary");
        playerCharacter = GaryOak;

        //Define my needed stuff some more
        int action = 0;

        System.out.println("Hi! This is my version of the Name Tag Game. It's very bare-bones at the moment.");
        while (action != 5)
        {
            System.out.println("Do you want to:");
            System.out.println("| Create A New Character (1) |\n| Load A Character (2) |\n| Fight A Character (3) |\n| View Characters (4) |\n| Exit (5) |");
            System.out.print("Please enter the number of your wanted action: ");

            try
            {
                action = promptForNumber();
            } catch (NumberFormatException e)
            {
                action = 0;
            }

            switch (action) {
                case 1: {
                    createCharacter();
                }
                break;
                case 2: {
                    Character p = getCharacter("load");
                    if (p == GaryOak && !userReturned) {
                        System.out.println("You can't load this character at this time.\n");
                    } else {
                        playerCharacter = p;
                    }
                }
                break;
                case 3: {
                    if (playerCharacter != GaryOak) {
                        @Nullable Character enemy = getCharacter("fight");
                        if (enemy != playerCharacter) {
                            Duel(playerCharacter, enemy);
                        } else {
                            System.out.println("You can't fight yourself!\n");
                        }
                    } else {
                        System.out.println("I'm sorry, please load or create a character.\n");
                    }
                }
                break;
                case 4: {
                    Character p = getCharacter("view");
                    System.out.println("\t:::STATS:::");
                    System.out.println("NAME: " + p.getCharacterName());
                    System.out.println("CLASS: " + p.getCharacterClass());
                    System.out.println("ATK: " + p.getAttackStat());
                    System.out.println("DEF: " + p.getDefenseStat());
                    System.out.println("SPD: " + p.getSpeedStat());
                    System.out.println("LVL: " + p.getCharacterLevel());
                    System.out.println("XP: " + p.getCharacterXP());
                    System.out.println("GOLD: " + p.getCharacterGold());
                    System.out.println();
                }
                break;
                case 5:
                    break;
                default: {
                    System.out.println("That is not a valid action.\n");
                }
                break;
            }
        }
        writeToFile(toFile, storeCharacters);
    }

    public static void readFile(File toRead) throws IOException
    {
        FileReader fr = new FileReader(toRead);
        BufferedReader reader = new BufferedReader(fr);
        try
        {
            while (true)
            {
                String name = reader.readLine();
                if (name == null)
                {
                    break;
                }
                String characterClass = reader.readLine();
                int atk = Integer.parseInt(reader.readLine());
                int def = Integer.parseInt(reader.readLine());
                int spd = Integer.parseInt(reader.readLine());
                int lvl = Integer.parseInt(reader.readLine());
                int xp = Integer.parseInt(reader.readLine());
                int gold = Integer.parseInt(reader.readLine());
                reader.readLine();
                Character p = new Character(atk, def, spd, characterClass, name);
                p.setCharacterLevel(lvl);
                p.setCharacterGold(gold);
                p.setCharacterXP(xp);
                charactersMap.put(p.getCharacterName(), p);
            }
        } catch (NumberFormatException e)
        {
            reader.close();
        }
    }

    public static void writeToFile(PrintWriter writer, File toStore)
    {
        //TODO: writer should really be static. Or local. No need for it to be a parameter, right?
        try
        {
            PrintStream stream = new PrintStream (toStore);
            stream.print("");
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }


        for (Map.Entry<String, Character> entry : charactersMap.entrySet())
        {
            Character p = entry.getValue();
            writer.println(p.getCharacterName());
            writer.println(p.getCharacterClass());
            writer.println(p.getAttackStat());
            writer.println(p.getDefenseStat());
            writer.println(p.getSpeedStat());
            writer.println(p.getCharacterLevel());
            writer.println(p.getCharacterXP());
            writer.println(p.getCharacterGold());
            writer.println();
        }
        writer.flush();
        writer.close();
    }

    public static int promptForNumber() {
        int input = -1;
        do
        {
            try
            {
                input = Integer.parseInt(console.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.print("That's not a number! Try again: ");
            }
        } while(input == -1);
        return input;
    }

    //Method to make sure my strings are not empty
    public static String notEmpty(String toCheck) {
        while(toCheck.isEmpty()){
            System.out.print("That's not a valid input! Please enter a better value: ");
            toCheck = console.nextLine();
        }
        return toCheck;
    }

    //My method to create a character
    public static void createCharacter()
    {
        //Prompt user
        System.out.print("Type in your character's name: ");
        String pName = console.nextLine();
        //Make sure the character has a unique name.
        while (charactersMap.containsKey(pName))
        {
            System.out.print("I'm sorry, that name is already taken." + '\n' + "Enter a new name: ");
            pName = console.nextLine();
        }
        //Make sure the character entered the right name.
        System.out.print("Alright! Your character's name is '" + pName + "'. Is that right? (y/n): ");
        String yesNo = console.next();
        yesNo = notEmpty(yesNo);
        while (!yesNo.toLowerCase().startsWith("y"))
        {
            if (yesNo.toLowerCase().startsWith("n"))
            {
                System.out.print("Type in a new name for your character: ");
                pName = console.nextLine();
                System.out.print("Your character's name is now '" + pName + "'. Is that right? (y/n): ");
                yesNo = console.next();
                yesNo = notEmpty(yesNo);
            }
            else
            {
                System.out.print("That's not a valid answer. Please say 'y' or 'n': ");
                yesNo = console.next();
                yesNo = notEmpty(yesNo);
            }
        }

        //Prompt them to enter attack. The next sections are nearly identical to the code for name.
        int pATK;
        System.out.print("Great! Now, do you want your attack to be 3, 4, or, 5?: ");
        do
        {
            pATK = promptForNumber();
            if (pATK > 5 || pATK < 3)
            {
                System.out.print("That's not a usable value for a character's starting attack. Please enter either a 3, 4, or 5: ");
            }
        } while(pATK > 5 || pATK < 3);
        System.out.print("Alright, your attack is " + pATK + ", is that right? (y/n): ");
        yesNo = console.next();
        yesNo = notEmpty(yesNo);
        while (yesNo.charAt(0) != 'y')
        {
            if (yesNo.charAt(0) == 'n')
            {
                System.out.print("Type in your character's new attack then: ");
                pATK = promptForNumber();
                System.out.print("Alright, your attack is " + pATK + ", is that right? (y/n): ");
                yesNo = console.next();
                yesNo = notEmpty(yesNo);
            }
            else
            {
                System.out.print("That's not a valid answer. Please say 'y' or 'n': ");
                yesNo = console.next();
                yesNo = notEmpty(yesNo);
            }
        }

        int pDEF;
        System.out.print("Awesome! Now, enter your defense value. Remember that for stats you can only assign one 3, one 4, and one 5: ");
        pDEF = promptForNumber();
        while (pDEF > 5 || pDEF < 3 || pDEF == pATK)
        {
            System.out.print("That's not a usable value for a character's starting defense. Please enter either a 3, 4, or 5, and not the same value as you did for attack: ");
            pDEF = promptForNumber();
        }
        System.out.print("Alright, your defense is " + pDEF + ", is that right? (y/n): ");
        yesNo = console.next();
        yesNo = notEmpty(yesNo);
        while (yesNo.charAt(0) != 'y')
        {
            if (yesNo.charAt(0) == 'n')
            {
                System.out.print("Type in your character's new defense then: ");
                pDEF = promptForNumber();
                System.out.print("Alright, your defense is " + pDEF + ", is that right? (y/n): ");
                yesNo = console.next();
                yesNo = notEmpty(yesNo);
            }
            else
            {
                System.out.println("That's not a valid answer. Please say 'y' or 'n': ");
                yesNo = console.next();
                yesNo = notEmpty(yesNo);
            }
        }

        int pSPD = 12 - pATK - pDEF;
        System.out.println("Well then your speed must be " + pSPD + ".");

        //It looks better to wait for confirmation.
        System.out.print("Okay! Your character has been created. Press enter to continue: ");
        console.nextLine();
        System.out.println();

        //Store the new character in a temporary variable, add the name to my file and the character + name to my map.
        Character p = new Character(pATK, pDEF, pSPD, "none", pName);
        charactersMap.put(pName, p);

        //output.println(p.getCharacterName());
        playerCharacter = p;
    }

    //Used anytime a character is to be listed and chosen
    //@Nullable
    public static Character getCharacter(String reason)
    {
        userReturned = false;
        System.out.println("The following characters are available:");
        charactersMap.keySet().forEach(System.out::println);

        while (true)
        {
            System.out.print("Enter the name of the character you'd like to " + reason + ": ");
            String name = console.nextLine();
            if (name.equals(""))
            {
                userReturned = true;
                System.out.println("No character loaded. Going back to the main menu.\n");
                return playerCharacter;
            }
            else if (charactersMap.containsKey(name))
            {
                return charactersMap.get(name);
            }
            else
            {
                System.out.println("Sorry, that character does not exist.");
            }
        }
    }

    //This method handles 1 round of combat
    public static boolean attack(Character attacker, Character defender, int round)
    {
        //Defining the defaults for if it's a tie and what the damage is.
        boolean tie = true;
        int dmg = 0;
        attacker.applyDamagingDebuffs();
        while (tie)
        {
            tie = false;

            //Defining stuff for later
            //NOTE: I store it like this instead of just checking using .rollAttack in the if statements so Autohits and misses work.
            int AttackRoll = attacker.rollAttack();
            int DefenseRoll = defender.rollDefense();
            dmg = DiceGenerator.roll(SIDES_PER_DIE);

            //if statement for Autohits. The reason I store the damage roll in a variable is so I can print it.
            //Crit Hit Flag is like this so targeted (which makes you get auto-hit on a 5 or 6) can work. Same with misses
            if (AttackRoll == CRITICAL_HIT_FLAG)
            {
                //Done like this so I can change dice systems (e.g start using d20)
                System.out.println("In round " + round + ", character " + attacker.getCharacterName() + " auto-hit! They dealt " + dmg + " damage.");
            }
            else if (AttackRoll == CRITICAL_MISS_FLAG)
            {
                System.out.println("In round " + round + ", character " + attacker.getCharacterName() + " auto-missed! :(");
                dmg = 0;
            }
            else if (AttackRoll > DefenseRoll)
            {
                //if statement for when the attacking character has a higher total attack than the other character's total defense.
                System.out.println("In round " + round + ", character " + attacker.getCharacterName() + " successfully attacked and dealt " + dmg + " damage");
            }
            else if (AttackRoll < DefenseRoll)
            {
                //if statement for when the attacking character has a lower total attack than the other character's total defense.
                System.out.println("In round " + round + ", character " + attacker.getCharacterName() + "'s attack was blocked.");
                dmg = 0;
            }
            else
            {
                //The last possible case for the rolls, wherein the total attack and defense are equal.
                if (attacker.getCurrentSpeed() > defender.getCurrentSpeed())
                {
                    //if statement for when the attacking character has a higher base speed than the other character does.
                    System.out.println("In round " + round + ", character " + attacker.getCharacterName() + " had attack equal to " + defender.getCharacterName() + "'s defense, but had higher base speed, so they dealt " + dmg + " damage");
                }
                else if (attacker.getCurrentSpeed() < defender.getCurrentSpeed())
                {
                    //if statement for when the attacking character has a lower base speed than the other character does.
                    System.out.println("In round " + round + ", character " + attacker.getCharacterName() + " had attack equal to " + defender.getCharacterName() + "'s defense, but had lower base speed, so " + attacker.getCharacterName() + "'s attack was blocked.");
                    dmg = 0;
                }
                else
                {
                    //The last possible case for the rolls, wherein both characters have the same base speed. If this happens, I need to go back to the start.
                    tie = true;
                }
            }
        }

        //Deals dmg at the end, if an attack didn't go through it's just 0.
        if (defender.hasBuff(Buff.DamageDebuff))
        {
            dmg += Buff.DamageDebuff.getBuffAmount();
            defender.removeBuff(Buff.DamageDebuff);
        }
        defender.dealDamage(dmg);
        return defender.isDead();
    }

    //The function that handles the entire duel
    @NotNull public static Character Duel(@NotNull Character character1, @NotNull Character character2)
    {
        //Round keeps track of the round number. The code inside the for loop is a single turn, and the loop continues until a either the round count hits max int or a character has died.
        for (int round = 1; round < Integer.MAX_VALUE; round++)
        {
            //Variable storing who goes first. Starts at null because it's yet to be set. Same with speed, but it auto-sets to tie instead of null.
            Character firstToAttack = null, firstToDefend = null;
            boolean tiedSpeed = true;

            //While loop that keeps running until a character wins initiative
            while (tiedSpeed)
            {
                //This just defines who goes first
                int speedDiff = character1.rollSpeed() - character2.rollSpeed();
                if (speedDiff > 0)
                {
                    firstToAttack = character1;
                    firstToDefend = character2;
                    tiedSpeed = false;
                    System.out.println("In round " + round + " character " + character1.getCharacterName() + " won initiative.");
                }
                else if (speedDiff < 0)
                {
                    firstToAttack = character2;
                    firstToDefend = character1;
                    tiedSpeed = false;
                    System.out.println("In round " + round + " character " + character2.getCharacterName() + " won initiative.");
                }
                else
                {
                    System.out.println("In round " + round + " both characters had the same total and base speed, so they had to re-roll");
                }
            }

            // attack returns whether or not someone has died in the attack.
            // If so, Duel returns the winner's name.
            if (attack(firstToAttack, firstToDefend, round))
            {
                System.out.println("Character " + firstToAttack.getCharacterName() + " has defeated " + firstToDefend.getCharacterName() + "!\n");
                firstToAttack.setHealth(firstToAttack.getBaseHealth());
                firstToDefend.setHealth(firstToDefend.getBaseHealth());
                return firstToAttack;
            }
            if (attack(firstToDefend, firstToAttack, round))
            {
                System.out.println("Character " + firstToDefend.getCharacterName() + " has defeated " + firstToAttack.getCharacterName() + "!\n");
                firstToAttack.setHealth(firstToAttack.getBaseHealth());
                firstToDefend.setHealth(firstToDefend.getBaseHealth());
                return firstToDefend;
            }

            //Roll for buff removal at end of round.
            character1.rollForBuffRemoval();
            character2.rollForBuffRemoval();

            System.out.println();
        }

        //Part that handles errors.
        System.out.println();
        System.out.println("\tWhoops! It looks like an error has occurred.");
        System.out.println("\tThat, or the players have been fighting for " + Integer.MAX_VALUE + " rounds.");
        System.out.println("\tThey have both, of course, died from exhaustion.");
        System.out.println();

        throw new IllegalStateException();
    }
}