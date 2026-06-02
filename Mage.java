/**
 * The Mage class represents a magic-based character with high attack
 * power but low defence.
 * <p>
 * A Mage uses powerful magical attacks to defeat enemies. Its special
 * ability is Fireball, which adds bonus damage to the base attack.
 * </p>
 *
 * This class demonstrates inheritance by extending the Character class
 * and method overriding through its own implementation of
 * specialAbility().
 *
 * @author Yuan Yuxuan
 * @version 1.0
 */
public class Mage extends Character
{
    /**
     * Default constructor for Mage.
     * Initializes the Mage with predefined stats.
     */
    public Mage()
    {
        super("Mage", 100, 45, 3);
    }

    /**
     * Executes the Mage's special ability.
     *
     * @return the Mage's attack value plus bonus fire damage
     */
    @Override
    public int specialAbility()
    {
        return getAttack() + 15;
    }
}