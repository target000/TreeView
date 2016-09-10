import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeNodeDefaultSelectionListener {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        final Tree tree = new Tree(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        for (int i = 0; i < 4; i++) {
            TreeItem item0 = new TreeItem(tree, 0);
            item0.setText("Item " + i);
            for (int j = 0; j < 4; j++) {
                TreeItem item1 = new TreeItem(item0, 0);
                item1.setText("SubItem " + i + " " + j);
                for (int k = 0; k < 4; k++) {
                    TreeItem item2 = new TreeItem(item1, 0);
                    item2.setText("SubItem " + i + " " + j + " " + k);
                }
            }
        }
        tree.addListener(SWT.DefaultSelection, new Listener() {
            public void handleEvent(Event e) {
                String string = "";
                TreeItem[] selection = tree.getSelection();
                for (int i = 0; i < selection.length; i++)
                    string += selection[i] + " ";
                System.out.println("DefaultSelection={" + string + "}");
            }
        });

        tree.getItems()[0].setExpanded(true);
        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}