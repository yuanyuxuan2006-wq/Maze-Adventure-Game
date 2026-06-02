import java.util.ArrayList;

/**
 * The {@code Room} class represents a single location in a labyrinth.
 * <p>
 * Each room has:
 * <ul>
 *   <li>A unique identifier ({@code id})</li>
 *   <li>An array of exits representing directions (North, East, South, West)</li>
 *   <li>A {@link Monster} that inhabits the room</li>
 * </ul>
 * </p>
 *
 * <p>
 * The exits array uses indices to represent directions:
 * <ul>
 *   <li>0: North</li>
 *   <li>1: East</li>
 *   <li>2: South</li>
 *   <li>3: West</li>
 * </ul>
 * A value greater than 0 indicates a valid exit.
 * </p>
 *
 * @author Yuan Yuxuan
 * @version 1.0 - created a Room with id, exits and monster and all the basic
 * class components
 */
public class Room
{

    private int id;
    // each index in exits represents a direction
    // 0: North, 1: East, 2: South, 3: West
    private int[] exits;
    private Monster monster;

    /**
     * Default constructor that initializes the room with:
     * <ul>
     *   <li>id = -1</li>
     *   <li>an empty exits array</li>
     *   <li>a default Goblin monster</li>
     * </ul>
     */
    public Room()
    {
        this(-1,
             new int[4]
        );
    }

    /**
     * Constructs a Room with a given ID and exit configuration.
     * <p>
     * This constructor initializes the room's identifier and its available
     * exits. It also randomly determines whether the room contains a monster.
     * There is a 50% chance that a monster will be generated for the room using
     * the {@link MonsterGenerator}, otherwise the room will be created without
     * a monster.
     * </p>
     *
     * @param id    the unique identifier of the room
     * @param exits an array representing exit connections in the order: North,
     *              East, South, West
     */
    public Room(int id, int[] exits)
    {
        this.id = id;
        this.exits = exits;

        if ((int) (Math.random() * 100) < 50)
            this.monster = MonsterGenerator.generateRandomMonster(this.id);
        else
            this.monster = null;
    }

    /**
     * Generates a human-readable description of available exits.
     *
     * @return a formatted string describing the exits of the room
     */
    private String availableExits()
    {
        ArrayList<String> availableExits = getAvailableExits();

        if (availableExits.isEmpty())
            return "no exits";
        else if (availableExits.size() == 1)
            return "exit at " + availableExits.getFirst();

        String result = String.join(", ", availableExits);

        int lastComma = result.lastIndexOf(", ");
        if (lastComma != -1)
        {
            result = result.substring(0, lastComma)
                     + " and "
                     + result.substring(lastComma + 2);
        }

        return "exits at " + result;
    }

    /**
     * Compares this room with another room for equality.
     * <p>
     * Two rooms are considered equal if they have the same id.
     * </p>
     *
     * @param room the room to compare with
     * @return {@code true} if the rooms are equal, otherwise {@code false}
     */
    public boolean equals(Room room)
    {
        return this.id == room.id;
    }

    /**
     * Retrieves a list of all available exit directions from the room.
     * <p>
     * This method checks the room's exit array and determines which directions
     * are valid (i.e. have a value greater than 0). It then returns a list of
     * corresponding direction names that the player can use to navigate.
     * </p>
     *
     * @return an ArrayList of available exit directions (e.g. North, East,
     * South, West)
     */
    public ArrayList<String> getAvailableExits()
    {
        ArrayList<String> availableExits = new ArrayList<String>();
        if (exits[0] > 0)
            availableExits.add("North");
        if (exits[1] > 0)
            availableExits.add("East");
        if (exits[2] > 0)
            availableExits.add("South");
        if (exits[3] > 0)
            availableExits.add("West");
        return availableExits;
    }

    /**
     * Returns the exit value at a specific index.
     *
     * @param index the index representing a direction (0–3)
     * @return the exit value, or 0 if the index is invalid
     */
    public int getExitAt(int index)
    {
        if (index >= 0 && index < exits.length)
            return exits[index];

        return 0;
    }

    /**
     * Retrieves the room ID associated with a given exit direction.
     * <p>
     * This method maps a direction string (North, East, South, West) to its
     * corresponding value in the exits array and returns the room ID linked to
     * that direction.
     * </p>
     *
     * @param direction the direction of the exit (North, East, South, or West)
     * @return the room ID associated with the specified direction
     */
    public int getExitAt(String direction)
    {
        if (direction.equals("North"))
            return exits[0];
        else if (direction.equals("East"))
            return exits[1];
        else if (direction.equals("South"))
            return exits[2];

        return exits[3];
    }

    /**
     * Returns the exits array.
     *
     * @return the exits array
     */
    public int[] getExits()
    {
        return exits;
    }

    /**
     * Returns the room's id.
     *
     * @return the room id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Returns the monster in the room.
     *
     * @return the {@link Monster} object
     */
    public Monster getMonster()
    {
        return monster;
    }

    /**
     * Checks whether the room has at least one valid exit.
     * <p>
     * This method iterates through the exits array and determines if there is
     * any direction with a value greater than 0, indicating an available exit.
     * </p>
     *
     * @return {@code true} if at least one exit exists, {@code false} otherwise
     */
    public boolean hasExit()
    {
        int index = 0;
        while (index < exits.length && exits[index] == 0)
            index++;

        return index < exits.length;
    }

    /**
     * Checks whether the room contains a monster.
     * <p>
     * This method determines if a monster object has been assigned to the room.
     * A non-null monster indicates that the room contains an active or defeated
     * enemy entity.
     * </p>
     *
     * @return {@code true} if a monster exists in the room, {@code false}
     * otherwise
     */
    public boolean hasMonster()
    {
        return monster != null;
    }

    /**
     * Sets the exit value at a specific index.
     *
     * @param index the index representing a direction (0–3)
     * @param value the exit value to set
     */
    public void setExitAt(int index, int value)
    {
        if (index >= 0 && index < exits.length)
            exits[index] = value;
    }

    /**
     * Sets the exits array.
     *
     * @param exits the new exits array (must not be null and must have length
     *              4)
     */
    public void setExits(int[] exits)
    {
        if (exits != null && exits.length == 4)
            this.exits = exits;
    }

    /**
     * Sets the room's id.
     *
     * @param id the new room id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Sets the monster in the room.
     *
     * @param monster the new monster (must not be null)
     */
    public void setMonster(Monster monster)
    {
        if (monster != null)
            this.monster = monster;
    }

    /**
     * Returns a string representation of the room.
     * <p>
     * Includes the room id, the monster present, and available exits.
     * </p>
     *
     * @return a formatted string describing the room
     */
    public String toString()
    {
        return "Room " + id + ", which has a " +
               ((monster == null) ?
                       "no monsters" :
                       monster.getName()) +
               " and " + availableExits();
    }

}
