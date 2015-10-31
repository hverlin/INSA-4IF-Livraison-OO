package com.hexanome.model;

import java.util.ArrayList;

/**
 * This class represents the path to follow to execute all the deliveries.
 * @author paul
 */
public class Path {
    /**
     * All the arcs to take, in the right order.
     */
    private ArrayList<Arc> arcs;
    /**
     * The total duration of the path.
     */
    private float pathDuration;
    
    /**
     * Constructor.
     * @param arcs the list of arcs of the path.
     */
    public Path(ArrayList<Arc> arcs) {
        this.arcs = arcs;
        this.pathDuration = 0;
        for (Arc arc : arcs) {
            this.pathDuration += arc.getDuration();
        }
    }
    
    /**
     * Returns the first node of the path.
     * @return the first node of the path.
     */
    public Node getFirstNode() {
        // \todo implement here
        return null;
    }
    
    /**
     * Returns the last node of the path.
     * @return the last node of the path.
     */
    public Node getLastNode() {
        // \todo implement here
        return null;
    }
    
    /**
     * Determines if the path contains the node given in parameter.
     * @param node the node to look for.
     * @return true if the node is contained in the path.
     */
    public boolean containsNode(Node node)
    {
        // \todo implement here
        return false;
    }

    @Override
    public String toString() {
        String strarcs = "{";
        for (Arc arc : arcs) {
            strarcs += arc.toString() + ",";
        }
        strarcs += strarcs.substring(0, strarcs.length()-1) + "}";
        return String.format("\"Path\" : {\n"
                + "\"pathDuration\":%s, \"arcs\":\"%s\"\n"
                + "}", strarcs, pathDuration);
    }
    
}
