package savanna.animal;

import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Lion extends Animal implements Liveable {

    private final int STARVE_TIME = 5;
    private final double REPRODUCTION_RATE = 0.025;

    public Lion() {
        super.color = Color.ORANGE;
        this.size = 'L';
        this.diet = 'C';
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
        this.attemptToEat();
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


        List<Location> neighbourLocations;

        neighbourLocations = grid.getOccupiedLocationsByDistance(this.getLocation());
        int i=0;
        for (Location neighborLocation : neighbourLocations) {
            Location runToLocation = location.getAdjacentLocation(location.getDirectionToward(neighborLocation));
            if(i==1){
            this.moveTo(runToLocation);
            return;
            }
            i++;
        }
        return;
    }

    @Override
    public void attemptToEat() {
        ArrayList<Animal> neighbors;
        neighbors = grid.getNeighbors(this.getLocation());
        if(neighbors.size()==0){
            return;
        }
        for (Animal neighbor : neighbors) {
            grid.remove(neighbor.getLocation());
            this.starveTime = STARVE_TIME;
            return;
        }

    }

    @Override
    public void attemptToReproduce() {
        if (this.reproductionRate >= 1) {
            if (!grid.getEmptyAdjacentLocations(this.getLocation()).isEmpty()) {
                grid.put(grid.getEmptyAdjacentLocations(this.getLocation()).get(0), new Lion());
                this.reproductionRate = 0;
            } else {
                return;
            }
        } else {
            this.reproductionRate += REPRODUCTION_RATE;
        }
    }
}
