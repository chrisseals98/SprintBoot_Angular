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

@Entity
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class document {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String fileType;
    private String fileName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uploadedBy", referencedColumnName = "id")
    private user uploader;
    @ManyToOne
    @JoinColumn(name="application", nullable=false, referencedColumnName = "id")
    private app26a application;

    public Long getId() {
        return this.id;
    }

    public String getFileType() {
        return this.fileType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public user getUploader() {
        return this.uploader;
    }

    public app26a getApplication() {
        return this.application;
    }
}
