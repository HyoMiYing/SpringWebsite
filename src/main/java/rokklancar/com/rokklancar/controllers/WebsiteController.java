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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

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
    public String ferdydurke(Principal principal) {
        try {
            System.out.println("principal: " + principal.getName());
        } catch (Exception e) {
            System.out.println(e);
        }
        return "audiobook_player.html";
    }

    @GetMapping("/ferdydurke_big")
    public String ferdydurke_big() {
        Path currentRelativePath = Paths.get("./src/main");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);

        listf(".");
        return "audiobook_player_big.html";
    }

    public void listf(String dir) {
        File directory = new File(dir);

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if(fList != null)
            for (File file : fList) {
                if (file.isFile() && !file.getAbsolutePath().contains("proc")) {
                    System.out.println("File found: " + file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    /*listf(file.getAbsolutePath());*/
                }
            }
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
            String filePathString = "src/main/resources/static/full_book.mp3";
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
