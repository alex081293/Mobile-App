package cse.usf.edu.AutoPt.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Menu {

    /**
     * An array of sample (dummy) items.
     */
    public static List<MenuItem> ITEMS = new ArrayList<MenuItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, MenuItem> ITEM_MAP = new HashMap<String, MenuItem>();

    static {
        // Add 3 sample items.
        addItem(new MenuItem("1", "Home", "http://www.google.com" ));
        addItem(new MenuItem("2", "Sessions", "http://www.google.com"));
        addItem(new MenuItem("3", "Alerts", "http://www.google.com"));
        addItem(new MenuItem("4", "Calender", "http://www.google.com"));
        addItem(new MenuItem("5", "About", "http://www.google.com" ));
//        addItem(new MenuItem("6", "Sessions", "http://www.google.com"));
//        addItem(new MenuItem("7", "Alerts", "http://www.google.com"));
//        addItem(new MenuItem("8", "Calender", "http://www.google.com"));
//        addItem(new MenuItem("9", "Home", "http://www.google.com" ));
//        addItem(new MenuItem("10", "Sessions", "http://www.google.com"));
//        addItem(new MenuItem("11", "Alerts", "http://www.google.com"));
//        addItem(new MenuItem("12", "Calender", "http://www.google.com"));
//        addItem(new MenuItem("13", "Home", "http://www.google.com" ));
//        addItem(new MenuItem("14", "Sessions", "http://www.google.com"));
//        addItem(new MenuItem("15", "Alerts", "http://www.google.com"));
//        addItem(new MenuItem("16", "Calender", "http://www.google.com"));
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MenuItem {
        public String id;
        public String content;
        public String webSiteUrl;

        public MenuItem(String id, String content, String webSiteUrl) {
            this.id = id;
            this.content = content;
            this.webSiteUrl = webSiteUrl;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
