package nl.knaw.dans.inco.main;

import nl.knaw.dans.inco.fedora.FedoraConnector;
import nl.knaw.dans.inco.fedora.PerDateDatasetIterator;
import nl.knaw.dans.inco.obs.PartialEmdReader;

public class AppFixed
{

    public static void main(String[] args) throws Exception
    {
        FedoraConnector connector = new FedoraConnector();
        connector.connect();
        
        PerDateDatasetIterator iter = new PerDateDatasetIterator();
        
        Conveyor conveyor = new Conveyor(iter);
        conveyor.addDatasetSimplex(new PartialEmdReader());
        
        conveyor.run();
    }

}
