import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class TreeModel {

    private static final String NEWLINE = "\n";
    private List<String> lineList = null;
    private Node root = null;

    // This main is for testing
    public static void main(String[] args) throws IOException {

        // this method is used to check if the generated tree is the same as the one read directly
        // readFileWriteFile("SAPupConsole.log");

        String filePath = "C:\\workspace_project\\TreeView\\test_hier.txt";
        TreeModel tm = new TreeModel();
        tm.root = tm.generateTree2(filePath, "ControlFiles/type1_test.properties");

        traverseTree(tm.root);
    }

    // Test method - this method will traverse the tree and print
    private static void traverseTree(Node root) {
        List<Node> list = root.getChildren();
        for (Node node : list) {
            System.out.println(node.getKey());
            for (Node innerNode : node.getChildren()) {
                System.out.println(innerNode.getKey());
                for (Node l3node : innerNode.getChildren()) {
                    System.out.println(l3node.getKey());
                    for (Node l4node : l3node.getChildren()) {
                        System.out.println(l4node.getKey());
                    }
                }
            }
        }
    }

    // Test method - this method will read start phase and mod to a file
    public static void readFileWriteFile(String filePath) throws IOException {

        File file = new File(filePath);

        // Create Reader
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        // Create Writer
        PrintWriter writer = new PrintWriter("testout2.txt", "UTF-8");

        String line = null;
        while ((line = br.readLine()) != null) {

            if (Regex.isPhaseStart(line)) {
                writer.println(Regex.getPhaseStartName(line));
            } else if (Regex.isModuleBegin(line)) {
                writer.println(Regex.getModuleBeginName(line));
            }
        }

        // Close both reader and writer
        writer.close();
        br.close();
    }

    // This method takes the log file path string to build a tree based on Module and Phases
    // It returns the root of the built tree
    // Note each node of the tree corresponds ONLY to the start phases

    public static Node generateTree(String filePath) throws IOException {
        // Create a file object based on the path
        File file = new File(filePath);

        // Setup reader
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        // Create a root node
        Node root = new Node();

        // Initialize readLine string and node
        String line = null;
        Node moduleNode = null;
        Node phaseNode = null;

        // want to dynamically populate the content while constructing the tree

        // maintain a collection of nodes
        List<Node> bagOfNodes = new ArrayList<Node>();

        // Tree -- root > Module > phase
        // Read file line by line here
        while ((line = br.readLine()) != null) {

            // module always happens before phase

            // if the current line reads the beginning token of a module
            if (Regex.isModuleBegin(line)) {
                // create a node
                moduleNode = new Node(Regex.getModuleBeginName(line));
                // the module node is created under the root node
                root.addChild(moduleNode);

                // add this node to the list of node
                bagOfNodes.add(moduleNode);
            }

            // if the current line reads the starting token of a phase
            if (Regex.isPhaseStart(line)) {
                // create a node
                phaseNode = new Node(Regex.getPhaseStartName(line));
                // the phase node is created under the module node
                moduleNode.addChild(phaseNode);

                // add this node to the list of node
                bagOfNodes.add(phaseNode);
            }

            // go through each node in the list
            for (Node node : bagOfNodes) {

                if (node.keepAddLine()) {
                    node.addContent2Node(line);

                    if (line.toLowerCase().contains("error")) {
                        node.setHasError();
                        node.getParent().setHasError();
                    } else if (line.toLowerCase().contains("warn")) {
                        node.setHasWarning();
                    }

                }

            }

            // this line reads the end of a module
            if (Regex.isModuleEnd(line)) {

                for (Node node : bagOfNodes) {

                    if (node.keepAddLine() == true && node.getKey().equalsIgnoreCase(Regex.getModuleEndName(line))) {
                        node.setAddLineFalse();
                        break;
                    }
                }
            }

            // this line reads the end of a phase
            if (Regex.isPhaseEnd(line)) {

                for (Node node : bagOfNodes) {
                    // find the node with the key
                    if (node.keepAddLine() == true && node.getKey().equalsIgnoreCase(Regex.getPhaseEndName(line))) {
                        node.setAddLineFalse();
                        break;
                    }
                }
            }

        }

        br.close();

        return root;
    }

    // TODO IMPORTANT METHOD
    /**
     * This method is very important this is the more generalized version of the above generateTree
     * method
     * 
     * @param logFilePath
     * @param ctrlFilePath
     * @return
     * @throws IOException
     */
    public static Node generateTree2(String logFilePath, String ctrlFilePath) throws IOException {

        // Setup reader
        FileInputStream fis = new FileInputStream(new File(logFilePath));
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        // Create a root node
        Node root = new Node();

        // Initialize readLine string and node
        String line = null;

        // get level data from Configuartor class
        Configurator myconfig = new Configurator(ctrlFilePath);

        int maxTotalLevel = myconfig.getMaxLevel();
        int type = myconfig.getType();

        Node[] nodeLevel = new Node[maxTotalLevel];

        // maintain a collection of nodes
        List<Node> bagOfNodes = new ArrayList<Node>();

        // Read file line by line here
        while ((line = br.readLine()) != null) {

            for (int i = 0; i < myconfig.getMaxLevel(); i++) {

                // In the original version MODULE is level 1 and PHASE is level 2
                // When in doubt, please refer to the corresponding section in "generateTree" method
                // which is more intuitive
                if (myconfig.isLevelStart(i + 1, line)) {
                    nodeLevel[i] = new Node(myconfig.getNodeNameByLevel(i + 1, line));

                    if (i == 0) {
                        root.addChild(nodeLevel[i]);
                    } else {
                        nodeLevel[i - 1].addChild(nodeLevel[i]);
                    }

                    bagOfNodes.add(nodeLevel[i]);

                }

            }

            // go through each node in the list
            for (Node node : bagOfNodes) {

                if (node.keepAddLine()) {
                    node.addContent2Node(line);

                    if (line.toLowerCase().contains("error")) {
                        node.setHasError();
                        node.getParent().setHasError();
                    } else if (line.toLowerCase().contains("warn")) {
                        node.setHasWarning();
                    }

                }

            }

            for (int j = 0; j < nodeLevel.length; j++) {

                if (myconfig.isLevelEnd(j + 1, line)) {

                    for (Node node : bagOfNodes) {
                        if (node.keepAddLine() == true && node.getKey().equalsIgnoreCase(myconfig.getNodeEndNameByLevel(j + 1, line))) {
                            node.setAddLineFalse();
                            break;
                        }
                    }
                }

            }

        }

        fis.close();
        br.close();

        return root;
    }

    public Node generateTree_StartLine_EndLine(String filePath) throws IOException {

        readFile2StrArr(filePath);

        // ---------------------------------------------------------------------------------------------------

        // Create a root node
        Node root = new Node();

        // Initialize readLine string and node
        Node moduleNode = null;
        Node phaseNode = null;

        // want to dynamically populate the content while constructing the tree

        // maintain a collection of nodes
        List<Node> listOfAllNodes = new ArrayList<Node>();

        // Tree -- root > Module > phase
        // Read file line by line here
        int i = 0;
        while (i < lineList.size()) {

            // module always happens before phase

            // if the current line reads the beginning token of a module
            if (Regex.isModuleBegin(lineList.get(i))) {
                // create a node
                moduleNode = new Node(Regex.getModuleBeginName(lineList.get(i)));
                // the module node is created under the root node
                root.addChild(moduleNode);

                // add this node to the list of node
                listOfAllNodes.add(moduleNode);

                // RECORD LINE NUMBER
                moduleNode.setStartLine(i + 1);
            }

            // if the current line reads the starting token of a phase
            if (Regex.isPhaseStart(lineList.get(i))) {
                // create a node
                phaseNode = new Node(Regex.getPhaseStartName(lineList.get(i)));

                // the phase node is created under the module node
                moduleNode.addChild(phaseNode);

                // add this node to the list of node
                listOfAllNodes.add(phaseNode);

                // RECORD LINE NUMBER
                phaseNode.setStartLine(i + 1);

            }

            // go through each node in the list
            for (Node node : listOfAllNodes) {

                if (node.keepAddLine()) {
                    // do NOT need to write content into the node
                    // node.addContent2Node(lineArr[i]);

                    if (lineList.get(i).toLowerCase().contains("error")) {
                        node.setHasError();
                        node.getParent().setHasError();
                    } else if (lineList.get(i).toLowerCase().contains("warn")) {
                        node.setHasWarning();
                    }

                }

            }

            // this line reads the end of a module
            if (Regex.isModuleEnd(lineList.get(i))) {

                for (Node node : listOfAllNodes) {

                    if (node.keepAddLine() == true && node.getKey().equalsIgnoreCase(Regex.getModuleEndName(lineList.get(i)))) {
                        node.setAddLineFalse();
                        node.setEndLine(i + 1);
                        break;
                    }
                }
            }

            // this line reads the end of a phase
            if (Regex.isPhaseEnd(lineList.get(i))) {

                for (Node node : listOfAllNodes) {
                    // find the node with the key
                    if (node.keepAddLine() == true && node.getKey().equalsIgnoreCase(Regex.getPhaseEndName(lineList.get(i)))) {
                        node.setAddLineFalse();
                        node.setEndLine(i + 1);
                        break;
                    }
                }
            }
            i++;
        }

        return root;
    }

    public Node treeGen_Test(String str) throws IOException {

        readFile2StrArr(str);

        // Create a root node
        Node root = new Node();
        Node cat1 = null;
        Node cat2 = null;
        Node cat3 = null;
        Node cat4 = null;
        Node note = null;

        int i = 0;
        int missLineCount = -1;

        while (i < lineList.size()) {

            if (lineList.get(i).contains("root")) {
                root = new Node();
            } else if (lineList.get(i).contains("cat1")) {
                cat1 = new Node("cat1");
                root.addChild(cat1);
            } else if (lineList.get(i).contains("cat2")) {
                cat2 = new Node("cat2");
                cat1.addChild(cat2);
            } else if (lineList.get(i).contains("cat3")) {
                cat3 = new Node("cat3");
                cat2.addChild(cat3);
            } else if (lineList.get(i).contains("cat4")) {
                cat4 = new Node("cat4");
                cat3.addChild(cat4);
            } else if (lineList.get(i).contains("note")) {
                note = new Node("note");
                cat4.addChild(note);
            }

        }

        return root;
    }

    // THIS METHOD WILL ONLY NEED TO BE RUN ONCE
    // CONSTRUCT A GLOBAL PRIVAT FINAL STRING ARR TO GET THE VALUE
    private List<String> readFile2StrArr(String filePath) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(filePath, "r");
        int line = 0;
        String lineContent = null;
        List<String> list = new ArrayList<String>();
        // String[] strArr = null;
        while (true) {
            line++;
            lineContent = raf.readLine();

            if (lineContent == null) {
                break;
            }
            list.add(lineContent);
        }
        lineList = list;
        return list;
    }

    private String getLineFromFile(int from, int to) throws Exception {

        if (from > to || from <= 0 || to > lineList.size()) {
            throw new Exception("from > to || from <= 0 || to > lineList.size()");
        }

        if (from == to) {
            System.out.println(lineList.get(from - 1));
            return lineList.get(from - 1);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = from - 1; i <= to - 1; i++) {
            sb.append(lineList.get(i) + "\n");
        }

        // System.out.println(sb);
        return sb.toString();
    }

    public static Node generateTree_SAP_Note() throws IOException {
        File file = new File("sapnotes.txt");

        // Setup reader
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        // Create a root node
        Node root = new Node();

        Node curLevel1 = null;
        Node curLevel2 = null;
        Node curLevel3 = null;
        Node curLevel4 = null;

        // create cur 1 cur 2 cur 3 to cache the last upper level

        String line = null;
        while ((line = br.readLine()) != null) {
            int lvlNum = Regex.getLevel(line);
            switch (lvlNum) {
            case 1:
                curLevel1 = new Node(getContent(line));
                root.addChild(curLevel1);
                break;
            case 2:
                curLevel2 = new Node(getContent(line));
                curLevel1.addChild(curLevel2);
                break;
            case 3:
                curLevel3 = new Node(getContent(line));
                curLevel2.addChild(curLevel3);
                break;
            case 4:
                curLevel4 = new Node(getContent(line));
                curLevel3.addChild(curLevel4);
                break;

            default:
                break;
            }
        }

        return root;
    }

    // TODO IMPORTANT METHOD
    public static Node generateTree_SAP_Note2(String filepath, String ctrlFilepath) throws IOException {

        // Setup reader
        FileInputStream fis = new FileInputStream(new File(filepath));
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        // Create a root node
        Node root = new Node();

        //
        Configurator myconfig = new Configurator(ctrlFilepath);
        int maxTotalLevel = myconfig.getMaxLevel();

        Node[] nodeLevel = new Node[maxTotalLevel];
        String line = null;

        // Read line by line

        // TODO change regex
        while ((line = br.readLine()) != null) {
            int lvlNum = Regex.getLevel(line);
            runNodes(lvlNum - 1, nodeLevel, line, root, myconfig);
        }

        fis.close();
        br.close();

        return root;
    }

    private static void runNodes(int i, Node[] nodeLevel, String line, Node root, Configurator c) {

        if (i < 0) {
            return;
        }

        if (i == 0) {
            nodeLevel[i] = new Node(c.getContent(line));
            root.addChild(nodeLevel[i]);
        } else {
            nodeLevel[i] = new Node(c.getContent(line));
            nodeLevel[i - 1].addChild(nodeLevel[i]);
        }
    }

    // TODO local getContent, there is a Config getContent
    public static String getContent(String line) {
        int firstDashIndex = line.indexOf('-') + 1;
        // System.out.println(line.substring(firstDashIndex, line.length()).trim());
        return line.substring(firstDashIndex, line.length()).trim();
    }

    public static String getNoteNumber(String str) {

        if (!Regex.isNote4Key(str)) {
            return null;
        }

        // there is some inconsistency with "-", would prefer otherwise use "-" instead of detecting
        // space
        String[] strArr = str.split("\\s+");

        return strArr[0].trim();
    }

    private String extractLineFromFile(String filePath, int from, int to) throws Exception {
        if (from < 1) {
            throw new Exception("The lowest line number is 1!");
        }

        // note this line only needs to be built once
        List stringList = readFile2StrArr(filePath);

        if (to > stringList.size()) {
            throw new Exception("to exceeding the number of lines of the file!");
        }

        if (from > to) {
            throw new Exception("From is greater than To!");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = from - 1; i <= to - 2; i++) {
            sb.append(stringList.get(i) + NEWLINE);
        }

        sb.append(stringList.get(to - 1));

        return sb.toString();
    }

}
