package net.natewm.tickmarkup;

import java.util.Map;

public class Tag {
    private String name;
    private Map<String, Object> parameters;
    private Object contents;

    public Tag(String name, Map<String, Object> parameters, Object contents) {
        this.name = name;
        this.parameters = parameters;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Object getContents() {
        return contents;
    }

    public Object getParam(String key) {
        return parameters.get(key);
    }
}
