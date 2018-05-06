/*
 * MIT License
 *
 * Copyright 2018 Pekka Hyvönen.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.vaadin.pekka;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.function.Consumer;

@BodySize(height = "100vh", width = "100vw")
@Route("")
@Theme(Lumo.class)
@PageTitle("wysiwyg-e Java demo")
public class DemoView extends VerticalLayout {

    private WysiwygE wysiwygE;
    private HorizontalLayout options;

    public DemoView() {
        createComponent();
        createOptions();

        add(new H3("wysiwyg-e Java demo"), options, wysiwygE);
    }

    private void createComponent() {
        wysiwygE = new WysiwygE("300px", "100vw");

        wysiwygE.addValueChangeListener(this::onValueChange);
    }

    private void createOptions() {
        TextField placeholder = new TextField("Edit Placeholder");
        placeholder.setValue(wysiwygE.getPlaceholder());
        placeholder.addValueChangeListener(event -> wysiwygE.setPlaceholder(event.getValue()));

        options = new HorizontalLayout(placeholder,
                createCheckbox("Redo allowed", wysiwygE::setRedoAllowed, true),
                createCheckbox("Undo allowed", wysiwygE::setUndoAllowed, true),
                new Button("Undo", event -> wysiwygE.undo()),
                new Button("Redo", event -> wysiwygE.redo()),
                new Button("Show value", e->Notification.show(wysiwygE.getText()))
        );
        options.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    }

    private Checkbox createCheckbox(String label, Consumer<Boolean> setter, boolean initialValue) {
        Checkbox checkbox = new Checkbox(label);
        checkbox.setValue(initialValue);
        checkbox.addValueChangeListener(event -> setter.accept(event.getValue()));
        return checkbox;
    }

    private void onValueChange(HasValue.ValueChangeEvent<String> event) {
        Div div = new Div();
        div.getElement().setProperty("innerText", event.getValue());
        div.getStyle().set("background-color", "springgreen");

        Notification notification = new Notification();
        notification.setDuration(5000);
        notification.setPosition(Notification.Position.BOTTOM_END);
        notification.add(new Span("ValueChangeEvent, fromClient:" + event.isFromClient()), div);
        notification.open();
    }

}
