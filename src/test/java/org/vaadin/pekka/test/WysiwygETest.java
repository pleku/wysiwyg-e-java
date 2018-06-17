package org.vaadin.pekka.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vaadin.pekka.WysiwygE;

import java.util.stream.Stream;

public class WysiwygETest {

    private WysiwygE wysiwygE;

    @Before
    public void setup() {
        wysiwygE = new WysiwygE();
    }

    @Test
    public void testSetToolVisible() {
        Stream.of(WysiwygE.Tool.values()).forEach(tool -> Assert.assertTrue(wysiwygE.isToolVisible(tool)));

        Assert.assertTrue(wysiwygE.isAllToolsVisible());

        wysiwygE.setToolVisible(WysiwygE.Tool.BOLD, false);

        Assert.assertFalse(wysiwygE.isToolVisible(WysiwygE.Tool.BOLD));
        Assert.assertFalse(wysiwygE.isAllToolsVisible());

        wysiwygE.setToolVisible(WysiwygE.Tool.BOLD, true);

        Assert.assertTrue(wysiwygE.isToolVisible(WysiwygE.Tool.BOLD));
    }

    @Test
    public void testSetToolsVisible() {
        Stream.of(WysiwygE.Tool.values()).forEach(tool -> Assert.assertTrue(wysiwygE.isToolVisible(tool)));

        wysiwygE.setToolsVisible(WysiwygE.Tool.AUDIO, WysiwygE.Tool.LINK);

        Assert.assertTrue(wysiwygE.isToolVisible(WysiwygE.Tool.AUDIO));
        Assert.assertTrue(wysiwygE.isToolVisible(WysiwygE.Tool.LINK));
        Assert.assertFalse(wysiwygE.isAllToolsVisible());

        Stream.of(WysiwygE.Tool.values()).filter(tool -> tool != WysiwygE.Tool.AUDIO).filter(tool -> tool != WysiwygE.Tool.LINK).forEach(tool -> Assert.assertFalse("Tool " + tool + " should not be visible", wysiwygE.isToolVisible(tool)));

        wysiwygE.setToolsVisible(WysiwygE.Tool.VIDEO, WysiwygE.Tool.CODE);

        Assert.assertTrue(wysiwygE.isToolVisible(WysiwygE.Tool.VIDEO));
        Assert.assertTrue(wysiwygE.isToolVisible(WysiwygE.Tool.CODE));
        Assert.assertFalse(wysiwygE.isAllToolsVisible());

        Stream.of(WysiwygE.Tool.values()).filter(tool -> tool != WysiwygE.Tool.VIDEO).filter(tool -> tool != WysiwygE.Tool.CODE).forEach(tool -> Assert.assertFalse("Tool " + tool + " should not be visible", wysiwygE.isToolVisible(tool)));
    }

    @Test
    public void testSetToolsInvisible() {
        Stream.of(WysiwygE.Tool.values()).forEach(tool -> Assert.assertTrue(wysiwygE.isToolVisible(tool)));

        wysiwygE.setToolsInvisible(WysiwygE.Tool.VIDEO, WysiwygE.Tool.CODE);

        Assert.assertFalse(wysiwygE.isToolVisible(WysiwygE.Tool.VIDEO));
        Assert.assertFalse(wysiwygE.isToolVisible(WysiwygE.Tool.CODE));
        Assert.assertFalse(wysiwygE.isAllToolsVisible());

        Stream.of(WysiwygE.Tool.values()).filter(tool -> tool != WysiwygE.Tool.VIDEO).filter(tool -> tool != WysiwygE.Tool.CODE).forEach(tool -> Assert.assertTrue("Tool " + tool + " should be visible", wysiwygE.isToolVisible(tool)));

        wysiwygE.setToolsInvisible(WysiwygE.Tool.AUDIO, WysiwygE.Tool.LINK);

        Assert.assertFalse(wysiwygE.isToolVisible(WysiwygE.Tool.AUDIO));
        Assert.assertFalse(wysiwygE.isToolVisible(WysiwygE.Tool.LINK));
        Assert.assertFalse(wysiwygE.isAllToolsVisible());

        Stream.of(WysiwygE.Tool.values()).filter(tool -> tool != WysiwygE.Tool.AUDIO).filter(tool -> tool != WysiwygE.Tool.LINK).forEach(tool -> Assert.assertTrue("Tool " + tool + " should be visible", wysiwygE.isToolVisible(tool)));
    }

    @Test
    public void testSetToolsInvisible_setAllInvisible_allInvisible() {
        wysiwygE.setToolsInvisible(WysiwygE.Tool.AUDIO, WysiwygE.Tool.LINK);

        Assert.assertFalse(wysiwygE.isToolVisible(WysiwygE.Tool.AUDIO));
        Assert.assertFalse(wysiwygE.isToolVisible(WysiwygE.Tool.LINK));
        Assert.assertFalse(wysiwygE.isAllToolsVisible());

        Stream.of(WysiwygE.Tool.values()).filter(tool -> tool != WysiwygE.Tool.AUDIO).filter(tool -> tool != WysiwygE.Tool.LINK).forEach(tool -> Assert.assertTrue("Tool " + tool + " should be visible", wysiwygE.isToolVisible(tool)));

        wysiwygE.setAllToolsVisible(false);

        Stream.of(WysiwygE.Tool.values()).forEach(tool -> Assert.assertFalse("Tool "+ tool + " should not be visible", wysiwygE.isToolVisible(tool)));
    }

}
