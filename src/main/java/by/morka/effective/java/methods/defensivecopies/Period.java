package by.morka.effective.java.methods.defensivecopies;

import java.util.Date;

// Broken "immutable" time period class
public final class Period {
    private final Date start;
    private final Date end;

    /**
     * @param  start the beginning of the period
     * @param  end the end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start or end is null
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(
                    start + " after " + end);
        this.start = start;
        this.end   = end;
    }

    public Date start() {
        return start;
    }
    public Date end() {
        return end;
    }

    public String toString() {
        return start + " - " + end;
    }

//    // Repaired constructor - makes defensive copies of parameters
//    public Period(Date start, Date end) {
//        this.start = new Date(start.getTime());
//        this.end   = new Date(end.getTime());
//
    // PROTECTION AGAINST time-of-check time-of-use attack
//        if (this.start.compareTo(this.end) > 0)
//            throw new IllegalArgumentException(
//                    this.start + " after " + this.end);
//    }
//
//    // Repaired accessors - make defensive copies of internal fields
//    public Date start() {
//        return new Date(start.getTime());
//    }
//
//    public Date end() {
//        return new Date(end.getTime());
//    }
}
