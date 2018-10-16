package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class HtmlUtils {

    public static void main(String[] args) {
        HtmlUtils htmlUtils = new HtmlUtils();

//        String src = "<ul><li><a>Sách mới</a></h3><li><a >Sách sắp phát hành</a></h3><li><a>Sách bán chạy</a></h3><li><a>Sách giảm giá</a></h3><li><a>Sách hay Pibook khuyên đọc</a></h3></ul>";
        String src = "<option value=\"25&#xa; &#12;\"  selected >25</option>";
        System.out.println(src);
        src = htmlUtils.parseHtml2(src);
        src = htmlUtils.parseHtml3(src);
        System.out.println(src);
    }

    private static final char OPEN_BRACKET = '<';
    private static final char CLOSE_BRACKET = '>';
    private static final char SLASH_SIGN = '/';
    private static final char WHITE_SPACE = ' ';
    private static final char UNDERSCORE = '_';
    private static final char HYPHEN = '-';
    private static final char COLON = ':';
    private static final char PERIOD = '.';
    private static final char EQUAL = '=';
    private static final char AMP_ENTITY = '&';
    private static final char SEMICOLON = ';';
    private static final char SHARP = '#';
    private static final char X = 'x';

    private static final List<String> EMPTY_TAGS = Arrays.asList(
            "area", "base", "br", "col", "command",
            "embed", "hr", "img", "input", "keygen",
            "link", "meta", "param", "source", "track", "wbr");

    public String parseHtml2(String src) {
        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder();

        String status = "content";
        StringBuilder tagName = new StringBuilder(),
                attributeName = new StringBuilder(),
                attributeValue = new StringBuilder();

        Stack<String> stack = new Stack<>();

        for (int i = 0; i < reader.length; i++) {
            char c = reader[i];

            //CONTENT
            if ("content".equals(status)) {
                if (OPEN_BRACKET == c) {
                    status = "openBracket";
                }
                writer.append(c);
            } else if ("openBracket".equals(status)) {
                //START TAG
                if (Character.isLetter(c)) {
                    status = "tagName";
                    writer.append(c);

                    tagName.setLength(0);
                    tagName.append(c);

                //END TAG
                } else if (SLASH_SIGN == c) {
                    status = "endTag";
                    writer.append(c);

                    tagName.setLength(0);
                }
            //START TAG NAME
            } else if ("tagName".equals(status)) {
                if (Character.isLetterOrDigit(c)) {
                    tagName.append(c);
                    writer.append(c);
                }
                if (Character.isSpaceChar(c)) {
                    status = "insideTag";
                    writer.append(WHITE_SPACE);

                    if (!EMPTY_TAGS.contains(tagName.toString().toLowerCase())) {
                        stack.push(tagName.toString()); //PUSH OPEN TAG
                    }
                }
                if (SLASH_SIGN == c) {
                    status = "closeTagNow";
                    writer.append(c);
                }
                if (CLOSE_BRACKET == c) {
                    if (EMPTY_TAGS.contains(tagName.toString().toLowerCase())) {
                        writer.append(SLASH_SIGN);
                    } else {
                        stack.push(tagName.toString()); //PUSH OPEN TAG
                    }

                    status = "content";
                    writer.append(c);
                }
            //EMPTY TAG
            } else if ("closeTagNow".equals(status)) {
                if (CLOSE_BRACKET == c) {
                    status = "content";
                    writer.append(c);
                }
            //ATTRIBUTES
            } else if ("insideTag".equals(status)) {
                if (Character.isSpaceChar(c)) {
                    //ignored
                }
                if (SLASH_SIGN == c) {
                    status = "closeTagNow";
                    writer.append(c);
                }
                if (CLOSE_BRACKET == c) {
                    if (EMPTY_TAGS.contains(tagName.toString().toLowerCase())) {
                        writer.append(SLASH_SIGN);
                    }

                    status = "content";
                    writer.append(c);
                }

                if (isStartAttribute(c)) {
                    status = "attrName";
                    writer.append(c);

                    attributeName.setLength(0);
                    attributeName.append(c);
                }
            } else if ("attrName".equals(status)) {
                if (isNamedAttribute(c)) {
                    attributeName.append(c);

                    writer.append(c);
                } else if (Character.isSpaceChar(c)) {
                    status = "attrNameFin";
                } else if (EQUAL == c) {
                    status = "equal";
                    writer.append(c);

                //ALONE ATTRIBUTE DETECTED!!
                } else {
                    //FOR THE CASE OF "<...attribute>" (no value)
                    deleteLastString(writer, attributeName.toString());
                    status = "insideTag";
                    i--; //backward
                }
            } else if ("attrNameFin".equals(status)) {
                if (Character.isSpaceChar(c)) {
                    //ignored
                } else if (EQUAL == c) {
                    status = "equal";
                    writer.append(c);

                //ALONE ATTRIBUTE DETECTED!!
                } else {
                    deleteLastString(writer, attributeName.toString());
                    status = "insideTag";
                    i--;
                }
            } else if ("equal".equals(status)) {
                if (Character.isSpaceChar(c)) {
                    //ignored
                } else if (isOpenQuote(c)) {
                    status = "attrValue";
                    writer.append(c);

                //F*cking attribute that have the kind of name=value (without quotation!!)
                //just let it be null and consider its value as a new key,
                //but because no "=" followed hence it will be "alone attribute" and would be removed
                } else {
                    writer.append("\"null\"").append(WHITE_SPACE);
                    status = "insideTag";
                    i--;
                }
            } else if ("attrValue".equals(status)) {
                if (isCloseQuote(c)) {
                    status = "closeQuote";
                }
                writer.append(c);
            } else if ("closeQuote".equals(status)) {
                if (Character.isSpaceChar(c)) {
                    status = "insideTag";
                    writer.append(WHITE_SPACE);
                } else if (SLASH_SIGN == c) {
                    status = "closeTagNow";
                    writer.append(c);
                } else if (CLOSE_BRACKET == c) {
                    if (EMPTY_TAGS.contains(tagName.toString().toLowerCase())) {
                        writer.append(SLASH_SIGN);
                    }

                    status = "content";
                    writer.append(c);

                //TWO ATTRIBUTES STICK TOGETHER
                } else {
                    if (isStartAttribute(c)) {
                        writer.append(WHITE_SPACE);

                        status = "attrName";
                        writer.append(c);

                        attributeName.setLength(0);
                        attributeName.append(c);
                    }
                }
            //END TAG
            } else if ("endTag".equals(status)) {
                if (Character.isLetterOrDigit(c)) {
                    writer.append(c);
                    tagName.append(c);
                } else if (CLOSE_BRACKET == c) {

                    while (!stack.isEmpty() && !stack.peek().equals(tagName.toString())) {
                        //HARD CODE - CUSTOM FOR PIBOOK.VN
                        if (stack.peek().equals("li") && tagName.toString().equals("h3")) {
                            deleteLastString(writer, "</" + tagName.toString());
                            writer.append("</").append(stack.peek());
                            break;

                        //Auto adding close tag
                        } else {
                            writer.insert(writer.lastIndexOf("</"), "</" + stack.pop() + ">");
                        }
                    }

                    status = "content";
                    writer.append(c);
                    if (!stack.isEmpty()) {
                        stack.pop(); //POP OUT CLOSE TAG
                    }
                }
            }
        }
        stack.forEach(s -> toString());
        return writer.toString();
    }

    private static final List<String> LETTER_ENTITIES = Arrays.asList(
            "quot", "amp", "apos", "lt", "gt");

    public String parseHtml3(String src) {
        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder();

        String status = "content";
        StringBuilder entityName = new StringBuilder();

        boolean isHex = false;

        int maxLength = LETTER_ENTITIES.stream().map(String::length).max(Integer::compareTo).get();

        for (int i = 0; i < reader.length; i++) {
            char c = reader[i];

            if ("content".equals(status)) {
                if (AMP_ENTITY == c) {
                    status = "ampersand";
                    entityName.setLength(0); //init entity name
                }
                writer.append(c);
            } else if ("ampersand".equals(status)) {
                if (Character.isLetter(c)) {
                    status = "letter";
                    writer.append(c);
                    entityName.append(c);
                } else if (SHARP == c) {
                    status = "decimal";

                    writer.append(c);
                    entityName.append(c);

                    if (reader[i + 1] == X) {
                        isHex = true;
                        writer.append(X);
                        entityName.append(X);
                        i++; //skip the next "x"
                    }

                //Strange character encountered - replace ampersand with &amp;
                //Always backtrack one character when error occurs (we don't know whether this char is another '&')
                } else {
                    writer.append("amp;");
                    status = "content";
                    i--;
                }
            } else if ("decimal".equals(status)) {
                if ((!isHex && Character.isDigit(c)) || isHex && isHexadecimal(c)) {
                    writer.append(c);
                    entityName.append(c);
                } else if (SEMICOLON == c) {
                    status = "content";
                    writer.append(c);

                //Strange character encountered - replace ampersand with &amp;
                } else {
                    status = "content";
                    writer.insert(writer.lastIndexOf(entityName.toString()), "amp;");
                    i--; //backward
                }
            } else if ("letter".equals(status)) {
                //if semicolon is omitted and length keep running long long
                if (entityName.length() >= maxLength && SEMICOLON != c) {
                    String copied = entityName.toString();
                    int k = 1; //number of false characters in entity name
                    boolean found = false;

                    do {
                        if (LETTER_ENTITIES.contains(entityName.toString())) {
                            found = true;

                            deleteLastString(writer, copied);
                            writer.append(entityName.toString()).append(SEMICOLON);
                            i -= k; //back the cursor to k letters
                            status = "content";
                        } else {
                            k++;
                        }

                        //back-track entity name, one letter by one..
                        entityName.deleteCharAt(entityName.length() - 1);

                    } while (entityName.length() > 0 && !found);

                    //if found nothing - means that it was an alone ampersand => correct it with &amp;
                    if (entityName.length() == 0 && !found) {
                        writer.insert(writer.lastIndexOf(copied), "amp;");
                        status = "content";
                    }

                //keep accumulating entity name
                } else if (Character.isLetter(c)) {
                    writer.append(c);
                    entityName.append(c);

                //meet a semicolon
                //assume that entity was correct (length does not exceed max length; charset is valid)
                } else if (SEMICOLON == c) {
                    status = "content";
                    writer.append(c);

                    if (!LETTER_ENTITIES.contains(entityName.toString())) {
                        deleteLastString(writer, "&" + entityName.toString() + ";");
                    }
                //"strange" character encountered
                } else {
                    status = "content";

                    if (LETTER_ENTITIES.contains(entityName.toString())) {
                        writer.append(";").append(c);
                    } else {
                        writer.insert(writer.lastIndexOf(entityName.toString()), "amp;");
                        i--;
                    }
                }
            }

        }

        return writer.toString();
    }

    private void deleteLastString(StringBuilder builder, String toDelete) {
        //delete last appearance of "toDelete" and its trailing
        builder.delete(builder.lastIndexOf(toDelete), builder.length());
    }

    private Character quote = null;

    private boolean isOpenQuote(char c) {
        if (quote == null && ('\'' == c || '\"' == c)) {
            quote = c;
            return true;
        }
        return false;
    }

    private boolean isCloseQuote(char c) {
        if (quote.equals(c)) {
            quote = null;
            return true;
        }
        return false;
    }

    private boolean isStartAttribute(char c) {
        return Character.isLetter(c) || UNDERSCORE == c || COLON == c;
    }

    private boolean isNamedAttribute(char c) {
        return Character.isLetterOrDigit(c) || UNDERSCORE == c || HYPHEN == c || PERIOD == c;
    }

    private boolean isHexadecimal(char c) {
        return Character.isDigit(c) || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }
}
