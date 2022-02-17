package frc.robot;

public class CustomGyroscope {
    
    private ArduinoI2CServer arduino;

    public CustomGyroscope(ArduinoI2CServer arduino) {
        this.arduino = arduino;
    }

    public float[] getOrientation() {
        return getRaw('o');
    }

    public float getOrientationX() {
        return getOrientation()[0];
    }

    public float getOrientationY() {
        return getOrientation()[1];
    }

    public float getOrientationZ() {
        return getOrientation()[2];
    }

    public float[] getAcceleration() {
        return getRaw('a');
    }

    public float getAccelerationX() {
        return getAcceleration()[0];
    }

    public float getAccelerationY() {
        return getAcceleration()[1];
    }

    public float getAccelerationZ() {
        return getAcceleration()[2];
    }

    private float[] getRaw(char request) {
        byte temp[] = arduino.getData(request);

        String str[] = {
            String.valueOf((char)temp[0]),
            String.valueOf((char)temp[8]),
            String.valueOf((char)temp[16])
        };

        int i = 1;
        while(i < 7) {
            str[0] += String.valueOf((char)temp[i]);
            i++;
        }
        
        i+=2;
        while (i < 15) {
            str[1] += String.valueOf((char)temp[i]);
            i++;
        }

        i+=2;
        while (i < 23) {
            str[1] += String.valueOf((char)temp[i]);
            i++;
        }

        float temp1[] = {
            Float.parseFloat(str[0]),
            Float.parseFloat(str[1]),
            Float.parseFloat(str[2])
        };

        return temp1;
    }
}