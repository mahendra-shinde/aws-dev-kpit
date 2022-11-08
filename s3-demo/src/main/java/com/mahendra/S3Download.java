package com.mahendra;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Download {

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
		
		
		 //Downloading from S3 Bucket
		 ObjectListing list = client.listObjects(bucket);
		 for(S3ObjectSummary obj : list.getObjectSummaries() ){
		 		s3Download(client, bucket, obj.getKey());
		  }
		  
		 
	}

	private static void s3Download(AmazonS3 client, String bucket, String key){
	     S3Object obj = client.getObject(bucket, key);
	     S3ObjectInputStream stream = obj.getObjectContent(); // Get Binary data from s3 object
	     try {
			FileUtils.copyInputStreamToFile(stream, new File("d:\\temp101\\"+key));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  }
}
