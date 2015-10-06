package nl.knaw.dans.inco.fedora;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;


public class EMD extends XMLDoc
{
    
    protected final static Namespace NS_EMD = Namespace.getNamespace("emd", "http://easy.dans.knaw.nl/easy/easymetadata/");
    protected final static Namespace NS_EAS = Namespace.getNamespace("eas", "http://easy.dans.knaw.nl/easy/easymetadata/eas/");
    protected final static Namespace NS_DC = Namespace.getNamespace("dc", "http://purl.org/dc/elements/1.1/");
    protected final static Namespace NS_DCT = Namespace.getNamespace("dct", "http://purl.org/dc/terms/");
    
    
    
    public EMD(Document doc) {
        super(doc);
    }
    
    public String getFirstTitle() {
        String xpath = "/emd:easymetadata/emd:title/dc:title";
        String title = getFirstElementText(xpath);
        if (StringUtils.isBlank(title)) {
            xpath = "/emd:easymetadata/emd:title/dct:alternative";
            title = getFirstElementText(xpath);
        }
        return title;
    }
    
    public List<String> getPublishers() {
        String xpath = "/emd:easymetadata/emd:publisher/dc:publisher";
        return getElementValues(xpath);
    }
    
    public List<String> getRightsHolders() {
        String xpath = "/emd:easymetadata/emd:rights/dct:rightsHolder";
        return getElementValues(xpath);
    }
    
    public List<String> getOldCreators() {
        String xpath = "/emd:easymetadata/emd:creator/dc:creator";
        return getElementValues(xpath);
    }
    
    public List<String> getCreatorOrganizations() {
        String xpath = "/emd:easymetadata/emd:creator/eas:creator/eas:organization";
        return getElementValues(xpath);
    }
    
    public String getAccessRights() {
        //String xpath = "/emd:easymetadata/emd:rights/dct:accessRights[@eas:schemeId='common.dcterms.accessrights']";
        String xpath = "/emd:easymetadata/emd:rights/dct:accessRights";
        return getFirstElementText(xpath);
    }
    
    public String getPid() {
        String xpath = "/emd:easymetadata/emd:identifier/dc:identifier[@eas:scheme='PID']";
        return getFirstElementText(xpath);
    }
    
    public String getDoi() {
        String xpath = "/emd:easymetadata/emd:identifier/dc:identifier[@eas:scheme='DOI']";
        return getFirstElementText(xpath);
    }
    
    public String getMetadataFormat() {
        String xpath = "/emd:easymetadata/emd:other/eas:application-specific/eas:metadataformat";
        return getFirstElementText(xpath);
    }
    
    public String getArchisVondstMelding() {
        String xpath = "/emd:easymetadata/emd:identifier/dc:identifier[@eas:scheme='Archis_vondstmelding']";
        return getFirstElementText(xpath);
    }
    
    public String getArchisWaarneming() {
        String xpath = "/emd:easymetadata/emd:identifier/dc:identifier[@eas:scheme='Archis_waarneming']";
        return getFirstElementText(xpath);
    }
    
    public String getArchisOnderzoeksMeldingsNummer() {
        String xpath = "/emd:easymetadata/emd:identifier/dc:identifier[@eas:scheme='Archis_onderzoek_m_nr']";
        return getFirstElementText(xpath);
    }
    
    public List<String> getDCMITypes() {
        String xpath = "/emd:easymetadata/emd:type/dc:type[@eas:scheme='DCMI']";
        return getElementValues(xpath);
    }
    
    public List<String> getAudiences() {
        List<String> audiences = new ArrayList<String>();
        String xpath = "/emd:easymetadata/emd:audience/dct:audience";
        List<Element> elements = evaluate(xpath);
        for (Element element : elements) {
            String audId = element.getTextNormalize();
            audiences.add(audId);
        }
        return audiences;
    }

    @Override
    public Namespace[] getNamespaces()
    {
        return new Namespace[]{NS_EMD, NS_EAS, NS_DC, NS_DCT};
    }
    

}
