package net.natewm.tickmarkup;

import java.util.Map;

public class TickMarkupData {
    private DictionaryNode dict;

    public TickMarkupData() {
        dict = new DictionaryNode();
    }

    public TickMarkupData(DictionaryNode dict) {
        this.dict = dict;
    }

    public void putEntry(String key, Node value) {
        dict.putEntry(key, value);
    }

    public Node getEntry(String name) throws IncorrectTypeException {
        return dict.getEntry(name);
    }

    public Map<String, Node> getEntries() throws IncorrectTypeException {
        return dict.getEntries();
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();

        try {
            for (Map.Entry<String, Node> entry : dict.getEntries().entrySet()) {
                sb.append(entry.getKey());
                sb.append(": ");
                sb.append(entry.getValue().toFormattedString("", false));
                sb.append("\n");
            }
        } catch (IncorrectTypeException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public String toString() {
        return dict.toString();
    }
}
