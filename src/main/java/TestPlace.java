import crawler.Crawler;
import model.Book;
import model.BookProcessor;
import utils.FileUtils;
import utils.JAXBUtils;

public class TestPlace {
    public static void main( String args[] ) {
        long start = System.currentTimeMillis();

        String rulePath = FileUtils.getFilePath("crawlRules/book-rule.xml");

        BookProcessor bookProcessor = new BookProcessor();
        bookProcessor.setProcessObject(true);
        bookProcessor.setProcessList(true);

        Crawler<BookProcessor> crawler = new Crawler<>(rulePath);
        crawler.setResultProcessor(bookProcessor);
        crawler.crawl();

        long end = System.currentTimeMillis();
        System.out.println((end - start));

        JAXBUtils.marshalling(FileUtils.getFilePath("bookList.xml"), bookProcessor.getBookList(), Book.class);
//        crawler.getRepository().insertBatch(RawBook.convert(crawler.getResults()));
    }

}
