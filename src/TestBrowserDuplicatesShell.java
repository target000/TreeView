import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestBrowserDuplicatesShell extends Shell {

    public static void main(String args[]) {
        try {
            Display display = Display.getDefault();
            TestBrowserDuplicatesShell shell = new TestBrowserDuplicatesShell(display);

            FillLayout fillLayout = new FillLayout();
            // fillLayout.type = SWT.VERTICAL;
            // fillLayout.spacing = 0;
            // fillLayout.marginWidth = 0;
            // fillLayout.marginHeight = 0;

            TestBrowserDuplicatesComposite ba = new TestBrowserDuplicatesComposite(shell, SWT.NONE);
            // TextArea ta = new TextArea(shell, SWT.NONE);
            // ta.setLayout(fillLayout);
            ba.setLayout(fillLayout);

            
            
            //--------------------
            shell.setLayout(new FillLayout());
            shell.open();
            shell.layout();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TestBrowserDuplicatesShell(Display display) {
        super(display, SWT.SHELL_TRIM);
        createContents();
    }

    /**
     * Create contents of the shell.
     */
    protected void createContents() {
        setText("SWT Application");
        setSize(450, 300);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
