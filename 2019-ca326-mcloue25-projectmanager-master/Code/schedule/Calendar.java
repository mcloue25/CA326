/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Eoin
 */
import java.util.*;
//import static schedule.scraper.passer;
//import static schedule.scraper.
import static schedule.scraper.passingLines;


public class Calendar{

    
    private Map<String, List<Event>> map = new HashMap<>();
    private final String [] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    // Days array to print the days in order, there is a better way to do this that I will hopefully implement later.

    public static void main(String [] args) throws IOException, KeyManagementException, NoSuchAlgorithmException{
	Calendar calendar = new Calendar();
	calendar.start();
    }
    
    public Calendar(){
        
        map.put("Mon", new ArrayList<Event>());
	map.put("Tue", new ArrayList<Event>());
	map.put("Wed", new ArrayList<Event>());
	map.put("Thu", new ArrayList<Event>());
	map.put("Fri", new ArrayList<Event>());
	map.put("Sat", new ArrayList<Event>());
	map.put("Sun", new ArrayList<Event>());
        //map.put("Mon", new ArrayList<Classes>());
    }

    public void start() throws IOException, KeyManagementException, NoSuchAlgorithmException{
        String jk = "";
        System.out.println("Welcome to your calendar! Here's your commands:");
	System.out.println("Times entered should be in 24 hour formatting and be seperated by a colon e.g (14:30)");
        //neeed to get it to run webScraping.java and scraper.java first to import a users timetable
	this.commands();
	Scanner in = new Scanner(System.in);
	String input;
	System.out.print("What would you like to do? ");
	input = in.nextLine();

	while (! input.equals("close")){
            if (input.equals("commands"))
		this.commands();
            else if (input.equals("add"))
		this.addEvent();
            else if(input.equals("import timetable")){
                webScraping webScrape = new webScraping();
                webScrape.getTimeTable();
                scraper scrape = new scraper();
                String[] lectures = scraper.passer.split("\n");
                for(String lecture : lectures){
                    addClass(lecture);
                }
            }
            else if (input.equals("add class")){
                jk = in.next();
                this.addClass(jk);
            }
            else if (input.equals("delete"))
		this.deleteEvent();
            else if (input.equals("show day"))
		this.showDay();
            else if (input.equals("show week"))
		this.showWeek();
            else if(input.equals("assembly"))
                System.out.println("FAIL");
            else{
		System.out.println("Not a valid command, enter 'commands' to see all valid commands");
            }
            System.out.print("Enter another command: ");
            input = in.nextLine();
        }
        this.close();
    }

    public void deleteEvent(){
        String day = takeDay();
        List<Event> currrentDay = this.map.get(day);

        if (currrentDay.size() == 0){
            System.out.println("No appointments for that day");
            return;
        }

	int i = 1;                                  
	for (Event lecture : currrentDay){
            System.out.println(i + ": " + lecture);
            i++;
	}

	System.out.print("Select the number of the appointment you wish to delete: ");
	Scanner in = new Scanner(System.in);
	int toRemove = in.nextInt();
	toRemove--;
	currrentDay.remove(toRemove);
	System.out.println("Appointment removed");
    }

    public void showDay(String dayName){
            
        System.out.println("\n" + dayName);
        //System.out.println(dayName);
        System.out.println(new String(new char[40]).replace("\0", "-"));
        System.out.println("No.Day  " + "S-time " + "F-Time"); // + " FinishTime");
        int i = 1;
        List<Event> events = map.get(dayName);
                
        for (Event lecture : events){
            System.out.println(i + ": " + lecture);
            i++;
        }
        System.out.println(new String(new char[40]).replace("\0", "-"));
    }

    public void showDay(){
            
        String day = this.takeDay();
        System.out.println("\n" + day);
        System.out.println(new String(new char[40]).replace("\0", "-"));
        System.out.println("No.Day  " + "S-time " + "F-Time"); // + " FinishTime");
        List<Event> events = map.get(day);
        for (Event event : events){
            System.out.println(event);
        }
        System.out.println(new String(new char[40]).replace("\0", "-"));
    }

    public void showWeek(){
        System.out.println("\n" + "This week you have the following:");
        for (String day : this.days){
            showDay(day);
        }
    }

    public void addClass(String arg){
        //arg = arg.trim();                 //need to remove whitespace
        //System.out.println(arg);
        //ArrayList<String> lines =new ArrayList<String>();
        
        String [] lines = arg.split("/");
        //System.out.println(lines.length);
                                            //getting index out of bounds error, to do with number of attributes events has vs how many classes has. 4 vs 8
        String dayName = lines[0];
        Time startTime = new Time(lines[1]);
        Time finishTime = new Time(lines[2]);
        String moduleName = lines[3];
        String locations = lines[4];        //indexing the correct elements of the incoming class tuple
        String lecturer = lines[5];
        String moduleCode = lines[6];
        String weeksActive = lines[7];
        //System.out.println(locations);
        Classes newClass = new Classes(dayName, startTime, finishTime, moduleName, locations, lecturer, moduleCode, weeksActive);   //creates new instace of a lecture 
        //System.out.println(newClass);       //just to test if it works        it does
        this.addClassEvent(newClass); //pass to addClassAppointment to add it to the events list
    }
    
    public void addClassEvent(Classes lecture){
        String dayName = lecture.getDay();
        Time startTime = lecture.getStartTime();
        Time finishTime = lecture.getClassFinishTime();      //gets all relevant details of a classes instance to be passed to arrayList of type event
        String name = lecture.getModule();
         //try use events get day time etc see how that works 
        String locations = lecture.getLocations();
        String lecturer = lecture.getlecturer();
        String modCode = lecture.getModCode();
        //boolean active = lecture.isActive(); 
        //System.out.println(dayName + " " + startTime + "  " + finishTime + "  " + name + " " + locations + "  " + lecturer + "  " + modCode + "  getting this far");
        List<Event> currentDay = this.map.get(dayName);         //gets the map according to the day name
        currentDay.add(lecture);            //wont add subclass object to arrayList of superclass
    }
    
    public void addEvent(){
        String dayName = this.takeDay();
         System.out.print("When will your appointment start? (hour:mins): ");
        Time startTime = this.readTime();
        String name = this.readName();
        System.out.print("When will your appointment end? (hour:mins): ");
        Time finishTime = this.readTime();
        Event event = new Event(dayName, startTime, finishTime, name); //endTime);
        this.addEventAppointment(event);	//psses instance of event to be addded to the correct arryList according tot the dayName	
    }

    public void addEventAppointment(Event event){
        String dayName = event.getDay();
        Time startTime = event.getStartTime();
        String name = event.getName();
	Time finishTime = event.getFinishTime();

	List<Event> events = this.map.get(dayName);	// events is the list of all events for the user entered day.
        System.out.println(dayName);
	if(! (contains(this.days, dayName))){
            System.out.println("Not a valid day");
            return;
	}
                
	if((! startTime.validTime())){ // && (! finishTime.validTime())){
            System.out.println("Entered times are not valid");
            return;
	}
                
	else{
            events.add(event);
            System.out.println("Added event");
            
        }
    }
    
    private boolean contains(String [] array, String element){
        for (String s : array){
            if (s.equals(element)){
                return true;
            }
        }

        return false;	
    }

    public String takeDay(){
        Scanner in = new Scanner(System.in);
        System.out.print("Which day? ");
        String dayName = in.next();
            
        if (! contains(this.days, dayName)){
            System.out.println("Not a valid day");
        }
            
        else{ 
            return dayName;
        }
        return "";
    }
	
    public Time readTime(){
        Scanner in = new Scanner(System.in);
        String s = in.next(); 									// read in a string
        Time time = new Time(s);								// make it a Time object
            
        if (! time.validTime()){
            System.out.println("Entered time is not valid");
            System.out.print("Please enter a valid time: ");
            return this.readTime();
        }
        else{
            return time;
        }
    }
    
    public String readName(){
        Scanner in = new Scanner(System.in);
        System.out.print("Appointment name: ");
        String name = in.next();	
        return name;		
    }

    public void commands(){
        System.out.println("\n'close'          : close the calendar");
        System.out.println("'import timetable: imports your student timetable to your calendar");
        System.out.println("'add'            : add an appointment for your calendar");
        System.out.println("'delete'         : delete an appointment from you calendar");
        System.out.println("'show week'      : display your week and all your appointments");
        System.out.println(" 'show day'      : display your appointments for a specific day");
        System.out.println("'commands'       : see these commands again\n");
    }

    public void close(){
        System.out.println("Closing calendar");
    }
   //this.addClass("Mon/12:00/GLA.LG25, GLA.LG26, GLA.LG27/Sutherland A/Probability & Statistics/CA266[2] P1/20-30"); 
}
