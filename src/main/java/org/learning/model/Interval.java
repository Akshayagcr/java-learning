package org.learning.model;

//@Builder cannot use lombok builder  on record
public record Interval(int x, int y) implements Comparable<Interval> {

    @Override
    public int compareTo(Interval other) {
        if(this.x == other.x){
            return Integer.compare(this.y, other.y);
        }
        return Integer.compare(this.x, other.x);
    }

}
