package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;
import org.controlsfx.glyphfont.Glyph;

/**
 * This class represents the warehouse version of the nodeView
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class WarehouseNodeView extends Label implements INodeViewShape {
    com.hexanome.model.Node node;
    /**
     * Initializes the controller class.
     */
    public WarehouseNodeView(com.hexanome.model.Node node) {
        setGraphic(new Glyph("FontAwesome", "HOME"));
        this.node = node;
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        ContextManager.getInstance().getCurrentState().clickOnWarehouse(node);
    }

    @Override
    public Node asSceneNode() {
        return this;
    }

    @Override
    public PopOver createPopOver(com.hexanome.model.Node node) {
        return new PopOver(new PopOverContentWarehouse(node));
    }
}
