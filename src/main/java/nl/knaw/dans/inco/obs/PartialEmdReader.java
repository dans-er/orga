package nl.knaw.dans.inco.obs;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;

import com.yourmediashelf.fedora.client.FedoraClientException;

import nl.knaw.dans.inco.fedora.EMD;
import nl.knaw.dans.inco.fedora.Fedora;
import nl.knaw.dans.inco.pu.Audience;
import nl.knaw.dans.inco.pu.Dataset;
import nl.knaw.dans.inco.pu.Organization;

public class PartialEmdReader implements DatasetSimplex
{

    @Override
    public void process(Dataset dataset) throws ProcessingException
    {
        Fedora fedora = Fedora.instance();
        try
        {            
            EMD emd = new EMD(fedora.getEMD(dataset.getDatasetId()));
            
            String pid = emd.getPid();
            dataset.setUrn(pid);
            
            String doi = emd.getDoi();
            dataset.setDoi(doi);
            
            String title = emd.getFirstTitle();
            dataset.setDatasetTitle(title);
            
            String accessRights = emd.getAccessRights();
            dataset.setAccessRights(accessRights);
            
            List<String> codes = emd.getAudiences();
            for (String code : codes) {
                dataset.addAudience(new Audience(code));
            }
            
            List<String> publishers = emd.getPublishers();
            for (String name : publishers) {
                Organization org = new Organization("publisher");
                org.setName(name);
                dataset.addOrganization(org);
            }
            
            List<String> rightsholders = emd.getRightsHolders();
            for (String name : rightsholders) {
                Organization org = new Organization("rightsholder");
                org.setName(name);
                dataset.addOrganization(org);
            }
            
            List<String> creators = emd.getOldCreators();
            for (String name : creators) {
                Organization org = new Organization("creator");
                org.setName(name);
                dataset.addOrganization(org);
            }
            
            creators = emd.getCreatorOrganizations();
            for (String name : creators) {
                Organization org = new Organization("creator organization");
                org.setName(name);
                dataset.addOrganization(org);
            }
            
        }
        catch (JDOMException e)
        {
            throw new ProcessingException(e);
        }
        catch (IOException e)
        {
            throw new ProcessingException(e);
        }
        catch (FedoraClientException e)
        {
            throw new ProcessingException(e);
        }
    }

}
