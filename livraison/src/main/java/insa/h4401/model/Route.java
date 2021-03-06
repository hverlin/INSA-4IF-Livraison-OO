package insa.h4401.model;

import insa.h4401.utils.Publisher;
import insa.h4401.utils.Subscriber;

import java.util.*;

/**
 * This class represents a route, with the entire paths to follow to execute
 * every delivery.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Route implements Publisher {

    /**
     * Collection of path representing the route.
     */
    private LinkedList<Path> paths;

    /**
     * List of the subscribers.
     */
    private ArrayList<Subscriber> subscribers;

    /**
     * Planning associated with this route.
     */
    private Planning planning;

    /**
     * Total duration of the route.
     */
    private float totalDistance;

    /**
     * Constructs the route with the paths passed by parameter and update the
     * deliveries times.
     *
     * @param planning the planning
     * @param paths The paths representing the route.
     */
    public Route(Planning planning, LinkedList<Path> paths) {
        this.planning = planning;
        this.paths = paths;
        this.totalDistance = 0f;
        subscribers = new ArrayList<>();

        updateDeliveriesTime();
        updateArcTimeSlots();
    }

    /**
     * Updates the delivery time of each delivery contained in the path
     * collection.
     */
    private void updateDeliveriesTime() {
        // Get for each path the delivery object to update
        for (int i = 0, iMax = paths.size() - 1; i <= iMax; ++i) {
            Path path = paths.get(i);

            Delivery delivery = path.getLastNode().getDelivery();
            Delivery previousDelivery = path.getFirstNode().getDelivery();

            totalDistance += path.getPathDistance();

            if (delivery != null) {
                float deliveryTime = path.getPathDuration();

                if (previousDelivery != null) {
                    deliveryTime += previousDelivery.getDeliveryEndTime();
                } else { // Start is warehouse
                    deliveryTime += delivery.getTimeSlot().getStartTime();
                }

                if (deliveryTime < delivery.getTimeSlot().getStartTime()) {
                    delivery.setDeliveryTime(delivery.getTimeSlot().getStartTime());
                } else {
                    delivery.setDeliveryTime(deliveryTime);
                }
            }
        }
    }

    /**
     * Updates the associated time slots of the arcs contained in the path.
     */
    private void updateArcTimeSlots() {
        planning.getMap().resetArcs();

        paths.stream().forEach((path) -> {
            Delivery delivery = path.getLastNode().getDelivery();
            
            if (delivery == null) {
                delivery = path.getFirstNode().getDelivery();
            }
            
            if (delivery != null) {
                for (Arc arc : path.getArcs()) {
                    arc.addAssociatedTimeSlot(delivery.getTimeSlot());
                }
            }
        });
    }

    /**
     * @return The ordered list of paths.
     */
    public List<Path> getPaths() {
        return Collections.unmodifiableList(paths);
    }

    /**
     * Returns the total distance travelled with the compute route.
     *
     * @return The total distance of the route.
     */
    public float getTotalDistance() {
        return totalDistance;
    }

    /**
     * Returns the total duration of the route, in seconds.
     * 
     * @return The total duration of the route, in seconds.
     */
    public float getTotalDuration() {
        if (paths.isEmpty()) {
            return 0;
        }

        return getEndTime() - getStartTime();
    }

    /**
     * @return The time, in sum of seconds, when the route is done.
     */
    public float getEndTime() {
        if (paths.isEmpty()) {
            return 0;
        }
        Path lastPath = paths.get(paths.size() - 1);
        return lastPath.getFirstNode().getDelivery().getDeliveryEndTime() 
                + lastPath.getPathDuration();
    }

    /**
     * @return The time, in sum of seconds, when the route is started.
     */
    public float getStartTime() {
        if (paths.isEmpty()) {
            return 0;
        }
        Path firstPath = paths.get(0);
        return firstPath.getLastNode().getDelivery().getDeliveryTime() - firstPath.getPathDuration();
    }

    /**
     * Finds the delivery passed by parameter and returns the next delivery if
     * exists.
     *
     * @param delivery The delivery to find in the route.
     * @return The next delivery, or null if it doesn't exist.
     */
    public Delivery getNextDelivery(Delivery delivery) {
        for (Path p : paths) {
            Delivery currentDelivery = p.getFirstNode().getDelivery();

            if (currentDelivery != null) {
                if (currentDelivery.equals(delivery)) {
                    return p.getLastNode().getDelivery();
                }
            }
        }

        return null;
    }

    /**
     * Finds and returns the node of the previous delivery done before the
     * delivery passed by parameter.
     *
     * @param delivery The delivery to find.
     * @return The node of the previous delivery in the current route.
     */
    protected Node getNodePreviousDelivery(Delivery delivery) {
        for (Path p : paths) {
            if (p.getLastNode().getDelivery() != null
                    && p.getLastNode().getDelivery().equals(delivery)) {
                return p.getFirstNode();
            }
        }
        return null;
    }

    /**
     * Adds a delivery to the route, and notifies the subscribers.
     *
     * @param delivery             The delivery to add
     * @param nodePreviousDelivery The node with the delivery that will be
     *                             executed before the one to add.
     */
    protected void addDelivery(Delivery delivery, Node nodePreviousDelivery) {
        addDelivery(delivery, nodePreviousDelivery, true);
    }

    /**
     * Adds a delivery to the route.
     *
     * @param delivery             The delivery to add.
     * @param nodePreviousDelivery The node with the delivery that will be
     *                             executed before the one to add.
     * @param update               Notify the subscribers if update is true.
     */
    private void addDelivery(Delivery delivery, Node nodePreviousDelivery, boolean update) {
        // Find the previous delivery in the list of paths
        int indexPreviousPath = -1;
        Path pathToReplace = null;

        boolean previousPathFound = false;
        for (int indexPath = 0, maxIndexPath = paths.size() - 1;
                !previousPathFound && indexPath <= maxIndexPath;
                ++indexPath) {
            Path p = paths.get(indexPath);

            Node currentNode = p.getFirstNode();

            if (currentNode != null && currentNode.equals(nodePreviousDelivery)) {
                previousPathFound = true;
                indexPreviousPath = indexPath;
                pathToReplace = p;
            }
        }

        // Previous path with the previous delivery found
        if (pathToReplace != null) {
            // Compute the path between the previous delivery and the new delivery
            Path previousPath = planning.getMap().getFastestPath(pathToReplace.getFirstNode(),
                    delivery.getNode());

            // Compute the path between the next delivery and the new delivery
            Path nextPath = planning.getMap().getFastestPath(delivery.getNode(),
                    pathToReplace.getLastNode());

            // Replace the paths in the list
            paths.remove(indexPreviousPath);
            paths.add(indexPreviousPath, nextPath);
            paths.add(indexPreviousPath, previousPath);

        } else {
            Path previousPath = planning.getMap().getFastestPath(nodePreviousDelivery, delivery.getNode());
            Path nextPath = planning.getMap().getFastestPath(delivery.getNode(), nodePreviousDelivery);
            paths.add(previousPath);
            paths.add(nextPath);
        }

        if (update) {
            updateDeliveriesTime();
            updateArcTimeSlots();

            notifySubscribers();
        }
    }

    /**
     * Removes a delivery from the route and notifies the subscribers.
     *
     * @param deliveryToRemove The delivery to remove.
     */
    protected void removeDelivery(Delivery deliveryToRemove) {
        removeDelivery(deliveryToRemove, true);
    }

    /**
     * Removes a delivery from the route.
     *
     * @param deliveryToRemove The delivery to remove.
     * @param update           Notify the subscribers if update is true.
     */
    private void removeDelivery(Delivery deliveryToRemove, boolean update) {
        // Search the path to remove, according to the delivery to remove
        int indexPathtoRemove = -1;
        boolean pathToRemoveFound = false;
        Path pathToRemove = null;
        for (int indexPath = 0, maxIndex = paths.size() - 1;
             indexPath <= maxIndex && !pathToRemoveFound;
             ++indexPath) {
            Path path = paths.get(indexPath);
            Node currentNode = path.getLastNode();
            if (currentNode != null && currentNode.equals(deliveryToRemove.getNode())) {
                pathToRemoveFound = true;
                indexPathtoRemove = indexPath;
                pathToRemove = path;
            }
        }

        // Remove the path if it was previously found
        if (pathToRemove != null) {
            Node firstNode = pathToRemove.getFirstNode();
            Node lastNode = paths.get(indexPathtoRemove + 1).getLastNode();

            boolean addNewPath = (paths.size() > 2);

            // Remove the old paths in the list and the new one
            paths.remove(indexPathtoRemove + 1);
            paths.remove(indexPathtoRemove);

            // If the delivery to remove is in the last path, it's useless to
            // add a new path.
            if (addNewPath) {
                Path newPath = planning.getMap().getFastestPath(firstNode, lastNode);
                paths.add(indexPathtoRemove, newPath);
            }

            TimeSlot ts = deliveryToRemove.getTimeSlot();
            ts.removeDelivery(deliveryToRemove);

            if (update) {
                updateDeliveriesTime();
                updateArcTimeSlots();

                notifySubscribers();
            }
        }
    }

    /**
     * Swaps two deliveries and notifies the subscribers.
     *
     * @param delivery1 The first delivery to swap.
     * @param delivery2 The second delivery to swap.
     */
    void swapDeliveries(Delivery delivery1, Delivery delivery2) {

        Delivery previousDelivery = null, nextDelivery = null;

        // Find the delivery done first.
        for (Path path : paths) {
            Delivery destDelivery = path.getLastNode().getDelivery();
            if (destDelivery.equals(delivery1)) {
                nextDelivery = delivery1;
                previousDelivery = delivery2;
                break;
            } else if (destDelivery.equals(delivery2)) {
                nextDelivery = delivery2;
                previousDelivery = delivery1;
                break;
            }
        }

        // Swap deliveries
        //noinspection ConstantConditions
        if (previousDelivery != null && nextDelivery != null) {
            TimeSlot timeSlot = nextDelivery.getTimeSlot();
            removeDelivery(nextDelivery, false);

            timeSlot.addDelivery(nextDelivery);
            addDelivery(nextDelivery, previousDelivery.getNode(), false);

            updateDeliveriesTime();
            updateArcTimeSlots();

            notifySubscribers();
        }
    }

    /**
     * Add one subscriber.
     *
     * @param subscriber Subscriber to add
     */
    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
        subscriber.update(this, planning);
    }

    /**
     * Removes one subscriber.
     *
     * @param subscriber Subscriber to remove
     */
    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * Notifies all subscribers.
     */
    @Override
    public void notifySubscribers() {
        subscribers.stream().forEach((s) -> s.update(this, planning));
    }

    /**
     * Remove all subscribers.
     */
    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) {
            return false;
        }

        Route route = (Route) obj;

        if (this.subscribers.size() != route.subscribers.size() 
                || this.paths.size() != route.paths.size() 
                || !(this.planning.equals(route.planning))) {
            return false;
        }

        for (int i = 0; i < this.paths.size(); i++) {
            if (!(this.paths.get(i).equals(route.paths.get(i)))) {
                return false;
            }
        }

        for (int j = 0; j < this.subscribers.size(); j++) {
            if (!(this.subscribers.get(j).equals(route.subscribers.get(j)))) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash += 59 * hash + Objects.hashCode(this.paths);
        hash += 59 * hash + Objects.hashCode(this.subscribers);
        hash += 59 * hash + Objects.hashCode(this.planning);
        return hash;
    }

    /**
     * Returns the string describing the objet, used for debug only
     *
     * @return a string describing the object
     */
    @Override
    public String toString() {
        String strpaths = "{";
        for (Path path : paths) {
            strpaths += path.toString() + ",";
        }
        strpaths = strpaths.substring(0, strpaths.length() - 1) + "}";
        return String.format("{ \"Route\" : { \"paths\":%s } }", strpaths);
    }
}
