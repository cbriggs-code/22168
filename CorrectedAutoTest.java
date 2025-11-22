package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

// Import necessary classes for Vision Portal or EasyOpenCV
// For this example, we'll use a structure compatible with EasyOpenCV logic
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

// OpenCV Imports (Needed for the pipeline implementation)
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Autonomous(name="Corrected Auto OpMode", group="Production")
public class CorrectedAutoTest extends LinearOpMode {

    // --- 1. Class-Level Hardware & Utility Declarations ---
    private ElapsedTime runtime = new ElapsedTime();
    
    // Drive Motors
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    
    // Mechanism Motors/Servos
    private DcMotor powerDrive = null; // Assuming this is your launcher/shooter
    private DcMotor intake = null;
    private Servo push = null; // Assuming this is your push mechanism (e.g., in a launcher)

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
        
        // Set motor modes (Crucial for consistent movement)
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Set other motors as needed...

        // --- Vision Initialization (EasyOpenCV Setup) ---
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(com.qualcomm.robotcore.hardware.WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        visionPipeline = new PurpleSphereDetectionPipeline();
        webcam.setPipeline(visionPipeline);
        
        // Start Streaming
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
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
            
            telemetry.addData("Status", "Ready");
            telemetry.addData("Detected Sphere Position", detectedPosition);
            telemetry.addData("Runtime", runtime.toString());
            telemetry.update();
            sleep(50); // Small delay to allow other processes to run
        }

        // --- MATCH START ---
        waitForStart();
        runtime.reset(); // Start the timer

        // Finalize detection result upon start
        Position finalPosition = visionPipeline.getDetectedPosition();
        webcam.stopStreaming(); // Stop the camera feed to save resources

        // --- Autonomous Execution Logic based on Detection ---
        
        telemetry.addData("ACTION", "Starting sequence for position: " + finalPosition);
        telemetry.update();

        // 1. Example Movement based on Vision
        if (finalPosition == Position.LEFT) {
            // Move to left spike mark
            moveRobot(0.5, 0.0, 500); // Fwd for 0.5 sec
            turnRobot(0.5, 500); // Turn left
            // ... place pixel
        } else if (finalPosition == Position.CENTER) {
            // Execute the drive code from your original example
            driveSequenceFromOriginalCode();
        } else if (finalPosition == Position.RIGHT) {
            // Move to right spike mark
            moveRobot(0.5, 0.0, 500);
            turnRobot(-0.5, 500); // Turn right
            // ... place pixel
        }
        
        // Final action regardless of position (e.g., parking, launching)
        launchSequence();

        telemetry.addData("Status", "Autonomous sequence complete.");
        telemetry.update();
    }
    
    // --- 3. Helper Methods for Cleaner Code ---

    /**
     * Executes the original movement sequence from the user's provided code.
     */
    private void driveSequenceFromOriginalCode() {
         backLeftDrive.setPower(1);
         backRightDrive.setPower(1);
         frontLeftDrive.setPower(1);
         frontRightDrive.setPower(1);
         sleep(780);
         
         // ... rest of the original drive code
         
         // The original code was not structured well for movement functions,
         // so we keep the raw sequence for demonstration:
         backLeftDrive.setPower(0.5);
         backRightDrive.setPower(-0.5);
         frontLeftDrive.setPower(0.6);
         frontRightDrive.setPower(-0.6);
         sleep(280);
         
         // Stop drive motors
         setDrivePower(0.0);
    }
    
    /**
     * Executes the launch sequence (powerDrive and servo push).
     */
    private void launchSequence() {
        intake.setPower(1);
        sleep(4500); // Intake for a long time
        intake.setPower(0);
        
        // Launch code 
        powerDrive.setPower(-1); // Start shooter motor
        sleep(1000); 
        push.setPosition(0.7); // Push the element
        sleep(500);
        push.setPosition(1.0); // Reset the pusher
        sleep(500);
        powerDrive.setPower(0); // Stop shooter motor
    }

    /**
     * Helper to set all drive motors' power.
     */
    private void setDrivePower(double power) {
        backLeftDrive.setPower(power);
        backRightDrive.setPower(power);
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
    }

    /**
     * Helper for basic forward/backward movement (Mecanum/Tank drive).
     * @param power Drive power.
     * @param strafe Strafe power (0 for tank drive).
     * @param durationMs Duration in milliseconds.
     */
    private void moveRobot(double power, double strafe, long durationMs) {
        // Simple Mecanum movement calculation (example)
        frontLeftDrive.setPower(power + strafe);
        frontRightDrive.setPower(power - strafe);
        backLeftDrive.setPower(power - strafe);
        backRightDrive.setPower(power + strafe);
        sleep(durationMs);
        setDrivePower(0.0);
    }
    
    /**
     * Helper for basic turning.
     * @param power Turn power (positive for one direction, negative for the other).
     * @param durationMs Duration in milliseconds.
     */
    private void turnRobot(double power, long durationMs) {
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(-power);
        sleep(durationMs);
        setDrivePower(0.0);
    }
    
    // --- 4. EasyOpenCV Pipeline for Purple Sphere Detection ---
    /**
     * This class handles the actual image processing for the purple sphere detection.
     * It divides the screen into three regions (Left, Center, Right) and checks for purple in each.
     * 
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
