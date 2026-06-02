import do_not_edit.FileIO;

import java.util.ArrayList;

/**
 * Manages a collection of Labyrinth objects and provides functionality for
 * loading, storing, and retrieving labyrinth instances.
 * <p>
 * This class is responsible for reading labyrinth data from an external file,
 * constructing Labyrinth and Room objects, and making them available to the
 * rest of the application. It also supports selecting a random labyrinth for
 * gameplay.
 * </p>
 */
public class LabyrinthManager
{

    private ArrayList<Labyrinth> labyrinths;

    /**
     * Constructs a LabyrinthManager and loads all available labyrinths from
     * file.
     */
    public LabyrinthManager()
    {
        labyrinths = loadLabyrinths();
    }

    /**
     * Constructs a LabyrinthManager with a predefined list of labyrinths.
     *
     * @param labyrinths the list of labyrinths to manage
     */
    public LabyrinthManager(ArrayList<Labyrinth> labyrinths)
    {
        if (labyrinths != null && !labyrinths.isEmpty())
            this.labyrinths = labyrinths;
    }

    /**
     * Returns the list of all loaded labyrinths.
     *
     * @return an ArrayList of Labyrinth objects
     */
    public ArrayList<Labyrinth> getLabyrinths()
    {
        return labyrinths;
    }

    /**
     * Retrieves a random labyrinth from the available collection.
     * <p>
     * If no labyrinths are loaded or the list is empty, this method returns
     * null.
     * </p>
     *
     * @return a randomly selected Labyrinth, or null if none exist
     */
    public Labyrinth getRandomLabyrinth()
    {
        if (labyrinths == null || labyrinths.isEmpty())
            return null;

        return labyrinths.get((int) (Math.random() * labyrinths.size()));
    }

    /**
     * Loads labyrinth data from a file and constructs Labyrinth and Room
     * objects.
     * <p>
     * This method reads structured text data, where each labyrinth is defined
     * by a "LABYRINTH" header and contains multiple "ROOM" entries. Each room
     * stores directional exits (north, east, south, west) and is added to its
     * respective labyrinth.
     * </p>
     *
     * @return a list of fully constructed Labyrinth objects
     */
    private ArrayList<Labyrinth> loadLabyrinths()
    {
        FileIO fileIO = new FileIO();
        ArrayList<Labyrinth> labyrinths = new ArrayList<>();
        Labyrinth currentLab = null;

        for (String line : fileIO.readFile())
        {

            if (line.isEmpty()) continue;

            // New labyrinth
            if (line.startsWith("LABYRINTH"))
            {
                currentLab = new Labyrinth();
                currentLab.setId(Integer.parseInt(line.split(" ")[1]));
                labyrinths.add(currentLab);
            }

            // Room line
            else if (line.startsWith("ROOM"))
            {
                String[] parts = line.split(" ");

                int[] exits = new int[4];
                exits[0] = Integer.parseInt(parts[2]); // north
                exits[1] = Integer.parseInt(parts[3]); // east
                exits[2] = Integer.parseInt(parts[4]); // south
                exits[3] = Integer.parseInt(parts[5]); // west

                Room room = new Room(Integer.parseInt(parts[1]), exits);

                if (currentLab != null)
                    currentLab.addRoom(room);
            }
        }

        return labyrinths;
    }

    /**
     * Sets the list of managed labyrinths.
     *
     * @param labyrinths the new list of Labyrinth objects
     */
    public void setLabyrinths(ArrayList<Labyrinth> labyrinths)
    {
        this.labyrinths = labyrinths;
    }
}
