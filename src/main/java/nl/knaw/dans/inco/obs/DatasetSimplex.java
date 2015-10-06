package nl.knaw.dans.inco.obs;

import nl.knaw.dans.inco.pu.Dataset;

public interface DatasetSimplex
{
    void process(Dataset dataset) throws ProcessingException;
}
