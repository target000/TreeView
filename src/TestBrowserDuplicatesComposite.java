import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class TestBrowserDuplicatesComposite extends Composite {

    private static Browser browser = null;
    
    public TestBrowserDuplicatesComposite(Composite parent, int style) {
        super(parent, style);

        browser = new Browser( parent    , style);
        browser.setUrl("https://www.google.com/");
        setLayout(new FillLayout());
        
    }

    @Override
    protected void checkSubclass() {
    }

}
