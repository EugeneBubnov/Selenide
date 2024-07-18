package Support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.ByteArrayInputStream;

import static io.qameta.allure.Allure.addAttachment;

public class TestExention implements TestWatcher {
    ByteArrayInputStream screenStream;

    public void setScreenStream(ByteArrayInputStream screenStream) {
        this.screenStream = screenStream;
    }

    public void testFailed(ExtensionContext context, Throwable cause) {
        addAttachment("Скриншот перед закрытием браузера", screenStream);
        System.out.println("Test failed.");
    }
}