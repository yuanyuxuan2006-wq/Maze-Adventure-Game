/**
 * The {@code Character} class represents an entity in the game with basic
 * combat attributes such as health, attack, and defence.
 * <p>
 * A character can take damage, which is reduced based on its defence, and
 * provides getter and setter methods to access and modify its state.
 * </p>
 *
 * @author Yuan Yuxuan
 * @version <p>1.0 - created the basic scaffold for the Character class</p>
 * <p> a takeDamage method that reduces the character health</p>
 */
public abstract class Character
{

    private String name;
    private int health;
    private int attack;
    private int defense;

    /**
     * Default constructor that initializes a character with default values.
     */
    protected Character()
    {
    }

    /**
     * Constructs a character with specified attributes.
     *
     * @param name    the name of the character
     * @param health  the health value of the character
     * @param attack  the attack value of the character
     * @param defense the defence value of the character
     */
    protected Character(String name, int health, int attack, int defense)
    {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * Returns the attack value of the character.
     *
     * @return the attack value
     */
    public int getAttack()
    {
        return attack;
    }

    /**
     * Returns the defense value of the character.
     *
     * @return the defense value
     */
    public int getDefense()
    {
        return defense;
    }


    /**
     * Returns the current health of the character.
     *
     * @return the health value
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * Returns the name of the character.
     *
     * @return the character's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Restores the character's health by a specified amount.
     * <p>
     * This method increases the character's health by the given heal amount,
     * ensuring that the health value does not exceed the maximum limit of 100.
     * </p>
     *
     * @param healAmount the amount of health to restore
     */
    public void recoverHealth(int healAmount)
    {
        health = Math.min(100, health + healAmount);
    }

    /**
     * Sets the attack value of the character.
     *
     * @param attack the new attack value
     */
    public void setAttack(int attack)
    {
        this.attack = attack;
    }

    /**
     * Sets the defense value of the character.
     *
     * @param defense the new defense value
     */
    public void setDefense(int defense)
    {
        this.defense = defense;
    }

    /**
     * Sets the health value of the character.
     *
     * @param health the new health value
     */
    public void setHealth(int health)
    {
        this.health = health;
    }

    /**
     * Sets the name of the character.
     *
     * @param name the new name of the character
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Executes the character's special ability based on their class.
     * <p>
     * This method determines which special attack to use depending on the
     * character's name. Each character type has a unique ability that deals
     * different amounts of damage.
     * </p>
     *
     * <p>
     * Character abilities:
     * <ul>
     *     <li>Warrior -> Shield Slam</li>
     *     <li>Mage -> Fireball</li>
     *     <li>Hunter -> Double Shot</li>
     * </ul>
     * </p>
     *
     * @return the damage dealt by the selected special ability
     */
    public abstract int specialAbility();

    /**
     * Calculates and applies damage taken from an opponent's attack.
     * <p>
     * Damage is reduced by 50% of the character's defense value. The resulting
     * damage cannot be less than 0. The final damage is subtracted from the
     * character's health.
     * </p>
     *
     * @param opponentAttack the attack value of the opponent
     * @return the actual damage taken after defense reduction
     */

    public int takeDamage(int opponentAttack)
    {
        int damageTaken = Math.max(0, (opponentAttack - (int) (defense * 0.5)));
        health = Math.max(0, health - damageTaken);
        return damageTaken;
    }

    /**
     * Returns a string representation of the character.
     * <p>
     * Includes the name, health, attack, and defence values.
     * </p>
     *
     * @return a formatted string describing the character
     */
    @Override
    public String toString()
    {
        return name + " (" + health + "HP, " +
               attack + " attack, " +
               defense + " armor)";
    }
}
