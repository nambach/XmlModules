
package entity.rawbook.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incrementParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incrementFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incrementTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="booklist" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="booklistJsQuerySelector" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="book" type="{}bookType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pageType", propOrder = {
    "name",
    "url",
    "incrementParam",
    "incrementFrom",
    "incrementTo",
    "booklist",
    "booklistJsQuerySelector",
    "book"
})
public class PageType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String url;
    @XmlElement(required = true)
    protected String incrementParam;
    @XmlElement(required = true)
    protected String incrementFrom;
    @XmlElement(required = true)
    protected String incrementTo;
    @XmlElement(required = true)
    protected String booklist;
    @XmlElement(required = true)
    protected String booklistJsQuerySelector;
    @XmlElement(required = true)
    protected BookType book;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the incrementParam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncrementParam() {
        return incrementParam;
    }

    /**
     * Sets the value of the incrementParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncrementParam(String value) {
        this.incrementParam = value;
    }

    /**
     * Gets the value of the incrementFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncrementFrom() {
        return incrementFrom;
    }

    /**
     * Sets the value of the incrementFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncrementFrom(String value) {
        this.incrementFrom = value;
    }

    /**
     * Gets the value of the incrementTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncrementTo() {
        return incrementTo;
    }

    /**
     * Sets the value of the incrementTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncrementTo(String value) {
        this.incrementTo = value;
    }

    /**
     * Gets the value of the booklist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBooklist() {
        return booklist;
    }

    /**
     * Sets the value of the booklist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBooklist(String value) {
        this.booklist = value;
    }

    /**
     * Gets the value of the booklistJsQuerySelector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBooklistJsQuerySelector() {
        return booklistJsQuerySelector;
    }

    /**
     * Sets the value of the booklistJsQuerySelector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBooklistJsQuerySelector(String value) {
        this.booklistJsQuerySelector = value;
    }

    /**
     * Gets the value of the book property.
     * 
     * @return
     *     possible object is
     *     {@link BookType }
     *     
     */
    public BookType getBook() {
        return book;
    }

    /**
     * Sets the value of the book property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookType }
     *     
     */
    public void setBook(BookType value) {
        this.book = value;
    }

}
