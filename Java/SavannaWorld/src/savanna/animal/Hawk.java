package savanna.animal;

import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

import java.util.List;

public class Hawk extends Animal implements Liveable {

    private final int STARVE_TIME = 6;
    private final double REPRODUCTION_RATE = 0.03;

    public Hawk() {
        super.color = Color.RED;
        this.size = 'S';
        this.diet = 'C';
        this.flying = true;
        this.starveTime = STARVE_TIME;
        this.reproductionRate = 0;

    }

    @Override
    public void act() {
        this.starvationCheck();
        if (this.grid == null) {
            return;
        }
        this.attemptToMove();
        this.attemptToEat();
        this.attemptToReproduce();
    }

    @Override
    public void starvationCheck() {
        if (this.starveTime == 0) {
            this.removeSelfFromGrid();
            return;
        } else {
            this.starveTime--;
        }
    }

    @Override
    public void attemptToMove() {

        ArrayList<Animal> neighbors;
        Location runAwayLocation;
        ArrayList<Location> runAwayLocations;


        neighbors = grid.getNeighbors(this.getLocation());
        for (Animal neighbor : neighbors) {
            if ((neighbor.diet == 'C' && neighbor.size == 'M') || (neighbor.diet == 'C' && neighbor.size == 'L')) {
                runAwayLocation = location.getAdjacentLocation(location.getDirectionAway(neighbor.getLocation()));
                runAwayLocations = grid.getEmptyAdjacentLocations(this.getLocation());
                for (Location availableLocation : runAwayLocations) {
                    if (availableLocation == runAwayLocation) {
                        this.moveTo(runAwayLocation);
                        return;
                    }
                }
                return;
            }
        }
        List<Location> neighbourLocations;
        neighbourLocations = grid.getOccupiedLocationsByDistance(this.getLocation());
        for (Location neighborLocation : neighbourLocations) {
            if (grid.get(neighborLocation).diet == 'H' && grid.get(neighborLocation).size == 'S') {
                Location runToLocation = location.getAdjacentLocation(location.getDirectionToward(neighborLocation));
                this.moveTo(runToLocation);
                return;
            }
        }
    }

    @Override
    public void attemptToEat() {
        ArrayList<Animal> neighbors;
        neighbors = grid.getNeighbors(this.getLocation());
        if(neighbors.size()==0){
            return;
        }
        for (Animal neighbor : neighbors) {
            if (neighbor.diet == 'H' && neighbor.size == 'S') {
                grid.remove(neighbor.getLocation());
                this.starveTime = STARVE_TIME;
                return;
            }
        }
    }

    @Override
    public void attemptToReproduce() {
        if (this.reproductionRate >= 1) {
            if (!grid.getEmptyAdjacentLocations(this.getLocation()).isEmpty()) {
                grid.put(grid.getEmptyAdjacentLocations(this.getLocation()).get(0), new Hawk());
                this.reproductionRate = 0;
            } else {
                return;
            }
        } else {
            this.reproductionRate += REPRODUCTION_RATE;
        }
    }
}
