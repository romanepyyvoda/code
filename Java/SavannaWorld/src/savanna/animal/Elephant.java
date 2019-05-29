package savanna.animal;

import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Elephant extends Animal implements Liveable {

    private final int STARVE_TIME = 5;
    private final double REPRODUCTION_RATE = 0.02;

    public Elephant() {
        this.color = Color.GRAY;
        this.size = 'L';
        this.diet = 'H';
        this.flying = false;
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
            if (neighbor.diet == 'C') {
                runAwayLocation = location.getAdjacentLocation(location.getDirectionAway(neighbor.getLocation()));
                runAwayLocations = grid.getEmptyAdjacentLocations(this.getLocation());
                for (Location availableLocation : runAwayLocations) {
                    if (availableLocation == runAwayLocation) {
                        this.moveTo(runAwayLocation);
                        this.attemptToEat();
                        return;
                    }
                }
                return;
            }
        }
        runAwayLocations = grid.getEmptyAdjacentLocations(this.getLocation());
        int size = runAwayLocations.size();
        Random rand = new Random();
        int randomNum = rand.nextInt(((size - 1) - 0) + 1) + 0;
        this.moveTo(runAwayLocations.get(randomNum));
        this.attemptToEat();
        return;
    }

    @Override
    public void attemptToEat() {
        this.starveTime = STARVE_TIME;
    }

    @Override
    public void attemptToReproduce() {
        if (this.reproductionRate >= 1) {
            if (!grid.getEmptyAdjacentLocations(this.getLocation()).isEmpty()) {
                grid.put(grid.getEmptyAdjacentLocations(this.getLocation()).get(0), new Elephant());
                this.reproductionRate = 0;
            } else {
                return;
            }
        } else {
            this.reproductionRate += REPRODUCTION_RATE;
        }
    }
}
