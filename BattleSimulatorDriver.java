/**
 * The main driving class to the Labyrinth Battle Simulator.
 *
 * @author Yuan Yuxuan
 * @version 1.0 - created a scaffold for Task 1 with a main method and to-do
 * tasks
 */
public class BattleSimulatorDriver
{

    /**
     * Main method, this is where execution starts
     *
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        // creating an object of the BattleSimManager class so that we can
        // call the runSimulation method
        BattleSimManager battleSimManager = new BattleSimManager();
        // calling / invoking the runSimulation method
        battleSimManager.runSimulation();
    }

}
