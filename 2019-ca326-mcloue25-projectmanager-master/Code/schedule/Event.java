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
public class Event {
    
    public String day;
    public Time startTime;
    public Time finishTime;
    public String name;
    

    public Event(String day, Time startTime, Time finishTime, String name) { //Time finishTime)
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.name = name;
        
    }
    
    public String getDay(){
	return this.day;
    }

    public Time getStartTime(){
	return this.startTime;
    }
    
    public String getName(){
	return this.name;
    }
    public Time getFinishTime(){
        return this.finishTime;
    }

    public String toString(){
	return "(" + this.name + " " + this.startTime + " " + this.finishTime + " " + ")";     // + "heres the error";
    }

    public boolean equals(Event another){
	return (this.name == (another.name)) && (this.startTime == (another.startTime)) && (this.finishTime == (another.finishTime));
    }

}
    

