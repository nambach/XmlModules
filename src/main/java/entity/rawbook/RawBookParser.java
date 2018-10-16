package entity.rawbook;

import entity.rawbook.rules.BookType;
import entity.rawbook.rules.PageType;
import entity.rawbook.rules.PagesType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.DomUtils;
import utils.FileUtils;
import utils.JAXBUtils;
import utils.TextUtils;

import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class RawBookParser {
    //crawlRules
    //-----
    //List<RawBook>

    private PagesType pagesType;

    private List<RawBook> results;

    public RawBookParser(String crawlRulePath) {
        //get crawling rules
        pagesType = JAXBUtils.unmarshalling(crawlRulePath, null, PagesType.class);
    }

    public PagesType getPagesType() {
        return pagesType;
    }

    public List<RawBook> getResults() {
        return results;
    }

    public void parse() {
        results = new LinkedList<>();

        if (pagesType == null || pagesType.getPage().size() == 0) {
            System.out.println("Nothing to do");
            return;
        }

        //ITERATE ALL URLs
        for (int i = 0; i < pagesType.getPage().size(); i++) {
            PageType pageRule = pagesType.getPage().get(i);
            System.out.println();
            System.out.println(pageRule.getName());

            //ITERATE ALL PAGES IN ONE URL
            for (int pageNo = Integer.parseInt(pageRule.getIncrementFrom()); pageNo <= Integer.parseInt(pageRule.getIncrementTo()); pageNo++) {

                //FETCHING HTML
                String textContent = "";
                try {
                    String incrementParam = pageRule.getIncrementParam().replaceAll("\\{i}", "" + pageNo);

                    URL url = new URL(pageRule.getUrl() + incrementParam);
                    URLConnection connection = url.openConnection();

                    textContent = FileUtils.getString(connection.getInputStream());
                    FileUtils.exportFile(textContent, FileUtils.getFilePath("textutils/raw-" + i + ".xml"));

//                    org.jsoup.nodes.Document html = Jsoup.connect(pageRule.getUrl() + incrementParam).get();
//                    org.jsoup.nodes.Element targetFragment = html.body().selectFirst(pageRule.getBooklistJsQuerySelector());
//
//                    textContent = targetFragment.outerHtml();

                    textContent = TextUtils.refineHtml(textContent);

                    FileUtils.exportFile(textContent, FileUtils.getFilePath("textutils/" + i + ".xml"));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("IO Error: " + e);
                    continue;
                }

                //PARSE text into DOM
                Document document = DomUtils.parseStringIntoDOM(textContent);

                Node page = DomUtils.evaluateNode(document, pageRule.getBooklist(), Node.class);

                BookType bookRule = pageRule.getBook();
                NodeList books = DomUtils.evaluateNode(page, bookRule.getEntity(), NodeList.class);

                System.out.println(books.getLength());

                for (int j = 0; j < (books != null ? books.getLength() : 0); j++) {
                    Node book = books.item(j);

                    String title = DomUtils.evaluateNode(book, bookRule.getTitle(), String.class);
                    String link = DomUtils.evaluateNode(book, bookRule.getLink(), String.class);
                    String image = DomUtils.evaluateNode(book, bookRule.getImage(), String.class);
                    String author = DomUtils.evaluateNode(book, bookRule.getAuthor(), String.class);
                    String oldPrice = DomUtils.evaluateNode(book, bookRule.getOldPrice(), String.class);
                    String price = DomUtils.evaluateNode(book, bookRule.getPrice(), String.class);
                    String status = DomUtils.evaluateNode(book, bookRule.getStatus(), String.class);

                    RawBook rawBook = new RawBook(title, author, image, link, price, oldPrice, status);
                    results.add(rawBook);

                    System.out.println(rawBook);
                }

                //End of some code for iterate pages
            }
        }
    }
}
