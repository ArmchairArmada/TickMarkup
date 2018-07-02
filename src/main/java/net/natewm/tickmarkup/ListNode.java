package net.natewm.tickmarkup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ListNode implements Node {
    private List<Node> items;

    public ListNode() {
        items = new ArrayList<>();
    }

    public void addItem(Node item) {
        items.add(item);
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        return items.get(index);
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        return items;
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        throw new IncorrectTypeException("List");
    }

    @Override
    public String toFormattedString(String indent, boolean inline) {
        StringBuilder sb = new StringBuilder();
        String newIndent = indent + "\t";

        if (inline) {
            sb.append("`[");

            for (int i=0; i<items.size()-1; i++) {
                sb.append(items.get(i).toFormattedString(newIndent, inline));
                sb.append("` ");
            }
            if (items.size() > 0) {
                sb.append(items.get(items.size()-1).toFormattedString(newIndent, inline));
            }

            sb.append("`]");
        }
        else {
            sb.append("`[\n");

            for (Node item : items) {
                sb.append(newIndent);
                sb.append(item.toFormattedString(newIndent, inline));
                sb.append("\n");
            }

            sb.append(indent);
            sb.append("`]");
        }

        return sb.toString();
    }

    public String toString() {
        return items.toString();
    }
}
