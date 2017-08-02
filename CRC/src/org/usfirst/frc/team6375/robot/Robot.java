package org.usfirst.frc.team6375.robot;


import com.ctre.CANTalon;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	/* talons for arcade drive */
	CANTalon RIGHTMOTOR1 = new CANTalon(13); 		/* device IDs here (1 of 2) */
	CANTalon RIGHTMOTOR2 = new CANTalon(12);
	CANTalon RIGHTMOTOR3 = new CANTalon(14);
	CANTalon LEFTMOTOR1 = new CANTalon(17);
	CANTalon LEFTMOTOR2 = new CANTalon(11);
	CANTalon LEFTMOTOR3 = new CANTalon(15);
	CANTalon COLLECTOR = new CANTalon(31);
	CANTalon VERTICALTRANSPORT = new CANTalon(32);
	CANTalon HORIZONTALTRANSPORT = new CANTalon(21);
	CANTalon LEFTCLIMBER = new CANTalon(34);
	CANTalon RIGHTCLIMBER = new CANTalon(35);
	CANTalon COLLECTGEAR = new CANTalon(36);
	CANTalon LIFT = new CANTalon(22);
	CANTalon SHOTER1 = new CANTalon(25);
	CANTalon SHOTER2 = new CANTalon(24);
	CANTalon SHOTER3 = new CANTalon(23);
	  
	
	public ADXRS450_Gyro g1 = new ADXRS450_Gyro();
	//public Encoder Left_Encoder = new Encoder(4,5, false, Encoder.EncodingType.k4X);
	//public Encoder Right_Encoder = new Encoder(2,3, true, Encoder.EncodingType.k4X);
	
	Gamepad _xbox1=new Gamepad(1);
	Joystick _Joy=new Joystick(0);
	AnalogInput a1 = new AnalogInput(0);
	AnalogInput a2 = new AnalogInput(4);
	Timer timer = new Timer();
	
	
	/*public PIDOutput leftdrive;
	public PIDOutput Rightdrive;
	
	PIDController leftPID=new PIDController(0.1,0,0 , Left_Encoder, leftdrive);
	PIDController rightPID=new PIDController(0.1,0,0, Right_Encoder, Rightdrive);*/
	
	public double ratio=1.0;
	public double ratio1=1.0;
	
	//PARAMETERS
	public int a=1;
	public double TargetAngle1=-90;
	
	public double climbpower=1.0;
	
	public DigitalInput limitswitch1=new  DigitalInput(0);
	public DigitalInput limitswitch2=new  DigitalInput(3);
	
	//public Counter counter = new Counter(limitswitch1)\Y;
	//public DigitalInput limitswitch2=new  DigitalInput(4);

	// gyro calibration constant, may need to be adjusted;
	// gyro value of 360 is set to correspond to one full revolution
    
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     * @return 
     */
  
	
    public void robotInit() {  
    	CameraServer server1 = CameraServer.getInstance();
    	//CameraServer server2 = CameraServer.getInstance();
    	UsbCamera camera1 = new UsbCamera("cam0",0);
        camera1.setResolution(320,240);
        camera1.setFPS(10);
        server1.startAutomaticCapture(camera1);   
        
        //PID//
        /*Left_Encoder.setPIDSourceType(PIDSourceType.kDisplacement);
        Right_Encoder.setPIDSourceType(PIDSourceType.kDisplacement);

        Right_Encoder.setReverseDirection(true);
        ((CANTalon) Rightdrive).setInverted(true); //only one side needs inversion

        Left_Encoder.reset();
        Right_Encoder.reset();

        leftPID.setOutputRange(-0.75,0.75); //max speed it can set to motors
        rightPID.setOutputRange(-0.75,0.75); //max speed it can set to motors

        leftPID.enable();
        rightPID.enable();*/
    }
    
    public void autonomousInit() {
    	g1.reset();  	

    }
    
    public void autonomousPeriodic(){
    	while (isAutonomous() && isEnabled()){
    		switch(a){
    		//MOVE FORWARD
    		case 1:
    			timer.reset();
    			timer.start();
    			while (timer.get()<1.0){
    			LEFTMOTOR1.set(-0.6); 
	        	RIGHTMOTOR1.set(0.6);
	        	LEFTMOTOR2.set(-0.6);
	        	RIGHTMOTOR2.set(0.6);
	        	LEFTMOTOR3.set(-0.6);
	        	RIGHTMOTOR3.set(0.6);}
    			a++;
    			break;
    		//STOP
    		case 2:
    			LEFTMOTOR1.set(0);
	        	RIGHTMOTOR1.set(0);
	        	LEFTMOTOR2.set(0);
	        	RIGHTMOTOR2.set(0);
	        	LEFTMOTOR3.set(0);
	        	RIGHTMOTOR3.set(0);
	        	Timer.delay(0.2);
	        	a++;
	        	break;
	        //LIFT
    		case 3:
    			timer.reset();
    			timer.start();
    			while(timer.get()<0.5){
    			LIFT.set(LIFTPOWER(-1,limitswitch1.get(),limitswitch2.get()));
    			}
    			LIFT.set(0);
    			a++;
    			break;
	       // TURN 90
    		case 4:
    			timer.reset();
    			timer.start();
    			while (Math.abs(g1.getAngle())<90){
    			LEFTMOTOR1.set(0.6); 
	        	RIGHTMOTOR1.set(0.6);
	        	LEFTMOTOR2.set(0.6);
	        	RIGHTMOTOR2.set(0.6);
	        	LEFTMOTOR3.set(0.6);
	        	RIGHTMOTOR3.set(0.6);}
    			a++;     
    			break;
    		//STOP
    		case 5:
    			LEFTMOTOR1.set(0);
	        	RIGHTMOTOR1.set(0);
	        	LEFTMOTOR2.set(0);
	        	RIGHTMOTOR2.set(0);
	        	LEFTMOTOR3.set(0);
	        	RIGHTMOTOR3.set(0);
	        	Timer.delay(0.2);
	        	a++;
	        	break;
	        //LIFT
    		case 6:
    			timer.reset();
    			timer.start();
    			while(timer.get()<0.5){
    			LIFT.set(LIFTPOWER(-1,limitswitch1.get(),limitswitch2.get()));
    			}
    			LIFT.set(0);
    			a++;
    			break;
	         
    		case 7:
    			timer.reset();
    			timer.start();
    			while (timer.get()<1){
    			LEFTMOTOR1.set(-0.6); 
	        	RIGHTMOTOR1.set(0.6);
	        	LEFTMOTOR2.set(-0.6);
	        	RIGHTMOTOR2.set(0.6);
	        	LEFTMOTOR3.set(-0.6);
	        	RIGHTMOTOR3.set(0.6);}
    			a++;
    			break;
    		case 8:
    			timer.reset();
    			timer.start();
    			LEFTMOTOR1.set(0);
	        	RIGHTMOTOR1.set(0);
	        	LEFTMOTOR2.set(0);
	        	RIGHTMOTOR2.set(0);
	        	LEFTMOTOR3.set(0);
	        	RIGHTMOTOR3.set(0);
	        	Timer.delay(2);
	        	a++;
	        	break;
	        //SHOT
    		case 9:
    			timer.reset();
    			timer.start();
    			while (timer.get()<0.4){
    			LEFTMOTOR1.set(0.6); 
	        	RIGHTMOTOR1.set(-0.6);
	        	LEFTMOTOR2.set(0.6);
	        	RIGHTMOTOR2.set(-0.6);
	        	LEFTMOTOR3.set(0.6);
	        	RIGHTMOTOR3.set(-0.6);}
    			a++;
    			break;
    		case 10:
    			timer.reset();
    			timer.start();
    			LEFTMOTOR1.set(0); 
	        	RIGHTMOTOR1.set(0);
	        	LEFTMOTOR2.set(0);
	        	RIGHTMOTOR2.set(0);
	        	LEFTMOTOR3.set(0);
	        	RIGHTMOTOR3.set(0);
	        	Timer.delay(0.2);
    			a++;
    			break;
    			//TURN10
    		case 11:
    			timer.reset();
    			timer.start();
    			g1.reset();
    			while (Math.abs(g1.getAngle())<10&&timer.get()<0.25){
    			LEFTMOTOR1.set(-0.6); 
	        	RIGHTMOTOR1.set(-0.6);
	        	LEFTMOTOR2.set(-0.6);
	        	RIGHTMOTOR2.set(-0.6);
	        	LEFTMOTOR3.set(-0.6);
	        	RIGHTMOTOR3.set(-0.6);}
    			a++;     
    			break;
    		case 12:
    			timer.reset();
    			timer.start();
    			LEFTMOTOR1.set(0); 
	        	RIGHTMOTOR1.set(0);
	        	LEFTMOTOR2.set(0);
	        	RIGHTMOTOR2.set(0);
	        	LEFTMOTOR3.set(0);
	        	RIGHTMOTOR3.set(0);
	        	Timer.delay(0.2);
    			a++;
    			break;
    		case 13:
    			timer.reset();
    			timer.start();
    			while(timer.get()<0.3){
    				LIFT.set(1);
    			}
    		case 14:
    			timer.reset();
    			timer.start();
    			SHOTER1.set(0.7);
	    		SHOTER2.set(0.7);
	    		Timer.delay(1.5);
	    		VERTICALTRANSPORT.set(0.6);
	    		HORIZONTALTRANSPORT.set(1);
	    		Timer.delay(6);
	    		a++;
	    		break;
    		default:
    			LIFT.set(0);
    			LEFTMOTOR1.set(0);
	        	RIGHTMOTOR1.set(0);
	        	LEFTMOTOR2.set(0);
	        	RIGHTMOTOR2.set(0);
	        	LEFTMOTOR3.set(0);
	        	RIGHTMOTOR3.set(0);
	        	SHOTER1.set(0);
	    		SHOTER2.set(0);
	    		VERTICALTRANSPORT.set(0);
	    		HORIZONTALTRANSPORT.set(0);
	    		break;
    		}	
    	}	
    }

    /**
     * This function is called periodically during operator control
     */ 
    @Override
    public void disabledInit(){
    	//leftPID.disable();
    	//rightPID.disable();
    	LEFTMOTOR1.set(0);
    	RIGHTMOTOR1.set(0);
    	LEFTMOTOR2.set(0);
    	RIGHTMOTOR2.set(0);
    	LEFTMOTOR3.set(0);
    	RIGHTMOTOR3.set(0);
    	SHOTER1.set(0);
		SHOTER2.set(0);
		SHOTER3.set(0);
    }
    
    public void teleopInit() {
    	//Left_Encoder.reset();
    	//_drive.stopMotor();	
    }
    	
	@Override
    public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()){
			//System.out.println(Left_Encoder.get());
    	//TANK
	    	double LEFT =ratio*deadzone( _xbox1.getRawAxis(1),0.1);
	    	double RIGHT =ratio*deadzone(_xbox1.getRawAxis(5),0.1);
	    	LEFTMOTOR1.set(LEFT);
	    	RIGHTMOTOR1.set(-RIGHT);
	    	LEFTMOTOR2.set(LEFT);
	    	RIGHTMOTOR2.set(-RIGHT);
	    	LEFTMOTOR3.set(LEFT);
	    	RIGHTMOTOR3.set(-RIGHT);
	    	//System.out.println(ratio);
	    	
	    	if(_Joy.getTrigger()==false){
	    	LIFT.set(LIFTPOWER(-deadzone(_Joy.getRawAxis(1),0.2),limitswitch1.get(),limitswitch2.get()));
	    	}
	    	//1: true when released 2: false when released
	    	
	    	//COLLECTGEAR
	    	if (_Joy.getRawButton(3)==true&&_Joy.getRawButton(4)==false){
	    		COLLECTGEAR.set(-0.7);
	    	}
	    	
	    	else if(_Joy.getRawButton(4)==true){
	    		COLLECTGEAR.set(1);
	    	}
	    		
	    	else{
	    		COLLECTGEAR.set(0);
	    	}
	    	   	
	    	
	    	//CLIMB//
	    	if(_Joy.getRawButton(2)==true){ 
	    		//climbpower=climbpower*(deadzone(_Joy.getRawAxis(1),0.2));
	    		RIGHTCLIMBER.set(-climbpower*ratio1);
	    		LEFTCLIMBER.set(climbpower*ratio1);
	    	}
	    		else{
	    			LEFTCLIMBER.set(0);
	    			RIGHTCLIMBER.set(0);
	    		}
	    	
	    	//SHOOTER
	    	if(_Joy.getTrigger()==true){
	    		//SHOTER3.set(-SHOTPOWER(_Joy.getRawAxis(3)));
	    		SHOTER1.set(0.5);
	    		SHOTER2.set(0.5);
	    		//System.out.println("shot1 power:  " + SHOTPOWER(_Joy.getRawAxis(1))+"  shot2 power:" + SHOTPOWER(_Joy.getRawAxis(3)));
	    		}
	    	else{
	    		SHOTER1.set(0);
	    		SHOTER2.set(0);
	    		
	    	}
	    	if(_Joy.getRawButton(8)==true){
	    		VERTICALTRANSPORT.set(0.6);
	    		HORIZONTALTRANSPORT.set(1);
	    	}
	    	else{
	    		VERTICALTRANSPORT.set(0);
	    		HORIZONTALTRANSPORT.set(0);
	    	}
	    	//COLLECT BALL
	    	if (_Joy.getRawButton(6)==true){
	    		COLLECTOR.set(0.9);
	    	}
	    	else{
	    		COLLECTOR.set(0);
	    	}
	    	
	    	//SWITCHING POWER
	    	if(_xbox1.getRawButton(3)==true){
	    		ratio=0.4;
	    	}
	    	if(_xbox1.getRawButton(4)==true){
	    		ratio=0.7;
	    	}
	    	if(_xbox1.getRawButton(2)==true){
	    		ratio=1;
	    	}
	    	
	    	//Switch CLIMB POWER
	    	if(_Joy.getRawButton(11)==true){
	    		ratio1=0.5;
	    	}
	    	if(_Joy.getRawButton(12)==true){
	    		ratio1=1;
	    	}
		}
}

    /**
     * Limit motor values to the -1.0 to +1.0 range.
     * @return 
     */
     
	public void disabledPeriodic() {
    } 
    
    double deadzone(double input,double deadzone){
    	if(Math.abs(input)>deadzone)
    	{
    		return input;
    	}else
    	{
    		return 0;
    	}
    }
    
    public double limit(double num) {
        if (num > 1.0) {
          return 1.0;
        }
        if (num < -1.0) {
          return -1.0;
        }
        return num;
      }
    
    public double LIFTPOWER(double input,boolean uplimit,boolean lowlimit){
    	double finalspeed=0.0;
    	if (uplimit==false&&input<0){
    		finalspeed= 0.0;
    		System.out.println("UPLIMIT MET");
    	}
    	
    	else if (lowlimit==true&&input>0){
    		finalspeed= 0.0;
    		System.out.println("LOWLIMIT MET");
    	}
    	
    	else{
    		if(input>0){
    			finalspeed = 0.4*input;
    		}
    		if(input<0){
    			finalspeed = input;
    		}
    		
    	}
    	return finalspeed;
    	 
    }
    
    public double SHOTPOWER(double input){
    	input =0.25*(input+1);
    	return input;
    }
 
}
