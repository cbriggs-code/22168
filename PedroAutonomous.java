
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.bylazar.telemetry.PanelsTelemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;


@Autonomous(name = "Pedro Pathing Autonomous", group = "Autonomous")
@Configurable // Panels
public class PedroAutonomous extends OpMode {
    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState = 0; // Current autonomous path state (state machine)
    private Paths paths; // Paths defined in the Paths class

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));

        paths = new Paths(follower); // Build paths

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }
    public void start(){
        setPathState(0);
    }

    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
         autonomousPathUpdate(); // Update autonomous state machine

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.debug("pathState: ",pathState);
        panelsTelemetry.update(telemetry);
    }


    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(123.718, 124.298),

                                    new Pose(101.199, 100.032)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(38), Math.toRadians(45))

                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(101.199, 100.032),

                                    new Pose(101.199, 83.900)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))

                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(101.199, 83.900),

                                    new Pose(109.473, 83.900)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(0))

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(109.473, 83.900),

                                    new Pose(90.704, 83.724)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Path5 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(90.704, 83.724),

                                    new Pose(100.461, 72.145)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Path6 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(100.461, 72.145),

                                    new Pose(101.302, 99.718)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();
        }
    }
//    public enum PathState {
//        Path1,
//        Path2,
//        Path3,
//        Path4,
//        Path5,
//        Path6
//    }

    public int autonomousPathUpdate() {
        if(!follower.isBusy()) {
            switch (pathState) {
                case 0:
                    setPathState(1);
                    follower.followPath(paths.Path1, true);
                    break;
                case 1:
                    //if (!follower.isBusy()) {
                        setPathState(2);
                        follower.followPath(paths.Path2, true);
                    //}
                    break;
                case 2:
                    //if (!follower.isBusy()) {
                        setPathState(3);
                        follower.followPath(paths.Path3, true);
                    //}
                    break;
                case 3:
                    //if (!follower.isBusy()) {
                        setPathState(4);
                        follower.followPath(paths.Path4, true);
                    //}
                    break;
                case 4:
                    //if (!follower.isBusy()) {
                        setPathState(5);
                        follower.followPath(paths.Path5, true);
                    //}
                    break;
                case 5:
                    //if (!follower.isBusy()) {
                        follower.followPath(paths.Path6, true);
                        setPathState(6);
                    //}
                    break;
                default:
                    telemetry.addLine("all done (:");
                    break;

            }
        }
        // Event markers will automatically trigger at their positions
        // Make sure to register NamedCommands in your RobotContainer
        return pathState;
    }
    public void setPathState(int pState) {
        pathState = pState;
    }


}
    
