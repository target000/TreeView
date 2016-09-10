import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.XMLConfiguration;

public class DomParserExample {

    public static void main(String[] args) throws ConfigurationException {
        try {
            XMLConfiguration config = new XMLConfiguration("test.xml");

            // System.out.println(config.getRoot().getName());
            // System.out.println("--------------");
            //
            // System.out.println(config.getRoot().getChild(0).getChildrenCount());

            // System.out.println(config.getRoot().getChild(0).getChild(0).getValue());
            // System.out.println(config.getRoot().getChild(0).getChild(1).getValue());
            // System.out.println(config.getRoot().getChild(0).getChild(2).getValue());
            // System.out.println(config.getRoot().getChild(0).getChild(3).getValue());
            // System.out.println(config.getRoot().getChild(0).getChild(4).getValue());
            // System.out.println(config.getRoot().getChild(0).getChild(5).getValue());
            // System.out.println(config.getRoot().getChild(0).getChild(6).getValue());

            Node root = config.getRoot();

            int rootChildNum = -1;
            if (root != null && root.hasChildren()) {
                rootChildNum = root.getChildrenCount();
                System.out.println("num children = " + root.getChildrenCount());
            }

            for (int i = 0; i < rootChildNum; i++) {
                System.out.println(root.getChild(i).getName());

                for (int j = 0; j < root.getChild(i).getChildrenCount(); j++) {
                    // List list = root.getChild(i).getChild(i);
                    System.out.println(root.getChild(i).getChild(j).getName());
                }
                System.out.println("---");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

}