import crawler.Crawler;
import model.BookList;
import model.RawBook;
import utils.FileUtils;

import java.util.stream.Collectors;

public class TestPlace {
    public static void main( String args[] ) {
        long start = System.currentTimeMillis();
        Crawler crawler = new Crawler(FileUtils.getFilePath("crawlRules/book-rule.xml"));
        crawler.crawl();
        System.out.println(crawler.getResults().size());
        long end = System.currentTimeMillis();
        System.out.println((end - start));

        BookList bookList = new BookList();
        bookList.setBook(crawler.getResults()
                .stream()
                .map(RawBook::convert)
                .map(RawBook::toBook)
                .collect(Collectors.toList()));

//        JAXBUtils.marshalling(FileUtils.getFilePath("bookList.xml"), bookList, Book.class);
//        crawler.getRepository().insertBatch(RawBook.convert(crawler.getResults()));
    }

}
