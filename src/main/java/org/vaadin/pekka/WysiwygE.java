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

import java.util.Objects;

/**
 * A rich text editor that wraps the <a href="https://github.com/miztroh/wysiwyg-e">wysiwyg-e web component</a>.
 * <p>
 * <em>NOTE:</em> the editor should be always sized or it won't show up in the UI.
 * <p>
 * For setting and reading the editor value, use {@link #setValue(String)} and {@link #getValue()}.
 * <p>
 * For listening to value change events, use {@link #addValueChangeListener(ValueChangeListener)}.
 */
@Tag("wysiwyg-e")
@HtmlImport("bower_components/wysiwyg-e/wysiwyg-e.html")
public class WysiwygE extends Component implements HasSize, HasStyle, HasValue<WysiwygE, String> {

    /**
     * Constructs a wysiwyg-e rich text editor with default size of height 200px and width 400px.
     */
    public WysiwygE() {
        this("200px", "400px");
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
    }

    /**
     * Constructs a wysiwyg-e rich text editor with the given size and value change listener.
     *
     * @param height              the height for the editor
     * @param width               the width for the editor
     * @param valueChangeListener the value change listener to set
     */
    public WysiwygE(String height, String width, ValueChangeListener<WysiwygE, String> valueChangeListener) {
        this(height, width);
        addValueChangeListener(valueChangeListener);
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
