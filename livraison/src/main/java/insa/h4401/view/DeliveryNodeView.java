package insa.h4401.view;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.UIManager;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.PopOver;

/**
 * This class is the graphic component equivalent of the model delivery
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DeliveryNodeView extends Circle implements INodeViewShape {
    private insa.h4401.model.Node node;

    public DeliveryNodeView(insa.h4401.model.Node node) {
        if (node.getDelivery().getTimeSlot() == null ||
                node.getDelivery().getDeliveryTime() > node.getDelivery().getTimeSlot().getEndTime()) {
            setFill(Color.RED);
        } else {
            setFill(ColorsGenerator
                    .getTimeSlotColor(node.getDelivery().getTimeSlot()));
        }
        setRadius(5.0);
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        this.node = node;
    }

    /**
     * Handler for mouse click events
     *
     * @param nodeView indicate which  the node view is clicked
     */
    @Override
    public void onMouseClickedNotify(NodeView nodeView) {
        UIManager.getInstance().getMainWindow().getDeliveryTreeView().selectDelivery(nodeView);
        ContextManager.getInstance().getCurrentState().clickOnDelivery(node.getDelivery());
    }

    /**
     * Create a PopOver and return it
     *
     * @param node The model node to represent by the popover.
     * @return the popover
     */
    @Override
    public PopOver createPopOver(insa.h4401.model.Node node) {
        return new PopOver(new PopOverContentDelivery(node));
    }

    @Override
    public Node asSceneNode() {
        return this;
    }

}
