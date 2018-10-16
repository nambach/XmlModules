package crawler;

import model.crawling.Item;
import model.crawling.ItemDetail;
import model.crawling.Rule;
import model.crawling.Rules;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.DomUtils;
import utils.FileUtils;
import utils.JAXBUtils;
import utils.TextUtils;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Crawler {
    //crawlRules
    //-----
    //List<RawBook>

    private Rules rules;

    private List<Map<String, String>> results;

    public Crawler(String crawlRulePath) {
        //get crawling rules
        rules = JAXBUtils.unmarshalling(crawlRulePath, null, Rules.class);
    }

    public void crawl() {
        results = new LinkedList<>();

        if (rules == null || rules.getRule().size() == 0) {
            System.out.println("Nothing to do");
            return;
        }

        //ITERATE ALL URLs
        for (int i = 0; i < rules.getRule().size(); i++) {
            Rule rule = rules.getRule().get(i);
            System.out.println();
            System.out.println(rule.getName());

            //ITERATE ALL PAGES IN ONE URL
            for (int pageNo = Integer.parseInt(rule.getIncrementFrom()); pageNo <= Integer.parseInt(rule.getIncrementTo()); pageNo++) {

                //FETCHING HTML
                String textContent = "";
                try {
                    String incrementParam = rule.getIncrementParam().replaceAll("\\{i}", "" + pageNo);

                    URL url = new URL(rule.getUrl() + incrementParam);
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

                Node fragment = DomUtils.evaluateNode(document, rule.getFragmentXpath(), Node.class);

                NodeList collection = DomUtils.evaluateNode(fragment, rule.getCollectionXpath(), NodeList.class);

                Item itemRule = rule.getItem();
                for (int j = 0; j < (collection != null ? collection.getLength() : 0); j++) {
                    Node item = collection.item(j);

                    Map<String, String> obj = new HashMap<>();

                    for (ItemDetail detailRule : itemRule.getDetailXpath()) {
                        String name = detailRule.getDetailName();
                        String value = DomUtils.evaluateNode(item, detailRule.getValue(), String.class);

                        if (detailRule.isIsRelativeURL()) {
                            value = rule.getBasedUrl() + value;
                        }

                        obj.put(name, value);
                    }

                    results.add(obj);
                    System.out.println(obj);
                }

                //End of some code for iterate pages
            }
        }

        System.out.println(results.size());
    }
}
