package lineintersection;
public class Interval2D { 
    public final Interval1D intervalX;   // x-interval
    public final Interval1D intervalY;   // y-interval
   
    public Interval2D(Interval1D intervalX, Interval1D intervalY) {
        this.intervalX = intervalX;
        this.intervalY = intervalY;
    }

    // does this 2D interval a intersect b?
    public boolean intersects(Interval2D b) {
        if (!intervalX.intersects(b.intervalX)) return false;
        if (!intervalY.intersects(b.intervalY)) return false;
        return true;
    }

    // does this 2D interval contain (x, y)?
    public boolean contains(int x, int y) {
        return intervalX.contains(x) && intervalY.contains(y);
    }

    // return string representation
    public String toString() {
        return intervalX + " x " + intervalY;
    }

    public static void main(String[] args) {
        Interval1D intervalX = new Interval1D(0, 1);
        Interval1D intervalY = new Interval1D(5, 7);
        Interval2D box1 = new Interval2D(intervalX, intervalY);
        intervalX = new Interval1D(-5, 5);
        intervalY = new Interval1D(3, 8);
        Interval2D box2 = new Interval2D(intervalX, intervalY);
        
        System.out.println("box1 = " + box1);
        System.out.println("box2 = " + box2);
        System.out.println(box1.contains(1, 5));
        System.out.println(!box1.contains(2, 6));
        System.out.println(box1.intersects(box2));
        System.out.println(box2.intersects(box1));
    }
}
