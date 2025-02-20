package seals.demo.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.api.FoldersApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.auth.OAuth.OAuthToken;
import com.docusign.esign.model.Envelope;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeDocument;
import com.docusign.esign.model.EnvelopeDocumentsResult;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.FoldersRequest;
import com.docusign.esign.model.RecipientViewRequest;
import com.docusign.esign.model.Tabs;
import com.docusign.esign.model.TemplateRole;
import com.docusign.esign.model.Text;
import com.docusign.esign.model.ViewUrl;

import seals.demo.models.user;

@Service
public class DocusignService {

    @Value("${docusign.basepath}")
    private String basepath;

    @Value("${docusign.integration-key}")
    private String clientId;

    @Value("${docusign.user-id}")
    private String userId;

    @Value("${docusign.scopes}")
    private String scopes;

    @Value("${docusign.token.expiration}")
    private long expiresIn;

    @Value("${docusign.api.account-id}")
    private String apiId;

    @Value("${docusign.template-id}")
    private String templateId;

    public final String ENVELOPE_STATUS_SENT = "sent";
    public final String ENVELOPE_STATUS_CREATED = "created";
    public final String ENVELOPE_STATUS_COMPLETED = "completed";

    private final String SIGNER_ROLE_NAME = "Applicant";
    private final String[] tabLabels = {"Applicant Name and Address", "Email Address", "Name of Applicant Printed"};

    private OAuthToken _token;

    public DocusignService() {}

    public EnvelopeSummary createEnvelope(user user, String clientUserId) throws ApiException {
        EnvelopeDefinition envelopeDefinition = createEnvelopeDefinition(user, clientUserId);
        EnvelopesApi envelopesApi = getEnvelopesApi();
        EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(apiId, envelopeDefinition);
        return envelopeSummary;
    }

    public ViewUrl makeRecipientViewRequest(user user, String envelopeId, String clientUserId) throws ApiException {
        String dsReturnURL = "http://localhost:8080";
        String pingURL = "http://localhost:8080";

        RecipientViewRequest viewRequest = new RecipientViewRequest();
        // Set the url where you want the recipient to go once they are done signing
        // should typically be a callback route somewhere in your app.
        // The query parameter is included as an example of how
        // to save/recover state information during the redirect to
        // the DocuSign signing. It's usually better to use
        // the session mechanism of your web framework. Query parameters
        // can be changed/spoofed very easily.
        //String stateValue = "?state=123";
        viewRequest.setReturnUrl(dsReturnURL); //+ stateValue

        // How has your app authenticated the user? In addition to your app's
        // authentication, you can include authenticate steps from DocuSign.
        // Eg, SMS authentication
        String authenticationMethod = "none";
        viewRequest.setAuthenticationMethod(authenticationMethod);

        // Recipient information must match embedded recipient info
        // we used to create the envelope.
        viewRequest.setEmail(user.getEmail());
        viewRequest.setUserName(user.getFullName());
        //viewRequest.setClientUserId(clientUserId);

        // DocuSign recommends that you redirect to DocuSign for the
        // embedded signing. There are multiple ways to save state.
        // To maintain your application's session, use the pingUrl
        // parameter. It causes the DocuSign signing web page
        // (not the DocuSign server) to send pings via AJAX to your app.
        // NOTE: The pings will only be sent if the pingUrl is an https address
        String pingFrequency = "600";
        viewRequest.setPingFrequency(pingFrequency); // seconds
        viewRequest.setPingUrl(pingURL);

        EnvelopesApi envelopesApi = getEnvelopesApi();
        ViewUrl url = envelopesApi.createRecipientView(apiId, envelopeId, viewRequest);
        return url;
    }

    public Envelope getEnvelope(String envelopeId) throws ApiException {
        EnvelopesApi envelopesApi = getEnvelopesApi();
        Envelope envelope = envelopesApi.getEnvelope(apiId, envelopeId);
        return envelope;
    }

    public byte[] getAttachments(String envelopeId) throws ApiException {
        EnvelopesApi envelopesApi = getEnvelopesApi();
        EnvelopeDocumentsResult result = envelopesApi.listDocuments(apiId, envelopeId);
        List<EnvelopeDocument> documents = result.getEnvelopeDocuments();

        EnvelopeDocument signedDocument = documents.get(0);
        String documentId = signedDocument.getDocumentId();
        byte[] file = envelopesApi.getDocument(apiId, envelopeId, documentId);
        return file;
    }

    public void deleteEnvelope(String envelopeId) throws ApiException {
        FoldersApi foldersApi = getFoldersApi();
        FoldersRequest foldersRequest = new FoldersRequest();
        foldersRequest.setEnvelopeIds(Arrays.asList(envelopeId));
        foldersApi.moveEnvelopes(apiId, "recyclebin", foldersRequest);
    }

    private EnvelopesApi getEnvelopesApi() {
        OAuthToken token = getToken();
        ApiClient apiClient = new ApiClient(basepath);
        apiClient.addDefaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());
        return new EnvelopesApi(apiClient);
    }

    private FoldersApi getFoldersApi() {
        OAuthToken token = getToken();
        ApiClient apiClient = new ApiClient(basepath);
        apiClient.addDefaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());
        return new FoldersApi(apiClient);
    }

    //need better way of handling this token. Should use some spring boot method of handling it
    private OAuthToken getToken() {
        if(_token == null || _token.getExpiresIn() == 0) {
            boolean wasSuccessful = makeToken();

            if(wasSuccessful) {
                return _token;
            }
            else {
                return null;
            }
        }
        return _token;
    }

    private boolean makeToken() {
        try {
            byte[] privateKey = readFile("privatekey.txt");
            ApiClient apiClient = new ApiClient(basepath);
            _token = apiClient.requestJWTUserToken(clientId, userId, Arrays.asList(scopes), privateKey, expiresIn);
            return true;
        }
        catch(Exception exception) {
            System.out.println(exception.getMessage());
        }
        return false;
    }

    /**
     * Loads a file content and copy it into a byte array.
     *
     * @param path the absolute path within the class path
     * @return the new byte array that has been loaded from the file
     * @throws IOException in case of I/O errors
     */
    private byte[] readFile(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToByteArray(resource.getInputStream());
    }

    private TemplateRole createTemplateRole(user user, String clientUserId) {
        TemplateRole recipient = new TemplateRole();
        recipient.setEmail(user.getEmail());
        recipient.setName(user.getFullName());
        recipient.setRoleName(SIGNER_ROLE_NAME);
        recipient.setTabs(createTemplateTabs(user));
        //recipient.setClientUserId(clientUserId);
        return recipient;
    }

    private Tabs createTemplateTabs(user user) {
        Map<String, String> userValues = user.getMap();
        List<Text> textTabs = new ArrayList<Text>(0);

        for (String tabLabel : tabLabels) {
            Text text = new Text();
            text.tabLabel(tabLabel);
            text.setValue(userValues.get(tabLabel));
            textTabs.add(text);
        }

        Tabs tabs = new Tabs();
        tabs.setTextTabs(textTabs);

        return tabs;
    }

    private EnvelopeDefinition createEnvelopeDefinition(user recipient, String clientUserId) {
        EnvelopeDefinition envelope = new EnvelopeDefinition();
        envelope.setTemplateId(templateId);
        envelope.setTemplateRoles(Arrays.asList(createTemplateRole(recipient, clientUserId)));
        envelope.setStatus(ENVELOPE_STATUS_SENT);
        return envelope;
    }
}
