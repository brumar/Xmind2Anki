
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package progress_monitor;

import javax.swing.*;

import boutons.CreateAnkiAction;
import boutons.Xmind2AnkiFenetre;

import java.awt.Toolkit;


import Xmind2Anki.RunWorkbook;

import java.beans.*;
import java.util.Random;
 
public class ProgressMonitorDemo extends JPanel implements PropertyChangeListener {/*
                                 implements ActionListener,
                                            PropertyChangeListener {*/
 
	private Xmind2AnkiFenetre fenetre;
	private ProgressMonitor progressMonitor;
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;
    public static int advancement=5;
	public static String stateName="loading...";
	public static String dir="C://";
	public static String file="test.xmind";
	public static int nb_sheet;
	public static int current_sheet;
 
    class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
           // setProgress(0);
            try {
                Thread.sleep(100);
                while (progress < 100 && !isCancelled()) {
                    //Sleep for up to one second.
                    Thread.sleep(1000);
                    //Make random progress.
                   // progress += random.nextInt(10);
                    progress=advancement;
                    setProgress(advancement);
                }
            } catch (InterruptedException ignore) {}
            return null;
        }
 
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            //startButton.setEnabled(true);
            progressMonitor.setProgress(0);
        }
    }
 
    public ProgressMonitorDemo(Xmind2AnkiFenetre fenetre) {
    	this.fenetre = fenetre;
    	 action();
        /*super(new BorderLayout());
 
        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        //startButton.addActionListener(this);
 
        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);
 
        add(startButton, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));*/
       
    }
 
 
    /**
     * Invoked when the user presses the start button.
     */
    public void action() {
        progressMonitor = new ProgressMonitor(ProgressMonitorDemo.this,
        		"processing "+stateName+"...",
                                  "", 0, 100);
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToPopup(100);
        progressMonitor.setMaximum(100);
       
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
        //startButton.setEnabled(false);
        
        new Thread(new Runnable() {
  	      public void run() {
  	    	  
  	    	try {
				RunWorkbook.runW(dir, file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	      }
  	  }).start();
    }
 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote("processing");
            progressMonitor.setNote(message);
           // taskOutput.append(message);
            if (progressMonitor.isCanceled() || task.isDone() || progress==100 ) {
            	System.out.println("fin");
            	  
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                   // taskOutput.append("Task canceled.\n");
                } else {
                    //taskOutput.append("Task completed.\n");
                }
               // startButton.setEnabled(true);
                
                Toolkit.getDefaultToolkit().beep();
                
                progressMonitor.close();
                progressMonitor=null;
 
                
            }
        }
 
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
      //  JFrame frame = new JFrame("ProgressMonitorDemo");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        new ProgressMonitorDemo(CreateAnkiAction.fenetre);
        //JComponent newContentPane = new ProgressMonitorDemo(CreateAnkiAction.fenetre);
       // newContentPane.setOpaque(true); //content panes must be opaque
        //frame.setContentPane(newContentPane);
 
        //Display the window.
        //frame.pack();
      //  frame.setVisible(true);
    }
 
    public static void Runmonitor() {
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}