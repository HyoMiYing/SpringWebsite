package rokklancar.com.rokklancar.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import rokklancar.com.rokklancar.services.MediaStreamLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class WebsiteController {

    private MediaStreamLoader mediaLoaderService;

    public WebsiteController(MediaStreamLoader mediaLoaderService) {
        this.mediaLoaderService = mediaLoaderService;
    }

    @GetMapping("/")
    public String homepage() {
        return "index.html";
    }

    @GetMapping("/ferdydurke")
    public String ferdydurke() {
        return "audiobook_player.html";
    }

    @GetMapping("/ferdydurke_stream")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> ferdydurke_stream(
            @RequestHeader(value = "Range", required = false)
            String rangeHeader,
            HttpServletRequest request)
    {
        try
        {
            String filePathString = "/home/rok/Downloads/cela_knjiga.mp3";
            ResponseEntity<StreamingResponseBody> returnVal = mediaLoaderService.loadPartialMediaFile(filePathString, rangeHeader);

            return returnVal;
        }
        catch (FileNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
