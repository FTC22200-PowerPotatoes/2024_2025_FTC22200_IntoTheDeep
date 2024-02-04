package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Back Final", group = "CenterStage", preselectTeleOp = "Full")
public class Auto_BlueBackLimited extends CSBase {
    @Override
    public void runOpMode() {
        setup(color.b);

        // ---------------------
        // ------Main Code------
        // ---------------------

        drive(tilesToInches(-2.1));
        turn(90);
        setSpeed(1000);
        drive(tilesToInches(1.7));
        setSpeed(2000);
        ejectPixel();

        telemetry.addData("Path", "Complete");
        telemetry.update();
        s(1);  // Pause to display final telemetry message.
    }
}