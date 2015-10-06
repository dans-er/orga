package nl.knaw.dans.inco.fedora;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public abstract class XMLDoc
{
    
    private final static XPathFactory xFactory = XPathFactory.instance();
    
    private final Document doc;
    private Namespace[] nss;
    
    public XMLDoc(Document doc) {
        this.doc = doc;
        nss = getNamespaces();
    }
    
    public abstract Namespace[] getNamespaces();
    
    protected List<String> getElementValues(String xpath)
    {
        List<Element> elements = evaluate(xpath);
        List<String> values = new ArrayList<String>();
        for (Element element : elements) {
            values.add(element.getTextNormalize());
        }
        return values;
    }

    protected String getFirstElementText(String xpath)
    {
        Element element = evaluateFirstElement(xpath);
        if (element != null) {
            return element.getTextNormalize();
        } else {
            return null;
        }
    }
    
    protected Element evaluateFirstElement(String xpath, Namespace... namespaces) {
        if (namespaces == null | namespaces.length == 0) {
            namespaces = this.nss;
        }
        XPathExpression<Element> expr = xFactory.compile(
                xpath, Filters.element(), null, namespaces);
        return expr.evaluateFirst(doc);
    }
    
    protected List<Element> evaluate(String xpath, Namespace... namespaces) {
        if (namespaces == null | namespaces.length == 0) {
            namespaces = this.nss;
        }
        XPathExpression<Element> expr = xFactory.compile(
                xpath, Filters.element(), null, namespaces);
        return expr.evaluate(doc);
    }

}
