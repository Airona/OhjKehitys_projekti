package fi.swd.projektityo.domain;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageHelper {
	
	private final String credentials = "ReactImages-e37e5bd6e6fc.json";
	private final String projectId = "reactimages-games";
	private final String bucketName = "reactimages-games.appspot.com";
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
	
	public void getSampleBlob() {
		BlobId blobId = BlobId.of(bucketName, "Test");
		byte[] content = storage.readAllBytes(blobId);
		String contentString = new String(content, UTF_8);
		System.out.println(contentString);
	}
	
	public void updateSampleBlob() {
		BlobId blobId = BlobId.of(bucketName, "Test");
		Blob blob = storage.get(blobId);
		if (blob != null) {
		  byte[] prevContent = blob.getContent();
		  System.out.println(new String(prevContent, UTF_8));
		  WriteChannel channel = blob.writer();
		  try {
			  channel.write(ByteBuffer.wrap("Updated content".getBytes(UTF_8)));
			  channel.close();
			} catch (Exception e) {
				System.out.println("updateSampleBlob error\n" + e + "\n updateSampleBlob error /end");
			}		  
		}
	}
	
	public void listBucket() {
		// List all your buckets
		System.out.println("My buckets:");
		for (Bucket bucket : storage.list().iterateAll()) {
		  System.out.println(bucket);
		  
		  // List all blobs in the bucket
		  System.out.println("Blobs in the bucket:");
		  for (Blob blob : bucket.list().iterateAll()) {
		    System.out.println(blob);
		  }
		}
	}
	
	/*public void createBucket() {
	// Create a bucket
	storage.create(BucketInfo.of(bucketName));
	}*/
	
}
