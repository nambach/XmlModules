
package model.crawling;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for itemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="itemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="detailXpath" type="{http://nambm.io/crawling-rule}itemDetailType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemType", namespace = "http://nambm.io/crawling-rule", propOrder = {
    "detailXpath"
})
public class ItemType {

    @XmlElement(namespace = "http://nambm.io/crawling-rule", required = true)
    protected List<ItemDetailType> detailXpath;

    /**
     * Gets the value of the detailXpath property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detailXpath property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetailXpath().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemDetailType }
     * 
     * 
     */
    public List<ItemDetailType> getDetailXpath() {
        if (detailXpath == null) {
            detailXpath = new ArrayList<ItemDetailType>();
        }
        return this.detailXpath;
    }

}
