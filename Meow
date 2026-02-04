package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import android.graphics.Color;

/**
 * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
 * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
 * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
 * OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@TeleOp

public class Meow extends LinearOpMode {
    private Blinker control_Hub;
    private DcMotor m1;
    private DcMotor m2;
    private DcMotor m3;
    private DcMotor m4;
    private RevColorSensorV3 c1;
    private RevColorSensorV3 c2;
    private RevColorSensorV3 c3;

// package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.rev.RevColorSensorV3;

@TeleOp(name="ColorDetectionFix")
public class Meow extends LinearOpMode {
    private RevColorSensorV3 c1;

    /* Define target constants
    private final double GREEN_HUE = 120.0;
    private final double PURPLE_HUE = 280.0;
    private final double THRESHOLD = 20.0; // Degrees of tolerance

    @Override
    public void runOpMode() {
        // Initialize sensor
        c1 = hardwareMap.get(RevColorSensorV3.class, "c1");

        waitForStart();

        while (opModeIsActive()) {
            // Get RGB values and convert to HSV
            float[] hsv = new float[3];
            Color.RGBToHSV(c1.red(), c1.green(), c1.blue(), hsv);
            float currentHue = hsv[0];

            String detected = "None";

            if (getHueDistance(currentHue, GREEN_HUE) < THRESHOLD) {
                detected = "Green";
            } else if (getHueDistance(currentHue, PURPLE_HUE) < THRESHOLD) {
                detected = "Purple";
            }

            telemetry.addData("Hue", currentHue);
            telemetry.addData("Detected", detected);
            telemetry.update();
        }
    }

    // Helper to find the shortest distance between two angles (0-360)
    public double getHueDistance(double h1, double h2) {
        double diff = Math.abs(h1 - h2) % 360;
        return diff > 180 ? 360 - diff : diff;
    }
}
*/
    
    public double dist(int r1,int g1, int b1, int r2, int g2, int b2){
        return (JavaUtil.colorToHue(Color.rgb(r2, g2, b2))-JavaUtil.colorToHue(Color.rgb(r1, g1, b1)));
    }
    @Override
    public void runOpMode() {
        control_Hub = hardwareMap.get(Blinker.class, "Control Hub");
        m1 = hardwareMap.get(DcMotor.class, "m1");
        m2 = hardwareMap.get(DcMotor.class, "m2");
        m3 = hardwareMap.get(DcMotor.class, "m3");
        m4 = hardwareMap.get(DcMotor.class, "m4");
        c1 = hardwareMap.get(RevColorSensorV3.class, "c1");
        c2 = hardwareMap.get(RevColorSensorV3.class, "c2");
        c3 = hardwareMap.get(RevColorSensorV3.class, "c3");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            if(dist(c1.red(),c1.green(),c1.blue(),98,380,274)<=10||dist(c1.red(),c1.green(),c1.blue(),98,380,274)<=10){
            if(dist(c1.red(),c1.green(),c1.blue(),230,273,426)<=10){
            telemetry.addData("c1", "g");
            }
            if(dist(c1.red(),c1.green(),c1.blue(),98,380,274)<=10){
                telemetry.addData("c1", "p");
            }
                }else{
                    telemetry.addData("c1", "no ball in");
                }
            
            //telemetry.addData("c1c", c1.red()+"|"+c1.green()+"|"+c1.blue());
            telemetry.update();
            m1.setPower(-gamepad1.right_stick_x+gamepad1.left_stick_x+gamepad1.left_stick_y);
            m2.setPower(-gamepad1.right_stick_x-gamepad1.left_stick_x+gamepad1.left_stick_y);
            m3.setPower(-gamepad1.right_stick_x+gamepad1.left_stick_x-gamepad1.left_stick_y);
            m4.setPower(-gamepad1.right_stick_x-gamepad1.left_stick_x-gamepad1.left_stick_y);
            }
        }
    }
