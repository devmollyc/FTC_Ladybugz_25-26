package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

//add backroll to shooter
@TeleOp
public class MainTele2 extends OpMode {
    private DcMotor rightFront;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor intake;
    private DcMotorEx shooter;
    private double ticksPerRev;
    private Servo pusher;
    private DcMotor middle;


    @Override
    public void init() {
        //drivetrain init
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");

        //shooter init
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ticksPerRev = shooter.getMotorType().getTicksPerRev();


        intake = hardwareMap.get(DcMotor.class, "intake");
        middle = hardwareMap.get(DcMotor.class, "middle");
        pusher = hardwareMap.get(Servo.class, "pusher");
        pusher.setPosition(0.6);
        // pusher.scaleRange(0.5, 1.0);

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        shooter.setDirection(DcMotor.Direction.FORWARD);

        middle.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    @Override
    public void loop() {
        drivetrain();
        allintake();
        try {
            allshoot();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //shooter data
        double rotations = shooter.getCurrentPosition() / ticksPerRev;
        double targetV = 0; //need to update
        telemetry.addData("Shooter Power:", shooter.getPower());
        telemetry.addData("Encoder:", shooter.getCurrentPosition());
        telemetry.addData("Velocity:", shooter.getVelocity());
        telemetry.addData("max ticks:", ticksPerRev);

        //middle
        if (gamepad2.x || gamepad1.x) {
            middle.setDirection(DcMotor.Direction.FORWARD);
            middle.setPower(1);
        } else if (gamepad2.b){
            middle.setDirection(DcMotor.Direction.REVERSE);
            middle.setPower(1);
        } else {
            middle.setPower(0);
        }


       //pusher
        if (gamepad2.left_bumper) {
            pusher.setPosition(0.35);
        } else {
            pusher.setPosition(0.6);
        }


        telemetry.addData("pusher pos:", pusher.getPosition());

        telemetry.update();
    }

    public void allshoot() throws InterruptedException {
        if (gamepad2.aWasPressed()){
            shooter.setDirection(DcMotor.Direction.FORWARD);
            shooter.setPower(0.75); // need to find target v
            intake.setPower(1);
        } else if (gamepad2.yWasPressed()) {
            shooter.setPower(0);
            intake.setPower(0);
        }

        if (gamepad2.dpadUpWasPressed()){
            shooter.setVelocity(shooter.getVelocity() + 50.0);
        } else if (gamepad2.dpadDownWasPressed()) {
            shooter.setVelocity(shooter.getVelocity() - 50.0);

        telemetry.update();

        }

    }

    public void allintake() {
        if (gamepad1.right_bumper || gamepad2.right_bumper){
            intake.setPower(1);
        } else {
            intake.setPower(0);
        }

    }

    public void drivetrain() {
        //Drivetrain
        double moveX = gamepad1.left_stick_x;
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
                telemetry.addLine("drive multiplier: 1");
            } else if (gamepad1.left_bumper) {
                leftFront.setPower(frontLeftPower * 0.35);
                rightFront.setPower(frontRightPower * 0.35);
                leftBack.setPower(backLeftPower * 0.35);
                rightBack.setPower(backRightPower * 0.35);
                telemetry.addLine("drive multiplier: 0.35");
            } else {
                leftFront.setPower(frontLeftPower * 0.8);
                rightFront.setPower(frontRightPower * 0.8);

                leftBack.setPower(backLeftPower * 0.8);
                rightBack.setPower(backRightPower * 0.8);
                telemetry.addLine("drive multiplier: 0.8");

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
