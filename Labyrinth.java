import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The {@code Labyrinth} class represents a collection of {@link Room} objects
 * arranged in a fixed-size structure (maximum of 7 rooms).
 * <p>
 * It provides functionality to:
 * <ul>
 *   <li>Add rooms to the labyrinth (either at the next available position or a specific index).</li>
 *   <li>Ensure no duplicate rooms (based on room id) are added.</li>
 *   <li>Remove rooms by their unique identifier.</li>
 *   <li>Track the total number of rooms currently stored.</li>
 * </ul>
 *
 * <p>
 * The class maintains an internal array of {@code Room} objects and uses the
 * static field {@code numberOfRooms} to keep track of how many rooms have been created.
 * </p>
 *
 * <p>
 * Constraints:
 * <ul>
 *   <li>The labyrinth can hold a maximum of 7 rooms.</li>
 *   <li>Null rooms cannot be added.</li>
 *   <li>Duplicate rooms (based on id) are not allowed.</li>
 * </ul>
 *
 * <p>
 * This class is designed for use in a labyrinth-based application where rooms
 * need to be managed, stored, and accessed efficiently.
 * </p>
 *
 * @author Yuan Yuxuan
 * @version 1.0 - created a scaffold for the Labyrinth class
 */
public class Labyrinth
{
    // public access modifier because it is static (class variable) so does not
    // need to be called using an object - and is already loaded in the memory
    // with the class - so can be directly accessed could create a get method
    // to avoid accidental change and only access value through it but need it
    // in test file for easier access, so public for now static so that it is a
    // class variable and allows to count how many objects (rooms) were created
    public static int numberOfRooms;
    private final int CAPACITY = 7;
    private int id;
    private Room[] rooms;


    public Labyrinth()
    {
        numberOfRooms = 0;
        rooms = new Room[CAPACITY];
    }

    /**
     * Adds a {@link Room} to the next available position in the array of
     * rooms.
     * <p>
     * A room will only be added if:
     * <ul>
     *   <li>The array is not already full (maximum capacity of 7 rooms).</li>
     *   <li>The {@code Room} object provided is not {@code null}.</li>
     *   <li>No other rooms with the same id already exists in the array.</li>
     * </ul>
     * After successful addition, the {@code numberOfRooms} is incremented.
     *
     * @param room the {@code Room} to be added
     * @return {@code true} if the room was successfully added, {@code false} if
     * the array is full, the room is invalid, or a room with the same id
     * already exists
     */
    public boolean addRoom(Room room)
    {
        boolean added = false;

        if (numberOfRooms >= CAPACITY || room == null || contains(room))
            return added;

        rooms[nextAvailableIndex()] = room;
        numberOfRooms++;
        added = true;
        return added;
    }

    /**
     * Adds a {@link Room} to the specified position in the array of rooms
     * shifting elements to the right if necessary to make space.
     * <p>
     * A room will only be added if:
     * <ul>
     *   <li>The array is not already full (maximum capacity of 7 rooms).</li>
     *   <li>The {@code Room} object provided is not {@code null}.</li>
     *   <li>No other room with the same id already exists in the array.</li>
     *   <li>The specified index is within the valid range of the array (0–6).</li>
     * </ul>
     * After successful addition, the {@code numberOfRooms} is incremented.
     *
     * @param room       the {@code Room} to be added
     * @param indexToAdd the index where {@code Room} is to be added
     * @return {@code true} if the room was successfully added, {@code false} if
     * the array is full, the room is invalid, or a room with the same name
     * already exists
     */
    public boolean addRoom(Room room, int indexToAdd)
    {
        boolean added = false;

        // assuming you want to add to the next available index if there are
        // null elements to the left of the given indexToAdd
        // if (indexToAdd >= numberOfRooms && indexToAdd < CAPACITY && room != null)
        //    addRoom(room);

        // assuming you ignore any add if the indexToAdd is less than the numberOfRooms
        if (indexToAdd >= numberOfRooms || room == null || contains(room))
            return added;

        for (int index = CAPACITY - 2; index >= indexToAdd; index--)
        {
            rooms[index + 1] = rooms[index];
            rooms[index] = null;
        }

        rooms[indexToAdd] = room;
        numberOfRooms++;
        added = true;
        return added;
    }

    /**
     * Checks whether the given {@link Room} exists in the array.
     * <p>
     * The search iterates through the array and returns {@code true} if it
     * finds a non-{@code null} element that is equal to the provided
     * {@code Room}. Equality is determined using the {@link Room#equals(Room)}
     * method of the {@code Room} class.
     * </p>
     *
     * @param room the {@code Room} to search for
     * @return {@code true} if the array contains a matching {@code Room},
     * {@code false} if no match is found or if the provided {@code Room} is
     * {@code null}
     */
    private boolean contains(Room room)
    {
        boolean found = false;
        int index = 0;
        while (!found && index < rooms.length)
        {
            if (index < numberOfRooms && rooms[index].equals(room))
                found = true;

            index++;
        }

        return found;

    }

    public int getCAPACITY()
    {
        return CAPACITY;
    }

    public int getId()
    {
        return id;
    }

    /**
     * Finds the index of the room with the specified ID.
     *
     * <p>This method searches through the {@code rooms} array up to
     * {@code numberOfRooms} and returns the index of the first room whose ID
     * matches the given {@code idToRemove}.</p>
     *
     * @param idToRemove the ID of the room to locate
     * @return the index of the matching room, or {@code -1} if no such room
     * exists
     */
    private int getIndexToRemove(int idToRemove)
    {
        // int indexToRemove = -1;
        // int i = 0;

        // while (i < rooms.length && indexToRemove == -1)
        // {
        //     if (rooms[i].getId() == idToRemove)
        //     {
        //         indexToRemove = i;
        //     }
        //     i++;
        // }
        // return indexToRemove;

        return IntStream.range(0, numberOfRooms)
                        .filter(i -> rooms[i].getId() == idToRemove)
                        .findFirst()
                        .orElse(-1);
    }

    /**
     * Retrieves the total number of rooms.
     * <p>
     * This method returns the value of the static variable that tracks how many
     * rooms have been created or are available in the system.
     * </p>
     *
     * @return the total number of rooms
     */
    public static int getNumberOfRooms()
    {
        return numberOfRooms;
    }

    /**
     * Retrieves a room at a specified index.
     * <p>
     * This method returns the Room object stored at the given index in the rooms
     * array, provided that the index is within bounds and the room exists.
     * If the index is invalid or no room is present at that position, null is returned.
     * </p>
     *
     * @param index the position of the room in the rooms array
     * @return the Room at the specified index, or null if not found
     */
    public Room getRoomAt(int index)
    {
        if (index < numberOfRooms && rooms[index] != null)
            return rooms[index];

        return null;
    }

    /**
     * Retrieves a room based on its unique identifier.
     * <p>
     * This method searches through the list of rooms to find a room with a
     * matching ID. If a matching room is found, it is returned. If no matching
     * room exists or the provided ID is out of bounds, null is returned.
     * </p>
     *
     * @param newRoomId the unique identifier of the room to retrieve
     * @return the Room with the matching ID, or null if not found
     */
    public Room getRoomById(int newRoomId)
    {
        if (newRoomId < 0 || newRoomId >= numberOfRooms)
            return null;

        int index = 0;
        while (index < numberOfRooms && rooms[index].getId() != newRoomId)
        {
            index++;
        }

        return index == numberOfRooms ? null : rooms[index];
    }

    /**
     * Returns a copy of the rooms array.
     * <p>
     * A copy is returned instead of the original array to prevent external
     * modification of the internal state of the Labyrinth.
     * </p>
     *
     * @return a copy of the rooms array
     */
    public Room[] getRooms()
    {
        return Arrays.copyOf(rooms, rooms.length);
    }

    /**
     * Finds the next available index in the {@code rooms} array where a new
     * {@link Room} can be added.
     * <p>
     * The search starts from index {@code 0} and continues until either:
     * <ul>
     *   <li>a {@code null} slot is found, or</li>
     *   <li>the number of currently stored rooms ({@code numberOfRooms}) is reached.</li>
     * </ul>
     * </p>
     *
     * @return the index of the next available slot in the array; if the array
     * is full, this will return {@code numberOfRooms}
     */
    private int nextAvailableIndex()
    {
        int index = 0;

        while (index < numberOfRooms && rooms[index] != null)
        {
            index++;
        }

        return index;

    }

    /**
     * Removes a {@link Room} from the array by its id.
     * <p>
     * This method searches for a room whose id matches the provided {@code id}.
     * If a matching room is found, it is removed.
     * </p>
     * After removal, {@code numberOfRooms} is decremented. This method should
     * also shift subsequent elements to the left to fill the gap (if attempted
     * HD task).
     *
     * @param idToRemove the id of the {@code Room} to remove
     * @return {@code true} if a room with the specified id was found and
     * removed, {@code false} if no matching room exists or if the array is
     * empty
     */
    public boolean removeRoomById(int idToRemove)
    {
        boolean removed = false;

        if (numberOfRooms == 0)
            return removed;


        int indexToRemove = getIndexToRemove(idToRemove);


        if (indexToRemove != -1)
        {
            for (int index = indexToRemove; index < numberOfRooms; index++)
            {
                rooms[index] = rooms[index + 1];
                rooms[index + 1] = null;
            }
            removed = true;
        }

        if (removed)
            numberOfRooms--;

        return removed;
    }

    /**
     * Sets the unique identifier for the room.
     * <p>
     * This method assigns the provided value to the room's ID field.
     * </p>
     *
     * @param id the unique identifier to assign to the room
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Sets the total number of rooms in the labyrinth system.
     * <p>
     * This method updates the static variable that tracks the number of rooms
     * managed by the Labyrinth class.
     * </p>
     *
     * @param numberOfRooms the total number of rooms to set
     */
    public static void setNumberOfRooms(int numberOfRooms)
    {
        Labyrinth.numberOfRooms = numberOfRooms;
    }

    /**
     * Sets the rooms array using a copy of the provided array.
     * <p>
     * A copy is created to preserve encapsulation and prevent external
     * modification of the internal rooms array.
     * </p>
     *
     * @param rooms the array of {@link Room} objects to set
     */
    public void setRooms(Room[] rooms)
    {
        if (rooms != null)
            this.rooms = Arrays.copyOf(rooms, rooms.length);
    }

    /**
     * Returns a string representation of the Labyrinth.
     * <p>
     * The returned string includes the total number of rooms currently stored
     * in the labyrinth.
     *
     * @return a string describing the labyrinth and its number of rooms
     */
    public String toString()
    {
        return "Labyrinth " + id + " with " + numberOfRooms + " rooms";
    }


}
