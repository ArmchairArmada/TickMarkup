package net.natewm.tickmarkup;

import java.util.Map;

public class Tag {
    private String name;
    private Map<String, Node> parameters;
    private Node contents;

    public Tag(String name, Map<String, Node> parameters, Node contents) {
        this.name = name;
        this.parameters = parameters;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public Map<String, Node> getParameters() {
        return parameters;
    }

    public Node getContents() {
        return contents;
    }

    public Node getParam(String key) {
        return parameters.get(key);
    }

    public String toString() {
        return name + parameters + ": " + contents;
    }
}
