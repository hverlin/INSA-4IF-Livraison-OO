/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;

/**
 * @author antitoine
 * \todo TODO
 */
public class NothingSelectedState extends SelectionsStates {

    private static NothingSelectedState nothingSelectedState = null;

    private NothingSelectedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the NothingSelectedState,
     * it is a singleton
     * @return The instance of NothingSelectedState
     */
    public static NothingSelectedState getInstance() {
        if(nothingSelectedState == null)
        {
            nothingSelectedState = new NothingSelectedState();
        }
        return nothingSelectedState;
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
     * @see com.hexanome.controller.states.IState#btnRemoveDelivery(com.hexanome.model.Delivery)
     */
    @Override
    public void btnRemoveDelivery(Delivery delivery) {
        // \todo TODO
    }

}
