package nl.knaw.dans.inco.pu;


import static org.junit.Assert.*;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.inco.pu.Audience;
import nl.knaw.dans.inco.pu.Dataset;
import nl.knaw.dans.inco.pu.DatasetStore;
import nl.knaw.dans.inco.pu.Organization;
import nl.knaw.dans.inco.rdb.JPAUtil;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DatasetStoreTest
{
    
    @BeforeClass
    public static void beforeClass() {
        JPAUtil.setTestState(true);
    }
    
    @Test
    public void storeDataset() throws Exception {
        String datasetId = "easy-dataset:1";
        DatasetStore store = new DatasetStore(JPAUtil.getEntityManager());
        
        EntityTransaction tx = store.newTransAction();
        tx.begin();
        
        Dataset ods = new Dataset(datasetId);
        
        Organization org = new Organization("rightsholder");
        org.setName("Bla b.v.");
        ods.addOrganization(org);
        
        org = new Organization("publisher");
        org.setName("BlaPub b.v.");
        ods.addOrganization(org);
        
        Audience aud = new Audience("tis_tat");
        ods.addAudience(aud);
        
        store.saveOrUpdate(ods);
        tx.commit();
        store.clear();
        
        tx = store.newTransAction();
        tx.begin();
        
        ods = store.findByDatasetId(datasetId);
        assertEquals(datasetId, ods.getDatasetId());
        assertEquals(2, ods.getOrganizations().size());
        assertEquals(1, ods.getAudiences().size());
        
        store.remove(ods);
        
        tx.commit();
        store.clear();
    }

}
