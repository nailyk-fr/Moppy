#include <TimerOne.h>

boolean firstRun = true; // Used for one-run-only stuffs;

//First pin being used for floppies, and the last pin.  Used for looping over all pins.
const byte FIRST_PIN = 2;
const byte PIN_MAX = 17;
#define RESOLUTION 40 //Microsecond resolution for notes
#define SECUREPOS 10 //Don't reach end of motor steps


/*NOTE: Many of the arrays below contain unused indexes.  This is 
 to prevent the Arduino from having to convert a pin input to an alternate
 array index and save as many cycles as possible.  In other words information 
 for pin 2 will be stored in index 2, and information for pin 4 will be 
 stored in index 4.*/


/*An array of maximum track positions for each step-control pin.  Even pins
 are used for control, so only even numbers need a value here.  3.5" Floppies have
 80 tracks, 5.25" have 50.  These should be doubled, because each tick is now
 half a position (use 158 and 98).
 */
byte MAX_POSITION[] = {
  0,0,158,0,158,0,158,0,158,0,158,0,158,0,158,0,158,0};

//Array to track the current position of each floppy head.  (Only even indexes (i.e. 2,4,6...) are used)
byte currentPosition[] = {
  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

//Current period assigned to each pin.  0 = off.  Each period is of the length specified by the RESOLUTION
//variable above.  i.e. A period of 10 is (RESOLUTION x 10) microseconds long.
unsigned int currentPeriod[] = {
  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
};

//Current tick
unsigned int currentTick[] = {
  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 
};



//Setup pins (Even-odd pairs for step control and direction
void setup(){
  for (int p=FIRST_PIN;p<PIN_MAX;p++){ //Half max because we're stepping directly (no toggle)
    pinMode(p, OUTPUT);
  }

  //With all pins setup, let's do a first run reset
  resetAll();
  delay(1000);
	
  Timer1.initialize(RESOLUTION); // Set up a timer at the defined resolution
  Timer1.attachInterrupt(tick); // Attach the tick function

  Serial.begin(9600);
}

void loop(){
  //Only read if we have 3 bytes waiting
  if (Serial.available() > 2){
    //Watch for special 100-message to act on
    if (Serial.peek() == 100) {
      //Clear the peeked 100 byte so we can get the following data packet
      Serial.read();
      byte byte2 = Serial.read();
      
      //This isn't used for anything right now, always set to 0
      //byte byte3 = Serial.read();
      
      switch(byte2) {
        case 0: resetAll(); break;
        case 1: break;  //Connected
        case 2: break;  //Disconnected
        case 3: break;  //Sequence starting
        case 4: break;  //Sequence stopping
        default: resetAll(); break;
      }      
      //Flush any remaining messages.
      while(Serial.available() > 0) { Serial.read(); }
    } 
    else{
      currentPeriod[Serial.read()] = (Serial.read() << 8) | Serial.read();
    }
  }
}


/*
Called by the timer interrupt at the specified resolution.
 */
void tick()
{
  /* 
   If there is a period set for control pin 2, count the number of
   ticks that pass, and toggle the pin if the current period is reached.
   */

  for (int x=FIRST_PIN;x<PIN_MAX;x+=2){ //Half max because we're stepping directly (no toggle)
    if (currentPeriod[x]>0){
      currentTick[x]++;
      if (currentTick[x] >= currentPeriod[x]){
        togglePin(x,x+1);
        currentTick[x]=0;
      }
    }
  }

}

void togglePin(byte pin, byte direction_pin) {

  //Switch directions if end has been reached
  if (currentPosition[pin] >= (MAX_POSITION[pin])-SECUREPOS) {
    digitalWrite(direction_pin,HIGH);
  } 
  else if (currentPosition[pin]+SECUREPOS <= 0) {
    digitalWrite(direction_pin,LOW);
  }

  //Update currentPosition
  if (digitalRead(direction_pin) == HIGH){
    currentPosition[pin]--;
  } 
  else {
    currentPosition[pin]++;
  }

  // currentPosition[pin] += (-1)*((-1)*digitalRead(direction_pin));  //don't use this! Will probably need a cast for negative value. 

  //Pulse the control pin
  digitalWrite(pin,!digitalRead(pin));
}


//
//// UTILITY FUNCTIONS
//

//Not used now, but good for debugging...
void blinkLED(){
  digitalWrite(13, HIGH); // set the LED on
  delay(250);              // wait for a second
  digitalWrite(13, LOW); 
}

//Resets all the pins
void resetAll(){

  for (byte p=FIRST_PIN;p<=PIN_MAX;p+=2){
    for(byte s=0; s<=currentPosition[p] ; s++) {
      digitalWrite(p+1,HIGH); // Go in reverse
      digitalWrite(p,HIGH);
      delay(2); // give some time for pulse output
      digitalWrite(p,LOW);
    }
    currentPosition[p] = 0; // We're reset.
    currentPeriod[p] = 0; // Stop playing notes
    digitalWrite(p+1,LOW); // Can go forward now. 
  }

}
