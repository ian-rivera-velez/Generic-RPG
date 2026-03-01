import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    static ArrayList<Character> playerTeam = new ArrayList<>();
    static int enemiesDefeated = 0;
    static int roundsPerBattle = 0;
    static String[] enemyNames = {"Gary", "Alastor", "Hannah", "Matthew", "Trinity", "Michael", "David", "Caleb", "Miley", "Robin", "Logan", "Logan", "Kelly", "Sky", "Jaewon", "Isaac", "Aiden", "Kai", "Andrew", "Luke", "Vinson", "Boisco", "Daniel", "Hongwei", "Mr. Takahashi", "Josh"};

    public static ArrayList<Character> getTeam(){
        return playerTeam;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //Decides run type (sim or game)
        System.out.println("Would you like to run this as a simulation or as a game?");
        
        String answer = input.nextLine().toLowerCase();
        while (!answer.equals("game") || !answer.equals("simulation") || !answer.equals("sim")){
            System.out.println("Answer invalid. Please enter \"Game\" or \"Simulation\".")
            System.out.println("Would you like to run this as a simulation or as a game?");
            answer = input.nextLine().toLowerCase();
        }

        //runs as game
        if (answer.equals("game")){
            MediaFile.setInputFile("Generic RPG Save File");
            String save = MediaFile.readString();
            //asks to load save
            if (save != null){
                System.out.println("You have a party created. Would you like to use this party?");
                String decision = input.nextLine().toLowerCase();
                //loads save
                if (decision.equals("yes") || decision.equals("y")){
                    enemiesDefeated = Integer.parseInt(save);
                    save = MediaFile.readString();
                    characterCreation(-1, true, save);
                    System.out.println("Here's your party:");
                    for (Character current : playerTeam) {
                        System.out.println(current);
                    }
                    System.out.println("You have defeated " + enemiesDefeated + " out of 5 enemy teams!");
                } else if (decision.equals("no") || decision.equals("n")){ //doesn't load save (creates new party)
                    System.out.println("How many characters would you like to have in your party? (Up to 4)");
                    int partySize = 0;
                    boolean worked = false;
                    try{
                        partySize = Integer.parseInt(input.nextLine());
                    } catch (Exception e){
                        System.out.println("The party size needs to be a number. For example: 1, 2, 3, or 4.\nEnter an allowed value now:");
                        while(!worked){
                            try{
                                partySize = Integer.parseInt(input.nextLine());
                                worked = true;
                            } catch (Exception r){
                                System.out.println("The party size needs to be a number. For example: 1, 2, 3, or 4.\nEnter an allowed value now:");
                            }
                        }
                    }
                    while(partySize < 0 || partySize > 4){
                            System.out.println("Your party needs to have 1, 2, 3 or 4 players.");
                            System.out.println("How many characters would you like to have in your party? (Up to 4)");
                            partySize = Integer.parseInt(input.nextLine());
                    }
                    characterCreation(partySize, false, "");
                } else {
                    while(!(decision.equals("yes")|| decision.equals("no") || decision.equals("n") || decision.equals("y"))){
                        System.out.println("Invalid Choice. Choose yes or no.");
                        System.out.println("You have a character created. Would you like to use this character?");
                        decision = input.nextLine().toLowerCase();
                    }
                }
            } else { //if no choice, it just makes a new party.
                System.out.println("How many characters would you like to have in your party? (Up to 4)");
                int partySize;
                try{
                    partySize = Integer.parseInt(input.nextLine());
                } catch (Exception e){
                    System.out.println("Please enter a number.");
                }
                while(partySize < 0 || partySize > 4){
                    System.out.println("Your party needs to have 1, 2, 3 or 4 players.");
                    System.out.println("How many characters would you like to have in your party? (Up to 4)");
                    try{
                        partySize = Integer.parseInt(input.nextLine());
                    } catch (Exception e){
                        System.out.println("Please enter a number.");
                    }
                }
                characterCreation(partySize, false, "");
            }
            game();
        } else if (answer.equals("simulation") || answer.equals("sim")) { //runs as a simulation
            String answerToClass;
            do{
                System.out.println("Would you like to select a class? [y/n]");
                answerToClass = input.nextLine().toLowerCase();
            } while (!(answerToClass.equals("yes") || answerToClass.equals("y") || answerToClass.equals("n") || answerToClass.equals("no")));

            if (answerToClass.equalsIgnoreCase("yes") || answerToClass.equalsIgnoreCase("y")) {
                System.out.println("What class would you like your character to be? (enemies will be randomized every battle)");
                String type = input.nextLine().toLowerCase();
                System.out.println("How many battles?");
                int amount = input.nextInt();
                simulation(amount, type);
            } else {
                System.out.println("How many battles?");
                int amount = input.nextInt();
                simulation(amount);
            }
        }
    }

    /**
     * Runs the simulation version of the game. It will run however many battles you specify with the player character
     * as a class you choose. The enemy will be randomized each round.
     * @param amount how many battles you want to run
     * @param type the class of the "player" character
     */
    public static void simulation(int amount, String type){
        int battlesDone = 0;
        int battleResult;
        int playerWon = 0;
        int enemyWon = 0;
        Character player = switch (type.toLowerCase()) {
            case "warrior" -> new Warrior("player");
            case "healer" -> new Healer("player");
            case "wizard" -> new Wizard("player");
            case "cook" -> new Cook("player");
            default -> new Character("player");
        };

        for (int i = 0; i < amount; i++){
            Character enemy = switch ((int)(Math.random() * 4)) {
                case 0 -> new Warrior("enemy");
                case 1 -> new Healer("enemy");
                case 2 -> new Wizard("enemy");
                default -> new Cook("enemy");
            };
            battleResult = simBattle(player, enemy);
            if (battleResult == 1){
                playerWon++;
                battlesDone++;
            } else if (battleResult == 2) {
                enemyWon++;
                battlesDone++;
            } else {
                i--;
            }
            player.setHealth(50);
        }
        roundsPerBattle /= battlesDone;
        int percentWon = (int)((double)playerWon/amount * 100.0);
        System.out.println("Results: \nPlayer won: " + playerWon + " times\nEnemy won: " + enemyWon + " times\nWin Percentage: " + percentWon + "%\nThere were an average of " + roundsPerBattle + " rounds per battle.");
    }
    /**
     * Runs the simulation version of the game. It will run however many battles you specify. Both the player characters and the enemies will be randomized.
     * @param amount how many battles you want to run
     */
    public static void simulation(int amount){
        int battleResult;
        int playerWon = 0;
        int enemyWon = 0;
        Character player = switch ((int)(Math.random() * 4)) {
            case 0 -> new Warrior("player");
            case 1 -> new Healer("player");
            case 2 -> new Wizard("player");
            default -> new Cook("player");
        };
        System.out.println("The 'player' character will be a " + player.getType());
        for (int i = 0; i < amount; i++){
            Character enemy = switch ((int)(Math.random() * 4)) {
                case 0 -> new Warrior("enemy");
                case 1 -> new Healer("enemy");
                case 2 -> new Wizard("enemy");
                default -> new Cook("enemy");
            };
            battleResult = simBattle(player, enemy);
            if (battleResult == 1){
                playerWon++;
            } else if (battleResult == 2) {
                enemyWon++;
            } else {
                i--;
            }
            player.setHealth(50);
        }
        roundsPerBattle /= amount;
        int percentWon = (int)((double)playerWon/amount * 100.0);
        System.out.println("Results: \nPlayer won: " + playerWon + " times\nEnemy won: " + enemyWon + " times\nWin Percentage: " + percentWon + "%\nThere were an average of " + roundsPerBattle + " rounds per battle.");
    }

    /**
     * Character Creation Menu
     * @param characterCount how many characters you want
     * @param saveFile whether you want to use the save file.
     * @param info the first line of the save file. used to finish loading in the team.
     */
    public static void characterCreation(int characterCount, boolean saveFile, String info){
        if (!saveFile) {
            MediaFile.setOutputFile("Generic RPG Save File");
            //Character Creation (new team)
            Scanner input = new Scanner(System.in);
            MediaFile.writeString("0", "", true);
            for (int i = 0; i < characterCount; i++) {
                System.out.println("Character #" + (i+1) + ": ");
                System.out.println("What would you like your character's name to be?");
                String name = input.nextLine();
                MediaFile.writeString(name, "\t", false);
                MediaFile.writeString("50", "\t", false);
                System.out.println("Perfect! Now, would you like to select a class for you character? (y/n)");
                boolean worked = false;
                String selection = input.nextLine();
                if (selection.equalsIgnoreCase("yes") || selection.equalsIgnoreCase("y") || selection.equalsIgnoreCase("n") || selection.equalsIgnoreCase("no")){
                    worked = true;
                }
                while(!worked) {
                    System.out.println("Please choose yes or no.");
                    selection = input.nextLine();
                    if (selection.equalsIgnoreCase("yes") || selection.equalsIgnoreCase("y") || selection.equalsIgnoreCase("no") || selection.equalsIgnoreCase("n")) {
                        worked = true;
                    }
                }


                boolean possibleAnswerGiven = typeSetter(name, selection);
                while (!possibleAnswerGiven) {
                    possibleAnswerGiven = typeSetter(name, selection);
                }
                System.out.println("Your character has been created. Here are your stats:");
                System.out.println(playerTeam);
            }
            input.close();
            MediaFile.saveAndClose();
        } else {
            while (info != null) {
                //loading in the new team
                String[] save = info.split("\t");
                if (save[2].equalsIgnoreCase("Wizard")) {
                    playerTeam.add(new Wizard(save[0]));
                } else if (save[2].equalsIgnoreCase("Warrior")) {
                    playerTeam.add(new Warrior(save[0]));
                } else if (save[2].equalsIgnoreCase("Healer")) {
                    playerTeam.add(new Healer(save[0]));
                } else if (save[2].equalsIgnoreCase("Cook")) {
                    playerTeam.add(new Healer(save[0]));
                }
                playerTeam.get(playerTeam.size()-1).setHealth(Integer.parseInt(save[1]));
                info = MediaFile.readString();
            }
        }
    }

    /**
     * Sets the type for the character and puts it on the save file if it's a new team.
     * @param name the name of the character whose type is being set
     * @param selection whether they chose a type or if it's going to be random
     * @return whether the type setting worked
     */
    public static boolean typeSetter(String name, String selection){
        Scanner input = new Scanner(System.in);
        if ((selection.equals("y") || (selection.equals("yes")))) { //chosen class
            System.out.println("Okay! Enter a class now: \n[Healer] [Warrior] [Wizard] [Cook]");
            String type = input.nextLine().toLowerCase();
            if (type.equalsIgnoreCase("wizard")){
                playerTeam.add(new Wizard(name));
                MediaFile.writeString(type, "\t", true);
            } else if (type.equalsIgnoreCase("Healer")){
                playerTeam.add(new Healer(name));
                MediaFile.writeString(type, "\t", true);
            } else if (type.equalsIgnoreCase("Warrior")) {
                playerTeam.add(new Warrior(name));
                MediaFile.writeString(type, "\t", true);
            }else if (type.equalsIgnoreCase("Cook")){
                playerTeam.add(new Cook(name));
                MediaFile.writeString(type, "\t", true);
            } else {
                while (true){
                    System.out.println("Invalid class, try again.");
                    System.out.println("Okay! Enter a class now:");
                    type = input.nextLine().toLowerCase();
                    if (type.equalsIgnoreCase("wizard")){
                        playerTeam.add(new Wizard(name));
                        MediaFile.writeString(type, "\t", true);
                        break;
                    } else if (type.equalsIgnoreCase("Healer")){
                        playerTeam.add(new Healer(name));
                        MediaFile.writeString(type, "\t", true);
                        break;
                    } else if (type.equalsIgnoreCase("Warrior")) {
                        playerTeam.add(new Warrior(name));
                        MediaFile.writeString(type, "\t", true);
                        break;
                    } else if (type.equalsIgnoreCase("Cook")){
                        playerTeam.add(new Cook(name));
                        MediaFile.writeString(type, "\t", true);
                        break;
                    }
                }
            }
            input.close();
            return true; //it worked
        } else if(selection.equals("n") || selection.equals("no")) { //random class
            input.close();
            System.out.println("You've chosen not to choose a class. That's fine! We'll just randomly select it now.");
            int random = (int)(Math.random() * 4);
            if (random == 0){
                playerTeam.add(new Healer(name));
                MediaFile.writeString("Healer", "\t", true);
            } else if (random == 1){
                playerTeam.add(new Wizard(name));
                MediaFile.writeString("Wizard", "\t", true);
            } else if (random == 2) {
                playerTeam.add(new Warrior(name));
                MediaFile.writeString("Warrior", "\t", true);
            } else {
                playerTeam.add(new Cook(name));
                MediaFile.writeString("Cook", "\t", true);
            }
            System.out.println("Your character is now a " + playerTeam.get(playerTeam.size()-1).getType());
            return true; //it worked
        } else {
            input.close();
            return false; //it didn't work, so it will do it again
        }
    }

    /**
     * Runs the main portion of the game. From the start to the end.
     */
    public static void game(){
        System.out.println("\nWelcome to Generic RPG! To win, you must defeat 5 enemies. Good luck!");
        while (enemiesDefeated < 5) { //will keep giving new battles until 5 enemy teams were defeated
            int outcome = battle(); //runs a battle, which will give 3 options
            if (outcome == 1) { //the player won
                enemiesDefeated++;
                MediaFile.setOutputFile("Generic RPG Save File");
                MediaFile.writeString(String.valueOf(enemiesDefeated), "", true);
                for (Character current : playerTeam){
                    MediaFile.writeString(current.getName(), "\t", false);
                    MediaFile.writeString(String.valueOf(current.getHealth()), "\t", false);
                    MediaFile.writeString(current.getType(), "\t", true);
                }
                MediaFile.saveAndClose();
                System.out.println("\n[Game Saved!]\n");
                System.out.println("Congratulations on your victory! \nAmount of enemies defeated: " + enemiesDefeated);
                afterBattle();

            } else if (outcome == 2) { //the player ran
                System.out.println("You run away safely.");
                afterBattle();
            } else if (outcome == 3){
                System.out.println("Your team has been defeated :( \nThe game will end now. Bye bye!");
                System.exit(0);
            } else if (outcome == 4){ //the player quit
                System.out.println("Okay then! Thanks for playing, see you later!");
                MediaFile.writeString(Integer.toString(enemiesDefeated), "", true);
                for (Character current : playerTeam){
                    MediaFile.writeString(current.getName(), "\t", false);
                    MediaFile.writeString(Integer.toString(current.getHealth()), "\t", false);
                    MediaFile.writeString(current.getType(), "\t", true);
                }
                MediaFile.saveAndClose();
                System.exit(0);
            }
        }
        endOfGame(); // after 5 enemy teams were defeated, it will go to the after game section
    }

    /**
     * The end of game section. It asks if you want to play again, and if you do whether you want to use the same team.
     */
    public static void endOfGame(){
        Scanner input = new Scanner(System.in);
        System.out.println("\n\nYou've defeated 5 enemies. Congratulations, you won!");
        System.out.println("...");
        System.out.println("Now what?");
        System.out.println("I guess we can do it again. Would you like to? [yes] [no]");
        String endOfGame = input.nextLine().toLowerCase();
        if (endOfGame.equals("yes") || endOfGame.equals("y")){
            String newCharacter;
            do {
                System.out.println("You currently have a team created. \nWould you like to create a new team or stay with your current team? (if your characters died, they will not be in your new team) [keep] [new]");
                newCharacter = input.nextLine().toLowerCase();
            } while (!(newCharacter.equals("keep") || newCharacter.equals("k") || newCharacter.equals("new") || newCharacter.equals("n")));
            
            if (newCharacter.equals("keep") || newCharacter.equals("k")){ //using the same team resets the enemy count and heals the characters to full health
                enemiesDefeated = 0;
                for (Character current : playerTeam){
                    current.setHealth(50);
                }
                System.out.println("Okay! Off you go then!");
                game();
            } else if (newCharacter.equals("new") || newCharacter.equals("n")){ //a new team will ask for the party size and go to the characterCreation method
                System.out.println("\nGot it. We'll ask you to create a new party now.\n");
                System.out.println("How many party members would you like? (Up to 4)");
                int partySize = 0;
                boolean worked = false;
                while(!worked){ //makes sure the party size is acceptable
                    try{
                        partySize = Integer.parseInt(input.nextLine());
                        if (partySize < 4 && partySize > 0){
                            worked = true;
                        }
                    } catch (Exception r){
                        System.out.println("Please enter a number from 1 to 4.\nEnter an allowed value now:");
                    }
                }
                characterCreation(partySize, false, "");
                System.out.println("\nBack into the game we go!\n");
                enemiesDefeated = 0;
                game(); //restarts the game
            }
        } else {
            System.out.println("Okay! See you later then!");
            System.out.println("[Game exited]");
            System.exit(0);
        }
        input.close();
    }

    /**
     * The battle system for the simulations
     * @param player the "player" character
     * @param enemy the enemy character
     * @return whether the player won (1 = won, 2 = lost, 3 = failed (try again))
     */
    public static int simBattle(Character player, Character enemy){
        while((enemy.getHealth() > 0) && (player.getHealth() > 0)){
            player.simAttack(enemy);
            enemy.simAttack(player);
            roundsPerBattle++;
        }
        if ((enemy.getHealth() <= 0) && (player.getHealth() > 0)){
            return 1;
        } else if ((player.getHealth() <= 0) && (enemy.getHealth() > 0)) {
            return 2;
        }
        return 3;
    }

    /**
     * Handles the battle system.
     * 1. Creates an enemy team of the same size as the player's team.
     * 2. Asks for player input on who to attack with what player character.
     * 3. Runs rounds until one of these two dies. The one that dies is removed from the according team.
     * 4. Rest of enemy characters take turns attacking a random character on your team
     * @return whether the player won (1 = won, 2 = lost, 3 = run, 4 = quit)
     */
    public static int battle() {
        Scanner input = new Scanner(System.in);
        ArrayList<Character> enemyTeam = new ArrayList<>();
        for (int i = 0; i < playerTeam.size(); i++) {
            Character enemy = null;
            String enemyName = enemyNames[(int) (Math.random() * enemyNames.length)];
            int random = (int) (Math.random() * 4);
            if (random == 0) {
                enemy = new Warrior(enemyName);
            } else if (random == 1) {
                enemy = new Healer(enemyName);
            } else if (random == 2) {
                enemy = new Wizard(enemyName);
            } else if (random == 3) {
                enemy = new Cook(enemyName);
            }
            enemyTeam.add(enemy);
        }
        System.out.println("You approach a group of evil looking people.\n\nThese are their stats:");

        while((!enemyTeam.isEmpty()) || (!playerTeam.isEmpty())) {
            for (int i = 0; i < enemyTeam.size(); i++) {
                System.out.println(i + 1 + ". " + enemyTeam.get(i));
            }

            System.out.println("Who would you like to attack? [Use the number] [-1 to display the list again]");
            int enemyOption = 0;
            boolean worked = false;
            while (!worked) {
                try {
                    enemyOption = Integer.parseInt(input.nextLine()) - 1;
                    if (enemyOption <= enemyTeam.size() && enemyOption >= -1){
                        worked = true;
                    }
                } catch (Exception e) {
                    System.out.println("The choice needs to be a number on the list.");
                }
            }
            if (enemyOption == -1) {
                System.out.println("Enemy Team: ");
                for (int i = 0; i < enemyTeam.size(); i++) {
                    System.out.println(i + 1 + ". " + enemyTeam.get(i));
                }
            }
            System.out.println("This is your team.\nThese are their stats: ");
            for (int i = 0; i < playerTeam.size(); i++) {
                System.out.println(i + 1 + ". " + playerTeam.get(i));
            }
            System.out.println("Who would you like to attack with? [Use the number]");
            int playerOption = 0;
            worked = false;
            while (!worked) {
                try {
                    playerOption = Integer.parseInt(input.nextLine()) - 1;
                    if ((playerOption < playerTeam.size()) && (playerOption >= 0)){
                        worked = true;
                    } else {
                        System.out.println("The choice needs to be a number on the list.");
                    }
                } catch (Exception e) {
                    System.out.println("The choice needs to be a number on the list.");
                }
            }

            input.close();

            int result = round(playerTeam.get(playerOption), enemyTeam.get(enemyOption));
            if (result == 1) {
                enemyTeam.remove(enemyOption);
                if (enemyTeam.isEmpty()){
                    break;
                }
            } else if (result == 2) {
                return 2;
            } else if (result == 3) {
                return 3;
            } else if (result == 4){
                return 4;
            }
            for (Character character : enemyTeam) {
                int playerAttacked = (int) (Math.random() * playerTeam.size());
                character.attack(playerTeam.get(playerAttacked));
                if (playerTeam.get(playerAttacked).getHealth() <= 0) {
                    System.out.println(playerTeam.get(playerAttacked).getType() + " " + playerTeam.get(playerAttacked).getName() + " has died. They will now be removed from your team.");
                    playerTeam.remove(playerAttacked);
                }
            }
        }
        if (enemyTeam.isEmpty()){
            return 1;
        } else if (playerTeam.isEmpty()){
            return 3;
        }
        return 2;
    }

    /**
     * Handles each round in the battle
     * 1. Asks player for an action
     * 2. Action is performed
     * 3. If an enemy dies, it's removed from the enemy team so it doesn't show up the next round.
     * @param player the player character that is attacking
     * @param enemy the enemy character that is being attacked
     * @return whether the player won (1 = won, 2 = lost, 3 = run, 4 = quit)
     */
    public static int round(Character player, Character enemy){
        Scanner input = new Scanner(System.in);
        String[] catchphrase = {"Haha! Come after me if you dare!", "Are you scared yet?", "Prepare to face your doom!",
                "You think you can defeat me? How amusing!", "Your destiny ends here!", "Say goodbye to your friends, for this will be your last battle!"};
        System.out.println(enemy.getType() + " " + enemy.getName() + ": " + catchphrase[(int)(Math.random() * catchphrase.length)] + "\n");
        while((enemy.getHealth() > 0) && (player.getHealth() > 0)) {
            System.out.println("Enemy Health: " + enemy.getHealth() + " hp");
            System.out.println("Player Health: " + player.getHealth() + " hp");
            System.out.println("What would you like to do? [Fight] [Special Attack] [Run] [Quit]");
            String answer = (input.nextLine()).toLowerCase();
            if ((player.getHealth() > 0) && (enemy.getHealth() > 0)) {
                switch (answer) {
                    case "kill e":
                        System.out.println("[Cheat code used; enemy killed]");
                        enemy.setHealth(0);
                        return 1;
                    case "kill p":
                        System.out.println("[Cheat code used; player killed]");
                        player.setHealth(0);
                        return 3;
                    case "f":
                    case "fight":
                        player.attack(enemy);
                        enemy.attack(player);
                        break;
                    case "sp":
                    case "special attack":
                        player.specialAttack(enemy);
                        break;
                    case "run":
                    case "r":
                        return 2;
                    case "quit":
                    case "q":
                        return 4;
                    default:
                        System.out.println("Please choose a valid answer. (Fight, Special Attack, Run, or Exit)");
                        while (!(answer.equals("run") || answer.equals("fight") || answer.equals("quit") || answer.equals("special attack") || answer.equals("sp") || answer.equals("q") || answer.equals("r") || answer.equals("f"))) {
                            System.out.println("What would you like to do? [Fight] [Special Attack] [Run] [Quit]");
                            answer = (input.nextLine()).toLowerCase();
                        }
                        break;
                }
            }
        }
        input.close();
        if (enemy.getHealth() <= 0){
            return 1;
        } else if (player.getHealth() <= 0) {
            playerTeam.remove(player);
            return 3;
        }
        return 4;
    }

    /**
     * This runs after every battle unless the goal was reached.
     * 1. Shows your current stats
     * 2. Gives you the option to heal, return to battle, or quit
     * 3. Does the action that was chosen.
     */
    public static void afterBattle(){
        if (enemiesDefeated >= 5){
            endOfGame();
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Your current stats: ");
        for (Character current : playerTeam){
            System.out.println(current);
        }
        System.out.println("What would you like to do now?");
        boolean acceptedAnswer = false;
        while (!acceptedAnswer) {
            System.out.println("[Heal] [Battle] [Quit]");
            String runChoice = input.nextLine().toLowerCase();
            switch (runChoice) {
                case "heal", "h" -> {
                    System.out.println("Your team stops at a bakery and eats a fulfilling meal. (+10 hp)");
                    for (Character current : playerTeam) {
                        current.setHealth(Math.min((current.getHealth() + 10), 50));
                    }
                    System.out.println("You head back into battle. \n");
                    acceptedAnswer = true;
                }
                case "battle", "b" -> {
                    System.out.println("You head back into battle. \n");
                    acceptedAnswer = true;
                }
                case "quit", "q" -> {
                    System.out.println("Okay. See ya!");
                    System.exit(0);
                }
                default -> System.out.println("That's not an option. Try again.");
            }
        }
        input.close();
    }

}

