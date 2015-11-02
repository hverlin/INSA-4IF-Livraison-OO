package com.hexanome.controller.command;

import com.hexanome.controller.ModelManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;

/**
 * This class represent the action of adding a delivery to the planning
 *
 * @author paul
 * @see ICommand
 */
public class AddDeliveryCommand implements ICommand {

    private Node node;
    private Delivery prevDelivery;
    private TimeSlot timeSlot;
    private Delivery delivery;

    /**
     * Construct a new AddDeliveryCommand to add a new delivery to the planning
     *
     * @param node Delivery to add
     * @param prevDelivery Delivery preceding the delivery to add
     */
    public AddDeliveryCommand(Node node, Delivery prevDelivery) {
        this.node = node;
        this.prevDelivery = prevDelivery;
        this.timeSlot = prevDelivery.getTimeSlot();
        this.delivery = null;
    }

    /**
     * Execute the command by adding a delivery to the planning
     *
     * @see ICommand
     * @return
     */
    @Override
    public ICommand execute() {
        if (ModelManager.getInstance().getPlanning() != null) {
            delivery = ModelManager.getInstance().getPlanning().addDelivery(node, prevDelivery, timeSlot);
        } else {
            // \todo treat error case
        }
        return this;
    }

    /**
     * Reverse execution of the command by removing the delivery from the
     * planning
     *
     * @see ICommand
     * @return
     */
    @Override
    public ICommand reverse() {
        if (ModelManager.getInstance().getPlanning() != null) {
            ModelManager.getInstance().getPlanning().removeDelivery(delivery);
        } else {
            // \todo treat error case
        }
        return this;
    }

}
