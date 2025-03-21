package com.company.demo.app;

import com.company.demo.entity.Comment;
import com.company.demo.entity.Post;
import io.jmix.core.EntityStates;
import io.jmix.core.common.util.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    public static final String BASE_URL = "https://dummyjson.com";
    public static final String POSTS_URL = BASE_URL + "/posts";
    public static final String POST_COMMENTS_URL = BASE_URL + "/posts/{postId}/comments";
    public static final String IMAGE_URL = BASE_URL + "/image/1200x400/%06x?fontFamily=pacifico&text=%s";

    private final RestTemplate restTemplate;
    private final EntityStates entityStates;

    public PostService(RestTemplate restTemplate,
                       EntityStates entityStates) {
        this.restTemplate = restTemplate;
        this.entityStates = entityStates;
    }

    public List<Post> fetchPosts() {
        log.debug("Fetching posts");

        PostDto postDto = restTemplate.getForObject(POSTS_URL, PostDto.class);
        if (postDto == null || CollectionUtils.isEmpty(postDto.posts())) {
            return Collections.emptyList();
        }

        List<Post> posts = postDto.posts();
        setState(posts);

        return posts;
    }

    public List<Comment> fetchPostComments(Long postId) {
        Preconditions.checkNotNullArgument(postId);

        log.debug("Fetching comments for post {}", postId);

        CommentDto commentDto = restTemplate.getForObject(POST_COMMENTS_URL, CommentDto.class, postId);
        if (commentDto == null || CollectionUtils.isEmpty(commentDto.comments())) {
            return Collections.emptyList();
        }

        List<Comment> comments = commentDto.comments();
        setState(comments);

        return comments;
    }

    public Long fetchPostCommentsNumber(Long postId) {
        Preconditions.checkNotNullArgument(postId);

        log.debug("Fetching comments number for post {}", postId);

        return RandomUtils.secure().randomLong(0, 6);

        /*PostCommentsNumber commentsNumber = restTemplate.getForObject(POST_COMMENTS_URL,
                PostCommentsNumber.class, postId);
        return commentsNumber != null ? commentsNumber.total() : 0;*/
    }

    private <T> void setState(List<T> items) {
        for (T item : items) {
            entityStates.setNew(item, false);
        }
    }

    public String fetchPostImageUrl(Post post) {
        return IMAGE_URL
                .formatted(RandomUtils.insecure().randomInt(0, 0xFFFFFF + 1), post.getTitle());
    }

    private record PostDto(List<Post> posts) {
    }

    private record CommentDto(List<Comment> comments) {
    }

    private record PostCommentsNumber(Long total) {
    }
}