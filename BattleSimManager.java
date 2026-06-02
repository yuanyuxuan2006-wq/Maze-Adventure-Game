import java.util.ArrayList;

/**
 * Manages the core gameplay of the Labyrinth Battle Simulator.
 * <p>
 * This class is responsible for controlling the overall game flow, including
 * player creation, labyrinth generation, room navigation, combat encounters,
 * and game state transitions (win/loss conditions).
 * </p>
 *
 * <p>
 * The manager coordinates interactions between the player, rooms, monsters, and
 * console input/output system. It ensures the game progresses in a structured
 * loop where the player explores rooms, fights monsters, and attempts to escape
 * the labyrinth.
 * </p>
 *
 * <p>
 * Key responsibilities include:
 * <ul>
 *     <li>Initialising the game and generating a random labyrinth</li>
 *     <li>Creating and resetting the player character</li>
 *     <li>Handling room entry events and state changes</li>
 *     <li>Managing turn-based combat via the battle system</li>
 *     <li>Processing movement through the labyrinth</li>
 *     <li>Determining win and game over conditions</li>
 * </ul>
 * </p>
 *
 * @author FIT1051 Admin
 * @version <p>1.0 - created the basic scaffold for the class</p>
 * <p>1.1 - changed Battle logic to repeat until either player dies,
 * and to include special abilities for characters. Also, added pre-defined
 * choices for characters</p>
 * <p>1.2 - added core game logic - including initializing Labyrinth,
 * rooms, navigating and managing gameplay logic.</p>
 *
 */
public class BattleSimManager
{
    private final ConsoleIO console;
    private Labyrinth currentLabyrinth;
    private Character player;
    private Room currentRoom;


    /**
     * Default constructor that initializes a new player
     */
    public BattleSimManager()
    {
        this(new Warrior(), new ConsoleIO());
    }

    /**
     * Constructs a BattleSimManager with a specified player.
     *
     * @param player the player character
     */
    public BattleSimManager(Character player, ConsoleIO console)
    {
        this.player = player;
        this.console = console;
        initializeGame();
    }

    /**
     * Executes a turn-based battle between the player and the monster in the
     * current room.
     * <p>
     * During the battle, the monster and player take turns dealing damage to
     * each other until either the player or the monster is defeated. The player
     * has a chance to activate a special ability once per battle, which deals
     * additional damage. The chance of activating this ability increases over
     * time if it is not triggered.
     * </p>
     *
     * <p>
     * After the battle ends:
     * <ul>
     *     <li>If the player loses, the monster is declared the victor.</li>
     *     <li>If the player wins, they recover a fixed amount of health.</li>
     * </ul>
     * </p>
     *
     * @return the name of the victor (either the player or the monster)
     */
    public String battle()
    {
        String victor = player.getName();
        Monster monster = currentRoom.getMonster();
        int damageDealt = 0;
        int abilityActivationChance = 1;
        boolean abilityActivated = false;
        do
        {
            damageDealt = player.takeDamage(monster.getAttack());
            console.display(monster.getName() + " attacks for " + damageDealt +
                            " to the player.");

            int random = 1 + (int) (Math.random() * 100);
            if (!abilityActivated && random <= abilityActivationChance)
            {
                damageDealt = player.specialAbility();
                monster.setHealth(Math.max(0, monster.getHealth() - damageDealt));
                console.display("You activate your special ability " +
                                "and deal " + damageDealt + " damage to the " +
                                "monster.");
                abilityActivated = true;
            } else
            {
                damageDealt = monster.takeDamage(player.getAttack());
                console.display(player.getName() + " attacks for " + damageDealt +
                                " to the monster.");

                abilityActivationChance *= 2;
            }
        } while (player.getHealth() > 0 && monster.getHealth() > 0);

        if (monster.getHealth() > player.getHealth())
        {
            victor = monster.getName();
        } else
            player.recoverHealth(20);

        return victor;
    }

    /**
     * Creates the player character by prompting the user to select a character
     * type.
     * <p>
     * This method first displays the available character options, then reads
     * and validates the user's choice. Based on the selection, it initializes
     * the player character and confirms the start of the game with a narrative
     * message.
     * </p>
     *
     * <p>
     * After creation, the method outputs the selected character details and
     * begins the player's journey in the labyrinth.
     * </p>
     */

    private void createPlayerCharacter()
    {
        displayPlayerOptions();
        int choice = getIntInput("Choose wisely (1-3): ",
                                 "The labyrinth rejects your choice…",
                                 1, 3);

        initializePlayerCharacter(choice);

        console.display("The ancient powers accept your choice… your journey begins.");
        console.display("You are a " + player);

    }

    /**
     * Displays the game over message when the player fails to complete the
     * labyrinth.
     * <p>
     * This method outputs a final message indicating that the player was unable
     * to defeat the labyrinth. It marks the end of the game and informs the
     * player that their journey for fame and riches has come to an end.
     * </p>
     */
    private void displayGameOver()
    {
        console.display("You were not able to best the labyrinth this " +
                        "time adventurer. Your journey for fame and riches ends" +
                        "here! Game Over!");
    }

    /**
     * Displays the main menu options to the user.
     */
    private void displayMenu()
    {
        String menu = """
                      Choose an option:
                      1. Create a character
                      2. Navigate the Labyrinth
                      3. Exit
                      """;

        console.display(menu);
    }

    /**
     * Displays the movement menu for the current room.
     * <p>
     * This method first calls {@link #displayRoomMap()} to show a visual
     * representation of the room and its exits. It then displays the number of
     * available exits and prompts the player to choose a direction. Each
     * available exit is listed with a corresponding numeric option for user
     * input.
     * </p>
     *
     * @param availableExits a list of available exit directions from the
     *                       current room
     */
    private void displayMovementMenu(ArrayList<String> availableExits)
    {

        displayRoomMap();

        int numberOfExits = availableExits.size();
        console.display("There are " + numberOfExits + " exits from " +
                        "this room.");
        console.display("Choose the direction you want to take (1 - " +
                        numberOfExits + "): ");

        for (int i = 1; i <= numberOfExits; i++)
        {
            console.display(i + ". " + availableExits.get(i - 1));
        }
    }

    /**
     * Displays the available character options for the player to choose from.
     * <p>
     * This method presents a formatted menu showing the different character
     * classes, along with their corresponding attributes. It prompts the player
     * to select their identity before the game begins.
     * </p>
     */
    private void displayPlayerOptions()
    {
        String playerOptions = """
                               Stranger… who dares enter the labyrinth?
                               Reveal your identity:
                                    1. Warrior with 100 health, 20 attack and 20 defense
                                    2. Mage with 100 health, 45 attack, and 3 defense
                                    3. Hunter with 100 health, 30 attack and 8 defense
                               """;

        console.display(playerOptions);
    }

    /**
     * Displays a visual representation of the current room's map.
     * <p>
     * This method retrieves the exit configuration of the current room and
     * formats it into a simple ASCII-style map. Each direction (north, east,
     * south, west) is represented using icons and indicates whether an exit
     * exists in that direction. The player’s current position is shown at the
     * centre of the map.
     * </p>
     */
    private void displayRoomMap()
    {
        int[] exits = currentRoom.getExits();

        String north = format("⬆️", exits[0]);
        String east = format("➡️", exits[1]);
        String south = format("⬇️", exits[2]);
        String west = format("⬅️", exits[3]);

        console.lineBreak();

        console.display("  ------" + north + "------");
        console.display("  │              │");
        console.display(west + "     YOU      " + east);
        console.display("  │              │");
        console.display("  ------" + south + "------");
    }

    /**
     * Displays the victory message when the player successfully exits the
     * labyrinth.
     * <p>
     * This method outputs a narrative message indicating that the player has
     * successfully navigated the labyrinth and escaped, marking the end of the
     * game with a win condition.
     * </p>
     */
    private void displayVictoryMessage()
    {
        console.display("You are a force to be reckoned with. Nothing " +
                        "can stop you. You find your way out of the labyrinth" +
                        "and continue your journey for fame.");
    }

    /**
     * Displays the welcome message and game instructions.
     */
    private void displayWelcomeMessage()
    {
        String message = """
                         Welcome to the Labyrinth!
                         
                         You find yourself trapped in a mysterious maze made up of interconnected rooms.
                         Each room may contain hidden dangers… or a path to safety.
                         
                         Explore carefully:
                         
                         Move through rooms using the available exits (North, East, South, West)
                         Watch out for monsters lurking in the shadows
                         Choose your path wisely — not all routes lead forward
                         
                         Your goal is simple:
                         Find your way from Room 1 to the Exit and escape the labyrinth.
                         
                         Good luck, adventurer… you’ll need it.
                         """;
        console.display(message);
    }

    /**
     * Formats a directional exit indicator with a visual symbol.
     * <p>
     * This method appends a lock or blocked symbol to a directional arrow
     * depending on whether an exit is available. If the exit value is greater
     * than 0, it is considered open; otherwise, it is considered blocked.
     * </p>
     *
     * @param arrow the directional arrow symbol representing a direction
     * @param exit  the exit value indicating whether the direction is
     *              accessible
     * @return a formatted string showing the direction and its availability
     * status
     */
    private String format(String arrow, int exit)
    {
        return exit > 0 ?
                arrow + " 🔓" :
                arrow + " ⛔";
    }

    /**
     * Prompts the user for an integer input within a specified range.
     *
     * @param prompt       the message displayed to the user
     * @param errorMessage the message displayed if input is invalid
     * @param min          the minimum acceptable value
     * @param max          the maximum acceptable value
     * @return the validated integer input, or -1 if invalid
     */
    private int getIntInput(String prompt, String errorMessage, int min, int max)
    {
        int input = -1;
        do
        {
            try
            {
                input = console.getIntInput(prompt);
                if (input < min || input > max)
                {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException nfe)
            {
                console.display(errorMessage + " Please enter again.");
            } catch (IllegalArgumentException ie)
            {
                console.display("Please enter a number between " + min + " and " + max);
            }

        } while (input < min || input > max);

        return input;

    }

    /**
     * Returns the player character.
     *
     * @return the player
     */
    public Character getPlayer()
    {
        return player;
    }


    /**
     * Handles movement when the current room has multiple available exits.
     * <p>
     * This method displays a movement menu to the player and prompts them to
     * choose a direction. The selected exit is validated and used to determine
     * the next room. If the chosen exit leads to a special end condition (e.g.
     * room ID 100), the current room is set to {@code null}, indicating the end
     * of the labyrinth. Otherwise, the current room is updated to the
     * corresponding next room.
     * </p>
     *
     * @param availableExits a list of available exit directions from the
     *                       current room
     */

    private void handleMultipleExits(ArrayList<String> availableExits)
    {
        displayMovementMenu(availableExits);
        int choice = getIntInput("Where do you want to go? (1 - " +
                                 availableExits.size() + ")",
                                 "The labyrinth rejects your choice." +
                                 "..try again", 1, availableExits.size());

        int newRoomId = currentRoom.getExitAt(availableExits.get(choice - 1));
        if (newRoomId == 100)
            currentRoom = null;
        else
            currentRoom = currentLabyrinth.getRoomById(newRoomId);
    }

    /**
     * Handles the event that occurs when the player enters a room.
     * <p>
     * This method displays a message indicating the player has entered the
     * current room, then delegates further processing of the room’s state to
     * {@link #handleRoomState()}. This may include encountering a monster or
     * recovering if the room is safe.
     * </p>
     */
    private void handleRoomEntryEvent()
    {
        console.display("You step into " + currentRoom);

        handleRoomState();
    }

    /**
     * Determines and handles the current state of the room.
     * <p>
     * This method checks whether the current room contains a living monster. If
     * a monster is present and still has health remaining, it delegates
     * handling to {@link #handleRoomWithMonster()}. Otherwise, it treats the
     * room as safe and delegates to {@link #handleRoomWithoutMonsters()}.
     * </p>
     */
    private void handleRoomState()
    {
        if (currentRoom.hasMonster())
        {
            handleRoomWithMonster();

        } else
        {
            handleRoomWithoutMonsters();
        }
    }

    /**
     * Handles the encounter when a room contains a monster.
     * <p>
     * If the monster is still alive, the player is notified and a battle is
     * initiated. After the battle, if the player wins, a victory message is
     * displayed along with the player's remaining health.
     * </p>
     *
     * <p>
     * If the monster has already been defeated, the player is informed and
     * allowed to recover health instead.
     * </p>
     */
    private void handleRoomWithMonster()
    {
        if (currentRoom.getMonster().getHealth() > 0)
        {
            console.display("A " + currentRoom.getMonster().getName() +
                            " lurks in this room. It charges at you. You fight " +
                            " for survival.");
            String victor = battle();

            if (victor.equals(player.getName()))
                console.display("You defeated the monster and now have " +
                                player.getHealth() + " hp left");
        } else
        {
            console.display("You have already defeated the monster " +
                            "in this room.");
            recoverPlayer();
        }
    }

    /**
     * Handles the scenario where the current room contains no monsters.
     * <p>
     * This method informs the player that the room is empty of enemies and
     * allows the player to recover health by calling {@link #recoverPlayer()}.
     * </p>
     */
    private void handleRoomWithoutMonsters()
    {
        console.display("There are no monsters in this room.");
        recoverPlayer();
    }

    /**
     * Handles movement when the current room has only one available exit.
     * <p>
     * This method informs the player that there is only one possible path and
     * automatically progresses them to the next room. It retrieves the next
     * room ID based on the single available exit direction and updates the
     * current room accordingly.
     * </p>
     *
     * @param availableExits a list containing exactly one exit direction from
     *                       the current room
     */
    private void handleSingleExit(ArrayList<String> availableExits)
    {
        console.display("There is only one exit in this room.");
        console.display("Taking the exit...");
        int newRoomId = currentRoom.getExitAt(availableExits.getFirst());
        currentRoom = currentLabyrinth.getRoomById(newRoomId);
    }

    /**
     * Initializes the game state by setting up the labyrinth and starting
     * room.
     * <p>
     * This method creates a new {@link LabyrinthManager}, retrieves a randomly
     * selected labyrinth, and assigns it as the current labyrinth. It then sets
     * the starting room to the first room in the labyrinth (index 0), preparing
     * the game for player navigation.
     * </p>
     */
    private void initializeGame()
    {
        LabyrinthManager labyrinthManager = new LabyrinthManager();
        currentLabyrinth = labyrinthManager.getRandomLabyrinth();
        currentRoom = currentLabyrinth.getRoomAt(0);
    }

    /**
     * Initializes the player character based on the selected choice.
     * <p>
     * This method creates a new {@link Character} object with predefined
     * attributes depending on the user's selection. Each character type has
     * different stats for health, attack power, and defence.
     * </p>
     *
     * <p>
     * If the provided choice does not match any valid option, an error message
     * is displayed and no character is created.
     * </p>
     *
     * @param choice the selected character option:
     *               <ul>
     *                   <li>1 = Warrior</li>
     *                   <li>2 = Mage</li>
     *                   <li>3 = Hunter</li>
     *               </ul>
     */
    private void initializePlayerCharacter(int choice)
    {
        switch (choice)
        {
            case 1:
                player = new Warrior();
                break;

            case 2:
                player = new Mage();
                break;

            case 3:
                player = new Hunter();
                break;

            default:
                console.display(
                        "The labyrinth rejects your choice…try again!");
        }
    }
    /**
     * Controls the main gameplay loop for navigating the labyrinth.
     * <p>
     * This method first checks whether the player has selected a valid
     * character. If the player name is "unknown", the method prompts the user
     * to choose a character and exits early.
     * </p>
     *
     * <p>
     * While the player is alive and there are valid rooms to explore, the
     * method:
     * <ul>
     *     <li>Handles events when entering a room</li>
     *     <li>Retrieves the available exits from the current room</li>
     *     <li>Determines the next action based on the number of exits:
     *         <ul>
     *             <li>No exits: triggers a game over scenario</li>
     *             <li>One exit: automatically progresses to the next room</li>
     *             <li>Multiple exits: prompts the player to choose a direction</li>
     *         </ul>
     *     </li>
     * </ul>
     * </p>
     *
     * <p>
     * The loop continues until the player’s health reaches zero or there are no
     * further rooms to navigate.
     * After exiting the loop, the method determines whether the player has won
     * or lost and displays the appropriate message.
     * </p>
     */
    private void navigateTheLabyrinth()
    {
        if (player == null)
        {
            console.display("Bold strategy; entering a maze with no character. " +
                            "Let’s fix that");
            console.lineBreak();
            return;
        }

        do
        {
            handleRoomEntryEvent();

            ArrayList<String> availableExits = currentRoom.getAvailableExits();

            if (availableExits.isEmpty())
            {
                console.lineBreak();
                console.display("You fall into a pit with no chance of " +
                                "escape.");
                displayGameOver();
            } else if (availableExits.size() == 1)
                handleSingleExit(availableExits);
            else
                handleMultipleExits(availableExits);


        } while (player.getHealth() > 0 && currentRoom != null && currentRoom.hasExit());

        if (currentRoom == null && player.getHealth() > 0)
            displayVictoryMessage();
        else
            displayGameOver();

    }

    /**
     * Allows the player to rest and recover a fixed amount of health points
     * (HP).
     * <p>
     * This method defines a constant heal amount and displays a message to the
     * user indicating how much HP will be restored. It then calls
     * {@link Character#recoverHealth(int)} on the player object to apply the
     * healing.
     * </p>
     */
    private void recoverPlayer()
    {
        final int HEAL_AMOUNT = 5;
        console.display("You rest and recover " + HEAL_AMOUNT + " hp.");
        player.recoverHealth(HEAL_AMOUNT);
    }

    private void resetLabyrinth()
    {
        initializeGame();
    }

    /**
     * Resets the player's character by reinitializing it based on the player's
     * current name.
     * <p>
     * This method searches through a predefined list of available character
     * types ("Warrior", "Mage", "Hunter") to find a match with the player's
     * name. Once a match is found, it calls
     * {@link #initializePlayerCharacter(int)} using the corresponding index
     * (adjusted to match the expected choice value).
     * </p>
     *
     * <p>
     * If the player's name does not match any of the available character types,
     * the method will pass an out-of-range value to
     * {@code initializePlayerCharacter}, which is not an issue, since player
     * character is always initialized before navigating the labyrinth.
     * </p>
     */
    private void resetPlayerCharacter()
    {
        String[] availableCharacters = {"Warrior", "Mage", "Hunter"};

        int index = 0;
        while (index < availableCharacters.length &&
               !player.getName().equals(availableCharacters[index]))
        {
            index++;
        }

        initializePlayerCharacter(index + 1);
    }

    /**
     * Runs the full simulation by displaying a menu and processing user input.
     * <p>
     * Allows the user to create a character, navigate the labyrinth, or exit
     * the program.
     * </p>
     */
    public void runSimulation()
    {
        int userChoice = 0;

        displayWelcomeMessage();

        do
        {

            displayMenu();

            userChoice = getIntInput("Enter your choice (1-3): ",
                                     "That is not a valid choice.",
                                     1, 3);
            switch (userChoice)
            {
                case 1:
                    createPlayerCharacter();
                    break;
                case 2:
                    navigateTheLabyrinth();
                    resetPlayerCharacter();
                    resetLabyrinth();
                    break;
                case 3:
                    console.display("Thanks for playing!");
                    break;
            }
        } while (userChoice != 3);
    }

    /**
     * Sets the player character.
     *
     * @param player the player to set
     */
    public void setPlayer(Character player)
    {
        this.player = player;
    }

    /**
     * Returns a string representation of the battle setup.
     *
     * @return a string showing the player and monster
     */
    @Override
    public String toString()
    {
        return "Current Labyrinth: " + currentLabyrinth + '\n' +
               "Current Room: " + currentRoom + '\n' +
               "Player stats: " + player;
    }

}
