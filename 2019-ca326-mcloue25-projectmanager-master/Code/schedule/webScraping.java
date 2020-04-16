/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;



/**
 *
 * @author Eoin
 */
public class webScraping {
    
    //help for bypassing SSL certificate == https://nakov.com/blog/2009/07/16/disable-certificate-validation-in-java-ssl-connections/

    /**
     *
     * @throws IOException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    public void getTimeTable() throws IOException, KeyManagementException, NoSuchAlgorithmException{
        //main(String[] args) throws Exception {
        try{
        // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };
 
        // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
        // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
 
        // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        
        // Get users program schedule
            Scanner in = new Scanner(System.in);
            System.out.print("Enter course code: "); //gets course code
            String program = in.next();
            System.out.print("Enter academic year: ");  // gets academic year
            int year = in.nextInt();
            String code = ("https://www101.dcu.ie/timetables/feed.php?prog=" + program + "&per=" + year + "&week1=19&week2=30&hour=1-20&template=student");
    
    
            URL url = new URL(code); // establishes  fully integrated variable code as a url
            URLConnection con = url.openConnection(); //opens connection to the website url
            Reader reader = new InputStreamReader(con.getInputStream()); //gets input stream of url
        
        
            File timeTableFile = new File("C:/Users/Eoin/tableFile.txt");
            if(timeTableFile.exists()){
            //System.out.println("the file exists");
            }
            else{
                timeTableFile.createNewFile();
            }
        
            FileWriter filew = new FileWriter(timeTableFile);
            try (BufferedWriter writer = new BufferedWriter(filew)) {
                while (true) {
                    int ch = reader.read();
                    if (ch==-1) {
                        break;
                    }
                    else{
                        char text = (char)ch;
                        try {
                            writer.write(text);
                        }
                        catch(IOException ex){
                            System.out.println("problem writing to file");
                        } 
                    }
                }
            }
        //System.out.println("file written");
            scraper.scrape(timeTableFile);                      //need to figure out how to intgrate the two files together 
            FileReader filer = new FileReader(timeTableFile);
            BufferedReader breader = new BufferedReader(filer);
        }
        catch(IOException ex){
            System.out.println("Problem dtected");
        }
    }
}

