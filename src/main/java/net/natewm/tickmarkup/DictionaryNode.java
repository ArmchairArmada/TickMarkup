package net.natewm.tickmarkup;

import java.util.*;

public class DictionaryNode implements Node {
    private Map<String, Node> entries;

    public DictionaryNode() {
        entries = new HashMap<>();
    }

    public void putEntry(String key, Node value) {
        entries.put(key, value);
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        return entries.get(name);
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        return entries;
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        throw new IncorrectTypeException("Dictionary");
    }

    @Override
    public String toFormattedString(String indent, boolean inline) {
        StringBuilder sb = new StringBuilder();
        String newIndent = indent + "    ";

        if (inline) {
            sb.append("`{");

            List<Map.Entry<String, Node>> entryList = new ArrayList<>(entries.entrySet());
            for (int i=0; i<entryList.size()-1; i++) {
                sb.append(entryList.get(i).getKey());
                sb.append(": ");
                sb.append(entryList.get(i).getValue().toFormattedString(newIndent, inline));
                sb.append("` ");
            }
            if (entryList.size() > 0) {
                sb.append(entryList.get(entryList.size()-1).getKey());
                sb.append(": ");
                sb.append(entryList.get(entryList.size()-1).getValue().toFormattedString(newIndent, inline));
            }
            sb.append("`}");
        }
        else {
            sb.append("`{\n");

            for (Map.Entry<String, Node> entry : entries.entrySet()) {
                sb.append(newIndent);
                sb.append(entry.getKey());
                sb.append(": ");
                sb.append(entry.getValue().toFormattedString(newIndent, inline));
                sb.append("\n");
            }

            sb.append(indent);
            sb.append("`}");
        }

        return sb.toString();
    }

    public String toString() {
        return entries.toString();
    }
}
