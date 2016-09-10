import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

public class BrowserArea extends Composite {

    private static Browser browser = null;

    public BrowserArea(Composite parent, int style) {
        super(parent, style);

        browser = new Browser(parent, style);

        browser.setEnabled(true);
        browser.setVisible(true);

        // default landing page
        browser.setUrl("https://i7p.wdf.sap.corp/sap/support/notes/67739");

    }

    @Override
    protected void checkSubclass() {
        // this is intended to be blank
    }

    public static void setURL4Note(String noteNumber) {
        browser.setUrl("https://i7p.wdf.sap.corp/sap/support/notes/" + noteNumber);
    }

    public static void setURL(String url) {
        if (url == null) {
            browser.setUrl("https://i7p.wdf.sap.corp/sap/support/notes/67739");
        }
        browser.setUrl(url);

    }

}
