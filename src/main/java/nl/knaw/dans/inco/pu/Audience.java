package nl.knaw.dans.inco.pu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "audiences", indexes = {
        @Index(name = "audiences_dataset_id_index", columnList="dataset_id", unique = false)
        })
public class Audience implements Serializable
{

    private static final long serialVersionUID = -4279199285734270224L;
    
    @Id
    @GeneratedValue
    @Column(name = "aud_id")
    private Long id;
    
    @Column(name = "dataset_id", nullable = false)
    private String datasetId;
    
    @Column(name = "aud_code")
    private String code;
    
    @ManyToOne(optional = false)
    private Dataset parent;
    
    public Audience() {}
    
    public Audience(String code) {
        this.code = code;
    }
    
    public Long getId() {
        return id;
    }
    
    protected void setDataset(Dataset parent)
    {
        this.parent = parent;
        this.datasetId = parent.getDatasetId();
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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
