package savanna.animal;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

/**
 * A single animal that exists in SavannaWorld
 */
public abstract class Animal {

    /**
     * Do not change how these variables work!
     **/
    protected Grid<Animal> grid;
    protected Location location;
    protected Color color;


    protected char size;
    protected char diet;
    protected boolean flying;
    protected int starveTime;
    protected double reproductionRate;


    /***********************************
     * Do not change anything below here!
     ***********************************/

    /**
     * Act according to the subclass rules
     */
    public abstract void act();


    /**
     * Gets the color of this animal.
     *
     * @return the color of this animal
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of this animal.
     *
     * @param newColor the new color
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Gets the grid in which this animal is located.
     *
     * @return the grid of this animal, or <code>null</code> if this animal is
     * not contained in a grid
     */
    public Grid<Animal> getGrid() {
        return grid;
    }

    /**
     * Gets the location of this animal.
     *
     * @return the location of this animal, or <code>null</code> if this animal is
     * not contained in a grid
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Puts this animal into a grid. If there is another animal at the given
     * location, it is removed. <br />
     * Precondition: (1) This animal is not contained in a grid (2)
     * <code>loc</code> is valid in <code>gr</code>
     *
     * @param gr  the grid into which this animal should be placed
     * @param loc the location into which the animal should be placed
     */
    public void putSelfInGrid(Grid<Animal> gr, Location loc) {
        if (grid != null)
            throw new IllegalStateException(
                    "This animal is already contained in a grid.");

        Animal animal = gr.get(loc);
        if (animal != null)
            animal.removeSelfFromGrid();
        gr.put(loc, this);
        grid = gr;
        location = loc;
    }

    /**
     * Removes this animal from its grid. <br />
     * Precondition: This animal is contained in a grid
     */
    public void removeSelfFromGrid() {
        if (grid == null)
            throw new IllegalStateException(
                    "This animal is not contained in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different animal at location "
                            + location + ".");

        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Moves this animal to a new location. If there is another animal at the
     * given location, it is removed. <br />
     * Precondition: (1) This animal is contained in a grid (2)
     * <code>newLocation</code> is valid in the grid of this animal
     *
     * @param newLocation the new location
     */
    public void moveTo(Location newLocation) {
        if (grid == null)
            throw new IllegalStateException("This animal is not in a grid.");
        if (!grid.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        if (newLocation.equals(location))
            return;
        grid.remove(location);
        Animal other = grid.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        grid.put(location, this);
    }
}


