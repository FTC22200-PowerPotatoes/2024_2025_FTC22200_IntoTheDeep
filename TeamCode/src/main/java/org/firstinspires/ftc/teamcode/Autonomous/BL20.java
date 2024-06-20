package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.gampad;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.packet;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.time;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.voltage;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive.imuMultiply;
import static java.lang.Math.min;
import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robots.BradBot;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class BL20 {
    boolean logi=false, isRight;
    LinearOpMode op;
    BradBot robot;
    int bark = 0, delaySec =0, barg=0;
    TrajectorySequence[] spikey = new TrajectorySequence[3];
    TrajectorySequence[] intake = new TrajectorySequence[3];
    TrajectorySequence[] backToStack = new TrajectorySequence[3];
    TrajectorySequence[] droppy = new TrajectorySequence[3];
    TrajectorySequence[] drop = new TrajectorySequence[3];
    TrajectorySequence[] park = new TrajectorySequence[2];





    public BL20(LinearOpMode op, boolean isLogi){
        logi = isLogi;
        this.op=op;
        robot = new BradBot(op, false,isLogi);
        Pose2d startPose = new Pose2d(-37.5,61.5,toRadians(90));
        robot.roadrun.setPoseEstimate(startPose);
        imuMultiply = 1.039 + .002*(robot.getVoltage()-12.5);
        spikey[0] = robot.roadrun
                .trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-32,32,toRadians(180)),toRadians(-30))
                .build();

        spikey[1] = robot.roadrun
                .trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-40,30,toRadians(90)))
                .build();

        spikey[2] = robot.roadrun
                .trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-45,36,toRadians(90)))
                .build();

        if (!isLogi) {
            droppy[0] =
                    robot
                            .roadrun
                            .trajectorySequenceBuilder(spikey[0].end())
                            .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(30))
                            .lineToLinearHeading(new Pose2d(-40,38,toRadians(180)))
                            .lineToLinearHeading(new Pose2d(-40,58,toRadians(180)))
                            .lineToLinearHeading(new Pose2d(27,58,toRadians(180)))
                            .lineToLinearHeading(new Pose2d(38, 36, toRadians(-180)))
                            .lineToLinearHeading(new Pose2d(45.5,42,toRadians(180)))
                            .build();

            droppy[1] =
                    robot
                            .roadrun
                            .trajectorySequenceBuilder(spikey[1].end())
                            .lineToLinearHeading(new Pose2d(-40,58,toRadians(180)))
                            .lineToLinearHeading(new Pose2d(27,58,toRadians(180)))
                            .lineToLinearHeading(new Pose2d(45.5,35.5,toRadians(180)))
                            .build();

            droppy[2] =
                    robot
                            .roadrun
                            .trajectorySequenceBuilder(spikey[2].end())
                            .lineToLinearHeading(new Pose2d(-40,58,toRadians(180)))
                            .lineToLinearHeading(new Pose2d(27,58,toRadians(180)))
                            .lineToLinearHeading(new Pose2d(45,30,toRadians(180)))
                            .build();

        } else{
        }

        park[0] = robot.roadrun.trajectorySequenceBuilder(droppy[1].end())
                .lineToLinearHeading(new Pose2d(43,57, toRadians(180)))
                .build();
        park[1] = robot.roadrun.trajectorySequenceBuilder(droppy[1].end())
                .lineToLinearHeading(new Pose2d(43,20, toRadians(180)))
                .build();

//    robot.dropServo(1);
//    robot.dropServo(0);
        robot.setRight(false);
        robot.setBlue(true);
        robot.observeSpike();
        robot.hoverArm();
    }
    public void waitForStart(){
        while (!op.isStarted() || op.isStopRequested()) {
            bark = robot.getSpikePos();
            op.telemetry.addData("pixel", bark);
            packet.put("spike", bark);
            op.telemetry.addData("delaySec", delaySec);
            op.telemetry.addData("barg,0=L,1=R", barg);
            if (gampad.readGamepad(op.gamepad1.dpad_up, "gamepad1_dpad_up", "addSecs")) {
                delaySec++;
            }
            if (gampad.readGamepad(op.gamepad1.dpad_down, "gamepad1_dpad_down", "minusSecs")) {
                delaySec = min(0, delaySec - 1);
            }
            if (gampad.readGamepad(op.gamepad1.dpad_right, "gamepad1_dpad_right", "parkRight")) {
                barg=1;
            }
            if (gampad.readGamepad(op.gamepad1.dpad_left, "gamepad1_dpad_left", "parkLeft")) {
                barg=0;
            }
            robot.update();
        }
        op.resetRuntime();
        time=0;
    }
    public void purp()
    {
        robot.queuer.queue(false, true);
        robot.queuer.addDelay(delaySec);
        robot.queuer.waitForFinish();
        robot.followTrajSeq(spikey[bark]);
    }

    public void pre(){
        robot.queuer.waitForFinish();
        robot.followTrajSeq(droppy[bark]);
        if(bark==0) {
            robot.lowAuto(true);
            robot.yellowAuto(true);
            robot.drop(44);
        }
        else if(bark==1){
            robot.lowAuto(true);
            robot.yellowAuto(true);
            robot.drop(43.5);
        }
        else {
            robot.lowAuto(false);
            robot.yellowAuto(false);
            robot.drop(44);
        }
    }

    public void park(){
        robot.followTrajSeq(park[barg]);
        robot.queuer.addDelay(.5);
        robot.resetAuto();
        robot.queuer.waitForFinish();
        robot.queuer.queue(false, true);
    }

    public void update(){
        robot.update();
        robot.queuer.setFirstLoop(false);
    }

    public boolean isAutDone(){
        return !robot.queuer.isFullfilled()&&time<29.8;
    }
}
