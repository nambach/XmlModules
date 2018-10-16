
package model.crawling;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the model.crawling package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Pages_QNAME = new QName("http://nambm.io/crawling-rule", "pages");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: model.crawling
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PagesType }
     * 
     */
    public PagesType createPagesType() {
        return new PagesType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link PageType }
     * 
     */
    public PageType createPageType() {
        return new PageType();
    }

    /**
     * Create an instance of {@link ItemDetailType }
     * 
     */
    public ItemDetailType createItemDetailType() {
        return new ItemDetailType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nambm.io/crawling-rule", name = "pages")
    public JAXBElement<PagesType> createPages(PagesType value) {
        return new JAXBElement<PagesType>(_Pages_QNAME, PagesType.class, null, value);
    }

}
