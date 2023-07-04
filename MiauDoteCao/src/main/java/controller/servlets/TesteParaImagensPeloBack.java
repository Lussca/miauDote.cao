package controller.servlets;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.database.ServerValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import controller.handler.RequestResponseHandler;

/**
 * Servlet implementation class TesteParaImagensPeloBack
 */
@MultipartConfig
public class TesteParaImagensPeloBack extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Storage storage;
    private DatabaseReference databaseReference;
	RequestResponseHandler rrh = new RequestResponseHandler();
    public TesteParaImagensPeloBack() {
        super();

    }
    public void init() throws ServletException {
        try {
            // Initialize Firebase Admin SDK
            InputStream serviceAccount = getClass().getResourceAsStream("/path/to/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://your-firebase-project.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            // Initialize Firebase Storage
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();

            // Initialize Firebase Realtime Database
            databaseReference = FirebaseDatabase.getInstance().getReference();
        } catch (IOException e) {
            throw new ServletException("Failed to initialize Firebase", e);
        }
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
		 try {
	            // Get the JSON data from the request body
	            StringBuilder jsonBuilder = new StringBuilder();
	            String line;
	            BufferedReader reader = request.getReader();
	            while ((line = reader.readLine()) != null) {
	                jsonBuilder.append(line);
	            }
	            String json = jsonBuilder.toString();

	            // Parse the JSON data
	            // You can use a JSON parsing library like Jackson or Gson for more complex JSON parsing
	            Map<String, Object> jsonData = new HashMap<>();
	            // Assuming the JSON has a "title" field
	            jsonData.put("title", "Some title");

	            // Store the JSON data in Firebase
	            DatabaseReference jsonRef = databaseReference.child("json");
	            jsonRef.setValueAsync(jsonData);

	            // Process the image files
	            Collection<Part> parts = request.getParts();
	            for (Part part : parts) {
	                if (part.getName().equals("image")) {
	                    String fileName = part.getSubmittedFileName();
	                    String fileExtension = getFileExtension(fileName);
	                    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
	                    String imagePath = "images/" + uniqueFileName;

	                    // Store the image file in Firebase Storage
	                    BlobId blobId = BlobId.of("your-firebase-project.appspot.com", imagePath);
	                    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

	                    try (InputStream fileInputStream = part.getInputStream()) {
	                        Blob blob = storage.create(blobInfo, fileInputStream);

	                        // Get the download URL of the stored image
	                        URL downloadUrl = blob.signUrl(Duration.ofDays(14)); // URL valid for 2 weeks

	                        // Save the download URL in the Firebase Realtime Database
	                        DatabaseReference imageRef = databaseReference.child("images").push();
	                        imageRef.child("fileName").setValue(fileName);
	                        imageRef.child("downloadUrl").setValue(downloadUrl);
	                        imageRef.child("timestamp").setValue(ServerValue.TIMESTAMP);

	                        // Return the download URL as the servlet response
	                        response.getWriter().print(downloadUrl);
	                        return;
	                    }
	                }
	            }

	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().print("No image file found");
	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().print("Upload failed");
	        }
	    }
		  	
		  
	private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }   
	

	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rrh.configureCors(response);
	}

}
/*File uploads = new File("MiauDoteCao\\src\\main\\webapp\\WEB-INF\\images");
String multipartContentType = "multipart/form-data";
String fieldname = "file";
Part filePart = request.getPart(fieldname);
Map < Object, Object > responseData = null;

// Create path components to save the file.
final PrintWriter writer = response.getWriter();

String linkName = null;
String name = null;

try {
    // Check content type.
    if (request.getContentType() == null ||
        request.getContentType().toLowerCase().indexOf(multipartContentType) == -1) {

        throw new Exception("Invalid contentType. It must be " + multipartContentType);
    }

    // Get file Part based on field name and also image extension.
    filePart = request.getPart(fieldname);
    String type = filePart.getContentType();
    type = type.substring(type.lastIndexOf("/") + 1);

    // Generate random name.
    String extension = type;
    extension = (extension != null && extension != "") ? "." + extension : extension;
    name = UUID.randomUUID().toString() + extension ;

    // Get absolute server path.
    String absoluteServerPath = uploads + name;

    // Create link.
    String path = request.getHeader("referer");
    linkName = path + "files/" + name;

    // Validate image.
    String mimeType = filePart.getContentType();
    String[] allowedExts = new String[] {
        "gif",
        "jpeg",
        "jpg",
        "png",
        "svg",
        "blob"
    };
    String[] allowedMimeTypes = new String[] {
        "image/gif",
        "image/jpeg",
        "image/pjpeg",
        "image/x-png",
        "image/png",
        "image/svg+xml"
    };

    if (!ArrayUtils.contains(allowedExts, FilenameUtils.getExtension(absoluteServerPath)) ||
        !ArrayUtils.contains(allowedMimeTypes, mimeType.toLowerCase())) {

        // Delete the uploaded image if it dose not meet the validation.
        File file = new File(uploads + name);
        if (file.exists()) {
            file.delete();
        }

        throw new Exception("Image does not meet the validation.");
    }

    // Save the file on server.

    File file = new File(uploads, name);

    try (InputStream input = filePart.getInputStream()) {
        Files.copy(input, file.toPath());
    } catch (Exception e) {
      writer.println("<br/> ERROR: " + e);
    }

} catch (Exception e) {
    e.printStackTrace();
    writer.println("You either did not specify a file to upload or are " +
        "trying to upload a file to a protected or nonexistent " +
        "location.");
    writer.println("<br/> ERROR: " + e.getMessage());
    responseData = new HashMap < Object, Object > ();
    responseData.put("error", e.toString());

} finally {
    // Build response data.
    responseData = new HashMap < Object, Object > ();
    responseData.put("link", linkName);

    // Send response data.
    String jsonResponseData = new Gson().toJson(responseData);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponseData);
		}*/