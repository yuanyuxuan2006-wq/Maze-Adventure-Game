/**
 * The Hunter class represents a fast ranged attacker with balanced
 * combat abilities.
 * <p>
 * A Hunter specialises in rapid attacks and agility. Its special
 * ability is Double Shot, which performs two boosted attacks.
 * </p>
 *
 * This class demonstrates inheritance by extending the Character class
 * and method overriding through its own implementation of
 * specialAbility().
 *
 * @author Yuan Yuxuan
 * @version 1.0
 */
public class Hunter extends Character
{
    /**
     * Default constructor for Hunter.
     * Initializes the Hunter with predefined stats.
     */
    public Hunter()
    {
        super("Hunter", 100, 30, 8);
    }

    /**
     * Executes the Hunter's special ability.
     *
     * @return the total damage dealt by the Hunter's double shot attack
     */
    @Override
    public int specialAbility()
    {
        return (int)(getAttack() * 0.75) * 2;
    }
}