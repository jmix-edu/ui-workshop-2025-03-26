package com.company.demo.component.renderer;

import com.company.demo.app.PostService;
import com.company.demo.entity.Post;
import com.company.demo.view.comment.CommentsView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.fragmentrenderer.FragmentRenderer;
import io.jmix.flowui.fragmentrenderer.RendererItemContainer;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import org.springframework.beans.factory.annotation.Autowired;

@FragmentDescriptor("post-renderer.xml")
@RendererItemContainer("postDc")
public class PostRenderer extends FragmentRenderer<Div, Post> {

    @ViewComponent
    private JmixImage<Object> image;
    @ViewComponent
    private JmixButton likesBtn;
    @ViewComponent
    private JmixButton dislikesBtn;
    @ViewComponent
    private JmixButton commentsBtn;
    @ViewComponent
    private JmixButton viewsBtn;

    @Autowired
    private PostService postService;
    @Autowired
    private DialogWindows dialogWindows;

    @Override
    public void setItem(Post item) {
        super.setItem(item);

        image.setSrc(postService.fetchPostImageUrl(item));
        likesBtn.setText(item.getReactions().getLikes().toString());
        dislikesBtn.setText(item.getReactions().getDislikes().toString());
        commentsBtn.setText(postService.fetchPostCommentsNumber(item.getId()).toString());
        viewsBtn.setText(item.getViews().toString());
    }

    @Subscribe(id = "commentsBtn", subject = "clickListener")
    public void onCommentsBtnClick(final ClickEvent<JmixButton> event) {
        Post post = getItem();
        if (post == null) {
            return;
        }

        dialogWindows.view(UiComponentUtils.getView(this), CommentsView.class)
                .withViewConfigurer(commentsView ->
                        commentsView.setPost(post))
                .open();
    }
}