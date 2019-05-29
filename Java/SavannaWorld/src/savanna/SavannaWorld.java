package savanna;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.grid.UnboundedGrid;
import info.gridworld.world.World;
import savanna.animal.Animal;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The SavannaWorld class represents the World.
 */
public class SavannaWorld extends World<Animal> {

    private static final String DEFAULT_MESSAGE = "Click on a grid location to construct or manipulate an actor.";

    /**
     * Constructs an actor world with a default grid.
     */
    public SavannaWorld()
    {
        super(new BoundedGrid<>(20,20));
    }

    /**
     * Constructs an actor world with a given grid.
     * @param grid the grid for this world.
     */
    public SavannaWorld(Grid<Animal> grid)
    {
        super(grid);
    }

    /**
     * Show the world GUI
     */
    public void show()
    {
        if (getMessage() == null)
            setMessage(DEFAULT_MESSAGE);
        super.show();
    }

    /**
     * Adds an actor to this world at a given location.
     * @param loc the location at which to add the actor
     * @param actor the actor to add
     */
    public void add(Location loc, Animal actor)
    {
        actor.putSelfInGrid(getGrid(), loc);
    }

    /**
     * Adds an actor at a random empty location.
     * @param actor the actor to add
     */
    public void add(Animal actor)
    {
        Location loc = getRandomEmptyLocation();
        if (loc != null)
            add(loc, actor);
    }

    /**
     * Removes an actor from this world.
     * @param loc the location from which to remove an actor
     * @return the removed actor, or null if there was no actor at the given
     * location.
     */
    public Animal remove(Location loc)
    {
        Animal animal = getGrid().get(loc);
        if (animal == null)
            return null;
        animal.removeSelfFromGrid();
        return animal;
    }

    /**
     * Step through the simulation once.
     */
    public void step()
    {
        Grid<Animal> gr = getGrid();
        ArrayList<Animal> actors = new ArrayList<Animal>();
        for (Location loc : gr.getOccupiedLocations()) {
            actors.add(gr.get(loc));
        }

        Collections.shuffle(actors);

        for (Animal a : actors)
        {
            // only act if another actor hasn't removed a
            if (a.getGrid() == gr)
                a.act();
        }
    }

}
