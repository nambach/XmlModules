
package model.crawling;

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
 *         &lt;element name="basedUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incrementParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incrementFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incrementTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="collectionJsQuerySelector" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="collectionXpath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemXpath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="item" type="{http://nambm.io/crawling-rule}itemType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pageType", namespace = "http://nambm.io/crawling-rule", propOrder = {
    "name",
    "url",
    "basedUrl",
    "incrementParam",
    "incrementFrom",
    "incrementTo",
    "collectionJsQuerySelector",
    "collectionXpath",
    "itemXpath",
    "item"
})
public class PageType {

    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String name;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String url;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String basedUrl;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String incrementParam;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String incrementFrom;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String incrementTo;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String collectionJsQuerySelector;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String collectionXpath;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected String itemXpath;
    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected ItemType item;

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
     * Gets the value of the basedUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasedUrl() {
        return basedUrl;
    }

    /**
     * Sets the value of the basedUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasedUrl(String value) {
        this.basedUrl = value;
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
     * Gets the value of the collectionJsQuerySelector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectionJsQuerySelector() {
        return collectionJsQuerySelector;
    }

    /**
     * Sets the value of the collectionJsQuerySelector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectionJsQuerySelector(String value) {
        this.collectionJsQuerySelector = value;
    }

    /**
     * Gets the value of the collectionXpath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectionXpath() {
        return collectionXpath;
    }

    /**
     * Sets the value of the collectionXpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectionXpath(String value) {
        this.collectionXpath = value;
    }

    /**
     * Gets the value of the itemXpath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemXpath() {
        return itemXpath;
    }

    /**
     * Sets the value of the itemXpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemXpath(String value) {
        this.itemXpath = value;
    }

    /**
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    public ItemType getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setItem(ItemType value) {
        this.item = value;
    }

}
