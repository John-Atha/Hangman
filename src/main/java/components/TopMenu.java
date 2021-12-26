package components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class TopMenu {
    private MenuBar menuBar;
    private ArrayList<Menu> menus;
    private String menuItemStyles = "-fx-font-size: 50px";


    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    public void addMenu(Menu menu) {
        this.menus.add(menu);
        this.menuBar.getMenus().add(menu);
    }

    public void addMenuItem(int menuIndex, MenuItem item) {
        if (menuIndex>=this.menus.size()) {
            System.out.println("Invalid menu index.");
        }
        else {
            Menu menu = this.menus.get(menuIndex);
            menu.getItems().add(item);
        }
    }

    public TopMenu(ArrayList<String> menus, ArrayList<ArrayList<String>> items) {
        this.menuBar = new MenuBar();
        this.menus = new ArrayList<Menu>();

        for (String menu_name : menus) {
            Menu menu = new Menu(menu_name);
            this.addMenu(menu);
        }
        int index = 0;
        for (ArrayList<String> menu_items : items) {
            for (String item_name : menu_items) {
                MenuItem item = new MenuItem(item_name);
                addMenuItem(index, item);
            }
            index++;
        }
        menuBar.setStyle(menuItemStyles);
    }
}
