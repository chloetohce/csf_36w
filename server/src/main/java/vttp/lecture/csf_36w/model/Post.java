package vttp.lecture.csf_36w.model;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;

public class Post {
    private MultipartFile file;

    private String comment;

    private String id;

    public Post() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public MultipartFile getFile() {return file;}
    public void setFile(MultipartFile file) {this.file = file;}

    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String toString() {
        return Json.createObjectBuilder()
            .add("pid", this.id)
            .add("comments", this.comment)
            .add("picture", this.file.getOriginalFilename())
            .build()
            .toString();
    }
    
}
