/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.command.RemoveDeliveryCommand;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * @author antitoine
 * \todo TODO
 */
public class DeliverySelectedState extends SelectionsStates {

    private static DeliverySelectedState deliverySelectedState = null;

    private DeliverySelectedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the DeliverySelectedState,
     * it is a singleton
     * @return The instance of DeliverySelectedState
     */
    public static DeliverySelectedState getInstance() {
        if(deliverySelectedState == null)
        {
            deliverySelectedState = new DeliverySelectedState();
        }
        return deliverySelectedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnRemoveDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        Delivery prevDelivery = ModelManager.getInstance().getPlanning().getPreviousDelivery(delivery);
        // \todo treat case if delivery is the first of the list
        RemoveDeliveryCommand rmDeliveryCmd = new RemoveDeliveryCommand(delivery, prevDelivery);
        // ContextManager is asked to execute the command
        ContextManager.getInstance().executeCommand(rmDeliveryCmd);
        // We jump to emptyNodeSelected state
        ContextManager.getInstance().setCurrentState(EmptyNodeSelectedState.getInstance());
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void clickOnDelivery(Delivery delivery) {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnEmptyNode()
     */
    @Override
    public void clickOnEmptyNode(Node node) {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickSomewhereElse()
     */
    @Override
    public void clickSomewhereElse() {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#clickOnWarehouse(com.hexanome.model.Node)
     */
    @Override
    public void clickOnWarehouse(Node warehouse) {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#closePopOver()
     */
    @Override
    public void closePopOver() {
        // \todo TODO
    }

}
