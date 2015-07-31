package frames;

/*
 * TimerLabel.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * A label that will display and update the time.
 *
 * @author        Paul Tymann (ptt@cs.rit.edu)
 */

public class TimerLabel extends JLabel implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 232975711657643119L;
	private int hrs;          // Hours
    private int mins;         // Minutes
    private int secs;         // Seconds
    private int hsecs;        // Hundreds of seconds

    private String nums[];    // An array that will hold the string
                              // representation of the numbers from 0
                              // to 99.  It will be used to quickly convert
                              // numbers to strings.

    private boolean running;  // True if time should be advancing
    private boolean update;   // True if the display should be updated

    private Timer alarm;      // The timer that will be used to determine
                              // when a hundredth of a second has passed

    /**
     * Create a new timer label that contains a time of 0.
     */
    
    public TimerLabel() {
	super( "00:00:00.00", JLabel.CENTER );

	// Create the array that will be used to convert ints to strings.
	// This is done so that the conversion can be done quickly while
	// time is being recorded.  The conversion needs to be quick to
	// minimized the drift exhibited by the label.

	nums = new String[ 100 ];
	for ( int i = 0; i < 100; i++ ) {
	    nums[ i ]= Integer.toString( i );

	    // Leading zeros in front of single digit numbers

	    if ( i < 10 ) nums[ i ] = "0" + nums[ i ];
	}

	// Create the timer and set it to go off in 10 milliseconds
	// (one hundredth of a second)

	alarm = new Timer( 10, this );
	alarm.start();

	// Reset the state of the label

	timerReset();
    }

    /**
     * The timer has gone off.  Update the state of the label and reset
     * the timer.
     *
     * @param ev the event that caused this listener to become active
     */
    
    public void actionPerformed( ActionEvent ev ) {

	// If the label is running, update the time.  The ugly if statement
	// is used in lieu of mod to avoid the cost of doing division.

	if (running) {
	    hsecs++;

	    if (hsecs==100) {
		hsecs=0;
		secs++;

		if (secs==60) {
		    secs=0;
		    mins++;

		    if (mins==60) {
			mins=0;
			hrs=(hrs+1)%100;
		    }
		}
	    }

	    if (update) updateText();
	}

	alarm.restart();
    }

    /**
     * Reset the state of the timer.
     */
    
    public void timerReset() {
	// Stop the timer

	alarm.stop();

	// Set the text back to 0

	hrs=mins=secs=hsecs=0;
	updateText();

	// Reset the label's logic

	running=false;
	update=true;

	// Restart the timer

	alarm.restart();
    }

    /**
     * Start the timer.  The label will now show the passage of time.
     */
    
    public void timerStart() {
	running = true;
	update = true;
    }

    /**
     * Stop the timer.  The label will not show the passage of time.
     */
    
    public void timerStop() {
	running = false;
	update = true;
    }

    /**
     * Freeze the display.  Time will continue to advance but the label will
     * no longer be updated.  Calling start() will "unfreeze" the label.
     */
    
    public void timerNoUpdate() {
	update = false;
    }

    /**
     * Update the label so that it reflects the actual time being mainted
     * by the timer.
     */
    
    public void updateText() {
	setText( "Время: " + nums[ hrs ]+ ":" + 
		 nums[ mins ] + ":" +
		 nums[ secs ]  );
    }
  
    

	
  

} // TimerLabel
