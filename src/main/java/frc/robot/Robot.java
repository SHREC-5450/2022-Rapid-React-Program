// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//test change

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

import javax.security.auth.callback.LanguageCallback;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Servo;

//import frc.robot.ArduinoI2CServer;
//import frc.robot.CustomGyroscope;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  public static final double fastSpeed = 0.75, slowSpeed = 0.50, trig_axis = 0.75, speedoffset = 1, intake = 0.35, intakeLift = .1, motorAutonomous = 0.25, climberPower = 1, climberpower = .75, Launcherpower = .34, autoLauncherpower = .27;

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String kCustomAuto2 = "My Auto 2";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  CANSparkMax motor1Left = new CANSparkMax(1, MotorType.kBrushless);
  CANSparkMax motor2Left = new CANSparkMax(2, MotorType.kBrushless);
  CANSparkMax motor3Right = new CANSparkMax(3, MotorType.kBrushless);
  CANSparkMax motor4Right = new CANSparkMax(4, MotorType.kBrushless);
  CANSparkMax motor5intake = new CANSparkMax(5, MotorType.kBrushless);
  CANSparkMax motor6index = new CANSparkMax(6, MotorType.kBrushless);
  CANSparkMax motor7launcher = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax motor8launcher = new CANSparkMax(8, MotorType.kBrushless);
  CANSparkMax motor9leftarmclimb = new CANSparkMax(9, MotorType.kBrushless);
  CANSparkMax motor10rightarmclimb = new CANSparkMax(10, MotorType.kBrushless);
  CANSparkMax motor11lift = new CANSparkMax(11, MotorType.kBrushless);

  XboxController controller1 = new XboxController(0);
  XboxController controller2 = new XboxController(1);
  Timer timergametime = new Timer();
  Timer launchtime = new Timer();
  //ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  PowerDistribution pdh = new PowerDistribution();
  boolean inverse = false;
  boolean fast = false;
  boolean fastlift = false;

  DigitalInput IntakeDownCircuit = new DigitalInput(0);
  DigitalInput IntakeUpCircuit = new DigitalInput(1);
  DigitalInput lowerClimberLimitleft = new DigitalInput(2);
  DigitalInput lowerClimberLimitright = new DigitalInput(3);
  //DigitalInput upperClimberLimitleft = new DigitalInput(8);
  //DigitalInput upperClimberLimitright = new DigitalInput(9);
  

  //Servo climberswitchleft = new Servo(0);
  //Servo climberswitchright = new Servo(9);

  //ArduinoI2CServer arduino = new ArduinoI2CServer(0x27);
  //CustomGyroscope gyro1 = new CustomGyroscope(arduino);

  //Thread gyroThread = new Thread(gyro1);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    CameraServer.startAutomaticCapture();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    m_chooser.addOption("My Auto 2", kCustomAuto2);
    SmartDashboard.putData("Auto choices", m_chooser);


    motor1Left.setIdleMode(IdleMode.kBrake);
    motor2Left.setIdleMode(IdleMode.kBrake);
    motor3Right.setIdleMode(IdleMode.kBrake);
    motor4Right.setIdleMode(IdleMode.kBrake);
    motor9leftarmclimb.setIdleMode(IdleMode.kBrake);
    motor10rightarmclimb.setIdleMode(IdleMode.kBrake);

    motor1Left.setOpenLoopRampRate(1.75);
    motor2Left.setOpenLoopRampRate(1.75);
    motor3Right.setOpenLoopRampRate(1.75);
    motor4Right.setOpenLoopRampRate(1.75);
    
    //gyro.calibrate();
   // gyro.getAngle();

    //gyroThread.start();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
   
   
    SmartDashboard.putNumber("Motor 1 left, current", pdh.getCurrent(11)); 
    SmartDashboard.putNumber("Motor 2 left, current", pdh.getCurrent(10)); 
    SmartDashboard.putNumber("Motor 3 right, current", pdh.getCurrent(19)); 
    SmartDashboard.putNumber("Motor 4 right, current", pdh.getCurrent(18)); 
    SmartDashboard.putNumber("Motor 5 intake, current", pdh.getCurrent(14)); 
    SmartDashboard.putNumber("Motor 7 launcher, current", pdh.getCurrent(17)); 
    SmartDashboard.putNumber("Motor 6 launcher, current", pdh.getCurrent(12));
    SmartDashboard.putNumber("Motor 8 launcher, current", pdh.getCurrent(13));
    SmartDashboard.putNumber("Motor 9 left arm, current", pdh.getCurrent(9));
    SmartDashboard.putNumber("Motor 10 right arm, current", pdh.getCurrent(0));
    SmartDashboard.putNumber("Motor 11 lift, current", pdh.getCurrent(15));

    SmartDashboard.putNumber("Motor 1 left, postion", motor1Left.getEncoder().getPosition());
    SmartDashboard.putNumber("Motor 2 left, postion", motor2Left.getEncoder().getPosition());
    SmartDashboard.putNumber("Motor 3 right, postion", motor3Right.getEncoder().getPosition());
    SmartDashboard.putNumber("Motor 4 right, postion", motor4Right.getEncoder().getPosition());
    
    SmartDashboard.putNumber("Motor 1 left, velocity", motor1Left.getEncoder().getVelocity());
    SmartDashboard.putNumber("Motor 2 left, velocity", motor2Left.getEncoder().getVelocity());
    SmartDashboard.putNumber("Motor 3 right, velocity", motor3Right.getEncoder().getVelocity());
    SmartDashboard.putNumber("Motor 4 right, velocity", motor4Right.getEncoder().getVelocity());
    SmartDashboard.putNumber("Motor 5 intake, velocity", motor5intake.getEncoder().getVelocity()); 
    SmartDashboard.putNumber("Motor 7 launcher, velocity", motor7launcher.getEncoder().getVelocity()); 
    SmartDashboard.putNumber("Motor 6 launcher, velocity", motor6index.getEncoder().getVelocity()); 
    SmartDashboard.putNumber("Motor 8 launcher, velocity", motor8launcher.getEncoder().getVelocity());
    SmartDashboard.putNumber("Motor 9 left arm, velocity", motor9leftarmclimb.getEncoder().getVelocity());
    SmartDashboard.putNumber("Motor 10 right arm, velocity", motor10rightarmclimb.getEncoder().getVelocity());
    SmartDashboard.putNumber("Motor 11 lift, velocity", motor11lift.getEncoder().getVelocity());

    //SmartDashboard.putNumber("Gyro 1, Angle", gyro.getAngle());
  
    //SmartDashboard.putNumber("Custom Gyro X", gyro1.getOrientationX());
    //SmartDashboard.putNumber("Custom Gyro Y", gyro1.getOrientationY());
    //SmartDashboard.putNumber("Custom Gyro Z", gyro1.getOrientationZ());
  
  }


  

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
   @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    //m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    
    timergametime.start();
    
   }
  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        
        // Put custom auto code here
        if (timergametime.get() < 15){
          //gyro.getAngle();

          double delay1 = 1.55,
          delay2 = delay1 + 0.75,
          delay3 = delay2 + 1,
          delay4 = delay3 + 2.1;
           
          if (timergametime.get() < delay1){
            motor7launcher.set(-autoLauncherpower);
            motor8launcher.set(-autoLauncherpower - .06);
          }
          else if (timergametime.get() > delay1 && timergametime.get() < delay2){
            motor6index.set(intake);
          }
          else if (timergametime.get() > delay2 && timergametime.get() < delay3){
            motor6index.set(0);
            motor7launcher.set(0);
            motor8launcher.set(0);
          }
          else if (timergametime.get() > delay3 && timergametime.get() < delay4){
            runDrive(motorAutonomous, -motorAutonomous);
          }
          else {
           motor6index.set(0);
           motor7launcher.set(0);
           motor8launcher.set(0);
           runDrive(0, 0);
         }
        }
        


         break;
         

      case kCustomAuto2:

       if (timergametime.get() < 15){
         
       }  
        break;
          
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }


  }


  /** This function is called once when teleop is enabled.hello */
  @Override
  public void teleopInit() {
    /*
    climberswitchright.setAngle(70);
    climberswitchleft.setAngle(180);
    */
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
   
    double speedleft;
    double speedright;

    if(controller1.getBButtonPressed()){
      inverse = !inverse;
    }

    if(controller1.getXButtonPressed()) {
      fast = !fast;
    }
    if(controller1.getYButtonPressed()){
      fastlift = !fastlift;
    }

    if (fast == true){
      speedright = fastSpeed;
      speedleft = fastSpeed;
    }
    else {
      speedright = slowSpeed;
      speedleft = slowSpeed;
    }

    double left;
    double right;
    if (inverse == true){
      left = controller1.getLeftY() *speedleft;
      right = -controller1.getRightY() *speedright;
    }
    else{
      left = -controller1.getLeftY() *speedleft;
      right = controller1.getRightY() *speedright;
    }

    if ((Math.abs(controller1.getLeftY()))>.4 || (Math.abs(controller1.getRightY()))>.4){
      runDrive(left, right);
    }
    else{
      runDrive(0, 0);
    }

    if (controller1.getRightBumper()){
      motor5intake.set(-intake);
    }
    else if (controller1.getLeftBumper()){
      motor5intake.set(intake);
    }
    else{
      motor5intake.set(0);
    }
    
    if (controller2.getAButton()){
      
      if (IntakeDownCircuit.get() == false){
       motor11lift.set(-intakeLift);
      }
      else if (IntakeDownCircuit.get() == true){
        motor11lift.set(0);
      }
      
    }
    else if (controller2.getYButton()){
      
      if (IntakeUpCircuit.get() == false){
        motor11lift.set(intakeLift);
       }
      else if (IntakeUpCircuit.get() == true){
        motor11lift.set(0);
      }
      
    }
    else {
      motor11lift.set(0);
    }
    
    if (controller2.getRightTriggerAxis()>= trig_axis){
     motor7launcher.set(-Launcherpower);                                                                                                                                                                                                    
     motor8launcher.set(-Launcherpower);
    }
   else{
     motor7launcher.set(0);
     motor8launcher.set(0);
    }
   
    if (controller2.getLeftTriggerAxis()>=trig_axis && controller2.getXButton()){
      motor6index.set(.5);
    }
    else if (controller2.getRightBumper()){
      motor6index.set(-.15);
      motor8launcher.set(.3);
    }
    else if (controller2.getLeftTriggerAxis()>=trig_axis){
      motor6index.set(.3);
    }
    else{
      motor6index.set(0);
    }

    if (fastlift == true) {
      if (controller1.getPOV() == 0) {
        motor9leftarmclimb.set(climberPower);
        motor10rightarmclimb.set(climberPower);
      }
      else if (controller1.getPOV() == 180) {
        motor9leftarmclimb.set(-climberPower);
        motor10rightarmclimb.set(-climberPower);
      }
    }
    else {
      if (controller1.getPOV() == 0) {
        motor9leftarmclimb.set(climberpower);
        motor10rightarmclimb.set(climberpower);
      }
      else if (controller1.getPOV() == 180) {
        motor9leftarmclimb.set(-climberpower);
        motor10rightarmclimb.set(-climberpower);
      }
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    
    if (lowerClimberLimitright.get() == true) {
      motor9leftarmclimb.set(.05);
      motor10rightarmclimb.set(.05);
    }
    else if (lowerClimberLimitright.get() == false) {
      motor9leftarmclimb.set(0);
      motor10rightarmclimb.set(0);
    }
    
  }


  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  public void runDrive(double leftSpeed, double rightSpeed) {
    motor1Left.set(leftSpeed * speedoffset);
    motor2Left.set(leftSpeed * speedoffset);
    motor3Right.set(rightSpeed);
    motor4Right.set(rightSpeed);
  }
  public void sleep(double sleeptime){}
}