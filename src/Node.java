import java.util.ArrayList;
import java.util.List;

// Node class is the foundation of a tree

class Node {

    private final static String END_OF_LINE = "\n";

    private static int totalNumberOfNodes = 0;

    private String key = null;

    // this contains the actual content in each node
    private StringBuilder sb = new StringBuilder();

    // this two indexes contains only the starting line number and ending line number
    private int startLine = -1;
    private int endLine = -1;

    private Node parent = null;
    private List<Node> children = new ArrayList<Node>();

    private boolean keepAddLine = false;
    private boolean hasError = false;
    private boolean hasWarning = false;

    private boolean visited = false;

    // If non-arg constructor is used, only when a root node is created
    public Node() {
        // the key for root node is "root"
        key = "root";
        keepAddLine = true;
    }

    // Always create a node with key as its argument
    public Node(String key) {
        this.key = key;
        keepAddLine = true;
        totalNumberOfNodes++;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited2True() {
        visited = true;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public boolean hasError() {
        return hasError;
    }

    public void setHasError() {
        hasError = true;
    }

    public boolean hasWarning() {
        return hasWarning;
    }

    public void setHasWarning() {
        hasWarning = true;
    }

    public void setAddLineTrue() {
        keepAddLine = true;
    }

    public void setAddLineFalse() {
        keepAddLine = false;
    }

    public boolean keepAddLine() {
        return keepAddLine;
    }

    public String getKey() {
        return key;
    }

    public String getContent() {
        return sb.toString();
    }

    public void addContent2Node(String line) {
        sb.append(line).append(END_OF_LINE);
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getNumberOfChildren() {
        return children.size();
    }

    public boolean hasChildren() {
        return (children.size() != 0);
    }

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
    }

    public static int getTotalNumberOfNodes() {
        // + 1 is to account for root node
        return totalNumberOfNodes + 1;
    }

}