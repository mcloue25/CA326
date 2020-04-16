/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.Dictionary;


/**
 *
 * @author Eoin
 */
public class Classes extends Event{

    public String locations;
    public String lecturer;
    public String moduleCode;
    public String weeksActive;

    public Classes(String day, Time startTime, Time finishTime, String name, String locations, String lecturer, String moduleCode, String weeksActive){
        super(day, startTime, finishTime, name);
        
        this.locations = locations;
        this.lecturer = lecturer;
        this.moduleCode = moduleCode;
        this.weeksActive = weeksActive;
    }
    
    public String getDay(){
        return this.day;
    }
    
    public Time getClassTime(){
        return this.startTime;
    }
    
    public Time getClassFinishTime(){
        return this.finishTime;
    }
    public String getModule(){
        return this.name;
    }
    
    public String getLocations(){
        return this.locations;
    }
    
    public String getlecturer(){
        return this.lecturer;
    }
    
    public String getModCode(){
        return this.moduleCode;
    }
    
    public String toString(){
        return  this.day + "  " + this.startTime + "  " + this.finishTime + "  " + this.name + " " + this.locations + "  " + this.lecturer + "  " + "  " + this.moduleCode + "  " + this.weeksActive ;
    }
    
    
    
}
