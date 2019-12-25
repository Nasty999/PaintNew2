package test.io;

import main.io.IOFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class IOFileTest {
    private IOFile ioFile;

    @Before
    public void setUp() {
        ioFile = new IOFile("C:\\Users\\aser\\Desktop\\PaintNew2\\server\\src\\test\\io\\test.txt");
    }

    @Test
    public void initUsers() {
        HashMap<String, String> users1 = ioFile.initUsers();
        HashMap<String, String> users2 = ioFile.initUsers();
        Assert.assertEquals(users1, users2);
    }

    @Test
    public void changeDataBase() {
        HashMap<String, String> users1 = ioFile.initUsers();
        users1.put("name2", "password2");
        ioFile.changeDataBase(users1);
        HashMap<String, String> users2 = ioFile.initUsers();
        Assert.assertEquals(users1, users2);
    }

    @Test
    public void registration() {
        ioFile.registration("name1", "password1");
        HashMap<String, String> users1 = ioFile.initUsers();
        Assert.assertEquals(users1.get("name1"),"password1");
    }
}