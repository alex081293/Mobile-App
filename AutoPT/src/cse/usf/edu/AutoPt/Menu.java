package cse.usf.edu.AutoPt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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
    	
    	String messages = "Messages";
    	int countM = getUnseenMessages();
    	if (countM > 0) {
    		messages = messages + "          *Unread*: " + countM;
    	}
    	
        // Add 3 sample items.
        addItem(new MenuItem("1", "Home", "" ));
        addItem(new MenuItem("2", "Therapy Sessions", ""));
        addItem(new MenuItem("3", "Notification Settings", ""));
        addItem(new MenuItem("4", messages,""));
        addItem(new MenuItem("5", "Calender", ""));
        addItem(new MenuItem("6", "About", "" ));
       
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
    public static int getUnseenMessages() {
    	String query = "SELECT COUNT(*) as count FROM messages WHERE userId='" + patientDetailActivity.user.drId 
    			+ "' and userType='0' and patient='" + patientDetailActivity.user.pId + "'" + " and viewed='0'";
    	new dbMakeQuery().execute(query, "f");
    	patientDetailActivity.loadComplete = false;
    	while (patientDetailActivity.loadComplete == false) {};
    	JSONObject countObj;
		try {
			countObj = new JSONObject(patientDetailActivity.results);
			return countObj.getInt("count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
    }
}
