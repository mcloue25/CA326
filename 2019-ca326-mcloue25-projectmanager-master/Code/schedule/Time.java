/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

/**
 *
 * @author Eoin
 */
public class Time {
    private int hour;
    private int mins = 00; 

    public Time(String input){
	// input is something of the form hour:mins e.g (12:30)
	// need to split it and set the class attributes equal to it's parts
	String [] times = input.split(":");
	hour = Integer.parseInt(times[0]);
	mins = Integer.parseInt(times[1]);
	this.hour = hour;
	this.mins = mins;
    }

    public boolean validTime(){
	return (this.hour < 24 && this.hour >= 0 && this.mins < 60 && this.mins >= 0);
    }

    public int getHour(){
	return this.hour;
    }

    public int getMinute(){
	return this.mins;
    }

    public boolean isBefore(Time other){
	//return (this.hour <= other.hour) && (this.mins <= other.mins);
        if (this.hour < other.hour)
            return true;
	else if (this.hour > other.hour)
            return false;
	else{
            if (this.mins <= other.mins)
		return true;
            else
		return false;
            }
    }

    public boolean isAfter(Time other){
        return (! this.isBefore(other));
    }

    public String toString(){
	return this.hour + ":" + this.mins;
    }

}


