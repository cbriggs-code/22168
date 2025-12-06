package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;
// so like just import this stuff later.....
@TeleOp(name="Normaldrive", group="Linear OpMode")
public class Normaldrive extends LinearOpMode {
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor powerDrive;
    DcMotor intake;
    Servo push;
    private final int READ_PERIOD = 1;

    private HuskyLens huskyLens;

@Override
    public void runOpMode() {
      huskyLens = hardwareMap.get(HuskyLens.class, "aiCam");
      Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
      backLeftDrive = hardwareMap.get(DcMotor.class, "m1");
      backRightDrive = hardwareMap.get(DcMotor.class, "m2");
      frontLeftDrive = hardwareMap.get(DcMotor.class, "m3");
      frontRightDrive = hardwareMap.get(DcMotor.class, "m4");
      powerDrive = hardwareMap.get(DcMotor.class, "spinLaunch");
      intake = hardwareMap.get(DcMotor.class, "intake");
      push = hardwareMap.get(Servo.class, "push");
      backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
      frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
      
      Boolean outToShoot = false;
      Boolean inToShoot = false;
      Boolean mainWheelOut = false;
      Boolean mainWheelIn = false;
      Boolean intakePush = true;
      Integer internalTimer = 0;
      Integer pos = 1;
      rateLimit.expire();
      if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);

        telemetry.update();
      waitForStart();
      while (opModeIsActive()) {
        if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();
            HuskyLens.Block[] blocks = huskyLens.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
                pos = 1;
                if(blocks[i].y>60){pos=2;}
                if(blocks[i].y<40){pos=0;}
                /*
                 * Here inside the FOR loop, you could save or evaluate specific info for the currently recognized Bounding Box:
                 * - blocks[i].width and blocks[i].height   (size of box, in pixels)
                 * - blocks[i].left and blocks[i].top       (edges of box)
                 * - blocks[i].x and blocks[i].y            (center location)
                 * - blocks[i].id                           (Color ID)
                 *
                 * These values have Java type int (integer).
                 */
            }
        if(gamepad1.dpad_up){}
        if(gamepad2.crossWasPressed()){inToShoot=false;outToShoot=!outToShoot; powerDrive.setDirection(DcMotor.Direction.FORWARD);
         }
         if(gamepad2.circleWasPressed()){outToShoot=false;inToShoot=!inToShoot; powerDrive.setDirection(DcMotor.Direction.REVERSE);
         }
        if(gamepad2.rightBumperWasPressed()){mainWheelIn=false;mainWheelOut=!mainWheelOut; intake.setDirection(DcMotor.Direction.FORWARD);}
        if(gamepad2.leftBumperWasPressed()){mainWheelOut=false;mainWheelIn=!mainWheelIn; intake.setDirection(DcMotor.Direction.REVERSE);}
        if(gamepad2.squareWasPressed()){internalTimer=3000;intakePush=false;}
        if(internalTimer<0){intakePush=true;}
        //if(gamepad1.triangleWasPressed()){intakePush=true;}
        internalTimer -= 100;
        backLeftDrive.setPower(-gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x);
        backRightDrive.setPower(-gamepad1.left_stick_y+gamepad1.left_stick_x-gamepad1.right_stick_x);
        frontLeftDrive.setPower(-gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x);
        frontRightDrive.setPower(-gamepad1.left_stick_y-gamepad1.left_stick_x-gamepad1.right_stick_x);
          // need to replace this stuff with the dead wheel encoders
        telemetry.addData("left", backLeftDrive.getCurrentPosition());
        telemetry.addData("right", backRightDrive.getCurrentPosition());
        telemetry.addData("top", frontLeftDrive.getCurrentPosition());
        telemetry.update();
        if(gamepad1.dpad_up){
          backLeftDrive.setPower(.4);
          backRightDrive.setPower(.4);
          frontLeftDrive.setPower(.4);
          frontRightDrive.setPower(.4);
        }
        if(gamepad1.dpad_down){
          backLeftDrive.setPower(-.4);
          backRightDrive.setPower(-.4);
          frontLeftDrive.setPower(-.4);
          frontRightDrive.setPower(-.4);
        }
        if(gamepad1.dpad_left){
          backLeftDrive.setPower(.4);
          backRightDrive.setPower(-.4);
          frontLeftDrive.setPower(-.4);
          frontRightDrive.setPower(.4);
        }
        if(gamepad1.dpad_right){
          backLeftDrive.setPower(-.4);
          backRightDrive.setPower(.4);
          frontLeftDrive.setPower(.4);
          frontRightDrive.setPower(-.4);
        }
        if(mainWheelOut || mainWheelIn){
          intake.setPower(1);
          
        }else{
          intake.setPower(0);
        }
        if(outToShoot || inToShoot){
            // code for going back to the start point to test encoders would be here but the testing i have does not have dead wheels nor a working imu system
            powerDrive.setPower(-1);
        }else{
          powerDrive.setPower(0);
        }
        if(intakePush){
          push.setPosition(1);
          
        }else{
          push.setPosition(0.7);
        }
      }
    }

}


