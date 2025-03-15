package vttp.lecture.csf_36w.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vttp.lecture.csf_36w.model.Post;
import vttp.lecture.csf_36w.repository.PostRepository;

@RestController
@RequestMapping("api/post")
// @CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    @Autowired
    private PostRepository repository;

    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePost(@RequestPart MultipartFile picture, @RequestPart String comments) {
        System.out.println(picture.getOriginalFilename());
        
        Post post = new Post();
        post.setComment(comments);
        post.setFile(picture);

        try {
            repository.savePost(post);
        } catch (DataAccessException | IOException e) {
            return ResponseEntity.badRequest()
                .body("""
                    {
                        "message": "%s",
                        "timestamp": "%s"
                    }
                """
                .formatted("Your request could not be processed.", new Date().getTime()));
        }
        
        return ResponseEntity.ok()
            .body(post.toString());
    }

    @GetMapping("")
    public String test() {
        return "ok!";
    }
}
