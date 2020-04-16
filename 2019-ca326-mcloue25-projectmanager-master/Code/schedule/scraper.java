/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author Eoin
 */

public class scraper{
static String passer = "";
static String [] passingLines;   
    /**
     *
     * @param timeTableFile
     * @return
     */
    //public static String passer;

    public static void scrape(File timeTableFile){
        // TODO code application logic here
        File input = timeTableFile;
        Document doc = null;
        try{
            doc = Jsoup.parse(input, "UTF-8", "");
            Elements table = doc.select("table:nth-of-type(2)"); //get table number 6 for table needed
            Elements tbody = table.select("tbody");
            Elements rows = tbody.select("tr");
            Elements timeRow = tbody.select("tr:nth-of-type(1)");
            String previous = "";
            //try{
            
            for (int i = 1; i < 11; i++) { //  
                //System.out.println(rows.size());  prints 70, coounts inner and outer tr's
                String row = "tr:nth-of-type(" + i + ")";
                Elements currentRow = rows.select(row);
                
                Elements cols = currentRow.select("td");
                Element dayName = cols.get(0);
                String strDayName = dayName.text();
                
                String[] daysOfWeek = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"}; //list to check day name 
                List<String> list = Arrays.asList(daysOfWeek);
                int timeCounter = 2;       // no class starts at 8
                
                if(!list.contains(strDayName)){     //for rows after the first one that dont have a day name as col(0)
                    strDayName = previous;
                    timeCounter+=1;                 //move starting position up 1 to fix no day name problem
                }
                
                for(int j = 0; j < cols.size(); j+=1){              //goes through every column of a line
                    String column = "td:nth-of-type(" + j + ")";
                    String tds = "";
                    Elements currentColumn = cols.select(column);   //current column being looked at

                    if(currentColumn.text().equals("")){        //to account for empty slots of the table
                        timeCounter+=1;                     
                        //continue; 
                    }
                    else{
                        Elements classTime = timeRow.select("td:nth-of-type(" + timeCounter + ")"); // index to timeRow[ctimeCounter] for correct time 
                        String strtimes = classTime.text();
                        Elements tables = currentColumn.select("table"); // to get the inner tables of a cell
                        
                        if(currentColumn.hasAttr("colspan")){         //get length of colspan to add to timeCounter 
                            int colspan = Integer.parseInt(currentColumn.attr("colspan"));   //convert from in to string
                            timeCounter += colspan;       
                        }
                        Elements finishTime = timeRow.select("td:nth-of-type(" + timeCounter + ")"); // finish time = timerow[timeCounter + colspan]
                        String ft = finishTime.text();
                        
                        if(ft.equals("")){
                            ft = "18:00";
                        }
                        for (Element cell : tables) {       //to disect inner tables
                            Elements innertables = tables.select("table");
                            Elements innertbody = innertables.select("tbody");
                            Elements tr = innertbody.select("tr");
                            Element location = tr.select("td").get(0);
                            Element lecturer = tr.select("td").get(1);
                            Element  Module = tr.select("td").get(2);
                            Element moduleCode = tr.select("td").get(3);
                            Element runningWeeks = tr.select("td").get(4);
                            
                            tds = strtimes + "/" + ft + "/" + Module.text() + "/" + location.text() + "/" + lecturer.text() + "/" + moduleCode.text() + "/" + runningWeeks.text(); // string to pass each instance of a class as
                        }
                    }
                    if("".equals(tds)){
                        continue; 
                    }
                    else{
                        if(list.contains(dayName.text())){  //if current rows day name is a dy of the week
                            previous = dayName.text();      // to account for the second row of a day
                        }
                        
                        String text = dayName.text() + "/" + tds + "\n";        //full string that will be passed
                        passingLines = text.split("\n");
                        
                        for(int a = 0; a < passingLines.length; a++){       //list of all class instance strings that just need to be checked to make sure theyre correct
                            String currentLine = passingLines[a];
                            String dayTitle = currentLine.substring(0,3);  //gets name of the day
                            if(list.contains(dayTitle)) {    //if name of the day == a day in days of week
                                continue;
                                //currentLine= currentLine.toLowerCase();
                                //System.out.println(currentLine);
                                //addclass(currentLine);
                            } 
                            else {
                                if(!list.contains(dayTitle)){
                                    //String finished = strDayName + currentLine;
                                    String newLine = strDayName + currentLine;          // add a correct day name to this line that doesnt have a day name 
                                    passingLines[a] = newLine;
                                    //System.out.println(currentLine);
                                }    
                            }
                            
                        }
                        
                    }
                    
                    
                adder(passingLines);        // pass to adder
                         
                }
                
            // times are correect but look at the wrong td, class slot td(13) != timeRow td(13) fixed                                                                    
            }                       // how to integrate this code with the code to get around bad ssl certificate       fixed 
            //System.out.println(Arrays.toString(passingLines));
                                    // get correct number of rows 
        }                            // pass String [] lines file and pass to calendar line by line
        catch(IOException ex){
            System.out.println("cant find it");
        }   
    //return "";
    }
    
    
    public static String adder(String [] newString){    //add this line from the list to the string adder
        for(String line : newString){
            //System.out.println(line);
            passer+= line + "\n";
        }
        //System.out.print(passer);
        
        return passer;      //returns passer to calendar to create instances of classes 
    }  
}
//for loop to go though all data

