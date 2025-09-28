public class Normaldrive extends LinearOpMode {
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    BNO055IMU imu;

@Override
    public void runOpMode() {
      backLeftDrive = hardwareMap.get(DcMotor.class, "backLeftDrive");
      backRightDrive = hardwareMap.get(DcMotor.class, "backRightDrive");
      frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftDrive");
      frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
      imu = hardwareMap.get(BNO055IMU.class, "imu");
      backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
      frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
      Boolean thelongdriveback = false;
      while (opModeIsActive()) {
        if(gamepad1.a){thelongdriveback=!thelongdriveback;}
        backLeftDrive.setPower(gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x);
        backRightDrive.setPower(gamepad1.left_stick_y+gamepad1.left_stick_x-gamepad1.right_stick_x);
        frontLeftDrive.setPower(gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x);
        frontRightDrive.setPower(gamepad1.left_stick_y-gamepad1.left_stick_x-gamepad1.right_stick_x);
          // need to replace this stuff with the dead wheel encoders
        telemetry.addData("left", backLeftDrive.getCurrentPosition());
        telemetry.addData("right", backRightDrive.getCurrentPosition());
        telemetry.addData("top", frontLeftDrive.getCurrentPosition());
        telemetry.update();
        if(thelongdriveback){
            //code for going back to the start point to test encoders would be here but the testing i have does not have dead wheels nor a working imu system
        }
      }
    }

}
