package net.natewm.tickmarkup;

import javafx.util.Pair;

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

    /**
     * Parses a TickMarkup formatted string.
     *
     * @param source  TickMarkup source string.
     * @return DictionaryNode of processed data from file.
     */
    public TickMarkupData parse(String source) throws SyntaxException {
        DictionaryNode dict = new DictionaryNode();
        int index = 0;

        while (index < source.length()) {
            index = parseWhitespace(source, index);
            index = parseDictItem(source, index, dict);
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }

        return new TickMarkupData(dict);
    }

    /**
     * Checks if a character is whitespace.
     *
     * @param c  Character to check.
     * @return  True if whitespace character, else false.
     */
    private static boolean isWhitespace(char c) {
        return (c == ' ' || c == '\t' || c == '\r' || c == '\n');
    }

    /**
     * Checks if a character can be used in a hexadecimal number.
     *
     * @param c Character to check.
     * @return  True if valid hexadecimal digit, else false.
     */
    private static boolean isHexChar(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    /**
     * Skips over whitespace characters.
     *
     * @param source  TickMarkup formatted code.
     * @param index  Current index position.
     * @return  Index position after skipping whitespace.
     */
    private int parseWhitespace(String source, int index) {
        while (index < source.length() && isWhitespace(source.charAt(index))) {
            ++index;
        }
        return index;
    }

    /**
     * Processes separators, like tick-space and newline.
     *
     * @param source  TickMarkup formatted code.
     * @param index  Current index position.
     * @return  Index position after separator (or same index if no separator).
     */
    private int parseSeparator(String source, int index) {
        if (index < source.length() && source.charAt(index) == '\n') {
            ++index;
        }
        else if (index < source.length()-1 && source.charAt(index) == special && source.charAt(index+1) == ' ') {
            index += 2;
        }
        return index;
    }

    /**
     * Parses a dictionary item from the data file.
     *
     * @param source  TickMarkup formatted code.
     * @param index  Current index position.
     * @param dict  DictionaryNode this item will belong to.
     * @return  Index position after processing dictionary item.
     */
    private int parseDictItem(String source, int index, DictionaryNode dict) throws SyntaxException {
        int idStart = index;

        index = parseIdentifier(source, index);
        if (index == idStart)
            return index;   // No identifier, so no dictionary item.

        int idEnd = index;
        if (source.charAt(index) != ':') {
            throw new SyntaxException("Missing expected colon.");
        }
        ++index;  // Skip ':'

        index = parseWhitespace(source, index);
        Pair<Integer, Node> result = parseNode(source, index);
        index = result.getKey();

        try {
            dict.getEntries().put(source.substring(idStart, idEnd), result.getValue());
        } catch (IncorrectTypeException e) {
            // We know this is a DictionaryNode, so there shouldn't be any errors.
            e.printStackTrace();
        }

        return index;
    }

    /**
     * Parses dictionary key identifiers.
     *
     * @param source  TickMarkup formatted code.
     * @param index  Current index position.
     * @return  Index after processing identifier.
     */
    private int parseIdentifier(String source, int index) {
        while (index < source.length()
                && source.charAt(index) != '\n'
                && source.charAt(index) != ':'
                && source.charAt(index) != special) {
            ++index;
        }
        return index;
    }

    /**
     * Parses a dictionary node.
     *
     * @param source  TickMarkup formatted code.
     * @param index  Current index.
     * @return  Index after processing and dictionary node.
     */
    private Pair<Integer, Node> parseDictNode(String source, int index) throws SyntaxException {
        DictionaryNode node = new DictionaryNode();
        int start = -1;

        while (index < source.length() && index != start) {
            start = index;
            index = parseWhitespace(source, index);
            index = parseDictItem(source, index, node);
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }
        if (index >= source.length()-1) {
            throw new SyntaxException("Unexpected end of file at " + index + ".");
        }
        if (index < source.length()-1 && !(source.charAt(index) == special && source.charAt(index+1) == '}')) {
            throw new SyntaxException("Missing dictionary closing brace at " + index + ".");
        }
        // Skip `}
        index += 2;

        return new Pair<>(index, node);
    }

    /**
     * Parses a list node.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing and List Node.
     */
    private Pair<Integer, Node> parseListNode(String source, int index) throws SyntaxException {
        ListNode node = new ListNode();
        Pair<Integer, Node> result;
        int start = -1;

        while (index < source.length() && index != start) {
            start = index;
            index = parseWhitespace(source, index);
            result = parseNode(source, index);
            if (index != result.getKey()) {
                index = result.getKey();
                try {
                    node.getItems().add(result.getValue());
                } catch (IncorrectTypeException e) {
                    // We know this is a ListNode, so should be no errors.
                    e.printStackTrace();
                }
            }
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }
        if (index >= source.length()-1) {
            throw new SyntaxException("Unexpected end of file at " + index + ".");
        }
        if (index < source.length()-1 && !(source.charAt(index) == special && source.charAt(index+1) == ']')) {
            throw new SyntaxException("Missing list closing brace at " + index + ".");
        }
        // Skip `]
        index += 2;

        return new Pair<>(index, node);
    }

    /**
     * Parse Tag node.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing and Tags Node.
     */
    private Pair<Integer, Node> parseTagsNode(String source, int index) throws SyntaxException {
        TagsNode node = new TagsNode();
        int start = -1;

        while (index < source.length() && index != start) {
            start = index;
            index = parseWhitespace(source, index);
            index = parseTagsItem(source, index, node);
            index = parseWhitespace(source, index);
            index = parseSeparator(source, index);
        }
        if (index >= source.length()-1) {
            throw new SyntaxException("Unexpected end of file at " + index + ".");
        }
        if (index < source.length()-1 && !(source.charAt(index) == special && source.charAt(index+1) == '>')) {
            throw new SyntaxException("Missing tag closing brace at " + index + ".");
        }
        // Skip `>
        index += 2;

        return new Pair<>(index, node);
    }

    /**
     * Parse an individual tag item in a tags list..
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @param node  TagsNode the tag will belong to.
     * @return  Index after parsing.
     */
    private int parseTagsItem(String source, int index, TagsNode node) throws SyntaxException {
        int idStart = index;
        index = parseIdentifier(source, index);
        if (index == idStart) {
            // No identifier, so no tag.
            return index;
        }
        int idEnd = index;

        Node params = null;
        if (source.charAt(index) == special) {
            Pair<Integer, Node> result = parseNode(source, index);
            index = result.getKey();
            params = result.getValue();
        }
        if (source.charAt(index) != ':') {
            throw new SyntaxException("Missing expected colon at " + index + ".");
        }
        // Skip ':'
        ++index;

        index = parseWhitespace(source, index);
        Pair<Integer, Node> result = parseNode(source, index);
        index = result.getKey();

        try {
            node.getTags().add(new Tag(source.substring(idStart, idEnd), params, result.getValue()));
        } catch (IncorrectTypeException e) {
            // We know this is a TagsList, so there shouldn't be any errors.
            e.printStackTrace();
        }

        return index;
    }

    /**
     * Parses hexadecimal formatted integers.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing and Integer node.
     */
    private Pair<Integer, Node> parseHex(String source, int index) {
        int start = index;
        while (isHexChar(source.charAt(index))) {
            ++index;
        }
        int value = Integer.parseInt(source.substring(start, index), 16);
        return new Pair<>(index, new IntegerNode(value));
    }

    /**
     * Parses a binary formatted integer.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing and Integer node.
     */
    private Pair<Integer, Node> parseBinary(String source, int index) {
        int start = index;
        while (source.charAt(index) == '0' || source.charAt(index) == '1') {
            ++index;
        }
        int value = Integer.parseInt(source.substring(start, index), 2);
        return new Pair<>(index, new IntegerNode(value));
    }

    /**
     * Parses a boolean node.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing.
     */
    private Pair<Integer, Node> parseBoolNode(String source, int index) throws SyntaxException {
        // NOTE: If more keywords are added, this function would need to be renamed.
        int start = index;
        while (isAlphabetic(source.charAt(index))) {
            ++index;
        }
        String substr = source.substring(start, index);
        if (substr.equals("true") || substr.equals("yes")) {
            return new Pair<>(index, new BooleanNode(true));
        }
        else if (substr.equals("false") || substr.equals("no")) {
            return new Pair<>(index, new BooleanNode(false));
        }
        throw new SyntaxException("Unrecognized keyword at " + index + ".");
    }

    /**
     * Parse decimal nodes (doubles).
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @param num  Number for digits before the decimal point.
     * @return  The index after parsing and decimal node.
     */
    private Pair<Integer, Node> parseDecimalNode(String source, int index, int num) {
        int start = index;
        while (isDigit(source.charAt(index))) {
            ++index;
        }
        String substr = source.substring(start, index);
        double value = Double.parseDouble(num + "." + substr);
        return new Pair<>(index, new DecimalNode(value));
    }

    /**
     * Parses digits, which may be integers, decimals, dates, or time.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing.
     */
    private Pair<Integer, Node> parseDigits(String source, int index) {
        int start = index;
        if (source.charAt(index) == '-')
            ++index;

        while (index < source.length() && isDigit(source.charAt(index))) {
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

        return new Pair<>(index, new IntegerNode(num));
    }

    /**
     * Parses a string node.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing.
     */
    private Pair<Integer, Node> parseStringNode(String source, int index) {
        // TODO: Escaped characters
        int start;
        StringBuilder sb = new StringBuilder();
        boolean continueLine = true;

        while (continueLine) {
            continueLine = false;
            start = index;
            while (index < source.length()
                    && source.charAt(index) != '\n'
                    && source.charAt(index) != '\r'
                    && source.charAt(index) != special) {
                ++index;
            }

            // Check for string continuation characters to skip.
            if (source.charAt(start) == '|' || source.charAt(start) == ':') {
                // TODO: Figure out if there is a better way of doing this.  Skip first line?
                ++start;
            }
            sb.append(source.substring(start, index));

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

        return new Pair<>(index, new StringNode(sb.toString()));
    }

    /**
     * Determines what kind of node this is and parses it.
     *
     * @param source  TickMarkup code.
     * @param index  Start index.
     * @return  Index after parsing and Node that was parsed.
     */
    private Pair<Integer, Node> parseNode(String source, int index) throws SyntaxException {
        if (source.charAt(index) == special) {
            ++index;
            // Dictionary
            if (source.charAt(index) == '{') {
                return parseDictNode(source, index+1);
            }
            // List
            else if (source.charAt(index) == '[') {
                return parseListNode(source, index+1);
            }
            // Tags
            else if (source.charAt(index) == '<') {
                return parseTagsNode(source, index+1);
            }
            // Hex / Binary
            else if (source.charAt(index) == '0') {
                // Hex
                if (source.charAt(index+1) == 'x') {
                    return parseHex(source, index+2);
                }
                // Binary
                else if (source.charAt(index+1) == 'b') {
                    return parseBinary(source, index+2);
                }
                else {
                    return parseDigits(source, index);
                }
            }
            // Boolean
            else if (isAlphabetic(source.charAt(index))) {
                return parseBoolNode(source, index);
            }
            // Decimal
            else if (source.charAt(index) == '.') {
                return parseDecimalNode(source, index+1, 0);
            }
            // Integer, Decimal, Date, Time, DateTime
            else if (isDigit(source.charAt(index)) || source.charAt(index) == '-') {
                return parseDigits(source, index);
            }
            else if (source.charAt(index) == '}' || source.charAt(index) == ']' || source.charAt(index) == '>') {
                return new Pair<>(index-1, null);
            }
        }
        // String
        else {
            return parseStringNode(source, index);
        }

        throw new SyntaxException("Unable to parse node at " + index + ".");
    }
}
