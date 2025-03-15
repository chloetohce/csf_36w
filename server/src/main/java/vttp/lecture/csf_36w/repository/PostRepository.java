package vttp.lecture.csf_36w.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.lecture.csf_36w.model.Post;

@Repository
public class PostRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String insert = """
            insert into posts(pid, comments, picture)
            values(?, ?, ?);
            """;
    
    public void savePost(Post post) throws DataAccessException, IOException {
        jdbcTemplate.update(insert, post.getId(), post.getComment(), 
            post.getFile().getInputStream());
    }
}
