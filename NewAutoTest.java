import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import com.qualcomm.robotcore.util.ElapsedTime;

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class NewAutoTest extends LinearOpMode {
    
    @Override
    public void runOpMode() {
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor powerDrive;
    DcMotor intake;
    Servo push;
    USB Camera;
        
   private ElapsedTime runtime = new ElapsedTime();
        
        backLeftDrive = hardwareMap.get(DcMotor.class, "m1");
        backRightDrive = hardwareMap.get(DcMotor.class, "m2");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "m3");
        frontRightDrive = hardwareMap.get(DcMotor.class, "m4");
        powerDrive = hardwareMap.get(DcMotor.class, "spinLaunch");
        intake = hardwareMap.get(DcMotor.class, "intake");
        push = hardwareMap.get(Servo.class, "push");
        Camera = hardwareMap.get(USB.class, "Camera");
        Timer = hardwareMap.get(timer.class, "Timer");
        //powerDrive = hardwareMap.get(DcMotor.class, "powerDrive");
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        Boolean thelongdriveback = false;
        waitForStart();
        //while (opModeIsActive()) {
        if (runtime.seconds(0) >= 30.0){
            backLeftDrive.setPower(0);
            backRightDrive.setPower(0);
            frontLeftDrive.setPower(0);
            frontRightDrive.setPower(0);
            powerDrive.setPower(0);
            intake.setPower(0);
            push.setPower(0);
            Camera.setPower(0);
        }
            
            backLeftDrive.setPower(1);
            backRightDrive.setPower(1);
            frontLeftDrive.setPower(1);
            frontRightDrive.setPower(1);
            sleep(780);
            backLeftDrive.setPower(0.5);
            backRightDrive.setPower(-0.5);
            frontLeftDrive.setPower(0.6);
            frontRightDrive.setPower(-0.6);
            sleep(280);
            backLeftDrive.setPower(.3);
            backRightDrive.setPower(.3);
            frontLeftDrive.setPower(.3);
            frontRightDrive.setPower(.3);
            sleep(360);
            backLeftDrive.setPower(.3);
            backRightDrive.setPower(.3);
            frontLeftDrive.setPower(.3);
            frontRightDrive.setPower(.3);
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
        

    if{
    enum DetectedColor {
        PURPLE,
        GREEN,
        NONE // Used if no required color is detected
    }

    /**
     * Represents a detected AprilTag, including its ID and pixel location (center).
     * In a real system, this would hold more data like rotation, size, and corner coordinates.
     */
    static class AprilTagDetection {
        final int id;
        final int x; // Center X coordinate in pixels
        final int y; // Center Y coordinate in pixels

        public AprilTagDetection(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("Tag ID: %d @ (%d, %d)", id, x, y);
        }
    }

    /**
     * The result of processing the items associated with a single AprilTag.
     */
    static class OrderDetectionResult {
        final int tagId;
        final List<DetectedColor> detectedOrder;
        final boolean isValid;

        public OrderDetectionResult(int tagId, List<DetectedColor> detectedOrder, boolean isValid) {
            this.tagId = tagId;
            this.detectedOrder = detectedOrder;
            this.isValid = isValid;
        }

        @Override
        public String toString() {
            String status = isValid ? "VALID" : "INVALID (Missing items)";
            return String.format("Tag %d | Status: %s | Order: %s", tagId, status, detectedOrder);
        }
    }

    // --- Vision Processing Core Logic ---

    /**
     * Abstract method simulating the color detection within a specific Region of Interest (ROI).
     * * In a real OpenCV/FTC implementation, this would:
     * 1. Define the ROI based on the AprilTag coordinates.
     * 2. Convert the image (Mat) to the HSV colorspace.
     * 3. Apply the PURPLE (low/high) mask and GREEN (low/high) mask.
     * 4. Count the number of non-zero pixels in each mask within the ROI.
     * 5. Return the color with the highest pixel count above a set threshold.
     * * @param regionId A placeholder to indicate which item's region we are checking (1, 2, or 3).
     * @return The color detected in the ROI.
     */
    private DetectedColor detectColorInROI(int regionId) {
        // --- REAL-WORLD CODE WOULD GO HERE (OpenCV Color Masking) ---
        // For simulation, we'll return fixed, ordered colors for demonstration.
        switch (regionId) {
            case 1:
                return DetectedColor.PURPLE;
            case 2:
                return DetectedColor.GREEN;
            case 3:
                return DetectedColor.PURPLE;
            default:
                return DetectedColor.NONE;
        }
    }

    /**
     * The main vision pipeline method. It takes a list of AprilTags detected
     * in the current frame and determines the color order for the associated 
     * 3-item set (max 2 Purple, 1 Green).
     * * @param tags A list of AprilTag detections from the frame.
     * @return A list of order results for each primary AprilTag detected.
     */
    public List<OrderDetectionResult> processFrame(List<AprilTagDetection> tags) {
        List<OrderDetectionResult> allResults = new ArrayList<>();

        // 1. Iterate over each AprilTag (assuming the tag marks the start/center of a set)
        for (AprilTagDetection tag : tags) {
            System.out.println("\nProcessing set anchored by: " + tag);

            List<DetectedColor> colorOrder = new ArrayList<>();
            int purpleCount = 0;
            int greenCount = 0;
            boolean isValid = true;

            // 2. Define 3 separate Regions of Interest (ROIs) relative to the Tag's position (x, y).
            // Example:
            // Item 1 ROI: (tag.x + 100, tag.y)
            // Item 2 ROI: (tag.x + 200, tag.y)
            // Item 3 ROI: (tag.x + 300, tag.y)

            // We simulate checking three distinct regions for the items 1, 2, and 3.
            for (int i = 1; i <= 3; i++) {
                DetectedColor color = detectColorInROI(i);
                colorOrder.add(color);

                if (color == DetectedColor.PURPLE) {
                    purpleCount++;
                } else if (color == DetectedColor.GREEN) {
                    greenCount++;
                } else {
                    // If any item is NONE, the set is invalid or incomplete for this demonstration
                    isValid = false; 
                }
            }

            // 3. Validation: Check if the set meets the required composition (2 Purple, 1 Green)
            // Note: The color detection logic above already dictates the colors, but in a real-world
            // scenario, this check ensures we only process sets that adhere to the 2P/1G rule.
            if (purpleCount != 2 || greenCount != 1) {
                System.out.println("  -> WARNING: Set composition is not (2 Purple, 1 Green)! Actual: P=" + purpleCount + ", G=" + greenCount);
                isValid = false; // Override validity if composition fails
            }


            // 4. Create and store the result
            OrderDetectionResult result = new OrderDetectionResult(tag.id, colorOrder, isValid);
            allResults.add(result);
            System.out.println("  -> Result: " + result);
        }

        return allResults;
    }

    // --- Main Method for Demonstration ---

    public static void main(String[] args) {
        System.out.println("--- Camera Vision System Initialized ---");
        
        NewAutoTest processor = new NewAutoTest();

        // 1. Mock AprilTag Input (Simulating tags found in a frame)
        List<AprilTagDetection> frameTags = new ArrayList<>();
        // Tag 5 is the anchor for our successful set (P, G, P)
        frameTags.add(new AprilTagDetection(5, 500, 300)); 
        // Tag 2 is another tag detected, maybe it anchors a different set
        frameTags.add(new AprilTagDetection(2, 100, 800)); 

        // 2. Process the frame
        List<OrderDetectionResult> results = processor.processFrame(frameTags);

        // 3. Output Final Command/Data
        System.out.println("\n--- Final Order Detection Summary ---");
        for (OrderDetectionResult result : results) {
            if (result.isValid) {
                // This is the data you would send to your robot/control system
                System.out.printf("Action for Tag %d: Detected valid order -> %s\n", 
                    result.tagId, result.detectedOrder);
            } else {
                System.out.printf("Action for Tag %d: Invalid set, skipping or executing fallback.\n", 
                    result.tagId);
            }
        }
        
        System.out.println("\n[Note: This is a simulation. The 'detectColorInROI' method would contain actual OpenCV/computer vision logic.]");
    }
}
