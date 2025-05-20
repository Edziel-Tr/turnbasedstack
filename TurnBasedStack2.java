package turnbasedstack2;

import java.util.*;

public class TurnBasedStack2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Stack<Integer> LastHP = new Stack<>();
        Random randomizer = new Random();
        Stack<Integer> JinguMasteryDao = new Stack<>();

        String playerName = "";
        String botName = "Vinze And Friends";

        int playerHP = 1000;
        int playerMaxHP = 4000;
        int botHP = 5000;
        int botMaxHP = 10000;
        int playerMinDmg = 100;
        int playerMaxDmg = 2000;
        int botMinDmg = 210;
        int botMaxDmg = 375;
        int stunTurns = 0;
        int turnCount = 1;

        int[] worldSavior = {0, 0}; 

        int attackCounter = 0;
        boolean auraSwordActive = false;

        int consecutiveAttackCount = 0;

        System.out.println("Your name is?" + playerName + ":");
        
        System.out.println("Choose a Class Sir " + playerName + ":");
        System.out.println("1. Knight");
        System.out.println("2. Mage");
        System.out.println("3. Ranger");
        System.out.println("4. Warrior");

        String chosenClass = "";
        while (true) {
            System.out.print("Enter your class choice: ");
            chosenClass = scan.nextLine().trim();
            if (chosenClass.equalsIgnoreCase("Knight")) {
                playerHP = 1200;
                playerMaxHP = 1200;
                playerMinDmg = 150;
                playerMaxDmg = 1800;
                System.out.println("You chose Knight: High HP, balanced damage.");
                break;
            } else if (chosenClass.equalsIgnoreCase("Mage")) {
                playerHP = 800;
                playerMaxHP = 800;
                playerMinDmg = 300;
                playerMaxDmg = 2500;
                System.out.println("You chose Mage: Lower HP, high burst damage.");
                break;
            } else if (chosenClass.equalsIgnoreCase("Ranger")) {
                playerHP = 900;
                playerMaxHP = 900;
                playerMinDmg = 200;
                playerMaxDmg = 2200;
                System.out.println("You chose Ranger: Fast attacks, moderate HP.");
                break;
            } else if (chosenClass.equalsIgnoreCase("Warrior")) {
                playerHP = 1100;
                playerMaxHP = 1100;
                playerMinDmg = 180;
                playerMaxDmg = 2000;
                System.out.println("You chose Warrior: Balanced HP and damage.");
                break;
            } else {
                System.out.println("Invalid class choice. Please select Knight, Mage, Ranger, or Warrior.");
            }
        }

        System.out.println("You encountered an enemy!");

        while (playerHP > 0 && botHP > 0) {
            if (playerHP < 0) {
                playerHP = 0;
            }
            if (botHP < 0) {
                botHP = 0;
            }

            System.out.println("\n    Turn " + turnCount + "   ");

            if (worldSavior[0] > 0) {
                worldSavior[0]--;
                if (worldSavior[0] == 0) {
                    worldSavior[1] = 2; 
                    System.out.println("World Savior effect faded! Cooldown begins.");
                }
            }
            if (worldSavior[1] > 0) {
                worldSavior[1]--;
            }

            if (playerHP <= playerMaxHP * 0.75 && playerHP < playerMaxHP) {
                int heal = randomizer.nextInt(51) + 100;
                playerHP += heal;
                if (playerHP > playerMaxHP) {
                    heal -= (playerHP - playerMaxHP);
                    playerHP = playerMaxHP;
                }
                LastHP.push(heal);
                System.out.println("World Tree Blessing healed you for " + heal + " HP.");
            }
            
            if (botHP <= botMaxHP * 0.40 && botHP < botMaxHP) {
                int healBot = randomizer.nextInt(51) + 100;
                playerHP += healBot;
                if (botHP > botMaxHP) {
                    healBot -= (botHP - botMaxHP);
                    botHP = botMaxHP;
                }
                LastHP.push(healBot);
                System.out.println("Enemy has triggered his passive " + healBot + " HP.");
            }

            if (turnCount % 2 == 1) { 
                if (stunTurns > 0) {
                    System.out.println("Enemy is stunned! You get another turn.");
                    stunTurns--;
                }

                System.out.println("What would you like to do?");
                System.out.println("Player HP: " + playerHP);
                System.out.println("Bot HP: " + botHP);
                System.out.println(">>> Attack");
                System.out.println(">>> Stun");
                System.out.println(">>> Dance");
                System.out.println(">>> Skip");
                System.out.println(">>> World Savior" + (worldSavior[0] > 0 ? " (Active: " + worldSavior[0] + " turns left)" : (worldSavior[1] > 0 ? " (Cooldown: " + worldSavior[1] + ")" : "")));

                String in = scan.nextLine().trim();

                if (in.equalsIgnoreCase("Attack")) {
                    attackCounter++;
                    consecutiveAttackCount++;

                    if (attackCounter >= 3 && !auraSwordActive) {
                        auraSwordActive = true;
                        System.out.println("Aura Sword activated! +500 bonus damage!");
                    }

                    if (consecutiveAttackCount == 4) {
                        int baseDmg = randomizer.nextInt(playerMaxDmg - playerMinDmg + 1) + playerMinDmg;
                        int amplifiedDmg = baseDmg + 1000;
                        if (auraSwordActive) {
                            amplifiedDmg += 500;
                        }
                        botHP -= amplifiedDmg;
                        JinguMasteryDao.push(amplifiedDmg);
                        System.out.println("JinguMasteryDao activated! Amplified Attack dealt " + amplifiedDmg + " damage to the enemy!");
                        consecutiveAttackCount = 0;
                    } else {
                        int playerDmg = randomizer.nextInt(playerMaxDmg - playerMinDmg + 1) + playerMinDmg;
                        if (auraSwordActive) {
                            playerDmg += 500;
                        }
                        botHP -= playerDmg;
                        System.out.println("You dealt " + playerDmg + " damage to the enemy.");
                        if (consecutiveAttackCount < 4) {
                            System.out.println("[" + (4 - consecutiveAttackCount) + " consecutive Attacks left to activate JinguMasteryDao!]");
                        }
                    }
                } else if (in.equalsIgnoreCase("Stun")) {
                    System.out.println("You stunned the enemy for 2 turns!");
                    stunTurns = 2;
                    consecutiveAttackCount = 0;
                } else if (in.equalsIgnoreCase("Dance")) {
                    System.out.println("You performed a dance. It's not very effective...");
                    consecutiveAttackCount = 0;
                } else if (in.equalsIgnoreCase("Skip")) {
                    System.out.println("You skipped your turn.");
                    consecutiveAttackCount = 0;
                } else if (in.equalsIgnoreCase("World Savior")) {
                    if (worldSavior[0] == 0 && worldSavior[1] == 0) {
                        worldSavior[0] = 3; 
                        System.out.println("World Savior activated! Blocking enemy attacks for 3 turns.");
                    } else {
                        System.out.println("Skill is on cooldown or already active.");
                    }
                    consecutiveAttackCount = 0;
                } else {
                    System.out.println("Invalid action. You lost your turn.");
                    consecutiveAttackCount = 0;
                }

            } else { 
                if (stunTurns > 0) {
                    System.out.println("Bot is stunned and skips its turn!");
                    stunTurns--;
                } else if (worldSavior[0] > 0) {
                    System.out.println("World Savior blocked the enemy's attack!");
                } else {
                    if (botHP <= botMaxHP / 2 && randomizer.nextInt(100) < 25) {
                        int healAmount = randomizer.nextInt(501) + 500;
                        botHP += healAmount;
                        if (botHP > botMaxHP) {
                            healAmount -= (botHP - botMaxHP);
                            botHP = botMaxHP;
                        }
                        System.out.println("The enemy has triggered healing passive: healed " + healAmount + " HP.");
                    } else {
                        int botDmg = randomizer.nextInt(botMaxDmg - botMinDmg + 1) + botMinDmg;
                        playerHP -= botDmg;
                        System.out.println("The enemy dealt " + botDmg + " damage to you.");
                    }
                }
            }

            turnCount++;
        }

        
        if (playerHP <= 0 && botHP <= 0) {
            System.out.println("\nIt's a draw! Both you and the enemy have fallen!");
        } else if (playerHP <= 0) {
            System.out.println("\nYou have been defeated by " + botName + "!");
        } else if (botHP <= 0) {
            System.out.println("\nCongratulations, " + playerName + "! You defeated " + botName + "!");
        }
    }
}
