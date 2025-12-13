package org.firstinspires.ftc.teamcode.util

class Utils {
    fun delay(millis: Long, nanos: Int = 0) {
        try {
            Thread.sleep(millis, nanos);
        }
        catch ( e: InterruptedException ) {
            Thread.currentThread().interrupt();
        }
    }
}