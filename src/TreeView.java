import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeView {
    public static void main(String[] args) throws IOException {

        // SHELL creation TO BE REMOVED LATER
        // TODO this section of code will be replaced later
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Tree View");
        shell.setLayout(new FillLayout());
        // TODO above code will be replaced later

        // Generate tree model based on file
        String fileName = "C:\\workspace_project\\TreeView\\SAPupConsole.log";
        Node root = TreeModel.generateTree(fileName);

        // TREEVIEW generation
        final Tree tree = new Tree(shell, SWT.SINGLE);

        TreeItem treeItem_layer1 = null;
        TreeItem treeItem_layer2 = null;

        for (Node node_layer1 : root.getChildren()) {

            treeItem_layer1 = new TreeItem(tree, SWT.NONE);
            treeItem_layer1.setText(node_layer1.getKey());
            treeItem_layer1.setData(node_layer1);

            if (node_layer1.hasWarning()) {
                // treeItem_layer1.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_DARK_YELLOW));
                treeItem_layer1.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
            }

            if (node_layer1.hasError()) {
                // treeItem_layer1.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
                treeItem_layer1.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));

            }

            for (Node node_layer2 : node_layer1.getChildren()) {

                treeItem_layer2 = new TreeItem(treeItem_layer1, SWT.NONE);
                treeItem_layer2.setText(node_layer2.getKey());
                treeItem_layer2.setData(node_layer2);

                if (node_layer2.hasWarning()) {
                    // treeItem_layer2.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_DARK_YELLOW));
                    treeItem_layer2.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
                }

                if (node_layer2.hasError()) {
                    treeItem_layer2.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
                    // treeItem_layer2.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
                }

            }
        }

        // ADD NODE LISTENER - need refactor
        tree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                Node node = null;
                TreeItem[] selection = tree.getSelection();
                for (int i = 0; i < selection.length; i++) {
                    node = (Node) selection[i].getData();
                }
                // print the content of the node
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(node.getContent());
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        });

        // TODO this part of code will be removed later
        // open shell
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
        // TODO this part of code will be removed later

    }
}