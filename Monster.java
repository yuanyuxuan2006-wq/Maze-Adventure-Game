/**
 * This class represents a monster in a Labyrinth Battle Simulator.
 *
 * @author Yuan Yuxuan
 * @version 1.0 - A basic Monster class with a name field, constructors,
 * getters, setters and toString method
 */
public class Monster
{

    private String name;
    private int health;
    private int attack;
    private int defense;

    /**
     * Default constructor that sets the name of the Monster to unknown
     */
    public Monster()
    {
        this("unknown", 100, 10, 10);
    }

    /**
     * Parameterized constructor that initializes the name of the Monster to the
     * parsed name
     *
     * @param name    the name of the monster
     * @param health  the health value for the monster
     * @param attack  the attack value for the monster
     * @param defense the defense value for the monster
     */
    public Monster(String name, int health, int attack, int defense)
    {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * Accessor method for attack of the monster
     *
     * @return attack of the monster
     */
    public int getAttack()
    {
        return attack;
    }

    /**
     * Accessor method for defense of the monster
     *
     * @return defense of the monster
     */
    public int getDefense()
    {
        return defense;
    }

    /**
     * Accessor method for health of the monster
     *
     * @return health of the monster
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * Accessor method for name of the monster
     *
     * @return name of the monster
     */
    public String getName()
    {
        return name;
    }

    /**
     * A basic mutator method for attack of the monster, no guardian code.
     *
     * @param attack new attack for the monster
     */
    public void setAttack(int attack)
    {
        this.attack = attack;
    }

    /**
     * A basic mutator method for defense of the monster, no guardian code.
     *
     * @param defense new defense for the monster
     */
    public void setDefense(int defense)
    {
        this.defense = defense;
    }

    /**
     * A basic mutator method for health of the monster, no guardian code.
     *
     * @param health new health for the monster
     */
    public void setHealth(int health)
    {
        this.health = health;
    }

    /**
     * A basic mutator method for name of the monster, no guardian code.
     *
     * @param name new name for the monster
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Calculates and applies damage taken from an opponent's attack.
     * <p>
     * The damage is reduced based on the current defense value. Specifically,
     * 50% of the defense is subtracted from the opponent's attack value. The
     * final damage cannot be less than 0.
     * </p>
     * <p>
     * The calculated damage is then deducted from the entity's health.
     * </p>
     *
     * @param opponentAttack the attack value of the opponent
     * @return the actual amount of damage taken after defense reduction
     */
    public int takeDamage(int opponentAttack)
    {
        int damageTaken = Math.max(0, (opponentAttack - (int) (defense * 0.5)));
        health -= damageTaken;
        return damageTaken;
    }

    /**
     * Returns a String containing the name of the playlist
     *
     * @return playlist name
     */
    public String toString()
    {
        return name + " (" + health + " HP, " + attack + "attack, " +
               defense + " defence)";
    }

}
