package nl.knaw.dans.inco.fedora;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.request.GetDatastreamDissemination;
import com.yourmediashelf.fedora.client.request.RiSearch;
import com.yourmediashelf.fedora.client.response.FedoraResponse;
import com.yourmediashelf.fedora.client.response.RiSearchResponse;

public class Fedora
{
    
    private static Fedora INSTANCE;
    
    public static final String DS_EMD = "EMD";
    
    public static final String DS_AMD = "AMD";
    
    public static Fedora instance() {
        if (INSTANCE == null) {
            INSTANCE = new Fedora();
        }
        return INSTANCE;
    }
    
    private Fedora() {}
    
    public Document getEMD(String datasetId) throws IOException, JDOMException, FedoraClientException {
        FedoraResponse response = new GetDatastreamDissemination(datasetId, DS_EMD).execute();
        return getDoc(response.getEntityInputStream());
    }
    
    public Document getAMD(String datasetId) throws IOException, JDOMException, FedoraClientException {
        FedoraResponse response = new GetDatastreamDissemination(datasetId, DS_AMD).execute();
        return getDoc(response.getEntityInputStream());
    }
    
    public List<String> getFileIdentifiers(String datasetId) throws FedoraClientException, IOException {
        String query = 
                  "PREFIX dans: <http://dans.knaw.nl/ontologies/relations#> "
                + "PREFIX fmodel: <info:fedora/fedora-system:def/model#> "

                + "SELECT ?s "
                + "WHERE "
                + "{ "
                + "   ?s dans:isSubordinateTo <info:fedora/" + datasetId + "> . "
                + "   ?s fmodel:hasModel <info:fedora/easy-model:EDM1FILE> "
                + "}";
        RiSearchResponse response = new RiSearch(query)
            .lang("sparql")
            .format("csv")
            .execute();
        InputStream ins = null;
        try
        {
            ins = response.getEntityInputStream();
            LineIterator it = IOUtils.lineIterator(ins, "UTF-8");
            List<String> identifiers = new ArrayList<String>();
            it.next(); // s (heading)
            while (it.hasNext()) {
                String identifier = it.next().split("/")[1];
                identifiers.add(identifier);
            }
            return identifiers;
        } finally {
            IOUtils.closeQuietly(ins);
        }        
    }
    
    
    protected Document getDoc(InputStream ins) throws IOException, JDOMException {
        try
        {
            Document doc = new SAXBuilder().build(ins);
            return doc;
        }
        finally {
            ins.close();
        }
    }

}
