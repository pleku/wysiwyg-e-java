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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.dom.Element;

import java.util.Objects;

/**
 * A rich text editor that wraps the <a href="https://github.com/miztroh/wysiwyg-e">wysiwyg-e web component</a>.
 *
 * <em>NOTE:</em> the editor should be always sized or it won't show up in the UI.
 * <p>
 * For setting and reading the editor value, use {@link #setValue(String)} and {@link #getValue()}.
 * <p>
 * For listening to value change events, use {@link #addValueChangeListener(ValueChangeListener)}.
 */
@Tag("wysiwyg-e")
@HtmlImport("bower_components/wysiwyg-e/wysiwyg-e.html")
@HtmlImport("bower_components/wysiwyg-e/tools/bold.html")
@HtmlImport("bower_components/wysiwyg-e/tools/underline.html")
@HtmlImport("bower_components/wysiwyg-e/tools/strike.html")
@HtmlImport("bower_components/wysiwyg-e/tools/color.html")
@HtmlImport("bower_components/wysiwyg-e/tools/clear.html")
@HtmlImport("bower_components/wysiwyg-e/tools/code.html")
@HtmlImport("bower_components/wysiwyg-e/tools/link.html")
@HtmlImport("bower_components/wysiwyg-e/tools/image.html")
@HtmlImport("bower_components/wysiwyg-e/tools/audio.html")
@HtmlImport("bower_components/wysiwyg-e/tools/video.html")
@HtmlImport("bower_components/wysiwyg-e/tools/ordered.html")
@HtmlImport("bower_components/wysiwyg-e/tools/indent.html")
@HtmlImport("bower_components/wysiwyg-e/tools/outdent.html")
@HtmlImport("bower_components/wysiwyg-e/tools/justify.html")
@HtmlImport("bower_components/wysiwyg-e/tools/heading.html")
@HtmlImport("bower_components/wysiwyg-e/tools/blockquote.html")
public class WysiwygE extends Component implements HasSize, HasStyle, HasValue<WysiwygE, String> {

    /**
     * Constructs a wysiwyg-e rich text editor with default size of height 250px and width 600px.
     */
    public WysiwygE() {
        this("250px", "600px");
    }

    /**
     * Constructs a wysiwyg-e rich text editor with the given size.
     *
     * @param height the height for the editor
     * @param width  the width for the editor
     */
    public WysiwygE(String height, String width) {
        setHeight(height);
        setWidth(width);
        initToolbar();
    }

    /**
     * Inits the toolbar with the following tools:
     * {@code
     * <wysiwyg-tool-bold></wysiwyg-tool-bold>
     * <wysiwyg-tool-italic></wysiwyg-tool-italic>
     * <wysiwyg-tool-underline></wysiwyg-tool-underline>
     * <wysiwyg-tool-strike></wysiwyg-tool-strike>
     * <wysiwyg-tool-color></wysiwyg-tool-color>
     * <wysiwyg-tool-clear></wysiwyg-tool-clear>
     * <wysiwyg-tool-code></wysiwyg-tool-code>
     * <wysiwyg-tool-link></wysiwyg-tool-link>
     * <wysiwyg-tool-image></wysiwyg-tool-image>
     * <wysiwyg-tool-audio></wysiwyg-tool-audio>
     * <wysiwyg-tool-video></wysiwyg-tool-video>
     * <wysiwyg-tool-ordered></wysiwyg-tool-ordered>
     * <wysiwyg-tool-unordered></wysiwyg-tool-unordered>
     * <wysiwyg-tool-indent></wysiwyg-tool-indent>
     * <wysiwyg-tool-outdent></wysiwyg-tool-outdent>
     * <wysiwyg-tool-justify right center full></wysiwyg-tool-justify>
     * <wysiwyg-tool-heading h1 h2 h3 h4 h5 h6></wysiwyg-tool-heading>
     * <wysiwyg-tool-blockquote></wysiwyg-tool-blockquote>
     * }
     */
    protected void initToolbar() {
        getElement().appendChild(new Element("wysiwyg-tool-bold"));
        getElement().appendChild(new Element("wysiwyg-tool-italic"));
        getElement().appendChild(new Element("wysiwyg-tool-underline"));
        getElement().appendChild(new Element("wysiwyg-tool-strike"));
        getElement().appendChild(new Element("wysiwyg-tool-color"));
        getElement().appendChild(new Element("wysiwyg-tool-clear"));
        getElement().appendChild(new Element("wysiwyg-tool-code"));
        getElement().appendChild(new Element("wysiwyg-tool-link"));
        getElement().appendChild(new Element("wysiwyg-tool-image"));
        getElement().appendChild(new Element("wysiwyg-tool-audio"));
        getElement().appendChild(new Element("wysiwyg-tool-video"));
        getElement().appendChild(new Element("wysiwyg-tool-ordered"));
        getElement().appendChild(new Element("wysiwyg-tool-unordered"));
        getElement().appendChild(new Element("wysiwyg-tool-indent"));
        getElement().appendChild(new Element("wysiwyg-tool-outdent"));
        Element justifyElement = new Element("wysiwyg-tool-justify").setAttribute("right", true)
                .setAttribute("center", true)
                .setAttribute("full", true);
        getElement().appendChild(justifyElement);
        // FIXME there is an JS error when opening heading bar
        Element headingElement = new Element("wysiwyg-tool-heading")
                .setAttribute("h1", true)
                .setAttribute("h2", true)
                .setAttribute("h3", true)
                .setAttribute("h3", true)
                .setAttribute("h4", true)
                .setAttribute("h5", true)
                .setAttribute("h6", true);
        getElement().appendChild(headingElement);
        getElement().appendChild(new Element("wysiwyg-tool-blockquote"));
    }

    @Override
    public void setValue(String value) {
        getElement().setProperty(getClientValuePropertyName(), value);
    }

    @Synchronize("blur")
    @Override
    public String getValue() {
        String value = getElement().getProperty(getClientValuePropertyName());
        return value == null ? getEmptyValue() : value;
    }

    @Override
    public String getEmptyValue() {
        return "";
    }

    /**
     * Sets the placeholder that is shown when the input is empty.
     * <p>
     * Default value is "Edit your content here...".
     *
     * @param placeholder the placeholder text to set, not {@code null}
     */
    public void setPlaceholder(String placeholder) {
        Objects.requireNonNull(placeholder, "Placeholder cannot be null. Use empty string instead.");
        getElement().setProperty("placeholder", placeholder);
    }

    /**
     * Gets the current placeholder text.
     * <p>
     * Default value is "Edit your content here...".
     *
     * @return placeholder text
     */
    public String getPlaceholder() {
        return getElement().getProperty("placeholder", "Edit your content here...");
    }

    /**
     * Sets whether redo is allowed or not. Default is {@code true}.
     * <p>
     * NOTE: setting this to {@code false} also makes any {@link #redo()} calls not have any effect.
     *
     * @param redoAllowed
     */
    public void setRedoAllowed(boolean redoAllowed) {
        getElement().setProperty("noRedo", !redoAllowed);
    }

    /**
     * Is redo allowed or not. Default is {@code true}.
     *
     * @return is redo allowed or not
     * @see #setRedoAllowed(boolean)
     */
    public boolean isRedoAllowed() {
        return !getElement().getProperty("noRedo", false);
    }

    /**
     * Is undo allowed or not. Default is {@code true}.
     *
     * @return is undo allowed or not
     * @see #setUndoAllowed(boolean)
     */
    public void setUndoAllowed(boolean undoAllowed) {
        getElement().setProperty("noUndo", !undoAllowed);
    }

    /**
     * Is undo allowed or not. Default is {@code true}.
     *
     * @return is Undo allowed or not
     * @see #setUndoAllowed(boolean)
     */
    public boolean isUndoAllowed() {
        return !getElement().getProperty("noUndo", false);
    }

    /**
     * Redoes the last edit.
     */
    public void redo() {
        getElement().callFunction("redo");
    }

    /**
     * Undoes the last edit.
     */
    public void undo() {
        getElement().callFunction("undo");
    }

    // locale support is function based, it returns a JsonObject with values for redo/undo for each supported language
//    public void setLocale(Locale locale) {
//        getElement().setProperty("language", locale.getLanguage());
//    }
//
//    public Locale getLocale() {
//        String language = getElement().getProperty("language", "en");
//        return new Locale(language);
//    }

}
