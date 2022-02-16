package frc.robot;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import edu.wpi.first.wpilibj.I2C;

public class ArduinoI2CServer {

    private int arduinoAddress;
    private I2C i2CServer;

    public ArduinoI2CServer(int arduinoAddress) {
        this.arduinoAddress = arduinoAddress;
        this.i2CServer = new I2C(I2C.Port.kOnboard, this.arduinoAddress);
    }

    public FloatBuffer getData(char request) {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        

        return buffer.asFloatBuffer();
    }
}
