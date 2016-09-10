import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private static final String strBEGIN = "BEGIN".toUpperCase();
    private static final String strStart = "START".toUpperCase();
    private static final String strMODULE = "MODULE".toUpperCase();
    private static final String strEND = "END".toUpperCase();
    private static final String strPhase = "PHASE".toUpperCase();
    private static final String prefixPatternRight = "\\>\\s";
    private static final String prefixPatternleft = "\\<\\s";
    private static final String prefixPatternRight2 = "\\>>\\s+";
    private static final String prefixPatternLeft2 = "\\<<\\s+";
    private static final String yearTimePattern = "\\d{4}/\\d{2}/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";

    // TODO Test main
    public static void main(String[] args) {
        
//        String keyWordsPattern = "\\s+" + strStart + "\\s+\\S+\\s+" + strPhase;
//        String totalPattern = "\\>>\\s+\\d{4}/\\d{2}/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}" + keyWordsPattern;
        

        String str1 = "> 2016/06/13 23:05:24  BEGIN INITIAL MODULE   Select roadmap";

        String str2 = "< 2016/06/13 23:11:54  END   INITIAL MODULE   Select roadmap";

        String str3 = ">> 2016/06/13 23:05:24  START OF PHASE MOD_SELROADMAP/TOOLVERSXML_ROADMAP";

        String str4 = "<< 2016/06/13 23:05:25  END OF PHASE   MOD_SELROADMAP/READ_SHAREDINPUT";

        System.out.println(isModuleBegin(str1));
        System.out.println(isModuleEnd(str2));
        System.out.println(isPhaseStart(str3));
        System.out.println(isPhaseEnd(str4));

        System.out.println("----");

        System.out.println(isModuleBegin(str2));
        System.out.println(isModuleEnd(str1));
        System.out.println(isPhaseStart(str4));
        System.out.println(isPhaseEnd(str3));
    }

    // Regex for sapupconsole.log

    public static boolean isModuleBegin(String str) {
        if (str == null) {
            return false;
        }

        // String totalPattern = property.getProperty("lvl2_end_regex");

        String keyWordsPattern = "\\s+" + strBEGIN + "\\s+\\S+\\s+" + strMODULE;
        String totalPattern = prefixPatternRight + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }

    public static boolean isModuleEnd(String str) {
        if (str == null) {
            return false;
        }

        String keyWordsPattern = "\\s+" + strEND + "\\s+\\S+\\s+" + strMODULE;
        String totalPattern = prefixPatternleft + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }

    public static boolean isPhaseStart(String str) {
        if (str == null) {
            return false;
        }

        String keyWordsPattern = "\\s+" + strStart + "\\s+\\S+\\s+" + strPhase;
        String totalPattern = prefixPatternRight2 + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }

    public static boolean isPhaseEnd(String str) {
        if (str == null) {
            return false;
        }

        String keyWordsPattern = "\\s+" + strEND + "\\s+\\S+\\s+" + strPhase;
        String totalPattern = prefixPatternLeft2 + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }

    public static String getModuleBeginName(String str) {
        // PRE-CONDITION: use isModule method mentioned above to first make sure this is a method

        if (str == null) {
            return null;
        }

        String keyWordsPattern = "\\s+" + strBEGIN + "\\s+\\S+\\s+" + strMODULE;
        String totalPattern = prefixPatternRight + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()) {
            return null;
        }

        String out = str.replaceAll(totalPattern, "").trim();

        return out;

    }

    public static String getModuleEndName(String str) {
        // PRE-CONDITION: use isModule method mentioned above to first make sure this is a method

        if (str == null) {
            return null;
        }

        String keyWordsPattern = "\\s+" + strEND + "\\s+\\S+\\s+" + strMODULE;
        String totalPattern = prefixPatternleft + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()) {
            return null;
        }

        String out = str.replaceAll(totalPattern, "").trim();

        return out;

    }

    public static String getPhaseStartName(String str) {
        if (str == null) {
            return null;
        }

        String keyWordsPattern = "\\s+" + strStart + "\\s+\\S+\\s+" + strPhase;
        String totalPattern = prefixPatternRight2 + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()) {
            return null;
        }

        String out = str.replaceAll(totalPattern, "").trim();

        return out;

    }

    public static String getPhaseEndName(String str) {
        if (str == null) {
            return null;
        }

        String keyWordsPattern = "\\s+" + strEND + "\\s+\\S+\\s+" + strPhase;
        String totalPattern = prefixPatternLeft2 + yearTimePattern + keyWordsPattern;

        Pattern pattern = Pattern.compile(totalPattern);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()) {
            return null;
        }

        String out = str.replaceAll(totalPattern, "").trim();

        return out;

    }

    // Regex for sap notes

    public static int getLevel(String str) {
        if ( str == null ){
            return -1;
        }
        
        String pat = "LVL\\d{1}-";

        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return Character.getNumericValue(str.charAt(3));
        }
        return -1;
    }

    public static boolean isNote4Line(String str) {
        String pattern2 = "LVL\\d{1}-[\\d]";

        Pattern pattern = Pattern.compile(pattern2);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean isNote4Key(String str) {
        String pattern2 = "[\\d]";

        Pattern pattern = Pattern.compile(pattern2);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

}
