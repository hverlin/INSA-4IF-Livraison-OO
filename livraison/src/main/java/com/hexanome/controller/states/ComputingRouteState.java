/**
 * 
 */
package com.hexanome.controller.states;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.IOManager;

/**
 * @author antitoine
 * \todo TODO
 */
public class ComputingRouteState extends DefaultState {

    private static ComputingRouteState computingRouteState = null;

    private ComputingRouteState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the ComputingRouteState,
     * it is a singleton
     * @return The instance of ComputingRouteState
     */
    public static ComputingRouteState getInstance() {
        if(computingRouteState == null)
        {
            computingRouteState = new ComputingRouteState();
        }
        return computingRouteState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        // \todo TODO
    }

}
