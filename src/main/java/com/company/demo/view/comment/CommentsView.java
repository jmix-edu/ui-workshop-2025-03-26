package com.company.demo.view.comment;

import com.company.demo.app.PostService;
import com.company.demo.entity.Comment;
import com.company.demo.entity.Post;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@ViewController(id = "Comment_.list")
@ViewDescriptor(path = "comments-view.xml")
@DialogMode(width = "37.5em")
public class CommentsView extends StandardView {

    @ViewComponent
    private MessageList messageList;

    @Autowired
    private PostService postService;

    public void setPost(Post post) {
        List<Comment> comments = postService.fetchPostComments(post.getId());
        updateMessages(comments);
    }

    private void updateMessages(List<Comment> comments) {
        List<MessageListItem> messages = comments.stream()
                .map(comment -> {
                    MessageListItem message = new MessageListItem();
                    message.setText(comment.getBody());
                    message.setUserName(comment.getUser().getFullName());
                    message.setTime(LocalDateTime.now().toInstant(ZoneOffset.UTC));
                    message.setUserColorIndex(RandomUtils.secure().randomInt(0, 7));

                    return message;
                }).toList();

        messageList.setItems(messages);
    }
}
