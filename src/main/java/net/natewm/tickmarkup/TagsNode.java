package net.natewm.tickmarkup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TagsNode implements Node {
    private List<Tag> tags;

    public TagsNode() {
        tags = new ArrayList<>();
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        return tags.get(index);
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        return tags;
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        throw new IncorrectTypeException("Tags List");
    }

    public String toString() {
        return tags.toString();
    }
}
