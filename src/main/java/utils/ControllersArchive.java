package utils;

import controllers.*;

public class ControllersArchive {

    private static AddProductController addProductController;
    private static WorkspaceController workspaceController;
    private static NewTabController currentTabController;
    private static LibraryController libraryController;
    private static EnteringNameController enteringNameController;
    private static MainMenuController mainMenuController;

    public static void setAddProductController(AddProductController productController) {
        ControllersArchive.addProductController = productController;
    }

    public static AddProductController getAddProductController() {
        return ControllersArchive.addProductController;
    }

    public static void setWorkspaceController(WorkspaceController workspaceController) {
        ControllersArchive.workspaceController = workspaceController;
    }

    public static WorkspaceController getWorkspaceController() {
        return ControllersArchive.workspaceController;
    }

    public static void setLibraryController(LibraryController libraryController) {
        ControllersArchive.libraryController = libraryController;
    }

    public static LibraryController getLibraryController() {
        return ControllersArchive.libraryController;
    }

    public static void setCurrentTabController(NewTabController currentTabController) {
        ControllersArchive.currentTabController = currentTabController;
    }

    public static NewTabController getCurrentTabController() {
        return ControllersArchive.currentTabController;
    }

    public static void setEnteringNameController(EnteringNameController enteringNameController) {
        ControllersArchive.enteringNameController = enteringNameController;
    }

    public static EnteringNameController getEnteringNameController() {
        return ControllersArchive.enteringNameController;
    }

    public static void setMainMenuController(MainMenuController mainMenuController) {
        ControllersArchive.mainMenuController = mainMenuController;
    }

    public static MainMenuController getMainMenuController() {
        return ControllersArchive.mainMenuController;
    }
}
