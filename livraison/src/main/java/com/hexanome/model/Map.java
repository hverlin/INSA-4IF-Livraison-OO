package com.hexanome.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Map {
    private ArrayList<Node> nodes;
    private ArrayList<Arc> arcs;
    
    /**
     * 
     * @param nodes
     * @param arcs 
     */
    public Map() { 
    }
    /**
     * Factory method to build nodes in map
     * @param id
     * @param location
     * @return 
     */
    public Node createNode(int id, Point location) {
        // Create a new node 
        Node n = new Node(id, location);
        // Add the new node to the local collection
        nodes.add(n);
        // Return the new node
        return n;
    }
    /**
     * Factory method to build arcs in the map
     * @param streetName
     * @param length
     * @param avgSpeed
     * @param srcNodeId
     * @param destNodeId
     * @return 
     */
    public Arc createArc(String streetName, float length, float avgSpeed, int srcNodeId, int destNodeId) {
        // Retreive source node
        Node srcNode = getNodeById(srcNodeId);
        // Creating a new arc
        Arc a = new Arc(streetName, length, avgSpeed, srcNode, getNodeById(destNodeId));
        // Attaching the arc to its source node
        srcNode.AttachOutgoingArc(a);
        // Adding arc to the internal collection
        arcs.add(a);
        // Return the new arc
        return a;
    }
    /**
     * 
     * @param location
     * @return 
     */
    public Node getNodeByLocation(Point location) {
        // \todo implement here
        return null; 
    }
    /**
     * 
     * @param id
     * @return 
     */
    public Node getNodeById(int id) {
       // \todo implement here
        return null; 
    }
    /**
     * 
     * @return 
     */
    public ArrayList<Arc> getArcs() {
        return arcs;
    }
    /**
     * 
     * @return 
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    /**
     * 
     * @param start
     * @param end 
     * @return
     */
    public Path getFastestPath(Node start, Node end) {
        // \todo implement here
        return null;
    }
    
    public void clear() {
        // \todo implement here
    }
}
