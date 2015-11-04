package com.hexanome.controller;

import com.hexanome.controller.command.AddDeliveryCommand;
import com.hexanome.controller.command.RemoveDeliveryCommand;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.TypeWrapper;
import com.hexanome.view.ConstView;
import com.hexanome.view.MainWindow;
import com.hexanome.view.NodeView;
import com.hexanome.view.PopOverContentEmptyNode;
import java.util.Optional;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage; // \todo Doit disparaitre !

/**
 * This controller manage all the view and is notify when something happens on
 * the view
 *
 * It implements the sigleton design pattern and only one instance should exist
 * when the application is running
 *
 * Should always be use as following UIManager.getInstance(). ...
 */
public class UIManager {

    /**
     * Main Window of the application see MainWindow class for more information
     */
    private MainWindow mainWindow;

    private static UIManager uimanager = null;

    private UIManager() {
    }

    /**
     * Return the single of the UIManager
     *
     * @return the UIManager Instance
     */
    public static UIManager getInstance() {
        if (uimanager == null) {
            uimanager = new UIManager();
        }
        return uimanager;
    }

    /**
     * Create the main Window and return it
     *
     * @param stage
     * @return the mainWindow which should be integrated into the scene
     */
    MainWindow createMainWindow(Stage stage) {
        mainWindow = new MainWindow(stage);
        return mainWindow;
    }

    /**
     * Asks for confirmation
     * @param message
     * @return
     */
    public boolean askConfirmation(String message) {
        boolean res;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(message);
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    /**
     * @return the mainWindow
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void beginLoadMap() {
        mainWindow.setLoadingState("Loading Map...");
        mainWindow.getMapView().clearMap();
        mainWindow.getDeliveryTreeView().clearTree();
    }

    public void endLoadMap() {
        mainWindow.getMapView().clearMap();
        ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
        mainWindow.resetCursorAndInfoLabel();
    }

    public void beginLoadPlanning() {
        mainWindow.setLoadingState("Loading Planning...");
        mainWindow.getDeliveryTreeView().clearTree();
        mainWindow.getMapView().clearDeliveries();
    }

    public void endLoadPlanning() {
        // Add view subscribers to the model
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTreeView());
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());
        // Update mainwindow
        mainWindow.resetCursorAndInfoLabel();
    }

    public void showError(String msg) {
        mainWindow.resetCursorAndInfoLabel();
        // Ask main window to display error
        mainWindow.displayError(msg);
    }

}
