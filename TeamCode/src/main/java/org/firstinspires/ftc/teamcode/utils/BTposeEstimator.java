package org.firstinspires.ftc.teamcode.utils;

import com.arcrobotics.ftclib.geometry.Pose2d;
import org.firstinspires.ftc.teamcode.utils.HolonomicOdometry;

import java.util.function.DoubleSupplier;

public class BTposeEstimator extends HolonomicOdometry {
    //todo: find out how northstar finds error from genericPNP with SOLVEPNP_IPPE_SQUARE info in - https://docs.opencv.org/4.x/d5/d1f/calib3d_solvePnP.html

    public BTposeEstimator(DoubleSupplier leftEncoder, DoubleSupplier rightEncoder, DoubleSupplier horizontalEncoder, DoubleSupplier gyroAngle, double trackWidth, double centerWheelOffset) {
        super(leftEncoder, rightEncoder, horizontalEncoder, gyroAngle, trackWidth, centerWheelOffset);
    }

    public BTposeEstimator(Pose2d initialPose, double trackwidth, double centerWheelOffset) {
        super(initialPose, trackwidth, centerWheelOffset);
    }

    public BTposeEstimator(double trackwidth, double centerWheelOffset) {
        super(trackwidth, centerWheelOffset);
    }

    public void setPoseToCameraPose(Pose2d cameraPose){
        updatePose();
        robotPose = cameraPose;

    }
}
