package net.natewm.tickmarkup;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateNode implements Node {
    Date date;

    public DateNode(Date date) {
        this.date = date;
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        throw new IncorrectTypeException("Date");
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        return date;
    }

    @Override
    public String toFormattedString(String indent, boolean inline) {
        return "`" + date;
    }

    public String toString() {
        return date.toString();
    }
}
