package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@TeleOp(name="CameraDiveMode", group="Production")
public class CameraDriverMode extends LinearOpMode {

    // --- 1. Class-Level Hardware & Utility Declarations ---
    private ElapsedTime runtime = new ElapsedTime();
    
    // Drive Motors
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    
    // Mechanism Motors/Servos
    private DcMotor powerDrive = null;
    private DcMotor intake = null;
    private Servo push = null;

    // Vision Variables
    private OpenCvCamera webcam;
    private PurpleSphereDetectionPipeline visionPipeline;
    private Position detectedPosition = Position.CENTER; // Default or fallback position

    // Enum to define detection position
    public enum Position {
        LEFT,
        CENTER,
        RIGHT
    }
    
    // --- 2. Main OpMode Execution Method ---
    @Override
    public void runOpMode() {
        
        // --- Hardware Mapping ---
        backLeftDrive = hardwareMap.get(DcMotor.class, "m1");
        backRightDrive = hardwareMap.get(DcMotor.class, "m2");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "m3");
        frontRightDrive = hardwareMap.get(DcMotor.class, "m4");
        powerDrive = hardwareMap.get(DcMotor.class, "spinLaunch");
        intake = hardwareMap.get(DcMotor.class, "intake");
        push = hardwareMap.get(Servo.class, "push"); // Servo, not DcMotor!
        
        // Motor Directions
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        Boolean outToShoot = false;
        Boolean inToShoot = false;
        Boolean mainWheelOut = false;
        Boolean mainWheelIn = false;
        Boolean intakePush = true;
        Boolean cameraToggle = false;
        Integer internalTimer = 0;
        
        // Set motor modes (Crucial for consistent movement)
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Set other motors as needed...

        // --- Vision Initialization (EasyOpenCV Setup) ---
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        visionPipeline = new PurpleSphereDetectionPipeline();
        webcam.setPipeline(visionPipeline);
        
        // Start Streaming
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpenForTesting() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onFailure() {
                // Handle camera open failure
                telemetry.addData("Error", "Camera failed to open!");
                telemetry.update();
            }
        });

        telemetry.addData("Status", "Initialized & Vision Streaming");
        telemetry.update();

        // --- PRE-START Loop for Vision Detection ---
        while (!isStarted() && !isStopRequested()) {
            // Constantly update the detected position before the match starts
            detectedPosition = visionPipeline.getDetectedPosition();
        if(gamepad1.dpad_up){}
        if(gamepad2.crossWasPressed()){inToShoot=false;outToShoot=!outToShoot; powerDrive.setDirection(DcMotor.Direction.FORWARD);
         }
         if(gamepad2.circleWasPressed()){outToShoot=false;inToShoot=!inToShoot; powerDrive.setDirection(DcMotor.Direction.REVERSE);
         }
        if(gamepad2.rightBumperWasPressed()){mainWheelIn=false;mainWheelOut=!mainWheelOut; intake.setDirection(DcMotor.Direction.FORWARD);}
        if(gamepad2.leftBumperWasPressed()){mainWheelOut=false;mainWheelIn=!mainWheelIn; intake.setDirection(DcMotor.Direction.REVERSE);}
        if(gamepad2.squareWasPressed()){internalTimer=3000;intakePush=false;}
        if(gamepad1.AWasPressed()){cameraToggle = !cameraToggle;}
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
        telemetry.addData("finalPosition",finalPosition);
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
            if(cameraToggle){
                if (finalPosition == Position.LEFT) {
                    frontLeftDrive.setPower(.5);
                    frontRightDrive.setPower(-.5);
                    backLeftDrive.setPower(.5);
                    backRightDrive.setPower(-.5);
            // ... place pixel
        } else if (finalPosition == Position.CENTER) {
            // nothing yet
        } else if (finalPosition == Position.RIGHT) {
            // Move to right spike mark
            frontLeftDrive.setPower(-.5);
            frontRightDrive.setPower(.5);
            backLeftDrive.setPower(-.5);
            backRightDrive.setPower(.5);
            // ... place pixel
        }
            }
            
        }

        // --- MATCH START ---
        waitForStart();
        runtime.reset(); // Start the timer

        // Finalize detection result upon start
        Position finalPosition = visionPipeline.getDetectedPosition();
        webcam.stopStreaming(); // Stop the camera feed to save resources
    }
    
    // --- 3. Helper Methods for Cleaner Code ---

    /**
     * Executes the original movement sequence from the user's provided code.
     */
    
    /**
     * Executes the launch sequence (powerDrive and servo push).
     */
    public class PurpleSphereDetectionPipeline extends OpenCvPipeline {
        
        // Purple Sphere Detection Constants (Needs fine-tuning in a real environment!)
        // These are example HSV ranges for purple.
        private final Scalar PURPLE_LOW = new Scalar(100, 100, 100);
        private final Scalar PURPLE_HIGH = new Scalar(160, 255, 255);

        // Define three Regions of Interest (ROIs) on the camera view
        // These coordinates are relative to the 320x240 image size set above.
        // You MUST tune these coordinates based on your robot's camera placement.
        static final Rect ROI_LEFT = new Rect(new Point(0, 100), new Point(100, 200));
        static final Rect ROI_CENTER = new Rect(new Point(110, 100), new Point(210, 200));
        static final Rect ROI_RIGHT = new Rect(new Point(220, 100), new Point(320, 200));
        
        // Output variable
        private Position position = Position.CENTER;

        /**
         * The core image processing method, run automatically by EasyOpenCV.
         */
        @Override
        public Mat processFrame(Mat input) {
            // 1. Convert to HSV (Hue, Saturation, Value) for better color detection
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
            
            // 2. Create a mask that only shows purple pixels
            Mat mask = new Mat();
            Core.inRange(hsv, PURPLE_LOW, PURPLE_HIGH, mask);

            // 3. Find the average pixel value (sum of white pixels in the mask) in each ROI
            
            // Analyze Left ROI
            Mat submatLeft = mask.submat(ROI_LEFT);
            double avgLeft = Core.sumElems(submatLeft).val[0] / ROI_LEFT.area(); // Avg pixel value
            submatLeft.release();

            // Analyze Center ROI
            Mat submatCenter = mask.submat(ROI_CENTER);
            double avgCenter = Core.sumElems(submatCenter).val[0] / ROI_CENTER.area();
            submatCenter.release();

            // Analyze Right ROI
            Mat submatRight = mask.submat(ROI_RIGHT);
            double avgRight = Core.sumElems(submatRight).val[0] / ROI_RIGHT.area();
            submatRight.release();
            
            // 4. Determine the position based on the highest average brightness (most purple)
            double threshold = 50.0; // Tune this to confirm an object is present
            
            if (avgLeft > threshold && avgLeft > avgCenter && avgLeft > avgRight) {
                position = Position.LEFT;
            } else if (avgCenter > threshold && avgCenter > avgLeft && avgCenter > avgRight) {
                position = Position.CENTER;
            } else if (avgRight > threshold && avgRight > avgLeft && avgRight > avgCenter) {
                position = Position.RIGHT;
            } else {
                position = Position.CENTER; // Fallback position
            }

            // 5. Draw the ROIs and result on the input frame for viewing on the driver station
            Imgproc.rectangle(input, ROI_LEFT, position == Position.LEFT ? new Scalar(0, 255, 0) : new Scalar(255, 0, 0), 2);
            Imgproc.rectangle(input, ROI_CENTER, position == Position.CENTER ? new Scalar(0, 255, 0) : new Scalar(255, 0, 0), 2);
            Imgproc.rectangle(input, ROI_RIGHT, position == Position.RIGHT ? new Scalar(0, 255, 0) : new Scalar(255, 0, 0), 2);

            // Release mats to prevent memory leaks
            hsv.release();
            mask.release();
            
            return input; // Return the frame with bounding boxes
        }

        /**
         * Called by the OpMode to get the most recently detected position.
         */
        public Position getDetectedPosition() {
            return position;
        }
    }
}
