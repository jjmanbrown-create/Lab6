
package classpractice;

public class MyRectangle {

    private double width = 50;
    private double height = 40;

    public MyRectangle() {
    }

    public MyRectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public void setHeight(double height) {
        if (height > 0) {
            this.height = height;
        }
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }

    public String toString() {
        return "Rectangle with width " + width + " and height " + height;
    }
}
