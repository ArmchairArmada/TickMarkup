package net.natewm.tickmarkup;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Node {
    Node getEntry(String name) throws IncorrectTypeException;
    Map<String, Node> getEntries() throws IncorrectTypeException;
    Node getItem(int index) throws IncorrectTypeException;
    List<Node> getItems() throws IncorrectTypeException;
    Tag getTag(int index) throws IncorrectTypeException;
    List<Tag> getTags() throws IncorrectTypeException;
    String getStr() throws IncorrectTypeException;
    int getInt() throws IncorrectTypeException;
    double getDec() throws IncorrectTypeException;
    boolean getBool() throws IncorrectTypeException;
    Date getDate() throws IncorrectTypeException;
}
