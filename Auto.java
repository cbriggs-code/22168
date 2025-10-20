package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
// so like just import this stuff later.....
public class Normaldrive extends LinearOpMode {
    DcMotor m1;
    DcMotor m2;
    DcMotor m3;
    DcMotor m4;
    DcMotor powerDrive;
    BNO055IMU imu;

@Override
    public void runOpMode() {
      backLeftDrive = hardwareMap.get(DcMotor.class, "backLeftDrive");
      backRightDrive = hardwareMap.get(DcMotor.class, "backRightDrive");
      frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftDrive");
      frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
      powerDrive = hardwareMap.get(DcMotor.class, "powerDrive");
      imu = hardwareMap.get(BNO055IMU.class, "imu");
      backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
      frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
      Boolean thelongdriveback = false;
      while (opModeIsActive()) {
          // need to replace this stuff with the dead wheel encoders
        telemetry.addData("left", backLeftDrive.getCurrentPosition());
        telemetry.addData("right", backRightDrive.getCurrentPosition());
        telemetry.addData("top", frontLeftDrive.getCurrentPosition());
        telemetry.update();
            powerDrive.setPower(1);
        }
      }
    }

}
