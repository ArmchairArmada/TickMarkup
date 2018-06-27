package net.natewm.tickmarkup;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class IntegerNode implements Node {
    int value;

    public IntegerNode(int value) {
        this.value = value;
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        return value;
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        throw new IncorrectTypeException("Integer");
    }

    @Override
    public String toFormattedString(String indent, boolean inline) {
        return "`" + value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
