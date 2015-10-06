package nl.knaw.dans.inco.pu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "organizations", indexes = {
        @Index(name = "organizations_dataset_id_index", columnList="dataset_id", unique = false)
        })
public class Organization implements Serializable {

    private static final long serialVersionUID = 3465445052540251961L;
    
    @Id
    @GeneratedValue
    @Column(name = "org_id")
    private Long id;
    
    @Column(name = "dataset_id", nullable = false)
    private String datasetId;
    
    @Column(name = "org_name", length = 1024)
    private String name;
    
    // like where did this information coming from: publisher, rights holder, creator
    @Column(name = "org_type")
    private String type;
    
    @ManyToOne(optional = false)
    private Dataset parent;
    
    public Organization() {}
    
    public Organization(String type) {
        this.type = type;
    }
    
    public Long getId()
    {
        return id;
    }
    
    protected void setDataset(Dataset parent)
    {
        this.parent = parent;
        this.datasetId = parent.getDatasetId();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = StringUtils.abbreviate(name, 1024);
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDatasetId()
    {
        return datasetId;
    }

    public Dataset getParent()
    {
        return parent;
    }

}
