package nl.knaw.dans.inco.pu;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.knaw.dans.inco.rdb.DBEntity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "datasets", indexes = {
        @Index(name = "datasets_dataset_id_index", columnList="dataset_id", unique = true)})
public class Dataset extends DBEntity
{

    private static final long serialVersionUID = 2981285846755561531L;
    
    @Id
    @GeneratedValue
    @Column(name = "ds_id")
    private Long id;
    
    @NaturalId
    @Column(name = "dataset_id", nullable = false, unique = true)
    private String datasetId;

    @Column(name = "urn")
    private String urn;
    
    @Column(name = "doi")
    private String doi;
    
    @Column(name = "ds_title", length=512)
    private String datasetTitle;
    
    @Column(name = "ds_accessrights")
    private String accessRights;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade({CascadeType.ALL})
    private Set<Organization> organizations;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade({CascadeType.ALL})
    private Set<Audience> audiences;
    
    public Dataset() {}
    
    public Dataset(String datasetId) {
        this.datasetId = datasetId;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    public String getUrn()
    {
        return urn;
    }

    public void setUrn(String urn)
    {
        this.urn = urn;
    }

    public String getDoi()
    {
        return doi;
    }

    public void setDoi(String doi)
    {
        this.doi = doi;
    }

    public String getDatasetTitle()
    {
        return datasetTitle;
    }

    public void setDatasetTitle(String datasetTitle)
    {
        this.datasetTitle = StringUtils.abbreviate(datasetTitle, 512);
    }

    public String getDatasetId()
    {
        return datasetId;
    }
    
    public String getAccessRights()
    {
        return accessRights;
    }

    public void setAccessRights(String accessRights)
    {
        this.accessRights = accessRights;
    }

    public Set<Organization> getOrganizations() {
        if (organizations == null) {
            organizations = new HashSet<Organization>();
        }
        return organizations;
    }
    
    public Organization addOrganization(Organization organization) {
        organization.setDataset(this);
        getOrganizations().add(organization);
        return organization;
    }
    
    public Set<Audience> getAudiences() {
        if (audiences == null) {
            audiences = new HashSet<Audience>();
        }
        return audiences;
    }
    
    public Audience addAudience(Audience audience) {
        audience.setDataset(this);
        getAudiences().add(audience);
        return audience;
    }
}
