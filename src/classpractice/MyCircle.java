
package classpractice;

public class MyCircle {

    private double radius = 20;

    public MyCircle() {
    }

    public MyCircle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius > 0) {
            this.radius = radius;
        }
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getCircumference() {
        return 2 * Math.PI * radius;
    }

    public String toString() {
        return "Circle with radius " + radius;
    }
}
