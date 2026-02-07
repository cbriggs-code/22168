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
    
    @Autonomous(name = "Blue Bottom Pathing Autonomous", group = "Autonomous")
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
    public PathChain Path1;
public PathChain Path2;
public PathChain Path3;
public PathChain Path4;
public PathChain Path5;
public PathChain Path6;
public PathChain Path7;
public PathChain Path8;
public PathChain Path9;
    
    public Paths(Follower follower) {
      Path1 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(59.738, 9.346),
            
            new Pose(41.346, 100.822)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(140))
        
        .build();

Path2 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(41.346, 100.822),
            
            new Pose(23.626, 119.084)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(140))
        
        .build();

Path3 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(23.626, 119.084),
            
            new Pose(45.159, 97.290)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
        
        .build();

Path4 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(45.159, 97.290),
            
            new Pose(45.430, 34.607)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
        
        .build();

Path5 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(45.430, 34.607),
            
            new Pose(16.991, 35.084)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
        
        .build();

Path6 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(16.991, 35.084),
            
            new Pose(45.477, 34.551)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
        
        .build();

Path7 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(45.477, 34.551),
            
            new Pose(45.234, 97.477)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
        
        .build();

Path8 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(45.234, 97.477),
            
            new Pose(23.421, 119.065)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(140))
        
        .build();

Path9 = follower.pathBuilder().addPath(
          new BezierLine(
            new Pose(23.421, 119.065),
            
            new Pose(30.131, 127.308)
          )
        ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(140))
        
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
              case 6:
                    //if (!follower.isBusy()) {
                        follower.followPath(paths.Path7, true);
                        setPathState(7);
                    //}
                    break;
              case 7:
                    //if (!follower.isBusy()) {
                        follower.followPath(paths.Path8, true);
                        setPathState(8);
                    //}
                    break;
              case 8:
                    //if (!follower.isBusy()) {
                        follower.followPath(paths.Path9, true);
                        setPathState(9);
                    //}
                    break;
              case 9:
                    //if (!follower.isBusy()) {
                        follower.followPath(paths.Path10, true);
                        setPathState(10);
                    //}
                    break;
            }
        }
        return pathState;
    }
    public void setPathState(int pState) {
        pathState = pState;
    }
}
    
