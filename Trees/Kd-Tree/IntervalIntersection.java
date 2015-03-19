package lineintersection;
import java.util.HashSet;
import java.util.Arrays;

public class IntervalIntersection {

    // helper class for events in sweep line algorithm
    public static class Event implements Comparable<Event> {
        int time;
        Interval1D interval;

        public Event(int time, Interval1D interval) {
            this.time     = time;
            this.interval = interval;
        }

        public int compareTo(Event b) {
            Event a = this;
            if      (a.time < b.time) return -1;
            else if (a.time > b.time) return +1;
            else                      return  0; 
        }
    }


    // the sweep-line algorithm
    public static void main(String[] args) {
        int N = 5;

        // generate N random intervals
        Interval1D[] intervals = new Interval1D[N];
        for (int i = 0; i < N; i++) {
            int left  = (int) (Math.random() * 1000);
            int right = left + (int) (Math.random() * 10);
            intervals[i] = new Interval1D(left, right);
            System.out.println(intervals[i]);
        }
        System.out.println();

        // create events
        MinPQ<Event> pq = new MinPQ<Event>();
        for (int i = 0; i < N; i++) {
            Event e1 = new Event(intervals[i].low,  intervals[i]);
            Event e2 = new Event(intervals[i].high, intervals[i]);
            pq.insert(e1);
            pq.insert(e2);
        }

        // run sweep-line algorithm
        HashSet<Interval1D> st = new HashSet<Interval1D>();
        while (!pq.isEmpty()) {
            Event e = pq.delMin();
            int time = e.time;
            Interval1D interval = e.interval;
           
            // next event is the right endpoint of interval i
            if (time == interval.high)
                st.remove(interval);

            // next event is the left endpoint of interval i
            else {
                for (Interval1D x : st) {
                    System.out.println("Intersection:  " + interval + ", " + x);
                }
                st.add(interval);
            }
        }
    }

}
