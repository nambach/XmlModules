package utils;

import xmlchecker.EntitySyntaxChecker;
import xmlchecker.XmlSyntaxChecker;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    private static void testRefine() {
        String src = FileUtils.readTextContent(FileUtils.getFilePath("raw/Vinabook-full.html"));

        long start = System.currentTimeMillis();
        src = getBody(src);
        src = removeMiscellaneousTags(src);

        HtmlUtils htmlUtils = new HtmlUtils();
        src = htmlUtils.parseHtml2(src);
        src = htmlUtils.parseHtml3(src);

        FileUtils.exportFile(src, FileUtils.getFilePath("textutils/draft.xml"));

        System.out.println(System.currentTimeMillis() - start);
    }

    public static String refineHtml(String src) {
//        src = removeSpaceCharacters(src);
//        src = getBody(src);
//        src = encloseHtmlTag(src);
//        src = removeMiscellaneousTags(src);

        src = getBody(src);
        src = removeMiscellaneousTags(src);

//        HtmlUtils htmlUtils = new HtmlUtils();
//        src = htmlUtils.parseHtml2(src);
//        src = htmlUtils.parseHtml3(src);

        XmlSyntaxChecker xmlSyntaxChecker = new XmlSyntaxChecker();
        EntitySyntaxChecker entitySyntaxChecker = new EntitySyntaxChecker();
        src = xmlSyntaxChecker.check(src);
        src = entitySyntaxChecker.check(src);

        //crop one more time
        src = getBody(src);
        return src;
    }

    public static String getBody(String src) {
        String result = src;

        String expression = "<body.*?<[ ]*/[ ]*body[ ]*>";
        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(result);

        if (matcher.find()) {
            result = matcher.group(0);
        }

        return result;
    }

    public static String removeMiscellaneousTags(String src) {
        String result = src;
        List<String> doubleTags = Arrays.asList("script");

        for (String doubleTag : doubleTags) {
            //Match <singleTag> that contains anything but the phrase "/>", with reluctant matches
            String expression = "<tag.*?</tag[ ]*>".replaceAll("tag", doubleTag);
            result = result.replaceAll(expression, "");
        }


        //Remove all comments
        String expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");

        //Remove all whitespace
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");

        return result;
    }

    public static String encloseHtmlTag(String src) {
        String result = src;
        List<String> singleTags = Arrays.asList(
                "area", "base", "br", "col", "command",
                "embed", "hr", "img", "input", "keygen",
                "link", "meta", "param", "source", "track", "wbr");

        for (String singleTag : singleTags) {
            //Match <singleTag> that contains anything but the phrase "/>", with reluctant matches
            String expression = "<" + singleTag + "(((?!(/>)).)*?)>";
            Pattern pattern = Pattern.compile(expression);

            Matcher matcher = pattern.matcher(result);

            while (matcher.find()) {
                result = matcher.replaceFirst("<" + singleTag + matcher.group(1) + "/>");

                matcher = pattern.matcher(result);
            }
        }
        return result;
    }

    public static String removeSpaceCharacters(String src) {
        String spaceChar = "\\s+";
        Pattern spacePattern = Pattern.compile(spaceChar);

//        String whitespace = "[ ]{2,}";
//        Pattern whitespacePattern = Pattern.compile(whitespace);

        String spaceBetweenTags = "(>[ ]+<|>[ ]*&.*?;[ ]*<)";
        Pattern spaceBetweenTagsPattern = Pattern.compile(spaceBetweenTags);

        String result;

        result = spacePattern.matcher(src).replaceAll(" ");
//        result = whitespacePattern.matcher(result).replaceAll(" ");
        result = spaceBetweenTagsPattern.matcher(result).replaceAll("><");

        return result;
    }
}

//        String tagRegex = "<\\w+\\s+[^>]*?(?:>|/>)";
//        String attributeRegex = "(\\w+=\"[^/><]*?\")(\\w+=\"[^/><]*?\")";