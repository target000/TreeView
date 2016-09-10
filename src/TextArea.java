import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;

public class TextArea extends Composite {
    private static StyledText text = null;

    public TextArea(Composite parent, int style) {
        super(parent, style);

        text = new StyledText(this, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
        
        
        text.setEditable(false);

        
        text.addLineBackgroundListener(new LineBackgroundListener() {
            public void lineGetBackground(LineBackgroundEvent event) {
                if (event.lineText.toLowerCase().contains("warn")) {
                    event.lineBackground = getDisplay().getSystemColor(SWT.COLOR_YELLOW);

                } else if (event.lineText.toLowerCase().contains("err")) {
                    event.lineBackground = getDisplay().getSystemColor(SWT.COLOR_RED);

                }

            }
        });

    }

    public static void setText() {
        text.setText(TreeArea.getText());
    }

    @Override
    protected void checkSubclass() {
    }

}
