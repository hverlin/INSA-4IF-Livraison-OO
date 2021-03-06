/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.h4401.model;

import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author guillaume
 */
public class PathGraphTest {

    public PathGraphTest() {
    }

    /**
     * Test of getNbEdges method, of class PathGraph.
     */
    @Test
    public void testGetNbEdges() {
        System.out.println("getNbEdges");

        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node third = new Node(3, new Point(10, 30));

        Arc arc1 = new Arc("hollywood", 12, 31, first, second);
        Arc arc2 = new Arc("doowylloh", 12, 31, second, third);
        LinkedList<Arc> arcsList = new LinkedList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);
        Path path = new Path(arcsList);

        PathGraph pathGraph = new PathGraph();
        pathGraph.addPath(path);

        int expResult = 1;
        int result = pathGraph.getNbArcs();

        assertEquals(expResult, result);
    }

    /**
     * Test of getCost method, of class PathGraph.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");

        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node third = new Node(3, new Point(10, 30));

        Arc arc1 = new Arc("hollywood", 5, 10, first, second);
        Arc arc2 = new Arc("doowylloh", 6, 10, second, third);

        LinkedList<Arc> arcsList = new LinkedList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);

        Path path = new Path(arcsList);

        PathGraph pathGraph = new PathGraph();
        pathGraph.addPath(path);

        float result = pathGraph.getCost(0, 1);
        float expResult = 1.1F;

        assertEquals(expResult, result, 0);
    }

    /**
     * Test of isArc method, of class PathGraph.
     */
    @Test
    public void testIsArc() {
        System.out.println("isArc");

        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node third = new Node(3, new Point(10, 30));

        Arc arc1 = new Arc("hollywood", 5, 31, first, second);
        Arc arc2 = new Arc("doowylloh", 6, 31, second, third);

        LinkedList<Arc> arcsList = new LinkedList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);

        Path path = new Path(arcsList);

        PathGraph pathGraph = new PathGraph();
        pathGraph.addPath(path);

        //is arc
        assertEquals(true, pathGraph.isArc(0, 1));

        //is not arc
        assertEquals(false, pathGraph.isArc(1, 0));
    }

    /**
     * Test of addPath method, of class PathGraph.
     */
    @Test
    public void testAddPath() {
        System.out.println("addPath");

        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node third = new Node(3, new Point(10, 30));

        Arc arc1 = new Arc("hollywood", 5, 31, first, second);
        Arc arc2 = new Arc("doowylloh", 6, 31, second, third);

        LinkedList<Arc> arcsList = new LinkedList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);

        Path path = new Path(arcsList);

        PathGraph instance = new PathGraph();
        instance.addPath(path);                     //Function tested

        Path expResult = instance.indexAsPath(0, 1);

        List<Arc> expResultList = path.getArcs();
        List<Arc> resultList = expResult.getArcs();
        assertEquals(expResultList.size(), resultList.size());

        for (int i = 0; i < expResultList.size(); i++) {
            assertEquals(expResultList.get(i), resultList.get(i));
        }
    }

    /**
     * Test of indexAsPath method, of class PathGraph.
     */
    @Test
    public void testIndexAsPath() {
        System.out.println("indexAsPath");

        Node first = new Node(1, new Point(10, 10));
        Node second = new Node(2, new Point(10, 20));
        Node third = new Node(3, new Point(10, 30));

        Arc arc1 = new Arc("hollywood", 5, 31, first, second);
        Arc arc2 = new Arc("doowylloh", 6, 31, second, third);

        LinkedList<Arc> arcsList = new LinkedList<>();
        arcsList.add(arc1);
        arcsList.add(arc2);

        Path path = new Path(arcsList);

        PathGraph instance = new PathGraph();
        instance.addPath(path);

        Path expResult = instance.indexAsPath(0, 1); //Function tested

        List<Arc> expResultList = path.getArcs();
        List<Arc> resultList = expResult.getArcs();
        assertEquals(expResultList.size(), resultList.size());

        for (int i = 0; i < expResultList.size(); i++) {
            assertEquals(expResultList.get(i), resultList.get(i));
        }
    }

}
