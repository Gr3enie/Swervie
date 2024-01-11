// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Functions;
//import frc.robot.subsystems.SwerveModule;
import java.lang.Math;

public class SwerveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public static double RobotYawAngle = 0;
  public static double RobotYawRate = 0;
  public static double RobotYawOld = 0;
  public static SwerveModule flModule = new SwerveModule(Constants.flaMotor,Constants.fltMotor,Constants.flEncoder,false,false);
  public static SwerveModule frModule = new SwerveModule(Constants.fraMotor,Constants.frtMotor,Constants.frEncoder,false,false);
  public static SwerveModule blModule = new SwerveModule(Constants.blaMotor,Constants.bltMotor,Constants.blEncoder,false,false);
  public static SwerveModule brModule = new SwerveModule(Constants.braMotor,Constants.brtMotor,Constants.brEncoder,false,false);
  public SwerveSubsystem() {}

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotYawAngle = Functions.DeltaAngleDeg(Constants.gyro.getYaw(), 0);
    RobotYawRate = -(Constants.gyro.getYaw() - RobotYawOld)/0.02;
    RobotYawOld = Constants.gyro.getYaw();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  public static void Drive(double x, double y, double rotate) {
    double flx =  x + (Constants.turnMult * rotate);
    double fly =  x + (Constants.turnMult * rotate);
    double frx =  x + (Constants.turnMult * rotate);
    double fry =  x - (Constants.turnMult * rotate);
    double blx =  x - (Constants.turnMult * rotate);
    double bly =  x - (Constants.turnMult * rotate);
    double brx =  x - (Constants.turnMult * rotate);
    double bry =  x + (Constants.turnMult * rotate);
    double maxDist = Functions.Max(new double[]{
      Math.sqrt(Math.pow(flx,2) + Math.pow(fly,2)),
      Math.sqrt(Math.pow(frx,2) + Math.pow(fry,2)),
      Math.sqrt(Math.pow(blx,2) + Math.pow(bly,2)),
      Math.sqrt(Math.pow(brx,2) + Math.pow(bry,2)),1.0});
    double flAngle = Math.atan2(fly,flx);
    double frAngle = Math.atan2(fry,frx);
    double blAngle = Math.atan2(bly,blx);
    double brAngle = Math.atan2(bry,brx);
    double flThrottle = Math.sqrt(Math.pow(flx,2) + Math.pow(fly,2)) / maxDist;
    double frThrottle = Math.sqrt(Math.pow(frx,2) + Math.pow(fry,2)) / maxDist;
    double blThrottle = Math.sqrt(Math.pow(blx,2) + Math.pow(bly,2)) / maxDist;
    double brThrottle = Math.sqrt(Math.pow(brx,2) + Math.pow(bry,2)) / maxDist;
    flModule.Drive(flAngle, flThrottle);
    frModule.Drive(frAngle, frThrottle);
    blModule.Drive(blAngle, blThrottle);
    brModule.Drive(brAngle, brThrottle);
  }
  
}
