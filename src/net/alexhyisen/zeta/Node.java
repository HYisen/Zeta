package net.alexhyisen.zeta;

/**
 * Created by Alex on 2016/9/23.
 * Node is the basic and therefore the smallest component of the data.
 */
class Node {
    private Node[] neighbors;
    private State state;

    public Node[] getNeighbors() {
        return neighbors;
    }

    public State getState() {
        return state;
    }
}
