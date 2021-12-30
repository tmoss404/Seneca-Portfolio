package ca.Time;

import java.time.Duration;
import java.time.LocalDateTime;

public class Time implements Comparable<Time>, Cloneable {
    // Day-based operations constants
    final int HOURS_IN_DAY = 24;
    final int MINUTES_IN_DAY = 1440;
    final int SECONDS_IN_DAY = 86400;

    // Hour-based operations constants
    final int MINUTES_IN_HOUR = 60;
    final int SECONDS_IN_HOUR = 3600;
    
    // Minute-based operations constants
    final int SECONDS_IN_MINUTE = 60;

    long elapsed;

    long getHour(){
        long tmp;

        // Convert elapse seconds to hours
        tmp = this.elapsed / SECONDS_IN_HOUR;

        // Remove every full day, leaving the hours in the last day
        return tmp % HOURS_IN_DAY;
    }

    long getMinute(){
        long tmp;

        // Convert the elapsed seconds to minutes
        tmp = this.elapsed / SECONDS_IN_MINUTE;

        // Remove every full day, leaving minutes in the last day
        tmp = tmp % MINUTES_IN_DAY;

        // Remove every full hour leaving any minutes below 60
        return tmp % MINUTES_IN_HOUR;
    }

    long getSecond(){
        long tmp;

        // Remove every full day, leaving seconds in the last day
        tmp = this.elapsed % SECONDS_IN_DAY;

        // Remove every full minute leaving any seconds below 60
        return tmp % SECONDS_IN_MINUTE;
    }

    long getSeconds(){
        return elapsed;
    }

    public Time(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime JAN_1970 = LocalDateTime.of(1970, 1, 1, 0, 0);
        Duration elapsed = Duration.between(JAN_1970, now);
        this.elapsed = elapsed.toSeconds();
    }

    public Time(long elapsed){
        this.elapsed = elapsed;
    }

    public Time(long hour, long minute, long second){
        this.elapsed = second;
        // Minutes to seconds
        this.elapsed += minute*SECONDS_IN_MINUTE;
        // Hours to seconds
        this.elapsed += hour*SECONDS_IN_HOUR;
    }

    @Override
    public int compareTo(Time o) {
        
        return (int)(this.elapsed - o.getSeconds());
    }

    @Override
    public String toString() {

        String s = "";

        s += this.getHour() == 1 ? this.getHour() + " hour " : this.getHour() + " hours ";
        s += this.getMinute() == 1 ? this.getMinute() + " minute " : this.getMinute() + " minutes ";
        s += this.getSecond() == 1 ? this.getSecond() + " second " : this.getSecond() + " seconds";

        return s;
    }

    public Time clone(){
        return this;
    }

}
