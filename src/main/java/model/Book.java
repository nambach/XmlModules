package model;

public class Book {

    private String id;
    private String siteName;

    //Basic information
    private String title;
    private String authors;

    //Image
    private String image;

    //Link to original page
    private String link;

    //Prices
    private String price;
    private String oldPrice;

    //Status
    private String status;

    public Book() {
    }

    public Book(String id, String siteName, String title, String authors, String image, String link, String price, String oldPrice, String status) {
        this.id = id;
        this.siteName = siteName;
        this.title = title;
        this.authors = authors;
        this.image = image;
        this.link = link;
        this.price = price;
        this.oldPrice = oldPrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
