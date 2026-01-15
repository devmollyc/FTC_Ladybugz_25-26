
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "auto", name = "RedShoot")
public class RedShoot extends LinearOpMode {
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor shooter;

    CRServo rightServo;
    CRServo leftServo;

    ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");

        shooter = hardwareMap.get(DcMotor.class, "shooter");

        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        leftServo = hardwareMap.get(CRServo.class, "leftServo");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);



        waitForStart();

        runtime.reset();
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);

        // moving forward
        while (opModeIsActive() && (runtime.seconds() < 9)) {
            shooter.setPower(0.8);
            if (runtime.seconds() > 1 && runtime.seconds() < 1.8) {
                rightFront.setPower(-0.5);
                leftFront.setPower(-0.5);
                rightBack.setPower(-0.5);
                leftBack.setPower(-0.5);
            } else if (runtime.seconds() > 1.8 && runtime.seconds() < 1.9) {
                rightFront.setPower(0);
                leftFront.setPower(0);
                rightBack.setPower(0);
                leftBack.setPower(0);
            } else if (runtime.seconds() > 2 && runtime.seconds() < 6) {
                rightFront.setPower(0);
                rightBack.setPower(0);

                rightServo.setPower(1);
                leftServo.setPower(-1);
            } else if (runtime.seconds() > 6 && runtime.seconds() < 9) {
                rightFront.setPower(0.5);
                leftFront.setPower(-0.5);
                rightBack.setPower(-0.5);
                leftBack.setPower(0.5);
                }
        }
    }
}