package savanna;

import info.gridworld.grid.Location;
import info.gridworld.world.World;
import savanna.animal.*;

import java.util.ArrayList;
import java.util.List;

import static info.gridworld.grid.Location.*;

/**
 * The runner class for SavannaWorld
 */
public class SavannaWorldRunner {

    public static List<Integer> directions;
    /**
     * Starts SavannaWorld
     * @param args
     */
    public static void main(String[] args) {
        SavannaWorld world = new SavannaWorld();




        // TODO: Create animals here by calling world.add()
        world.add(new Hare());
        world.add(new GuineaFowl());
        world.add(new Impala());
        world.add(new Elephant());
        world.add(new Hawk());
        world.add(new Jackal());
        world.add(new Cheetah());
        world.add(new Lion());

        world.show();
    }

}
