package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MainTele extends OpMode {
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor shooter;

    CRServo rightServo;
    CRServo leftServo;

    final int READ_PERIOD = 30;

    HuskyLens huskyLens;



    public void init(){
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        leftServo = hardwareMap.get(CRServo.class, "leftServo");

        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop() {

//Drivetrain
        double moveX = -gamepad1.left_stick_x;
        double moveY = -gamepad1.left_stick_y;
        double turnX = gamepad1.right_stick_x;

        double frontLeftPower = moveY + moveX + turnX;
        double frontRightPower = moveY - moveX - turnX;
        double backLeftPower = moveY - moveX + turnX;
        double backRightPower = moveY + moveX - turnX;

        frontLeftPower *= 0.86;
        backLeftPower *= 0.86;

        //Drivetrain Driver Controls
        if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1) {

            if (gamepad1.right_bumper) {
                leftFront.setPower(frontLeftPower * 0.8);
                rightFront.setPower(frontRightPower * 0.8);
                leftBack.setPower(backLeftPower * 0.8);
                rightBack.setPower(backRightPower * 0.8);
            } else if (gamepad1.left_bumper) {
                leftFront.setPower(frontLeftPower * 0.25);
                rightFront.setPower(frontRightPower * 0.25);
                leftBack.setPower(backLeftPower * 0.25);
                rightBack.setPower(backRightPower * 0.25);
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

        // shooter/flywheel

        telemetry.addData("Shooter Power:", shooter.getPower());


        if (gamepad2.yWasPressed()) {
            shooter.setPower(1);
        } else if (gamepad2.aWasPressed()) {
            shooter.setPower(0);
        }


        if (gamepad2.b) {
            rightServo.setPower(1);
            leftServo.setPower(-1);
        } else if (gamepad2.x) {
            rightServo.setPower(-1);
            leftServo.setPower(1);
        } else {
            rightServo.setPower(0);
            leftServo.setPower(0);
        }

        if (gamepad2.dpadUpWasPressed()) {
            shooter.setPower(shooter.getPower() + 0.05);
        } else if (gamepad2.dpadDownWasPressed()) {
            shooter.setPower(shooter.getPower() - 0.05);
        }

        // huskylens
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
        int idealWidth = 60;
        int about = 5;

        if (!huskyLens.knock()) {
            telemetry.addData(">Lens Status:", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData("Lens Status:", "Working");
        }

        huskyLens.blocks();
        HuskyLens.Block[] blocks = huskyLens.blocks();
        telemetry.addData("Block count", blocks.length);
        for (HuskyLens.Block block : blocks) {
            if (block.id == 1) {
                telemetry.addData("Width", block.width);
                telemetry.update();
                if (gamepad1.a){
                    if (block.width < idealWidth - about){
                        telemetry.addData("Position:", "NOT ready");
                        leftFront.setPower(0.8);
                        rightFront.setPower(0.8);
                        leftBack.setPower(0.8);
                        rightBack.setPower(0.8);

                    } else if (block.width > idealWidth + about){
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
                    }
                }
            }
        }
    }
    
    }



