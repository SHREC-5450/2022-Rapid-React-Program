package frc.robot;

import edu.wpi.first.wpilibj.I2C;

public class ArduinoI2CServer {

    private int arduinoAddress;
    private I2C i2CServer;

    public ArduinoI2CServer(int arduinoAddress) {
        this.arduinoAddress = arduinoAddress;
        this.i2CServer = new I2C(I2C.Port.kOnboard, this.arduinoAddress);
    }

    public byte[] getData(char request) {
        byte buffer[] = new byte[24];
        byte toSend[] = {(byte)request};
        i2CServer.transaction(toSend, 1, buffer, 24);

        return buffer;
    }
}