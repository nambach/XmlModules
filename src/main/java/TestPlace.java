import crawler.Crawler;
import utils.FileUtils;

public class TestPlace {
    public static void main( String args[] ) {
        long start = System.currentTimeMillis();
        Crawler crawler = new Crawler(FileUtils.getFilePath("crawlRules/book-rule.xml"));
        crawler.crawl();
        System.out.println(crawler.getResults().size());
        long end = System.currentTimeMillis();
        System.out.println((end - start));
//        crawler.getRepository().insertBatch(RawBook.convert(crawler.getResults()));
    }

}
