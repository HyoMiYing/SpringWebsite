package rokklancar.com.rokklancar.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MediaStreamLoaderImplementation implements MediaStreamLoader{

    @Override
    public ResponseEntity<StreamingResponseBody> loadEntireMediaFile(String localMediaFilePath) throws IOException {
        Path filePath = Paths.get(localMediaFilePath);
        if (!filePath.toFile().exists()) {
            throw new FileNotFoundException("The media file does not exist.");
        }

        long fileSize = Files.size(filePath);
        long endPos = fileSize;
        if (fileSize > 0L)
        {
            endPos = fileSize - 1;
        }
        else
        {
            endPos = 0L;
        }

        ResponseEntity<StreamingResponseBody> retVal = loadPartialMediaFile(localMediaFilePath, 0, endPos);

        return retVal;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> loadPartialMediaFile(String localMediaFilePath, String rangeValues) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> loadPartialMediaFile(String localMediaFilePath, long fileStartPos, long fileEndPos) throws IOException {
        return null;
    }
}
