package com.hexanome.controller;

import com.hexanome.model.Delivery;
import com.hexanome.view.ConstView;
import com.hexanome.view.MainWindow;
import com.hexanome.view.NodeView;
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
     * Method that allow to notify the UIController
     *
     * @param action Action to be executed
     * @param arg Optional argument
     *
     * Expected @param arg for the following action Load_MAP : File file (valid
     * xml file) Load_PLANNING : File file (valid xml file)
     */
    public void NotifyUI(ConstView.Action action, Object arg) {
        // \todo implémenter toutes les méthodes dans les states pour plus avoir ce switch !!!
        switch (action) {
            case QUIT:
                ContextManager.getInstance().exit(); // Special undoable
                break;
            case ADD_DELIVERY:
                // Create an AddDeliveryCommand and give it to context manager
                break;
            case DELETE_DELIVERY:
                // Create a RemoveDeliveryCommand and give it to context manager
                break;
            case SWAP_DELIVERIES:
                // Create a SwapDeliveryCommand and give it to context manager
                break;
            case CLICK_ON_DELIVERY_NODE:
                ((NodeView) (arg)).showPopOver();
                mainWindow.disablePanning();
                break;
            case CLICK_ON_EMPTY_NODE:
                ((NodeView) (arg)).showPopOver();
                mainWindow.disablePanning();
                break;
            case CLICK_ON_WAREHOUSE:
                ((NodeView) (arg)).showPopOver();
                mainWindow.disablePanning();
                break;
            case HIDE_POPOVER:
                mainWindow.ennablePanning();
                break;
            case DELEVERY_SELECTED:
                mainWindow.getMapView().selectDelivery(((Delivery)(arg)));
                break;
            default:
                break;
        }
    }

    /**
     * Method that allow to notify the UIController
     *
     * @param action action to be executed
     */
    public void NotifyUI(ConstView.Action action) {
        NotifyUI(action, null);
    }

    /**
     * @return the mainWindow
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void beginLoadMap() {
        mainWindow.SetLoadingState("Loading Map...");
        mainWindow.getMapView().clearMap();
        mainWindow.getDeliveryTree().clearTree();
    }

    public void endLoadMap() {
        mainWindow.getMapView().clearMap();
        ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
        mainWindow.SetLoadingDone();
        ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
        ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
    }

    public void beginLoadPlanning() {
        mainWindow.SetLoadingState("Loading Planning...");
        mainWindow.getDeliveryTree().clearTree();
        mainWindow.getMapView().clearDeliveries();
    }

    public void loadError() {
        mainWindow.SetLoadingDone();
        mainWindow.displayError("The file can't be loaded !");
    }

    public void endLoadPlanning() {
        // Add view subscribers to the model
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTree());
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());
        // Update mainwindow
        mainWindow.SetLoadingDone();
    }

}
