package nl.knaw.dans.inco.main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import nl.knaw.dans.inco.fedora.PerDateDatasetIterator;
import nl.knaw.dans.inco.obs.DatasetSimplex;
import nl.knaw.dans.inco.obs.ProcessingException;
import nl.knaw.dans.inco.pu.Dataset;
import nl.knaw.dans.inco.pu.DatasetStore;
import nl.knaw.dans.inco.rdb.JPAUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Conveyor
{
    
    private Logger logger = LoggerFactory.getLogger(Conveyor.class);
    
    //private Fedora fedora;
    private PerDateDatasetIterator pdIter;
    private DatasetStore store;
    
    private List<DatasetSimplex> simplexes;
    
    public Conveyor(PerDateDatasetIterator pdIter) {
        this.pdIter = pdIter;
        //fedora = Fedora.instance();
        store = new DatasetStore(JPAUtil.getEntityManager());
        simplexes = new ArrayList<DatasetSimplex>();
    }
    
    public void addDatasetSimplex(DatasetSimplex simplex) {
        simplexes.add(simplex);
    }
    
    public void run() {
        int datasetCount = 0;
        int errorCount = 0;
        
        while (pdIter.hasNext()) {
            datasetCount++;
            String datasetId = pdIter.next();
            
            logger.info("processing dataset " + datasetId + ", dsCount=" + datasetCount + ", errors=" + errorCount);
            
            EntityTransaction tx = store.newTransAction();
            tx.begin();
            
            Dataset dataset = store.findByDatasetId(datasetId);
            if (dataset == null) {
                dataset = new Dataset(datasetId);
            }
            for (DatasetSimplex simplex : simplexes) {
                try
                {
                    simplex.process(dataset);
                }
                catch (ProcessingException e)
                {
                    logger.error("Unable to process:", e);
                    errorCount++;
                }
            }
            
            store.saveOrUpdate(dataset);
            tx.commit();
            store.clear();
            
        }
    }

}
