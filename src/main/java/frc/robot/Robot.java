// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//test change

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

//import javax.swing.text.Position;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PowerDistribution;

//import frc.robot.ArduinoI2CServer;
//import frc.robot.CustomGyroscope;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
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

  XboxController controller1 = new XboxController(0);
  XboxController controller2 = new XboxController(1);
  Timer timergametime = new Timer();
  Timer climbertime = new Timer();
  ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  PowerDistribution pdh = new PowerDistribution();
  boolean inverse = false;
  boolean climber = false;

  ArduinoI2CServer arduino = new ArduinoI2CServer(0x27);
  CustomGyroscope gyro1 = new CustomGyroscope(arduino);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    motor1Left.setIdleMode(IdleMode.kBrake);
    motor2Left.setIdleMode(IdleMode.kBrake);
    motor3Right.setIdleMode(IdleMode.kBrake);
    motor4Right.setIdleMode(IdleMode.kBrake);

    motor1Left.setOpenLoopRampRate(1.75);
    motor2Left.setOpenLoopRampRate(1.75);
    motor3Right.setOpenLoopRampRate(1.75);
    motor4Right.setOpenLoopRampRate(1.75);
    
    gyro.calibrate();
    gyro.getAngle();


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
    //SmartDashboard.putNumber("Motor 9 left arm, current", pdh.getCurrent(9));
    //SmartDashboard.putNumber("Motor 10 right arm, current", pdh.getCurrent(0));

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
    //SmartDashboard.putNumber("Motor 9 left arm, velocity", motor9leftarmclimb.getEncoder().getVelocity());
    //SmartDashboard.putNumber("Motor 10 right arm, velocity", motor10rightarmclimb.getEncoder().getVelocity());

    SmartDashboard.putNumber("Gyro 1, Angle", gyro.getAngle());

      
    SmartDashboard.putNumber("Custom Gyro X", gyro1.getOrientationX());
    SmartDashboard.putNumber("Custom Gyro Y", gyro1.getOrientationY());
    SmartDashboard.putNumber("Custom Gyro Z", gyro1.getOrientationZ());
  
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
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
   
   /*while (motor3Right.getEncoder().getPosition() < 21.2 ){
    motor3Right.set(.25);
  
   if (motor3Right.getEncoder().getPosition() > 21.2 ){
    motor3Right.set(0);
}
}*/
   
    timergametime.start();
    
    timergametime.get();
        
    while (timergametime.get() < 17 ){
     gyro.getAngle();
      
     if (timergametime.get() < 6){
      motor1Left.set(.252);
      motor2Left.set(.252);
      motor3Right.set(-.25);
      motor4Right.set(-.25);
     
     }
      else if (timergametime.get() > 6 && timergametime.get() < 7){
      motor1Left.set(0);
      motor2Left.set(0);
      motor3Right.set(0);
      motor4Right.set(0);

     }
     if (timergametime.get() > 7 && timergametime.get() < 8 ){
      motor1Left.set(.2);
      motor2Left.set(.2);
      motor3Right.set(.2);
      motor4Right.set(.2);
     }
     else if (timergametime.get() > 8 && timergametime.get() < 11){
      motor1Left.set(0);
      motor2Left.set(0);
      motor3Right.set(0);
      motor4Right.set(0);
      motor6index.set(.2);
      motor7launcher.set(.2);
      motor8launcher.set(.2);
     }
     else {
      motor6index.set(0);
      motor7launcher.set(0);
      motor8launcher.set(0);
    }
    }
     
}
  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
       
        
         break;
          
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

 

  /** This function is called once when teleop is enabled.hello */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double speedleft;
    double speedright;

    if(controller1.getBButtonPressed()){
      inverse = !inverse;
    }
    
    if (controller1.getStartButton()){
      climber = !climber;
    }

    if (controller1.getXButton()){
      speedright = 0.4;
      speedleft = 0.4;
    }
    else {
      speedright = 0.25;
      speedleft = 0.25;
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

    if ((Math.abs(controller1.getLeftY()))>.2 || (Math.abs(controller1.getRightY()))>.2){
      motor1Left.set(left);
      motor2Left.set(left);
      motor3Right.set(right);
      motor4Right.set(right);
    }
    else{
      motor1Left.set(0);
      motor2Left.set(0);
      motor3Right.set(0);
      motor4Right.set(0);
    }
    
    if (controller1.getRightBumper()){
      motor5intake.set(-.25);
    }
    else if (controller1.getLeftBumper()){
      motor5intake.set(.25);
    }
    else{
      motor5intake.set(0);
    }
   
    if (controller2.getRightTriggerAxis()>=0.75){
     motor7launcher.set(-.50);                                                                                                                                                                                                    
     motor8launcher.set(-.45);
    }
   else{
     motor7launcher.set(0);
     motor8launcher.set(0);
    }
   
    if (controller2.getLeftTriggerAxis()>=0.75 && controller2.getXButton()){
      motor6index.set(.5);
    }
    else if (controller2.getLeftTriggerAxis()>=0.75 && controller2.getBButton()){
      motor6index.set(-.15);
      motor8launcher.set(.3);
    }
    else if (controller2.getLeftTriggerAxis()>=0.75){
      motor6index.set(.3);
    }
    else{
      motor6index.set(0);
    }

    if (climber == true) {
      
      if (timergametime.get() <= 5){
        motor9leftarmclimb.set(.1);
        motor10rightarmclimb.set(.1);
      }
      if (timergametime.get() > 5) {
        climber = false;
        motor9leftarmclimb.set(0);
        motor10rightarmclimb.set(0);
        climbertime.stop();
        climbertime.reset();
      }
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
