package model;

import entity.RawBook;

import java.util.List;

public class CompareGroupModel {

    private String id;
    private RawBook coreMember;
    private List<RawBook> members;

    // Core book information
    private String title;
    private String authors;
    private String image;
    private String minPrice;
    private String maxPrice;

    // For suggestion
    private String suggestGroupId;

    public CompareGroupModel(String id, String title, String authors, String image, String minPrice, String maxPrice, String suggestGroupId) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.image = image;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.suggestGroupId = suggestGroupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RawBook getCoreMember() {
        return coreMember;
    }

    public void setCoreMember(RawBook coreMember) {
        this.coreMember = coreMember;
    }

    public List<RawBook> getMembers() {
        return members;
    }

    public void setMembers(List<RawBook> members) {
        this.members = members;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSuggestGroupId() {
        return suggestGroupId;
    }

    public void setSuggestGroupId(String suggestGroupId) {
        this.suggestGroupId = suggestGroupId;
    }
}
