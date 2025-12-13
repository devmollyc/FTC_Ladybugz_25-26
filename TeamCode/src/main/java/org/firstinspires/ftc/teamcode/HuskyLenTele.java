/* package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.hardware.dfrobot.HuskyLens;

@TeleOp
public class HuskyLenTele extends OpMode {
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor shooter;

    CRServo rightServo;
    CRServo leftServo;

    // final int READ_PERIOD = 1;

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


    boolean x = false;

    public void loop() {

        // huskylens
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
        int idealWidth = 40;
        int about = 5;

     /*   if (!huskyLens.knock()) {
            telemetry.addData(">Lens Status:", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData("Lens Status:", "Working");
        }
       // huskyLens.blocks();

      //  telemetry.addData("Block count", huskyLens.blocks.length);
    //    HuskyLens.Block block = getBlockById(HuskyLens huskylens, int 1);

  //      if (gamepad1.aWasPressed()) x = !x;
//            if (block.id == 1) {

        /*        if (block.width < idealWidth - about) {
                    telemetry.addData("Position:", "NOT ready");
                    moveForward();

                } else if (block.width > idealWidth + about) {
                    telemetry.addData("Position:", "NOT ready");
                   moveBackward();
                } else {
                    telemetry.addData("Position:", "Ready");
                    x = false;
                    stopMotors();
                }
            }
            telemetry.addData("x", x);

        }
     // functions/methods

        private HuskyLens.Block getBlock(HuskyLens husky, int id){
        for (HuskyLens.Block b : husky.blocks()){
            if(b.id == id){
                telemetry.addData("Tag found:", b.id);
                return b;
            } else {
                telemetry.addLine("Tag NOT Found");
                return null;
            }
        } }

        private void moveForward(){
            leftFront.setPower(0.8);
            rightFront.setPower(0.8);
            leftBack.setPower(0.8);
            rightBack.setPower(0.8);
        }

    private void moveBackward(){
        leftFront.setPower(-0.8);
        rightFront.setPower(-0.8);
        leftBack.setPower(-0.8);
        rightBack.setPower(-0.8);
    }

    private void stopMotors(){
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }


    }


*/
