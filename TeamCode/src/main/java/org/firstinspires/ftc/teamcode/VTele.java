package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class VTele extends OpMode {
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotorEx shooter;
    CRServo rightServo;
    CRServo leftServo;

    // final int READ_PERIOD = 1;

    HuskyLens huskyLens;


    public void init() {
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");

        shooter = (DcMotorEx) hardwareMap.get(DcMotor.class, "shooter");

        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        leftServo = hardwareMap.get(CRServo.class, "leftServo");

        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


   // boolean x = false;
    double targetVelocity = 0;
    boolean shootOn = false;

    public void loop() {

        // shooter/flywheel
        drivetrain();

        if (gamepad2.yWasPressed()) shootOn = !shootOn;

        shooter.setVelocity(targetVelocity);
        if (shootOn) {
            targetVelocity = 1800;
            if (Math.abs(shooter.getVelocity() - targetVelocity) < 10) {
                if (gamepad2.x) {
                    rightServo.setPower(1);
                    leftServo.setPower(-1);
                } else {
                    rightServo.setPower(0);
                    leftServo.setPower(0);
                }
            } else {
                rightServo.setPower(0);
                leftServo.setPower(0);
            }
        } else {
            targetVelocity = 0;
            if (gamepad2.b) {
                rightServo.setPower(-1);
                leftServo.setPower(1);
            } else if (gamepad2.x) {
                rightServo.setPower(1);
                leftServo.setPower(-1);
            } else {
                rightServo.setPower(0);
                leftServo.setPower(0);
            }
        }

        telemetry.addData("Shooter Power:", shooter.getPower());
        telemetry.addData("Current Velocity", shooter.getVelocity());
        telemetry.update();
    }

    public void drivetrain() {
        //Drivetrain
        double moveX = -gamepad1.left_stick_x;
        double moveY = -gamepad1.left_stick_y;
        double turnX = gamepad1.right_stick_x;

        double frontLeftPower = moveY + moveX + turnX;
        double frontRightPower = moveY - moveX - turnX;
        double backLeftPower = moveY - moveX + turnX;
        double backRightPower = moveY + moveX - turnX;

        frontLeftPower *= 0.98;
        backLeftPower *= 0.98;

        //Drivetrain Driver Controls
        if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1) {

            if (gamepad1.right_bumper) {
                leftFront.setPower(frontLeftPower);
                rightFront.setPower(frontRightPower);
                leftBack.setPower(backLeftPower);
                rightBack.setPower(backRightPower);
            } else if (gamepad1.left_bumper) {
                leftFront.setPower(frontLeftPower * 0.35);
                rightFront.setPower(frontRightPower * 0.35);
                leftBack.setPower(backLeftPower * 0.35);
                rightBack.setPower(backRightPower * 0.35);
            } else {
                leftFront.setPower(frontLeftPower * 0.8);
                rightFront.setPower(frontRightPower * 0.8);
                leftBack.setPower(backLeftPower * 0.8);
                rightBack.setPower(backRightPower * 0.8);
            }
        } else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }


    }

}

      /*  // huskylens

        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
        int idealWidth = 60;
        int about = 5;

        if (!huskyLens.knock()) {
            telemetry.addData(">Lens Status:", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData("Lens Status:", "Working");
        }

        if (gamepad1.aWasPressed()) x = !x;

        huskyLens.blocks();
        HuskyLens.Block[] blocks = huskyLens.blocks();
        telemetry.addData("Block count", blocks.length);
        if(blocks.length != 0) {
            HuskyLens.Block block = blocks[0];

//            if (block.id == 1) {
            telemetry.addData("Width", block.width);

            if (x) {
                if (block.width < idealWidth - about) {
                    telemetry.addData("Position:", "NOT ready");
                    leftFront.setPower(0.8);
                    rightFront.setPower(0.8);
                    leftBack.setPower(0.8);
                    rightBack.setPower(0.8);

                } else if (block.width > idealWidth + about) {
                    telemetry.addData("Position:", "NOT ready");
                    leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
                    rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

                    leftFront.setPower(0.8);
                    rightFront.setPower(0.8);
                    leftBack.setPower(0.8);
                    rightBack.setPower(0.8);
                } else {
                    telemetry.addData("Position:", "ready");
                    x = false;
                }
            }
            telemetry.addData("x", x);
        }
//        }

   */



