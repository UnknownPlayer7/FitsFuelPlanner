public class ControllersArchive {
    private static AddProductController addProductController;
    private static WorkspaceController workspaceController;
    private static NewTabController currentTab;
    private static LibraryController libraryController;
    private static EnteringNameController enteringNameController;
    private static MainMenuController mainMenuController;

    public static void setAddProductController(AddProductController newController){
        addProductController = newController;
    }

    public static AddProductController getAddProductController(){
        return addProductController;
    }

    public static void setWorkspaceController(WorkspaceController newController){
        workspaceController = newController;
    }

    public static WorkspaceController getWorkspaceController(){
        return workspaceController;
    }

    public static void setLibraryController(LibraryController newController){
        libraryController = newController;
    }

    public static LibraryController getLibraryController(){
        return libraryController;
    }

    public static NewTabController getCurrentTab() {
        return currentTab;
    }

    public static void setCurrentTab(NewTabController currentTab) {
        ControllersArchive.currentTab = currentTab;
    }

    public static EnteringNameController getEnteringNameController() {
        return enteringNameController;
    }

    public static void setEnteringNameController(EnteringNameController enteringNameController) {
        ControllersArchive.enteringNameController = enteringNameController;
    }

    public static MainMenuController getMainMenuController() {
        return mainMenuController;
    }

    public static void setMainMenuController(MainMenuController mainMenuController) {
        ControllersArchive.mainMenuController = mainMenuController;
    }
}
