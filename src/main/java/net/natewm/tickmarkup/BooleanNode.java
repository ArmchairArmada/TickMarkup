package net.natewm.tickmarkup;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BooleanNode implements Node {
    private boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        return value;
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        throw new IncorrectTypeException("Boolean");
    }

    public String toString() {
        return Boolean.toString(value);
    }
}
