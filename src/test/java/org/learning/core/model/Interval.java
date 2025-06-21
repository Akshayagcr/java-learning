package org.learning.core.model;

//@Builder cannot lombok builder  on record
public record Interval(int x, int y) implements Comparable<Interval> {

    @Override
    public int compareTo(Interval i) {
        if(this.x == i.x){
            return this.y - i.y;
        }
        return this.x - i.x;
    }

}
