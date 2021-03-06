package insa.h4401.view;

import insa.h4401.model.*;
import insa.h4401.utils.Publisher;
import insa.h4401.utils.Subscriber;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Reprensents a view of the map with nodes and arc
 * This is a one-to-one representation of the Map model
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class MapView extends AnchorPane implements Subscriber {

    private HashMap<Node, NodeView> nodeList = new HashMap<>();
    private HashMap<NodePair, LinkedList<Arc>> arcsMap = new HashMap<>();

    private Node latestNodeForOpenPopOver = null;

    @Override
    public void update(Publisher p, Object arg) {

        // called when a new map is loaded
        if (p instanceof Map) {
            clearMap();
            Map map = (Map) p;
            addArcs(map.getArcs());
            addEmptyNodes(map.getNodes(), Cursor.DEFAULT);
        } else if (p instanceof Planning) {
            updateMapWithDeliveries((Planning) p);
        }

        // called when a new Route is computed
        else if (p instanceof Route) {
            clearMap();
            Planning planning = (Planning) arg;
            Map map = planning.getMap();

            updateRouteOnMap(planning, map);
        }
    }

    /**
     * Remove everything from the mapView
     */
    public void clearMap() {
        nodeList.clear();
        arcsMap.clear();
        getChildren().clear();
    }

    private void addArcs(Collection<Arc> arcs) {
        if (arcs == null) {
            return;
        }

        for (Arc arc : arcs) {
            NodePair np = new NodePair(arc.getSrc(), arc.getDest());
            if (arcsMap.get(np) != null) {
                arcsMap.get(np).add(arc);
            } else {
                LinkedList<Arc> s = new LinkedList<>();
                s.add(arc);
                arcsMap.put(np, s);
            }
        }

        for (Entry<NodePair, LinkedList<Arc>> entrySet : arcsMap.entrySet()) {
            ArcView arcView = new ArcView(entrySet.getValue(), this);
            arcView.addArcs();
        }
    }

    private void addEmptyNodes(Collection<Node> nodes, Cursor cursor) {
        if (nodes != null) {
            for (Node node : nodes) {
                NodeView nv = new NodeView(node, cursor);
                nodeList.put(node, nv);
                nv.relocate(node.getLocation().x - nv.getPrefWidth() / 2,
                        node.getLocation().y - nv.getPrefHeight() / 2);
                nv.toFront();
            }
            getChildren().addAll(nodeList.values());
        }
    }

    /**
     * Update the nodes which contain a delivery.
     *
     * @param planning The planning loaded
     */
    private void updateMapWithDeliveries(Planning planning) {
        resetNodes();

        planning.getTimeSlots().stream().forEach((ts) ->
                ts.getDeliveries().stream().forEach((d) ->
                        (nodeList.get(d.getNode())).setType(ConstView.DELIVERY_NODE)));

        NodeView warehouseNodeView = nodeList.get(planning.getWarehouse());
        warehouseNodeView.setType(ConstView.WAREHOUSE_NODE);
    }

    private void updateRouteOnMap(Planning planning, Map map) {
        ArrayList<Arc> mapArc = new ArrayList<>(map.getArcs());

        addArcs(mapArc);

        addEmptyNodes(map.getNodes(), Cursor.HAND);

        for (TimeSlot ts : planning.getTimeSlots()) {
            for (Delivery d : ts.getDeliveries()) {
                (nodeList.get(d.getNode())).setType(ConstView.DELIVERY_NODE);
            }
        }

        NodeView warehouseNodeView = nodeList.get(planning.getWarehouse());
        warehouseNodeView.setType(ConstView.WAREHOUSE_NODE);
    }

    private void resetNodes() {
        nodeList.values().stream().forEach((nodeView) ->
                nodeView.setType(ConstView.EMPTY_NODE));
    }

    /**
     * Hide the PopOver which corresponds the node passed as paramater
     */
    public void hidePopOver() {
        if (latestNodeForOpenPopOver != null) {
            nodeList.get(latestNodeForOpenPopOver).hidePopOver();
            // Reset memory to avoid double closure
            latestNodeForOpenPopOver = null;
        }
    }

    /**
     * Show the popover above the node passed as parameter
     *
     * @param node node which should display a popover
     */
    public void showPopOver(Node node) {
        // Memorize latest open pop over
        latestNodeForOpenPopOver = node;
        // Show this pop over
        nodeList.get(node).showPopOver();
    }


}
