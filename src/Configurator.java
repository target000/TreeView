import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configurator {

    // this is for testing only, please remove after done
    public static void main(String[] args) throws IOException {

        // property.load(input);

        String str1 = "> 2016/06/13 23:05:24  BEGIN INITIAL MODULE   Select roadmap";

        String str2 = "< 2016/06/13 23:11:54  END   INITIAL MODULE   Select roadmap";

        String str3 = ">> 2016/06/13 23:05:24  START OF PHASE MOD_SELROADMAP/TOOLVERSXML_ROADMAP";

        String str4 = "<< 2016/06/13 23:05:25  END OF PHASE   MOD_SELROADMAP/READ_SHAREDINPUT";

        Configurator c1 = new Configurator("");
        System.out.println(c1.getMaxLevel());
        System.out.println(c1.getType());

        // TRUE
        System.out.println(c1.isLevelStart(1, str1));
        System.out.println(c1.isLevelEnd(1, str2));

        System.out.println(c1.isLevelStart(2, str3));
        System.out.println(c1.isLevelEnd(2, str4));

        System.out.println("-----------");

        // FALSE
        System.out.println(c1.isLevelStart(1, str2));
        System.out.println(c1.isLevelEnd(1, str1));

        System.out.println(c1.isLevelStart(2, str4));
        System.out.println(c1.isLevelEnd(2, str3));

        // FALSE
        System.out.println(c1.isLevelStart(1, str3));
        System.out.println(c1.isLevelEnd(1, str4));

        System.out.println(c1.isLevelStart(2, str1));
        System.out.println(c1.isLevelEnd(2, str2));

        // ---
        System.out.println("----");

        System.out.println(c1.getNodeNameByLevel(1, "1s - this is an awesome name to be removed"));
        System.out.println(c1.getNodeNameByLevel(2, "1s - this is an awesome name to be removed"));
        System.out.println(c1.getNodeNameByLevel(2, "1s - this is an awesome name to be removed"));

    }

    private Properties property = null;
    private Map<Object, Object> map = null;

    public Configurator(String filepath) throws IOException {
        property = new Properties();
        map = new HashMap<Object, Object>();

        // String strTest = "ControlFiles/type1_test.properties";
        // String strTest2 = "ControlFiles/sapnote.properties";
        // String strTest3 = "ControlFiles/sapupconsole.properties";
        InputStream input = new FileInputStream(filepath);
        property.load(input);

        // load key into hashmap
        // LHS of "=" is key of the hashmap
        // RHS of "=" is value of the hashmap
        for (Object key : property.keySet()) {
            map.put(key, property.get(key));
        }

        // // // TEST
        // // System.out.println("=== KEY VALUE PAIR ===");
        //
        // for (Object key : property.keySet()) {
        // // System.out.println(key.toString() + " = " + map.get(key).toString());
        // }

    }

    public Map<Object, Object> getMap() {
        return map;
    }

    public int getMaxLevel() {
        String str = getMap().get("max_total_level").toString();
        return Integer.parseInt(str);
    }

    public int getType() {
        String str = getMap().get("type").toString();
        return Integer.parseInt(str);
    }

    public boolean isLevelStart(int i, String line) {

        if (i > getMaxLevel()) {
            return false;
        }

        if (line == null) {
            return false;
        }

        // note that you have to comply with what you have in the control file
        String searchKey = "lvl" + i + "_start_regex";

        Pattern pattern = Pattern.compile((String) map.get(searchKey));
        Matcher matcher = pattern.matcher(line);

        return matcher.find();

    }

    public boolean isLevelEnd(int i, String line) {

        if (i > getMaxLevel()) {
            return false;
        }

        if (line == null) {
            return false;
        }

        // note that you have to comply with what you have in the control file
        String searchKey = "lvl" + i + "_end_regex";

        Pattern pattern = Pattern.compile((String) map.get(searchKey));
        Matcher matcher = pattern.matcher(line);

        return matcher.find();

    }

    public String getNodeNameByLevel(int i, String str) {
        if (str == null) {
            return null;
        }

        // note that you have to comply with what you have in the control file

        String searchKey = "lvl" + i + "_start_regex";
        String removePattern = property.getProperty(searchKey);
        Pattern pattern = Pattern.compile(removePattern);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()) {
            return null;
        }

        String out = str.replaceAll(removePattern, "").trim();

        return out;

    }

    public String getNodeEndNameByLevel(int i, String str) {
        if (str == null) {
            return null;
        }

        // note that you have to comply with what you have in the control file

        String searchKey = "lvl" + i + "_regex_remove";
        String removePattern = property.getProperty(searchKey);

        Pattern pattern = Pattern.compile(removePattern);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()) {
            return null;
        }

        String out = str.replaceAll(removePattern, "").trim();

        return out;

    }

    // this is for only open structure
    public int getLevel(String str) {

        if (str == null) {
            return -1;
        }

        Pattern pattern = Pattern.compile((String) map.get("level_indicator"));
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return Character.getNumericValue(str.charAt(3));
        }
        return -1;
    }

    public String getContent(String line) {
        if (line == null) {
            return null;
        }

        Pattern pattern = Pattern.compile((String) map.get("level_indicator"));
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return line.replaceAll((String) map.get("level_indicator"), "");
        }
        return null;
    }

}