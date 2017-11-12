package fi.swd.projektityo.domain;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageHelper {
	
	private String credentials = "ReactImages-e37e5bd6e6fc.json";
	private String projectId = "reactimages-games";
	private String bucketName = "reactimages-games.appspot.com";
	private Storage storage = null;
	
	public CloudStorageHelper() {
		setStorage();
	}
	
	public void setStorage() {
		try {
			this.storage = StorageOptions.newBuilder()
					.setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentials)))
					.setProjectId(projectId)
					.build()
					.getService();
		} catch (Exception e) {
			System.out.println("Firebase - Credentials Error.\n" + e + "\n Firebase - Credentials Error. /end");
			this.storage = null;
		}
	}
	
	public Storage getStorage() {
		return this.storage;
	}
	
	
	public void createSampleBlob() {
		// Upload a blob to the newly created bucket
    	BlobId blobId = BlobId.of(bucketName, "Test");
    	BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
    	storage.create(blobInfo, "image".getBytes(UTF_8));			
	}
	
	/*public void createBucket() {
	// Create a bucket
	storage.create(BucketInfo.of(bucketName));
	}*/
	
}
