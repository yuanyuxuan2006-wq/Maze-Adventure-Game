import java.util.ArrayList;

/**
 * Utility class responsible for generating monsters for rooms in the game.
 * <p>
 * This class provides a method to create a random monster based on the room ID,
 * allowing stronger monsters to appear in later stages of the labyrinth. It
 * maintains a predefined list of monster types with varying health, attack, and
 * defence values.
 * </p>
 *
 * @author Yuan Yuxuan
 * @version 1.0 - created the class and the {@link #generateRandomMonster(int)}
 * method
 */
public class MonsterGenerator
{

    /**
     * Generates a monster based on the room difficulty level.
     * <p>
     * This method creates a predefined list of monsters with varying stats and
     * selects one depending on the room ID. Early rooms generate weaker
     * monsters, while later rooms generate stronger enemies to increase game
     * difficulty. A random element is used within each difficulty range to add
     * variation.
     * </p>
     *
     * @param roomId the ID of the current room, used to determine monster
     *               strength
     * @return a Monster appropriate for the given room difficulty level
     */
    public static Monster generateRandomMonster(int roomId)
    {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        monsters.add(new Monster("Slime", 20, 5, 2));
        monsters.add(new Monster("Rat", 15, 6, 1));
        monsters.add(new Monster("Goblin", 30, 10, 5));
        monsters.add(new Monster("Skeleton", 25, 9, 4));
        monsters.add(new Monster("Orc", 50, 15, 8));
        monsters.add(new Monster("Dragon", 100, 25, 15));

        int random = (int) (Math.random() * 2);
        if (roomId <= 2)
            return monsters.get(random);
        else if (roomId <= 4)
            return monsters.get(random + 2);
        else if (roomId <= 5)
            return monsters.get(4);


        return monsters.get(5);

    }

}
