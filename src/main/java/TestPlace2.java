import crawler.Crawler;
import crawler.CrawlerResultProcessor;
import utils.FileUtils;

import java.util.List;
import java.util.Map;

public class TestPlace2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        String rulePath = FileUtils.getFilePath("crawlRules/saint-name-rule.xml");

        Crawler crawler = new Crawler<>(rulePath);
        crawler.setResultProcessor(new CrawlerResultProcessor() {
            @Override
            public boolean isNeededToProcessObject() {
                return true;
            }

            @Override
            public boolean isNeededToProcessList() {
                return false;
            }

            @Override
            public void processResultObject(Map<String, String> object) {
                if (object.getOrDefault("saintDate", null) == null) return;
                if (!object.getOrDefault("saintName", "").startsWith("Thánh")) return;
                object.put("saintName", object.get("saintName").replaceAll("Thánh", "").trim());
                System.out.println(object);
            }

            @Override
            public void processResultList(List<Map<String, String>> list) {

            }
        });
        crawler.crawl();

        long end = System.currentTimeMillis();
        System.out.println((end - start));
    }
}
