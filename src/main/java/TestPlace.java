import crawler.Crawler;
import entity.rawbook.RawBookParser;
import utils.FileUtils;
import utils.StAXUtils;

import java.util.HashMap;
import java.util.Map;

public class TestPlace {
    public static void main( String args[] ) {
        Crawler crawler = new Crawler(FileUtils.getFilePath("crawlRules/book-rule.xml"));
        crawler.crawl();
    }

    public static void testParser() {
        RawBookParser parser = new RawBookParser(FileUtils.getFilePath("crawlRules/vinabook.xml"));
        parser.parse();
        System.out.println(parser.getResults().size());
    }

    public static void testFragment() {
        String src = FileUtils.readTextContent(FileUtils.getFilePath("raw/Vinabook-full.html"));

//        src = TextUtils.refineHtml(src);
//        FileUtils.exportFile(src, FileUtils.getFilePath("staging/Vinabook-xx.xml"));

        Map<String, String> map = new HashMap<>();
        map.put("class", "template-products");
        src = StAXUtils.extract(src, "table", map);
        FileUtils.exportFile(src, FileUtils.getFilePath("refined/Vinabook-xx.xml"));
    }
}
