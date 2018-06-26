package net.natewm.tickmarkup;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

public class Parser {
    private char special;

    public Parser() {
        special = '`';
    }

    public Parser(char special) {
        this.special = special;
    }

    public Map<String, Object> parse(String source) {
        Map<String, Object> doc = new HashMap<>();
        int index=0;

        while (index < source.length()) {
            index = parseWhitespace(source, index);
            index = parseDictItem(source, index, doc);
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }

        return doc;
    }

    private int parseWhitespace(String source, int index) {
        while (index < source.length() && (source.charAt(index) == ' ' || source.charAt(index) == '\t' || source.charAt(index) == '\n' || source.charAt(index) == '\r')) {
            ++index;
        }
        return index;
    }

    private int parseSeparator(String source, int index) {
        if (index >= source.length())
            return index;

        if (source.charAt(index) == '\n') {
            ++index;
        }
        else if (source.charAt(index) == special && source.charAt(index+1) == ' ') {
            index += 2;
        }
        return index;
    }

    private int parseDictItem(String source, int index, Map<String, Object> dict) {
        if (source.charAt(index) == special)
            return index;

        int idStart = index;
        index = parseIdentifier(source, index);
        int idEnd = index;
        ++index;  // Skip ':'
        index = parseWhitespace(source, index);
        Pair<Integer, Object> result = parseNode(source, index);
        index = result.getKey();

        dict.put(source.substring(idStart, idEnd), result.getValue());

        return index;
    }

    private int parseIdentifier(String source, int index) {
        while (source.charAt(index) != ':' && source.charAt(index) != special) {
            ++index;
        }
        return index;
    }

    private Pair<Integer, Object> parseNode(String source, int index) {
        Pair <Integer, Object> result = null;

        if (source.charAt(index) == special) {
            ++index;
            // Dictionary
            if (source.charAt(index) == '{') {
                result = parseDictNode(source, index+1);
            }
            // List
            else if (source.charAt(index) == '[') {
                result = parseListNode(source, index+1);
            }
            // Tags
            else if (source.charAt(index) == '<') {
                result = parseTagsNode(source, index+1);
            }
            // Hex / Binary
            else if (source.charAt(index) == '0') {
                // Hex
                if (source.charAt(index+1) == 'x') {
                    result = parseHex(source, index+2);
                }
                // Binary
                else if (source.charAt(index+1) == 'b') {
                    result = parseBinary(source, index+2);
                }
                else {
                    result = parseDigits(source, index);
                }
            }
            // Boolean
            else if (source.charAt(index) == 't' || source.charAt(index) == 'f') {
                result = parseBoolNode(source, index);
            }
            // Decimal
            else if (source.charAt(index) == '.') {
                result = parseDecimalNode(source, index+1, 0);
            }
            // Integer, Decimal, Date, Time, DateTime
            else if (isDigit(source.charAt(index))) {
                result = parseDigits(source, index);
            }
        }
        // String
        else {
            result = parseStringNode(source, index);
        }

        return result;
    }

    private Pair<Integer, Object> parseDictNode(String source, int index) {
        Map<String, Object> node = new HashMap<>();

        while (source.charAt(index) != special && source.charAt(index+1) != '}') {
            index = parseWhitespace(source, index);
            index = parseDictItem(source, index, node);
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }
        index+=2;

        return new Pair<>(index, node);
    }

    private Pair<Integer, Object> parseListNode(String source, int index) {
        List<Object> node = new ArrayList<>();
        Pair<Integer, Object> result;

        index = parseWhitespace(source, index);
        while (!(source.charAt(index) == special && source.charAt(index+1) == ']')) {
            index = parseWhitespace(source, index);
            result = parseNode(source, index);
            index = result.getKey();
            node.add(result.getValue());
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }
        index+=2;

        return new Pair<>(index, node);
    }

    private Pair<Integer, Object> parseTagsNode(String source, int index) {
        List<Tag> node = new ArrayList<>();

        index = parseWhitespace(source, index);
        while (source.charAt(index) != special && source.charAt(index+1) != '>') {
            index = parseWhitespace(source, index);
            index = parseTagsItem(source, index, node);
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }
        index+=2;

        return new Pair<>(index, node);
    }

    private int parseTagsItem(String source, int index, List<Tag> node) {
        if (source.charAt(index) == special)
            return index;

        int idStart = index;
        index = parseIdentifier(source, index);
        int idEnd = index;

        Map<String, Object> params = null;
        if (source.charAt(index) == special && source.charAt(index+1) == '(') {
            Pair<Integer, Map<String, Object>> result = parseParams(source, index+2);
            index = result.getKey();
            params = result.getValue();
        }
        else {
            ++index;
        }
        index = parseWhitespace(source, index);
        Pair<Integer, Object> result = parseNode(source, index);
        index = result.getKey();

        node.add(new Tag(source.substring(idStart, idEnd), params, result.getValue()));

        return index;
    }

    private Pair<Integer, Map<String, Object>> parseParams(String source, int index) {
        if (source.charAt(index) == special)
            return new Pair<>(index+3, null);

        Map<String, Object> params = new HashMap<>();

        while (index < source.length() && source.charAt(index) != special && source.charAt(index+1) != ')') {
            index = parseWhitespace(source, index);
            index = parseDictItem(source, index, params);
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }
        index+=3;

        return new Pair<>(index, params);
    }

    private Pair<Integer, Object> parseHex(String source, int index) {
        int start = index;
        while (isHexChar(source.charAt(index))) {
            ++index;
        }
        int value = Integer.parseInt(source.substring(start, index), 16);
        return new Pair<>(index, value);
    }

    private boolean isHexChar(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    private Pair<Integer, Object> parseBinary(String source, int index) {
        int start = index;
        while (source.charAt(index) == '0' || source.charAt(index) == '1') {
            ++index;
        }
        int value = Integer.parseInt(source.substring(start, index), 2);
        return new Pair<>(index, value);
    }

    private Pair<Integer, Object> parseBoolNode(String source, int index) {
        int start = index;
        while (isAlphabetic(source.charAt(index))) {
            ++index;
        }
        String substr = source.substring(start, index);
        if (substr.equals("true")) {
            return new Pair<>(index, true);
        }
        else if (substr.equals("false")) {
            return new Pair<>(index, false);
        }
        // TODO: Throw exception
        return null;
    }

    private Pair<Integer, Object> parseDecimalNode(String source, int index, int num) {
        int start = index;
        while (isDigit(source.charAt(index))) {
            ++index;
        }
        String substr = source.substring(start, index);
        double value = Double.parseDouble(num + "." + substr);
        return new Pair<>(index, value);
    }

    private Pair<Integer, Object> parseDigits(String source, int index) {
        int start = index;
        while (isDigit(source.charAt(index))) {
            ++index;
        }
        int num = Integer.parseInt(source.substring(start, index));
        if (source.charAt(index) == '.') {
            return parseDecimalNode(source, index+1, num);
        }
        else if (source.charAt(index) == ':') {
            // TODO: Time
        }
        else if (source.charAt(index) == '-') {
            // TODO: Date
        }

        return new Pair<>(index, num);
    }

    private Pair<Integer, Object> parseStringNode(String source, int index) {
        // TODO: Escaped characters
        int start;
        StringBuilder sb = new StringBuilder();
        boolean continueLine = true;

        while (continueLine) {
            continueLine = false;
            start = index;
            while (index < source.length() && source.charAt(index) != '\n' && source.charAt(index) != '\r' && source.charAt(index) != special) {
                ++index;
            }
            if (start == index) {
                sb.append('\n');
            }
            else {
                if (source.charAt(start) == '|' || source.charAt(start) == ':') {
                    // TODO: Figure out if there is a better way of doing this.
                    ++start;
                }
                sb.append(source.substring(start, index));
            }
            index = parseWhitespace(source, index);
            if (index < source.length() && source.charAt(index) == '|') {
                ++index;
                continueLine = true;
            }
            if (index < source.length() && source.charAt(index) == ':') {
                ++index;
                continueLine = true;
                sb.append('\n');
            }
        }

        return new Pair<>(index, sb.toString());
    }
}
