package com.mahendra;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Upload {

	public static void main(String[] args) {

		// Use Access Key ID and Access Key Secret
		AWSCredentials credentials = new BasicAWSCredentials("AKIA4MWES47HE4NSPTNX" ,"Y2mSTFDAesxmJM+iuZILhC0KFrtgZTad9bsLGrpx");
		
		AmazonS3 client = AmazonS3ClientBuilder.standard().withCredentials(
					new AWSStaticCredentialsProvider(credentials)
				).withRegion(Regions.AP_SOUTH_1).build();
		
		String bucket = "my-designs";
		if(! client.doesBucketExistV2(bucket)) {
			client.createBucket(bucket);
		}
		
		/*
		 * Downloading from S3 Bucket
		 * ObjectListing list = client.listObjects(bucket);
		 * for(S3ObjectSummary obj : list ){
		 * 		s3Download(client, bucket, obj.getKey());
		 * }
		 * 
		 * static void s3Download(AmazonS3 client, String bucket, String key){
		 *    S3Object obj = client.getObject(bucket, key);
		 *    S3ObjectInputStream stream = obj.getObjectContent(); // Get Binary data from s3 object
		 *    FileUtils.copyInputStreamToFile(stream, new File("d:\\temp101\\"+key); 
		 * }
		 */
		
		/// Local folder which contains few images
		String path = "C:\\Users\\mahendra\\Documents\\diagrams\\aws-nov-22";
		File directory = new File(path);
		if(directory.isDirectory()) {
			File[] files = directory.listFiles();
			for(File fs : files) {
				uploadToS3(client, fs);
			}
		}
		
	}

	private static void uploadToS3(AmazonS3 client, File file) {
		client.putObject("my-designs",file.getName(),file);
		System.out.println("Uploading to S3 : "+file.getName());
	}
}
