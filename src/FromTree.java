import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class FromTree {
    public static void main(String[] args) throws IOException {

        TreeItem[] selection = null;

        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Text Tree Editor");
        shell.setLayout(new FillLayout());

        List<TreeItem> list = new ArrayList<TreeItem>();
        final Tree tree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        int level1NodeCount = 0;

        File file = new File("treetest.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        int lineCount = 0;
        String line = null;
        while ((line = br.readLine()) != null) {

            // whenever is a node there are some string/text associated with the node
            lineCount++;
            if (line.equals("1")) {
                list.add(new TreeItem(tree, SWT.NONE));
                list.get(level1NodeCount).setText("Level 1");
                list.get(level1NodeCount).setForeground(display.getSystemColor(SWT.COLOR_BLUE));
                // System.out.println("detected 1");
                level1NodeCount++;
            } else if (line.equals("2")) {
                TreeItem item = null;
                item = new TreeItem(list.get(level1NodeCount - 1), SWT.NONE);
                item.setText("Level 2");
                item.setForeground(display.getSystemColor(SWT.COLOR_RED));
                // System.out.println("detected 2");
            }
        }

        // tree.selectAll();

        // for (int i = 0; i < selection.length; i++) {
        // System.out.println(selection[i]);
        // }

        br.close();

        // add tree listener
        tree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {

                // TreeItem[] selection = tree.getSelection();

                // for (int i = 0; i < selection.length; i++) {
                System.out.println(tree.getSelection()[0]);

                // }
            }
        });

        // shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();

    }

    private static void readFile(String filePath) throws IOException {

        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {

            if (line.toLowerCase().contains("module")) {
                System.out.println(line);
            }
        }

        br.close();
    }

}
