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

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();

        String source = null;
        try {
            source = new String(Files.readAllBytes(Paths.get("test.tml")), Charset.defaultCharset());
            TickMarkupData data = parser.parse(source);

            for (Tag tag : data.getEntry("scene").getTags()) {
                System.out.println(tag.getName() + "(" +
                        tag.getParams().getEntry("actor") + ") - " + tag.getContents());
            }

            System.out.println(data.getEntry("description"));

            for (Tag tag : data.getEntry("document").getTag(0).getContents().getTags()) {
                System.out.println(tag.getContents());
            }

            System.out.println(data.getEntry("path"));
            System.out.println(data.getEntry("url"));

            System.out.println();
            System.out.println(data.toFormattedString(false));

        } catch (IOException | IncorrectTypeException | SyntaxException e) {
            e.printStackTrace();
        }
    }
}
