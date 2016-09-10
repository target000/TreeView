import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ParseNote {

    public static void main(String[] args) throws IOException {

        // read hier and create a treemmodel based on it

        // String keyroot = "root";
        // String keyroot1 = "cat1";
        // String keyroot2 = "cat2";
        // String keyroot3 = "cat3";
        // String note = "note";

        File file = new File("hier.txt");

        // Setup reader
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        Node root = null;
        Node cat1 = null;
        Node cat2 = null;
        Node cat3 = null;
        Node note;

        int missLineCount = 0;
        while ((line = br.readLine()) != null) {
            // System.out.println(line);

            switch (line.trim()) {
            case "root":
                root = new Node();
                break;
            case "cat1":
                cat1 = new Node("cat1");
                root.addChild(cat1);
                break;
            case "cat2":
                cat2 = new Node("cat2");
                cat1.addChild(cat2);
                break;
            case "cat3":
                cat3 = new Node("cat3");
                cat2.addChild(cat3);
                break;
            case "note":
                note = new Node("note");
                cat3.addChild(note);
                break;
            default:
                missLineCount++;
                break;
            }

        }

        System.out.println("missed line =" + missLineCount);

        List<Node> list = root.getChildren();
        for (Node node : list) {
            System.out.println(node.getKey());
            for (Node lvl1 : node.getChildren()) {
                System.out.println("  " + lvl1.getKey());
                for (Node lvl2 : lvl1.getChildren()) {
                    System.out.println("    " + lvl2.getKey());
                    for (Node lvl3 : lvl2.getChildren()) {
                        System.out.println("      " + lvl3.getKey());
                        for (Node lv4 : lvl3.getChildren()) {
                            System.out.println("        " + lv4.getKey());
                        }
                    }
                }
            }
        }

    } // END OF MAIN

} // END OF CLASS
