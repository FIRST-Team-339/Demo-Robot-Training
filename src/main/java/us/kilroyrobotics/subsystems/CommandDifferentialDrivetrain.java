// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package us.kilroyrobotics.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import us.kilroyrobotics.Constants.DriveConstants;

public class CommandDifferentialDrivetrain extends SubsystemBase {
  public interface DriveRequest {
    public void apply(DifferentialDrive drive);

    public static class ArcadeRequest implements DriveRequest {
      public double xSpeed;
      public double zRotation;

      @Override
      public void apply(DifferentialDrive drive) {
          drive.arcadeDrive(xSpeed, zRotation);
      }

      public ArcadeRequest withXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
        return this;
      }

      public ArcadeRequest withZRotation(double zRotation) {
        this.zRotation = zRotation;
        return this;
      }
    }
  }

  private TalonFX leftDriveMotor; 
  private TalonFX rightDriveMotor;
  private DifferentialDrive drive;

  /** Creates a new CommandDifferentialDrivetrain. */
  public CommandDifferentialDrivetrain() {
    leftDriveMotor = new TalonFX(DriveConstants.kLeftDriveMotorId);
    rightDriveMotor = new TalonFX(DriveConstants.kRightDriveMotorId);

    drive = new DifferentialDrive(leftDriveMotor::set, rightDriveMotor::set);
  }

  public Command applyRequest(Supplier<DriveRequest> requestSupplier) {
    return run(() -> requestSupplier.get().apply(drive));
  }

  public void setDeadband(double deadband) {
    drive.setDeadband(deadband);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
