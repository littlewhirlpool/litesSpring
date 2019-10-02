package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomBooleanEditor;

/**
 * @program: litespring->CustomBooleanEditor
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-02 09:43
 **/
public class CustomBooleanEditorTest {

    @Test
    public void testConverStringToBoolean(){
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true , ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false , ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true , ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false , ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true , ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false , ((Boolean)editor.getValue()).booleanValue());

        try {
            editor.setAsText("addf");
        }catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();
    }
}
