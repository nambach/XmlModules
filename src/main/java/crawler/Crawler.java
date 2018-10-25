package crawler;

import crawler.rule.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import repository.generic.impl.GenericRepositoryImpl;
import repository.impl.RawBookRepositoryImpl;
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

    private RawBookRepositoryImpl repository;

    public Crawler(String crawlRulePath) {
        //get crawling rules
        rules = JAXBUtils.unmarshalling(crawlRulePath, null, Rules.class);
//        repository = new RawBookRepositoryImpl(GenericRepositoryImpl.getFactory());
    }

    public Crawler(Rules rules) {
        this.rules = rules;
        repository = new RawBookRepositoryImpl(GenericRepositoryImpl.getFactory());
    }

    public List<Map<String, String>> getResults() {
        if (results == null) {
            results = new LinkedList<>();
        }
        return results;
    }

    public RawBookRepositoryImpl getRepository() {
        return repository;
    }

    public void crawl() {
        results = new LinkedList<>();

        if (rules == null || rules.getRule().size() == 0) {
            System.out.println("Nothing to do");
            return;
        }

        //ITERATE ALL RULES
        for (Rule rule : rules.getRule()) {

            String baseUrl = rule.getBasedUrl();
            String siteName = rule.getSiteName();
            String collectionXPath = rule.getCollectionXpath();
            Item itemRule = rule.getItem();

            System.out.println();
            System.out.println();
            System.out.println(siteName);

            //ITERATE ALL URLs (Topics)
            for (TopicType topic : rule.getTopics().getTopic()) {

                String topicName = topic.getTopicName();
                String topicCode = topic.getTopicCode();
                String fragmentXPath = topic.getFragmentXpath();
                UrlType topicURL = topic.getUrl();

                System.out.println();
                System.out.println(topicName);

                int from = Integer.parseInt(topicURL.getFrom());
                int to = Integer.parseInt(topicURL.getTo());
                int step = Integer.parseInt(topicURL.getStep());

                //ITERATE ALL PAGES IN ONE URL
                for (int pageNo = from; pageNo <= to; pageNo += step) {

                    //FETCHING HTML
                    String textContent;
                    try {
                        String incrementParam = "";
                        if (!topicURL.getIncrementParam().trim().equals("")) {
                            incrementParam = topicURL.getIncrementParam().replaceAll("\\{i}", String.valueOf(pageNo));
                        }

                        URL url = new URL(topicURL.getValue() + incrementParam);
                        URLConnection connection = url.openConnection();

                        textContent = FileUtils.getString(connection.getInputStream());

                        textContent = TextUtils.refineHtml(textContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("IO Error: " + e);
                        break;//exit pages loop
                    }

                    //PARSE text into DOM
                    Document document = DomUtils.parseStringIntoDOM(textContent);

                    Node fragment = DomUtils.evaluateNode(document, fragmentXPath, Node.class);

                    NodeList collection = DomUtils.evaluateNode(fragment, collectionXPath, NodeList.class);

                    if (collection == null || collection.getLength() == 0) {
                        System.out.println("empty collection");
                        break;//exit pages loop
                    }

                    collectionLoop:
                    for (int j = 0; j < collection.getLength(); j++) {
                        Map<String, String> obj = new HashMap<>();
                        Node item = collection.item(j);

                        obj.put("siteName", siteName);

                        for (ItemDetail detailXPath : itemRule.getDetailXpath()) {
                            String name = detailXPath.getDetailName();
                            String value = DomUtils.evaluateNode(item, detailXPath.getValue(), String.class);

                            if ("id".equals(name) && (value != null && value.trim().equals(""))) {
                                continue collectionLoop;//ignore item that does not have an ID
                            }

                            value = detailXPath.getPrefix() + value + detailXPath.getPostfix();

                            obj.put(name, value);
                        }//End one item

                        results.add(obj);
                        System.out.println(obj);

                    }//End items in one topic

                }//End topic

            }//End all topics for one rule

        }//End all rules

        System.out.println(results.size());
    }
}
