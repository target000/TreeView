import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeArea extends Composite {

    private static String nodeString = "";
    private static Node root = null;
    private static Tree tree = null;

    public TreeArea(Composite parent, int style, String filePath, String ctrlFilePath, boolean flag) throws IOException {
        super(parent, style);

        if (flag) {

            // TODO IMPORTANT METHOD HERE
            root = TreeModel.generateTree2(filePath, ctrlFilePath);
            // TREEVIEW generation
            tree = new Tree(this, SWT.SINGLE);

            // populate the tree using DFS
            populateTreeViewByDFS(root);

            // ADD NODE LISTENER
            tree.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event e) {
                    Node node = null;
                    TreeItem[] selection = tree.getSelection();
                    for (int i = 0; i < selection.length; i++) {
                        node = (Node) selection[i].getData();
                    }
                    nodeString = node.getContent();
                    TextArea.setText();

                }
            });
        } else {
            // Get the root from note not from sapup.log

            // TODO IMPORTANT METHOD HERE
            root = TreeModel.generateTree_SAP_Note2(filePath, ctrlFilePath);
            // TREEVIEW generation
            tree = new Tree(this, SWT.SINGLE);

            // populate the tree using DFS
            populateTreeViewByDFS(root);

            // THIS IS A LISTENER
            tree.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event e) {
                    Node node = null;
                    TreeItem[] selection = tree.getSelection();
                    for (int i = 0; i < selection.length; i++) {
                        node = (Node) selection[i].getData();
                    }
                    if (Regex.isNote4Key(node.getKey())) {
                        String noteNum = TreeModel.getNoteNumber(node.getKey());
                        BrowserArea.setURL4Note(noteNum);
                    }

                }
            });

        }

    }

    public static String getText() {
        return nodeString;
    }

    @Override
    protected void checkSubclass() {
    }

    /**
     * This method is very important! This method will populate the treeview based on the model
     * using depth first search this is different than using nested for loops as most SWT examples
     * since for loops you have to know the depth in ahead of time and there is no way you can avoid
     * hard coding, however by using this method, the treeview generation is fully dependent on the
     * model's size and nothing else
     * 
     * @param node
     */
    private void populateTreeViewByDFS(Node node) {

        Stack<Node> stack = new Stack<Node>();
        // what this map does is to map each node in model to link it to a SWT "treeItem"
        Map<Node, TreeItem> map = new HashMap<Node, TreeItem>();
        TreeItem treeItem = null;

        stack.add(node);

        while (!stack.isEmpty()) {
            Node popNode = stack.pop();

            List<Node> childrenNodes = popNode.getChildren();
            for (int i = 0; i < childrenNodes.size(); i++) {
                Node childNode = childrenNodes.get(i);
                stack.add(childNode);

                if (childNode.getParent().getKey().equals("root")) {
                    treeItem = new TreeItem(tree, SWT.NONE);
                    treeItem.setText(childNode.getKey());
                    treeItem.setData(childNode);

                    if (childNode.hasWarning()) {
                        treeItem.setBackground(getDisplay().getSystemColor(SWT.COLOR_YELLOW));
                    }

                    if (childNode.hasError()) {
                        treeItem.setBackground(getDisplay().getSystemColor(SWT.COLOR_RED));
                    }

                } else {
                    treeItem = new TreeItem(map.get(childNode.getParent()), SWT.NONE);
                    treeItem.setText(childNode.getKey());
                    treeItem.setData(childNode);

                    if (childNode.hasWarning()) {
                        treeItem.setBackground(getDisplay().getSystemColor(SWT.COLOR_YELLOW));
                    }

                    if (childNode.hasError()) {
                        treeItem.setBackground(getDisplay().getSystemColor(SWT.COLOR_RED));
                    }

                }

                map.put(childNode, treeItem);

            }

        }

    }

}
