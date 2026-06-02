/**
 * The Warrior class represents a heavily armoured melee fighter.
 * <p>
 * A Warrior has high defence and balanced attack power. Its special
 * ability is Shield Slam, which deals double the normal attack damage.
 * </p>
 *
 * This class demonstrates inheritance by extending the Character class
 * and method overriding through its own implementation of
 * specialAbility().
 *
 * @author Yuan Yuxuan
 * @version 1.0
 */
public class Warrior extends Character
{
    /**
     * Default constructor for Warrior.
     * Initializes the Warrior with predefined stats.
     */
    public Warrior()
    {
        super("Warrior", 100, 20, 20);
    }

    /**
     * Executes the Warrior's special ability.
     *
     * @return double the Warrior's attack value
     */
    @Override
    public int specialAbility()
    {
        return getAttack() * 2;
    }
}