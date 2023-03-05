package rokklancar.com.rokklancar.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AWSS3Service {


    private static AWSCredentials credentials = new BasicAWSCredentials(
            System.getenv("ACCESS_KEY"),
            System.getenv("SECRET_ACCESS_KEY")
    );

    private static AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();

    public static void downloadAudiobookFile() {

        File file = new File("src/main/resources/full_book.mp3");
        if(!file.exists()) {
            S3Object s3Object = s3client.getObject("ferdydurke-audio", "cela_knjiga.mp3");
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
