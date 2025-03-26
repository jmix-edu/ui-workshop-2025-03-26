package com.company.demo.view.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route("")
@ViewController(id = "MainView")
@ViewDescriptor(path = "main-view.xml")
public class MainView extends StandardView implements RouterLayout {

    @ViewComponent
    private Div contentWrapper;

    @Override
    public void showRouterLayoutContent(HasElement content) {
        Component target = null;
        if (content != null) {
            target = content.getElement().getComponent().orElseThrow(() ->
                    new IllegalArgumentException("Content must be a Component"));
        }

        if (target == null) {
            return;
        }

        target.addClassName("jmix-main-view-content");
        this.contentWrapper.add(target);
    }
}
