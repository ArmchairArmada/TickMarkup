package net.natewm.tickmarkup;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String toString() {
        return entries.toString();
    }
}
