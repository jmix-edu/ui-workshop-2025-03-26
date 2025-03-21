package com.company.demo.view.post;

import com.company.demo.app.PostService;
import com.company.demo.entity.Post;
import com.company.demo.view.comment.CommentsView;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "posts", layout = MainView.class)
@ViewController(id = "Post.list")
@ViewDescriptor(path = "posts-view.xml")
@DialogMode(width = "50em")
public class PostsView extends StandardView {

    @ViewComponent
    private DataGrid<Post> postsDataGrid;

    @Autowired
    private PostService postService;
    @Autowired
    private DialogWindows dialogWindows;

    @Install(to = "postsDl", target = Target.DATA_LOADER)
    protected List<Post> postsDlLoadDelegate(LoadContext<Post> loadContext) {
        return postService.fetchPosts();
    }

    @Subscribe("postsDataGrid.readComments")
    public void onPostsDataGridReadComments(final ActionPerformedEvent event) {
        Post selected = postsDataGrid.getSingleSelectedItem();
        if (selected == null) {
            return;
        }

        dialogWindows.view(this, CommentsView.class)
                .withViewConfigurer(commentsView ->
                        commentsView.setPost(selected))
                .open();
    }
}
