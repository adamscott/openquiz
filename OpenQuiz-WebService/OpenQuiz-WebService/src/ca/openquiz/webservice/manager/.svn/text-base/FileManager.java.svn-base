package ca.openquiz.webservice.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.UUID;

import ca.openquiz.webservice.enums.Bucket;
import ca.openquiz.webservice.tools.ExceptionLogger;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.sun.jersey.multipart.FormDataBodyPart;

public class FileManager {

	private static final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());

	public static String addNewFile(InputStream uploadedInputStream, FormDataBodyPart fileDetail, Bucket bucket) throws Exception{
		String id = UUID.randomUUID().toString();

		if(putFile(uploadedInputStream, fileDetail, bucket, id)){
			return id;
		}

		return null;
	}

	public static boolean replaceFile(InputStream uploadedInputStream, FormDataBodyPart fileDetail, Bucket bucket, String fileName) throws Exception{
		
		GcsFilename file = new GcsFilename(getBucketString(bucket), fileName);
		
		try {
			gcsService.getMetadata(file);
		} catch (IOException e) {
			return false;
		}
		
		return putFile(uploadedInputStream, fileDetail, bucket, fileName);
		
	}

	private static boolean putFile(InputStream uploadedInputStream, FormDataBodyPart fileDetail, Bucket bucket, String fileName) throws Exception{
		
		GcsFilename filename = new GcsFilename(getBucketString(bucket), fileName);

		GcsFileOptions.Builder optionsBuilder = new GcsFileOptions.Builder();
		optionsBuilder.mimeType(fileDetail.getMediaType().toString());
		if(bucket == Bucket.MediaQuestion){
			optionsBuilder.acl("public-read");
		}

		try {
			GcsOutputChannel outputChannel = gcsService.createOrReplace(filename, optionsBuilder.build());

			copy(uploadedInputStream, Channels.newOutputStream(outputChannel), getMaxFileSize(fileDetail));
			outputChannel.close();

			return true;

		} catch (IOException e) {
			ExceptionLogger.getLogger().warning(e.toString());
		} catch (Exception e) {
			throw e;
		}

		return false;
	}

	public static boolean removeFile(String fileName, Bucket bucket){
		try{
			GcsFilename file = new GcsFilename(getBucketString(bucket), fileName);
			gcsService.delete(file);
		}
		catch(Exception e){
			return false;
		}
		return true;
	}

	public static GcsInputChannel getFile(String fileName, Bucket bucket){
		GcsFilename filename = new GcsFilename(getBucketString(bucket), fileName);
		return gcsService.openPrefetchingReadChannel(filename, 0, 1024 * 1024);
	}

	private static long getMaxFileSize(FormDataBodyPart fileDetail){
		
		String contentType = fileDetail.getMediaType().toString();
		
		if(contentType.contains("video")){
			return 15000000L;
		}
		else if(contentType.contains("audio")){
			return 5000000L;
		}
		else if(contentType.contains("image")){
			return 2000000L;
		}
		
		return 10000000L;
	}
	
	public static String getFilePublicUrl(String id, Bucket bucket){
		return "https://storage.googleapis.com/" + getBucketString(bucket) + "/" + id;
	}

	private static String getBucketString(Bucket bucket){
		if(bucket == Bucket.MediaQuestion)
			return "media-questions-files_" + getCurrentProjectName();
		else if(bucket == Bucket.importQuestions)
			return "import-questions_" + getCurrentProjectName();
		return "other_" + getCurrentProjectName();
	}

	private static String getCurrentProjectName(){	
		return SystemProperty.applicationId.get();
	}

	private static void copy(InputStream input, OutputStream output, long maxSize) throws IOException, Exception {
		byte[] buffer = new byte[1024];
		int bytesRead = input.read(buffer);
		int TotalFileSize = 0;
		while (bytesRead != -1) {
			output.write(buffer, 0, bytesRead);
			bytesRead = input.read(buffer);
			TotalFileSize += bytesRead;
			if(TotalFileSize > maxSize && maxSize != 0){
				throw new Exception("File is too big. Videos must be under 15 MB, audios under 5 MB and images under 2MB");
			}
		}
	}
}
