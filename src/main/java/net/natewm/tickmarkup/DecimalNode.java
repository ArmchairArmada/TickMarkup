package net.natewm.tickmarkup;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DecimalNode implements Node {
    double value;

    public DecimalNode(double value) {
        this.value = value;
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        return value;
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        throw new IncorrectTypeException("Decimal");
    }

    public String toString() {
        return Double.toString(value);
    }
}
