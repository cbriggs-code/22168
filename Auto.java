import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class AutoJava extends LinearOpMode {
    
    @Override
    public void runOpMode() {
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor powerDrive;
    DcMotor intake;
    Servo push;
        
        backLeftDrive = hardwareMap.get(DcMotor.class, "m1");
        backRightDrive = hardwareMap.get(DcMotor.class, "m2");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "m3");
        frontRightDrive = hardwareMap.get(DcMotor.class, "m4");
        powerDrive = hardwareMap.get(DcMotor.class, "spinLaunch");
        intake = hardwareMap.get(DcMotor.class, "intake");
        push = hardwareMap.get(Servo.class, "push");
        //powerDrive = hardwareMap.get(DcMotor.class, "powerDrive");
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        Boolean thelongdriveback = false;
        waitForStart();
        //while (opModeIsActive()) {

            backLeftDrive.setPower(1.6);
            backRightDrive.setPower(1.6);
            frontLeftDrive.setPower(1.6);
            frontRightDrive.setPower(1.6);
            sleep(780);
            backLeftDrive.setPower(0.3);
            backRightDrive.setPower(-0.3);
            frontLeftDrive.setPower(0.3);
            frontRightDrive.setPower(-0.3);
            sleep(280);
            backLeftDrive.setPower(-0.3);
            backRightDrive.setPower(0.1);
            frontLeftDrive.setPower(-0.1);
            frontRightDrive.setPower(0.1);
            sleep(280);
            backLeftDrive.setPower(.3);
            backRightDrive.setPower(.3);
            frontLeftDrive.setPower(.3);
            frontRightDrive.setPower(.3);
            sleep(360);
            backLeftDrive.setPower(.22);
            backRightDrive.setPower(.22);
            frontLeftDrive.setPower(.22);
            frontRightDrive.setPower(.22);
            sleep(1400);
            backLeftDrive.setPower(0);
            backRightDrive.setPower(0);
            frontLeftDrive.setPower(0);
            frontRightDrive.setPower(0);
            intake.setPower(1);
            sleep(4500);
            //launch code 
            powerDrive.setPower(-1);
            push.setPosition(0.7);
            sleep(1000);
            push.setPosition(1);
            intake.setPower(0);
    }
}
