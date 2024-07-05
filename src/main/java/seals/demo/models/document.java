package seals.demo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="documents")
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class document {
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    private String file_type;
    private String file_name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uploaded_by", referencedColumnName = "id")
    private user uploader;
    @ManyToOne
    @JoinColumn(name="application", nullable=false, referencedColumnName = "id")
    private app26a application;

    public Long getId() {
        return this.id;
    }

    public String getFile_Type() {
        return this.file_type;
    }

    public String getFile_Name() {
        return this.file_name;
    }

    public user getUploader() {
        return this.uploader;
    }

    public app26a getApplication() {
        return this.application;
    }
}
