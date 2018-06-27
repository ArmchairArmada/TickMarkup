package net.natewm.tickmarkup;

import java.util.Map;

public class Tag {
    private String name;
    private Node params;
    private Node contents;

    public Tag(String name, Node params, Node contents) {
        this.name = name;
        this.params = params;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public Node getParams() {
        return params;
    }

    public Node getContents() {
        return contents;
    }

    public String toFormattedString(String indent, boolean inline) {
        if (params == null) {
            return name + ": " + contents.toFormattedString(indent, inline);
        }
        return name + params.toFormattedString(indent, true) + ": " + contents.toFormattedString(indent, inline);
    }

    public String toString() {
        if (params == null) {
            return name + ":" + contents;
        }
        return name + params + ":" + contents;
    }
}
