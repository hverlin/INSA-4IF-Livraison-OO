/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.model;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Estelle
 */
public class MapTest {

    public MapTest() {
    }

    /**
     * Test of createNode method, of class Map.
     */
    @Test
    public void testCreateNode() {
        System.out.println("createNode");
        // init objects
        int id = 20;
        Point location = new Point(15, 15);
        Map map = new Map();

        // use case
        Node expResultNode = new Node(id, location);
        Node resultNode = map.createNode(id, location);
        assertEquals(expResultNode.toString(), resultNode.toString());
        assertEquals(map.getNodeById(id).toString(), resultNode.toString());
    }

    /**
     * Test of createArc method, of class Map.
     */
    @Test
    public void testCreateArc() {
        System.out.println("createArc");
        // init objects
        String streetName = "holywood";
        float length = 125;
        float avgSpeed = 31;
        int srcNodeId = 15;
        int destNodeId = 20;
        Map map = new Map();
        map.createNode(15, new Point(20, 30));
        map.createNode(20, new Point(50, 42));

        // use case
        Arc expResult = new Arc(streetName, length, avgSpeed, map.getNodeById(15), map.getNodeById(20));
        Arc result = map.createArc(streetName, length, avgSpeed, srcNodeId, destNodeId);
        assertEquals(expResult.toString(), result.toString());
        assertEquals(map.getArcs().get(0).toString(), result.toString());
    }

    /**
     * Test of getNodeById method, of class Map.
     */
    @Test
    public void testGetNodeById() {
        System.out.println("getNodeById");
        Map map = new Map();
        Node expResult = map.createNode(1, new Point(20, 30));
        Node result = map.getNodeById(1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getArcs method, of class Map.
     */
    @Test
    public void testGetArcs() {
        System.out.println("getArcs");
        Map map = new Map();
        map.createNode(1, new Point(20, 30));
        map.createNode(2, new Point(30, 20));
        map.createNode(3, new Point(20, 20));
        Arc arc1 = map.createArc("hollywood", 12, 31, 1, 2);

        //For one arc 
        ArrayList<Arc> expResult = new ArrayList<>();
        expResult.add(arc1);

        java.util.List<Arc> result = map.getArcs();

        assertEquals(result.get(0).toString(), arc1.toString());
        assertEquals(result, expResult);
        //For several arc
        Arc arc2 = map.createArc("doowylloh", 12, 31, 2, 1);
        Arc arc3 = map.createArc("Insa", 12, 31, 1, 3);
        expResult.add(arc2);
        expResult.add(arc3);
        assertEquals(result, expResult);
    }

    /**
     * Test of getNodes method, of class Map.
     */
    @Test
    public void testGetNodes() {
        System.out.println("getNodes");
        Map map = new Map();
        Node node1 = map.createNode(1, new Point(20, 30));
        Collection<Node> result = map.getNodes();
        assertEquals(1, result.size());
        assertEquals(node1, result.iterator().next());
    }

    /**
     * Test of getFastestPath method, of class Map.
     */
    @Test
    public void testGetFastestPath() {
        System.out.println("getFastestPath");
        Map map = new Map();
        Node start = map.createNode(1, new Point(20, 30));
        map.createNode(2, new Point(20, 20));
        Node end = map.createNode(3, new Point(10, 20));

        map.createArc("route1", 12, 31, 1, 3);
        Arc arc2 = map.createArc("route2", 6, 31, 1, 2);
        Arc arc3 = map.createArc("route3", 5, 31, 2, 3);

        LinkedList<Arc> arcs = new LinkedList<>();
        arcs.add(arc2);
        arcs.add(arc3);
        Path expResult = new Path(arcs);

        Path result = map.getFastestPath(start, end);

        List<Arc> expResultList = expResult.getArcs();
        List<Arc> resultList = result.getArcs();
        assertEquals(expResultList.size(), resultList.size()); //test number of arcs.

        for (int i = 0; i < expResultList.size(); i++) {
            assertEquals(expResultList.get(i), resultList.get(i));
        }
    }
}
