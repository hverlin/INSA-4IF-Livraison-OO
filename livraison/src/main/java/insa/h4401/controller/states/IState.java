package insa.h4401.controller.states;

import insa.h4401.model.Delivery;
import insa.h4401.model.Node;
import insa.h4401.model.TimeSlot;

import java.io.File;

/**
 * This interface defines all the methods that should be implemented
 * by logical states.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public interface IState {

    /**
     * Click on button to load a new map from file.
     */
    void btnLoadMap();

    /**
     * Click on button to load a new planning from file.
     */
    void btnLoadPlanning();

    /**
     * Click on button to compute the best route to make deliveries.
     */
    void btnGenerateRoute();

    /**
     * Click on button to cancel a loading action.
     */
    void btnCancel();

    /**
     * Click on button to load file.
     *
     * @param file File to load as a map or a planning depending on the state.
     */
    void btnValidateFile(File file);

    /**
     * Left click pressed on a delivery.
     */
    void leftClickPressedOnDelivery();

    /**
     * Left click released on another delivery.
     *
     * @param sourceDelivery Delivery to switch with target delivery.
     * @param targetDelivery Delivery to switch with sourceDelivery.
     */
    void leftClickReleased(Delivery sourceDelivery, Delivery targetDelivery);

    /**
     * Simple click on a delivery.
     *
     * @param delivery The delivery clicked.
     */
    void clickOnDelivery(Delivery delivery);

    /**
     * Simple click somewhere else (not a interactive point).
     */
    void clickSomewhereElse();

    /**
     * Click on an empty node (not a delivery or warehouse node).
     *
     * @param node The empty node clicked.
     */
    void clickOnEmptyNode(Node node);

    /**
     * Click on button to add a new delivery.
     *
     * @param node                 The node to deliver.
     * @param previousDeliveryNode The node with the previous delivery.
     * @param timeSlot             The time slot of the new delivery.
     */
    void btnAddDelivery(Node node, Node previousDeliveryNode, TimeSlot timeSlot);

    /**
     * Click on button to remove a delivery.
     *
     * @param delivery The delivery to remove.
     */
    void btnRemoveDelivery(Delivery delivery);

    /**
     * Click on a specific node : the warehouse.
     *
     * @param warehouse The warehouse node clicked.
     */
    void clickOnWarehouse(Node warehouse);

    /**
     * Click on button to close the current map already loaded.
     */
    void btnCloseMap();

    /**
     * Click on button to clear the current planning already loaded.
     */
    void btnClearPlanning();

    /**
     * Set the default view for this state (enable/disable buttons ...).
     */
    void initView();
}
