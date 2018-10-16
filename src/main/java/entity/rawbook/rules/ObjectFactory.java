
package entity.rawbook.rules;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the entity.rawbook.rules package. 
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

    private final static QName _Pages_QNAME = new QName("", "pages");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: entity.rawbook.rules
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
     * Create an instance of {@link PageType }
     * 
     */
    public PageType createPageType() {
        return new PageType();
    }

    /**
     * Create an instance of {@link BookType }
     * 
     */
    public BookType createBookType() {
        return new BookType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pages")
    public JAXBElement<PagesType> createPages(PagesType value) {
        return new JAXBElement<PagesType>(_Pages_QNAME, PagesType.class, null, value);
    }

}
