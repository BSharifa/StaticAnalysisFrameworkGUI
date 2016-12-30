package easeStaticAnalysis;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class FunctionalitiesofUserfriendlyAmandroid {
	
	private String t;
	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private final static String newline = "\n";
	 public FunctionalitiesofUserfriendlyAmandroid()
	    {
		 
		 t="Open Apk Files: This button allows user to take input(apk) files from any directory. The selected apk files shows up in left side of the main window. User can add more than one file in that area(jlist) and select one of them for further analysis."
				+"\n"+"Run Amandroid: User gets an idea about the available plug-ins in amandroid."
				+"\n"+"Taint Analysis: Highlights specific security risks such as Leak paths. It attempts to identify tainted variable. A variable is vulnerable if it gets passes to a sink."
				+"\n" +"Decompile: Convert executable program code into some form of higher-level language.Different reasons for De-compilation such as understanding a program, recovering the source code for purposes of archiving or updatingfinding viruses, debugging programs."
				+"\n" +"Crypto Misuse: Misuse a library API in an improper way.For example using AES cipher in ECB mode at encryption is not secure."
				+"\n";
		 t1 ="Bug Report: Allows user to report bug in amandoird."
				 +"\n" +"Search: User can search for a particular word in Amandroid report."+"\n";
		 t2 ="File Menu: User can open analysis resutls in a jtree manner in right side of the window and User can open each of those file by clicking on it in differnet tab."+"\n"+"File menu: has a menuItem to close the window."+"\n";
		 t3 = "Run menu: It has Run Amandroid, Taint Analysis, Decompile, Crypto Misuse and Gengraph option. These all are Amandroid Plug-ins."+"\n";
		 t4 = "Help: It redirects user to Amandroid's webpage to provides installation instruction and learn more about it.";
		 JFrame frame = new JFrame("About Amandroid GUI");
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    JTextArea panel = new  JTextArea();
		    panel.setFont(new Font("Verdana", Font.PLAIN, 14)); 
	         
		    panel.setWrapStyleWord(true);
		    panel.setLineWrap(true);
		    panel.append(t+newline+t1+newline+t2+newline+t3+newline+t4);
		  
		    panel.setEditable(false);
		    
		   JScrollPane p = new JScrollPane(panel);

		    frame.getContentPane().add(BorderLayout.CENTER, p);
		   //frame.pack();
		   frame.setLocationByPlatform(true);
		    frame.setSize(500, 500);
		    frame.setVisible(true);
		    
		   

	    }
   
	

}
