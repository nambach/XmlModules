import crawler.Crawler;
import utils.FileUtils;

public class TestPlace {
    public static void main( String args[] ) {
        Crawler crawler = new Crawler(FileUtils.getFilePath("crawlRules/book-rule.xml"));
        crawler.crawl();
        System.out.println(crawler.getResults().size());
//        crawler.getRepository().insertBatch(RawBook.convert(crawler.getResults()));
    }

}
