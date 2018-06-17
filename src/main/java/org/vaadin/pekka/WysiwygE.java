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

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.dom.Element;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
public class WysiwygE extends AbstractSinglePropertyField<WysiwygE, String> implements HasSize, HasStyle {

    public enum Tool {
        BOLD, UNDERLINE, STRIKE, COLOR, CLEAR, CODE, LINK, IMAGE, AUDIO, VIDEO, ORDERED, INDENT, OUTDENT, JUSTIFY, HEADING, BLOCKQUOTE
    }

    /**
     * Constructs a wysiwyg-e rich text editor with all the tools visible and default size of height 300px and width 800px.
     */
    public WysiwygE() {
        this(true);
    }

    /**
     * Constructs a wysiwyg-e rich text editor with default size of height 300px and width 800px.
     *
     * @param allToolsVisible should the toolbar with tools be visible or not
     */
    public WysiwygE(boolean allToolsVisible) {
        this("300px", "800px", allToolsVisible);
    }

    /**
     * Constructs a wysiwyg-e rich text editor with default size of height 300px and width 800px and shows only the given tools.
     *
     * @param tools the tools to show, hides all other tools
     */
    public WysiwygE(Tool... tools) {
        this();
        setToolsVisible(tools);
    }

    /**
     * Constructs a wysiwyg-e rich text editor with all the tools visible and the given size.
     *
     * @param height the height for the editor
     * @param width  the width for the editor
     */
    public WysiwygE(String height, String width) {
        this(height, width, true);
    }

    /**
     * Constructs a wysiwyg-e rich text editor with the toolbar visible and the given size and shows only the given tools.
     *
     * @param height the height for the editor
     * @param width  the width for the editor
     * @param tools  the tools to show, hides all other tools
     */
    public WysiwygE(String height, String width, Tool... tools) {
        this(height, width, true);
        setToolsVisible(tools);
    }

    /**
     * Constructs a wysiwyg-e rich text editor.
     *
     * @param height       the height for the editor
     * @param width        the width for the editor
     * @param toolsVisible should the toolbar with tools be visible or not
     */
    public WysiwygE(String height, String width, boolean toolsVisible) {
        super("value", "", false);
        setSynchronizedEvent("blur");
        setHeight(height);
        setWidth(width);
        initToolbar();
        if (!toolsVisible) {
            setAllToolsVisible(toolsVisible);
        }
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

    /**
     * {@inheritDoc}
     */
    /* Inherited to get the type correct in class level javadocs. */
    public void setValue(String value) {
        super.setValue(value);
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

    /**
     * Sets all the tools visible or hidden.
     *
     * @param allToolsVisible {@code true} for setting all tools visible, {@code false} for hiding all tools
     * @see #setToolsInvisible(Tool...) for hiding specific tools
     * @see #setToolsVisible(Tool...) for showing specific tools
     * @see #setToolVisible(Tool, boolean) for showing or hiding specific tool
     */
    public void setAllToolsVisible(boolean allToolsVisible) {
        if (allToolsVisible) {
            setToolsInvisible();
        } else {
            setToolsVisible();
        }
    }

    /**
     * Returns whether all the tools in the toolbar are visible or not.
     *
     * @return {@code true} for visible, {@code false} if not
     */
    public boolean isAllToolsVisible() {
        Stream<Boolean> invisibleTools = Stream.of(Tool.values()).map(this::isToolVisible).filter(visible -> !visible);
        return invisibleTools.count() == 0;
    }

    /**
     * Sets the given tools visible and hides all other tools.
     *
     * @param tools the tools to set visible, not {@code null}
     * @see #setAllToolsVisible(boolean) for hiding all tools
     * @see #setToolsInvisible(Tool...) for hiding specific tools
     * @see #setToolVisible(Tool, boolean) for showing or hiding specific tool
     */
    public void setToolsVisible(Tool... tools) {
        Objects.requireNonNull(tools);

        Set<Tool> visibleTools = new HashSet<>(Arrays.asList(tools));
        Stream.of(Tool.values()).forEach(tool -> setToolVisibleInternal(tool, visibleTools.contains(tool)));
    }

    /**
     * Sets the given tools invisible and shows all other tools.
     *
     * @param tools the tools to set visible, not {@code null}
     */
    public void setToolsInvisible(Tool... tools) {
        Objects.requireNonNull(tools);

        Set<Tool> invisibleTools = new HashSet<>(Arrays.asList(tools));
        Stream.of(Tool.values()).forEach(tool -> setToolVisibleInternal(tool, !invisibleTools.contains(tool)));
    }

    /**
     * Sets the given tool visible or hidden.
     *
     * @param tool    the tool to set visible, not {@code null}
     * @param visible {@code true} to set visible, {@code false} to hide
     */
    public void setToolVisible(Tool tool, boolean visible) {
        Objects.requireNonNull(tool, "Tool cannot be null");

        setToolVisibleInternal(tool, visible);
    }

    /**
     * Returns whether the given tool is visible or hidden.
     *
     * @param tool the tool to check
     * @return {@code true} if visible, {@code false} false if not
     */
    public boolean isToolVisible(Tool tool) {
        Objects.requireNonNull(tool, "Tool cannot be null");

        return isToolVisibleInternal(tool);
    }

    private void setToolVisibleInternal(Tool tool, boolean visible) {
        final String tag = tool.name().toLowerCase();
        getElement().getChildren().filter(element -> element.getTag().endsWith(tag)).findAny().ifPresent(element -> element.setVisible(visible));
    }

    private boolean isToolVisibleInternal(Tool tool) {
        final String tag = tool.name().toLowerCase();
        return getElement().getChildren().filter(element -> element.getTag().endsWith(tag)).findAny().filter(Element::isVisible).isPresent();
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
