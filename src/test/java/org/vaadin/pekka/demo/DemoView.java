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
package org.vaadin.pekka.demo;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.vaadin.pekka.WysiwygE;

import java.util.function.Consumer;
import java.util.stream.Stream;

@BodySize(height = "100vh", width = "100vw")
@Route("")
@Theme(Lumo.class)
@PageTitle("wysiwyg-e Java demo")
public class DemoView extends VerticalLayout {

    private WysiwygE wysiwygE;

    public DemoView() {
        add(new H3("wysiwyg-e Java demo"));

        createComponent();
        createOptions();

        add(wysiwygE);
    }

    private void createComponent() {
        wysiwygE = new WysiwygE();

        wysiwygE.addValueChangeListener(this::onValueChange);
    }

    private void createOptions() {
        TextField placeholder = new TextField("Edit Placeholder");
        placeholder.setValue(wysiwygE.getPlaceholder());
        placeholder.addValueChangeListener(event -> wysiwygE.setPlaceholder(event.getValue()));

        ComboBox<ValueChangeMode> valueChangeModeComboBox = new ComboBox<>();
        valueChangeModeComboBox.setItems(ValueChangeMode.values());
        valueChangeModeComboBox.setValue(wysiwygE.getValueChangeMode());
        valueChangeModeComboBox.addValueChangeListener(event -> wysiwygE.setValueChangeMode(event.getValue()));

        FlexLayout toolbarOptions = new FlexLayout();
        toolbarOptions.getElement().getStyle().set("flex-wrap", "wrap");
        Stream.of(WysiwygE.Tool.values()).map(tool -> createToolCheckbox(tool)).forEachOrdered(toolbarOptions::add);

        Consumer<Boolean> settingToolbarVisiblity = toolbarVisible -> {
            wysiwygE.setAllToolsVisible(toolbarVisible);
            toolbarOptions.setEnabled(toolbarVisible);
            toolbarOptions.getChildren().map(component -> (Checkbox) component).forEach(cb -> cb.setValue(toolbarVisible));
        };

        HorizontalLayout options = new HorizontalLayout(placeholder,
                valueChangeModeComboBox,
                createCheckbox("Redo allowed", wysiwygE::setRedoAllowed, wysiwygE.isRedoAllowed()),
                createCheckbox("Undo allowed", wysiwygE::setUndoAllowed, wysiwygE.isUndoAllowed()),
                new Button("Undo", event -> wysiwygE.undo()),
                new Button("Redo", event -> wysiwygE.redo()),
                createCheckbox("All Tools Visible", settingToolbarVisiblity, wysiwygE.isAllToolsVisible()));
        options.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        add(options, new H6("Tool visiblity"), toolbarOptions);
    }

    private Checkbox createCheckbox(String label, Consumer<Boolean> setter, boolean initialValue) {
        Checkbox checkbox = new Checkbox(label);
        checkbox.setValue(initialValue);
        checkbox.addValueChangeListener(event -> setter.accept(event.getValue()));
        return checkbox;
    }

    private Checkbox createToolCheckbox(WysiwygE.Tool tool) {
        Checkbox checkbox = new Checkbox(tool.name().toLowerCase());
        checkbox.setValue(true);
        checkbox.addValueChangeListener(event -> wysiwygE.setToolVisible(tool, event.getValue()));
        return checkbox;
    }

    private void onValueChange(ComponentValueChangeEvent<WysiwygE, String> event) {
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
