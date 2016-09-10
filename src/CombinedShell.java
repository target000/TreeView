import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CombinedShell extends Shell {

    // FOR TYPE I, set typeSwitch to true

    // TYPE I -- ACTUAL FILE
    private static String filepath1 = "C:\\workspace_project\\TreeView\\SAPupConsole.log";
    private static String ctrlFilePath1 = "ControlFiles\\sapupconsole.properties";

    // TYPE I -- TESTING FILE
    private static String filepath2 = "C:\\workspace_project\\TreeView\\test_hier.txt";
    private static String ctrlFilePath2 = "ControlFiles\\type1_test.properties";

    // FOR TYPE II, set typeSwitch to false

    // TYPE II -- ACTUAL FILE
    private static String filepath3 = "C:\\workspace_project\\TreeView\\sapnotes.txt";
    private static String ctrlFilePath3 = "ControlFiles\\sapnote.properties";

    // TYPE II -- TESTING FILE
    private static String filepath4 = "C:\\workspace_project\\TreeView\\test_hier2.txt";
    private static String ctrlFilePath4 = "ControlFiles\\type2_test.properties";

    private boolean typeSwitch = false;

    public CombinedShell() throws IOException {

        Display display = Display.getDefault();
        CombinedShell shell = new CombinedShell(display);

        // Layout
        FillLayout fillLayout = new FillLayout();
        fillLayout.type = SWT.HORIZONTAL;

        // shell.setLayout(fillLayout);

        // create the tree area
        TreeArea treeArea = new TreeArea(shell, SWT.NONE, filepath3, ctrlFilePath3, typeSwitch);
        treeArea.setLayout(fillLayout);

        if (typeSwitch) {
            // create the text area
            TextArea textArea = new TextArea(shell, SWT.NONE);
            textArea.setLayout(fillLayout);
        } else {
            // create the browser area
            BrowserArea browserArea = new BrowserArea(shell, SWT.NONE);

            // browserArea.setLayout(fillLayout);
        }

        // --------------------------------------------

        shell.open();
        shell.layout();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

    }

    // MAIN METHOD -- STARTING POINT
    public static void main(String args[]) {
        try {
            new CombinedShell();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CombinedShell(Display display) {
        super(display, SWT.SHELL_TRIM);
        setLayout(new FillLayout(SWT.HORIZONTAL));
        createContents();
    }

    protected void createContents() {
        setText("My Tree Viewer");
    }

    public String getFilePath() {
        return filepath1;
    }

    public boolean getFlag() {
        return typeSwitch;
    }

    @Override
    protected void checkSubclass() {
    }
}
