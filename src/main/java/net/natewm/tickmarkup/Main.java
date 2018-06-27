package net.natewm.tickmarkup;

/*
TODO: Better syntax checking and throwing exceptions.
TODO: Comment nodes.
TODO: Date and time nodes.
*/


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();

        String source = null;
        try {
            source = new String(Files.readAllBytes(Paths.get("test.tml")), Charset.defaultCharset());
            DictionaryNode doc = parser.parse(source);

            for (Tag tag : doc.getEntry("scene").getTags()) {
                System.out.println(tag.getName() + "(" +
                        tag.getParams().getEntry("actor") + ") - " + tag.getContents());
            }

            System.out.println(doc.getEntry("description"));

            for (Tag tag : doc.getEntry("document").getTag(0).getContents().getTags()) {
                System.out.println(tag.getContents());
            }

            System.out.println(doc.getEntry("path"));
            System.out.println(doc.getEntry("url"));

            System.out.println();
            System.out.println(doc.toFormattedString("", false));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectTypeException e) {
            e.printStackTrace();
        }
    }
}
