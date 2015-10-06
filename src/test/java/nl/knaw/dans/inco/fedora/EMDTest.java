package nl.knaw.dans.inco.fedora;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import nl.knaw.dans.inco.fedora.EMD;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

public class EMDTest
{
    
    private Document readDoc(String file) throws JDOMException, IOException {
        SAXBuilder jdom = new SAXBuilder();
        return jdom.build("non-pub/test-files/" + file);
    }
    
    @Test
    public void evaluateFirstElement() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        String xpath = "emd:easymetadata/emd:other/eas:application-specific/eas:notanelement";
        Element element = emd.evaluateFirstElement(xpath, EMD.NS_EMD, EMD.NS_EAS);
        assertNull(element);
    }
    
    @Test
    public void getFirstTitle() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        assertEquals("Katwijk-Voorstraat 59", emd.getFirstTitle());
    }
    
    @Test
    public void getPublishers() throws Exception {
        EMD emd = new EMD(readDoc("emd5.xml"));
        List<String> publishers = emd.getPublishers();
        assertEquals(2, publishers.size());
        assertTrue(publishers.contains("Bas b.v."));
        assertTrue(publishers.contains("Bonk S.A."));
    }
    
    @Test
    public void getOldCreators() throws Exception {
        EMD emd = new EMD(readDoc("oldcreators.xml"));
        List<String> creators = emd.getOldCreators();
        assertEquals(2, creators.size());
        assertTrue(creators.contains("Braven, J.A. den"));
        assertTrue(creators.contains("Fermin, H.A.C."));
        
        emd = new EMD(readDoc("emd5.xml"));
        creators = emd.getOldCreators();
        assertEquals(0, creators.size());
    }
    
    @Test
    public void getCreatorsOrganizations() throws Exception {
        EMD emd = new EMD(readDoc("emd5.xml"));
        List<String> organizations = emd.getCreatorOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains("Wortels bv"));
        assertTrue(organizations.contains("Teller sa"));
    }
    
    @Test
    public void getNewCreators() throws Exception {
        
    }
    
    @Test
    public void getAccessRights() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        assertEquals("GROUP_ACCESS", emd.getAccessRights());
    }
    
    @Test
    public void getRightsHolders() throws Exception {
        EMD emd = new EMD(readDoc("emd5.xml"));
        List<String> rightsholders = emd.getRightsHolders();
        assertEquals(2, rightsholders.size());
        assertTrue(rightsholders.contains("Foo b.v."));
        assertTrue(rightsholders.contains("Bar bv"));
    }
    
    @Test
    public void getMetadataFormat() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        assertEquals("ARCHAEOLOGY", emd.getMetadataFormat());
    }
    
    @Test
    public void getArchisVondstMelding() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        assertEquals("422147", emd.getArchisVondstMelding());
    }
    
    @Test
    public void getArchisWaarneming() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        assertEquals("437059", emd.getArchisWaarneming());
    }
    
    @Test
    public void getArchisOnderzoeksMeldingsNummer() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        assertEquals("50931", emd.getArchisOnderzoeksMeldingsNummer());
    }
    
    @Test
    public void getDCMITypes() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        List<String> types = emd.getDCMITypes();
        assertEquals(2, types.size());
        assertTrue(types.contains("Dataset"));
        assertTrue(types.contains("Image"));
    }
    
    @Test
    public void getAudiences() throws Exception {
        EMD emd = new EMD(readDoc("emd.xml"));
        List<String> audiences = emd.getAudiences();
        assertEquals(1, audiences.size());
        assertEquals("easy-discipline:2", audiences.get(0));
    }

}
