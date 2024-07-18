package Support;

import java.awt.*;

public class MouseMoving implements Runnable{
    private static Robot robot;
    private static Integer coordinateX;
    private static Integer coordinateY;

    public MouseMoving() throws AWTException {
        robot = new Robot();
    }

    public static void main(String[] args) throws InterruptedException, AWTException {
        MouseMoving mouseMoving = new MouseMoving();
        mouseMoving.run();
    }

    public void run() {
        coordinateX = 600;
        coordinateY = 200;

        long counter = 0;
        while (counter <= 1000) {
            if (coordinateX < 1200 & coordinateY == 200) {
                robot.mouseMove(coordinateX, coordinateY);
                coordinateX += 20;
            }
            if (coordinateX == 1200 & coordinateY < 400) {
                robot.mouseMove(coordinateX, coordinateY);
                coordinateY += 20;
            }
            if (coordinateX > 600 & coordinateY == 400) {
                robot.mouseMove(coordinateX, coordinateY);
                coordinateX -= 20;
            }
            if (coordinateX == 600 & coordinateY > 200) {
                robot.mouseMove(coordinateX, coordinateY);
                coordinateY -= 20;
                counter += 1;
            }
        }
    }
}
