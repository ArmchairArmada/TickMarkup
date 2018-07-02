package net.natewm.tickmarkup;

import com.sun.deploy.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StringNode implements Node {
    String string;

    public StringNode(String string) {
        this.string = string;
    }

    @Override
    public Node getEntry(String name) throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public Map<String, Node> getEntries() throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public Node getItem(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public List<Node> getItems() throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public Tag getTag(int index) throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public List<Tag> getTags() throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public String getStr() throws IncorrectTypeException {
        return string;
    }

    @Override
    public int getInt() throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public double getDec() throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public boolean getBool() throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public Date getDate() throws IncorrectTypeException {
        throw new IncorrectTypeException("String");
    }

    @Override
    public String toFormattedString(String indent, boolean inline) {
        StringBuilder sb = new StringBuilder();
        String lines[] = string.split("\n");

        if (inline) {
            //return String.join("` ", lines);
            if (lines.length > 1) {
                sb.append("\n");
                sb.append(":");
                sb.append(lines[0]);
                sb.append("\n");
            }
            else {
                sb.append(lines[0]);
            }

            for (int i = 1; i < lines.length-1; i++) {
                sb.append(":");
                sb.append(lines[i]);
                sb.append("\n");
            }
            if (lines.length > 1) {
                sb.append(":");
                sb.append(lines[lines.length-1]);
            }
        }
        else {
            if (lines.length > 1) {
                sb.append("\n");
                sb.append(indent);
                sb.append(":");
                sb.append(lines[0]);
                sb.append("\n");
            }
            else {
                sb.append(lines[0]);
            }

            for (int i = 1; i < lines.length-1; i++) {
                sb.append(indent);
                sb.append(":");
                sb.append(lines[i]);
                sb.append("\n");
            }
            if (lines.length > 1) {
                sb.append(indent);
                sb.append(":");
                sb.append(lines[lines.length-1]);
            }
        }

        return sb.toString();
    }

    public String toString() {
        return string;
    }
}
