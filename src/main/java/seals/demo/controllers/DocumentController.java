package seals.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.docusign.esign.model.Envelope;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.ViewUrl;

import seals.demo.Services.DocusignService;
import seals.demo.models.app26a;
import seals.demo.models.document;
import seals.demo.models.fileType;
import seals.demo.models.user;
import seals.demo.repositories.app26aRepository;
import seals.demo.repositories.documentRepository;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private documentRepository documentRepository;

    @Autowired
    private app26aRepository app26aRepository;

    @Autowired
    private DocusignService docusignService;

    @GetMapping
    public List<document> getDocuments() {
        return (List<document>) this.documentRepository.findAll();
    }

    @GetMapping("/application")
    public List<document> getDocumentsForApplication(@RequestParam String id) {
        Stream<document> stream = StreamSupport.stream(documentRepository.findAll().spliterator(), false);
        Stream<document> filteredStream = stream.filter(document -> document.getApplication().getId().toString().equals(id));
        List<document> list = filteredStream.toList();
        
        try {
            for (document document : list) {
                Envelope envelope = docusignService.getEnvelope(document.getFileName());
    
                document.setStatus(envelope.getStatus());
            }
        }
        catch(Exception exception) {
            System.out.println(exception.getMessage());
        }
        
        return list;
    }

    @GetMapping("/sign")
    @CrossOrigin(origins = "http://localhost:8080")
    public RedirectView signDocuments(@RequestParam String id, @AuthenticationPrincipal OAuth2User oAuthUser) {

        Stream<document> stream = StreamSupport.stream(documentRepository.findAll().spliterator(), false);
        Stream<document> filteredStream = stream.filter(document -> document.getApplication().getId().toString().equals(id));
        List<document> list = filteredStream.toList();
        if(list.size() == 0) {
            //just for testing I put this in
            String firstName = "chris";
            String lastName = "seals";
            String email = "cmseals@tva.gov";
            user user = new user(firstName, lastName, email);

            try {
                EnvelopeSummary envelopeSummary = docusignService.createEnvelope(user, oAuthUser.getAttribute("id").toString());
                String envelopeId = envelopeSummary.getEnvelopeId();

                Optional<app26a> app = app26aRepository.findById(Long.parseLong(id));

                document document = new document(envelopeId, fileType.JAF, user, app.get());
                documentRepository.save(document);

                ViewUrl url = docusignService.makeRecipientViewRequest(user, envelopeId, oAuthUser.getAttribute("id").toString());

                return new RedirectView(url.getUrl(), false);
            }
            catch(Exception exception) {
                System.out.println(exception.getMessage());
                return null;
            }
        }
        else {
            document document = list.get(0);

            try {
                Envelope envelope = docusignService.getEnvelope(document.getFileName());
                
                if(envelope.getStatus().equals(docusignService.ENVELOPE_STATUS_COMPLETED)) {
                    return new RedirectView("http://localhost:8080");
                }
                else {
                    ViewUrl url = docusignService.makeRecipientViewRequest(document.getUploader(), document.getFileName(), oAuthUser.getAttribute("id").toString());
                    return new RedirectView(url.getUrl(), false);
                }
            }
            catch(Exception exception) {
                System.out.println(exception.getMessage());
                return null;
            }
        }
    }

    @GetMapping("/attachments")
    public ResponseEntity<ByteArrayResource> getAttachment(@RequestParam String id) {
        try {
            byte[] fileData = docusignService.getAttachments(id);

            ByteArrayResource resource = new ByteArrayResource(fileData);

            // Return the response entity
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF) // Set the content type
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename("SignedDocument.pdf")
                        .build().toString())
                    .body(resource);
        }
        catch(Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDocument(@RequestParam Long id) {
        try {
            document document = documentRepository.findById(id).get();
            docusignService.deleteEnvelope(document.getFileName());
            documentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }
    
}
