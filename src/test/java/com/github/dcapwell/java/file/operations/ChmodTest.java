package com.github.dcapwell.java.file.operations;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public final class ChmodTest {

  @Test(dataProvider = "chmods")
  public void chmodTest(Chmod chmod) {
    final File file = Files.tmpFile();
    final String name = file.getAbsolutePath();

    boolean result;

    result = chmod.chmod(file.getAbsolutePath(), 0000);
    Assert.assertTrue(result, "Returned false!");

    Assert.assertFalse(file.canExecute(), "Shouldn't be able to execute " + name);
    Assert.assertFalse(file.canRead(), "Shouldn't be able to read " + name);
    Assert.assertFalse(file.canWrite(), "Shouldn't be able to write " + name);

    result = chmod.chmod(file.getAbsolutePath(), 0777);
    Assert.assertTrue(result, "Returned false!");

    Assert.assertTrue(file.canExecute(), "Should be able to execute " + name);
    Assert.assertTrue(file.canRead(), "Should be able to read " + name);
    Assert.assertTrue(file.canWrite(), "Should be able to write " + name);
  }

  @Test(dataProvider = "chmods")
  public void chmodNonExistingFile(Chmod chmod) {
    boolean result = chmod.chmod("/file/does/not/exist", 0777);
    Assert.assertFalse(result, "Operation was successful?");
  }

  @Test
  public void toStringTest() {
    Assert.assertEquals(Chmods.toString(0000), "0000");
    Assert.assertEquals(Chmods.toString(0100), "0100");
    Assert.assertEquals(Chmods.toString(0200), "0200");
    Assert.assertEquals(Chmods.toString(0300), "0300");
    Assert.assertEquals(Chmods.toString(0400), "0400");
    Assert.assertEquals(Chmods.toString(0500), "0500");
    Assert.assertEquals(Chmods.toString(0600), "0600");
    Assert.assertEquals(Chmods.toString(0700), "0700");

    Assert.assertEquals(Chmods.toString(0110), "0110");
    Assert.assertEquals(Chmods.toString(0220), "0220");
    Assert.assertEquals(Chmods.toString(0330), "0330");
    Assert.assertEquals(Chmods.toString(0440), "0440");
    Assert.assertEquals(Chmods.toString(0550), "0550");
    Assert.assertEquals(Chmods.toString(0660), "0660");
    Assert.assertEquals(Chmods.toString(0770), "0770");
  }

  @DataProvider
  public static Iterator<Object[]> chmods() throws NoSuchMethodException, ClassNotFoundException {
    List<Object[]> mods = Lists.newArrayList(
        new Object[]{new JNAChmod()},
        new Object[]{new ForkChmod()},
        new Object[]{new ReflectChmod()},
        new Object[]{new JNRFFIChmod()}
    );
    // for java 6 support. NIO only works with java 7
    if (Chmods.isNIOSupported()) {
      mods.add(new Object[] { Chmods.createNIO() });
    }
    return mods.iterator();
  }
}
