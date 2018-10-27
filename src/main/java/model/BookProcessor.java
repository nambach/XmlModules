package model;

import crawler.CrawlerResultProcessor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookProcessor implements CrawlerResultProcessor {

    private boolean processObject = false;
    private boolean processList = false;

    private BookList bookList;

    public BookProcessor() {
    }

    public void setProcessObject(boolean processObject) {
        this.processObject = processObject;
    }

    public void setProcessList(boolean processList) {
        this.processList = processList;
    }

    public BookList getBookList() {
        return bookList;
    }

    @Override
    public boolean isNeededToProcessObject() {
        return processObject;
    }

    @Override
    public boolean isNeededToProcessList() {
        return processList;
    }

    @Override
    public void processResultObject(Map<String, String> object) {
        System.out.println(object);
    }

    @Override
    public void processResultList(List<Map<String, String>> list) {
        System.out.print("Total books: ");
        System.out.println(list.size());

        bookList = new BookList();
        bookList.setBook(list
                .stream()
                .map(RawBook::convert)
                .map(RawBook::toBook)
                .collect(Collectors.toList()));
    }
}
