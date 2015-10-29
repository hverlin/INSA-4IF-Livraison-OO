package com.hexanome.model;

/**
 *
 * @author paul
 */
public class Arc {
    private String streetName;
    private float length;
    private float avgSpeed;
    private float duration;
    private Node dest;

    public Node getDest() {
        return dest;
    }

    public Node getSrc() {
        return src;
    }
    private Node src;
    
    public Arc(String streetName, float length, float avgSpeed, Node src, Node dest) {
        this.streetName = streetName;
        this.length = length;
        this.avgSpeed = avgSpeed;
        this.duration = length*avgSpeed; // Unit : s
        this.dest = dest;
        this.src = src;
    }
    
    /**
     * Returns the time slot associated with the arc, if it exists.
     * @return The time slot if it exists, null otherwise.
     */
    public TimeSlot getAssociatedTimeSlot() {
        Delivery delivery = dest.getDelivery();
        return (delivery == null) ? null : delivery.getTimeSlot();
    }
    
    public float getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format(""
                + "{\n"
                + "\"streetName\":\"%s\",\n"
                + "\"length\":%s,\n"
                + "\"avgSpeed\":%s,\n"
                + "\"duration\":%s,\n"
                + "\"destNodeId\":%s,\n"
                + "\"srcNodeId\":%s\n"
                + "}", streetName, length, avgSpeed, duration, dest.getId(), src.getId());
    }
    
}
