package org.firstinspires.ftc.teamcode.mcanally;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MathOpModeLM extends OpMode {
    @Override
    public void init() {
    }

    @Override
    public void loop () {
        double speedforward = -gamepad1.left_stick_y / 2.0;
        telemetry.addData("Left stick y", gamepad1.left_stick_y);
        telemetry.addData("speed Forward", speedforward);
    }
}