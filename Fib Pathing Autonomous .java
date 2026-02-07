
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
    
    @Autonomous(name = "Fib Pathing Autonomous", group = "Autonomous")
    @Configurable // Panels
    public class PedroAutonomous extends OpMode {
      private TelemetryManager panelsTelemetry; // Panels Telemetry instance
      public Follower follower; // Pedro Pathing follower instance
      private int pathState; // Current autonomous path state (state machine)
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
      
      @Override
      public void loop() {
        follower.update(); // Update Pedro Pathing
        pathState = autonomousPathUpdate(); // Update autonomous state machine

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
      }

      
  public static class Paths {
    public PathChain Path2;
public PathChain Path2;
public PathChain Path3;
    
    public Paths(Follower follower) {
      Path2 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(19.813, 122.393),
            
            new Pose(19.991, 122.327)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(143))
        
        .build();

Path2 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(19.991, 122.327),
            
            new Pose(24.327, 119.112)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(143))
        
        .build();

Path3 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(24.327, 119.112),
            
            new Pose(34.402, 131.224)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(143))
        
        .build();
    }
  }
  

      public void autonomousPathUpdate() {
         if(!follower.isBusy()) {
            switch (pathState) {
                case 0:
                    setPathState(1);
                    follower.followPath(paths.Path1, true);
                    break;
                case 1:
                        setPathState(2);
                        follower.followPath(paths.Path2, true);
                    break;
                case 2:
                        setPathState(3);
                        follower.followPath(paths.Path3, true);
                    break;
                case 3:
                        setPathState(4);
                        follower.followPath(paths.Path4, true);
            }
        }
        return pathState;
    }
    public void setPathState(int pState) {
        pathState = pState;
    }


}
