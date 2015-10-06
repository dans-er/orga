package nl.knaw.dans.inco.pu;

import javax.persistence.EntityManager;

import nl.knaw.dans.inco.rdb.AbstractGenericStore;

public class DatasetStore extends AbstractGenericStore<Dataset, Long>
{

    public DatasetStore()
    {
        super(Dataset.class);
    }
    
    public DatasetStore(EntityManager em) {
        super(Dataset.class);
        setEntityManager(em);
    }
    
    // find by datasetId
    public Dataset findByDatasetId(String datasetId) {
        Dataset ods =  (Dataset) ((org.hibernate.jpa.HibernateEntityManager) getEntityManager()).getSession()
                .byNaturalId(getEntityBeanType()).using("datasetId", datasetId).load();
        return ods;
    }

}
