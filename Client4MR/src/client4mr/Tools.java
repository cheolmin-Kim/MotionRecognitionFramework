package client4mr;

import java.util.BitSet;

import com.raspoid.exceptions.RaspoidInterruptedException;


public class Tools {
    
    /* -----------------------------------------------
     *                    LOGGING
     * ---------------------------------------------*/
    private static boolean displayLogs = true;
    private static boolean displayDebugs = true;
    private static boolean colorsEnabled = true;
    
    /**
     * Private constructor to hide the implicit public one.
     */
    private Tools() {
    }
    
    /**
     * Colors that can be used to print log and debug messages to standard output.
     */
    public enum Color {
        
        /**
         * ANSI reset code - needed to stop the use of a color arround a String.
         */
        ANSI_RESET("\u001B[0m"),
        
        /**
         * ANSI black code.
         */
        ANSI_BLACK("\u001B[30m"),
        
        /**
         * ANSI red code.
         */
        ANSI_RED("\u001B[31m"),
        
        /**
         * ANSI green code.
         */
        ANSI_GREEN("\u001B[32m"),
        
        /**
         * ANSI yellow code.
         */
        ANSI_YELLOW("\u001B[33m"),
        
        /**
         * ANSI blue code.
         */
        ANSI_BLUE("\u001B[34m"),
        
        /**
         * ANSI purple code.
         */
        ANSI_PURPLE("\u001B[35m"),
        
        /**
         * ANSI cyan code.
         */
        ANSI_CYAN("\u001B[36m"),
        
        /**
         * ANSI white code.
         */
        ANSI_WHITE("\u001B[37m");

        private String code = "";
        Color(String code) {
            this.code = code;
        }
        
        @Override
        public String toString() {
            return code;
        }
    }
    
    /**
     * Print a new colored log message to the standard output, if the logs are enabled.
     * @param obj the object to print in the log content (obj.toString()).
     * @param color the color used to print the log.
     * @see #enableLogs()
     * @see #enableColors()
     * @see #disableLogs()
     * @see #disableColors()
     */
    public static void log(Object obj, Color color) {
        log(obj.toString(), color);
    }
    

    public static void log(String message, Color color) {
        if(displayLogs)
            System.out.println(colorsEnabled ? color + message + Color.ANSI_RESET : message); // NOSONAR
    }
    

    public static void log(Object obj) {
        log(obj.toString());
    }


    public static void log(String message) {
        if(displayLogs)
            System.out.println(message); // NOSONAR
    }
    

    public static void debug(Object obj, Color color) {
        debug(obj.toString(), color);
    }
    

    public static void debug(String message, Color color) {
        if(displayDebugs)
            System.out.println(colorsEnabled ? color + message + Color.ANSI_RESET : message); // NOSONAR
    }
    

    public static void debug(Object obj) {
        debug(obj.toString());
    }
    

    public static void debug(String message) {
        if(displayDebugs)
            System.out.println(message); // NOSONAR
    }
    
    /**
     * Enable printing log messages to the standard output.
     */
    public static void enableLogs() {
        displayLogs = true;
    }
    
    /**
     * Disable printing log messages to the standard output.
     */
    public static void disableLogs() {
        displayLogs = false;
    }
    
    /**
     * Enable printing debug messages to the standard output.
     */
    public static void enableDebugs() {
        displayDebugs = true;
    }
    
    /**
     * Disable printing debug messages to the standard output.
     */
    public static void disableDebugs() {
        displayDebugs = false;
    }
    
    /**
     * Enable the use of colors to print log/debug messages.
     */
    public static void enableColors() {
        colorsEnabled = true;
    }
    
    /**
     * Disable the use of colors to print log/debug messages.
     */
    public static void disableColors() {
        colorsEnabled = false;
    }
    
    /* -----------------------------------------------
     *                    OTHERS
     * ---------------------------------------------*/


    public static String getBinaryString(byte[] byteArray) {
        String result = "";
        int byteArrayLength = byteArray.length;
        for(int i = 0; i < byteArrayLength; i++) {
            byte currentByte = byteArray[i];
            result += String.format("%8s", Integer.toBinaryString(currentByte & 0xFF)).replace(' ', '0');
            if(i < byteArrayLength - 1)
                result += " ";
        }
        return result;
    }


    public static int decodeInt(int bitLength, byte[] incoming, int startingBitLocation) {
        int value = 0;
        while (bitLength-- > 0) {
            value <<= 1;
            int location = bitLength + startingBitLocation;
            boolean set = (incoming[location / 8] & (1 << (location % 8))) != 0;
            if (set) {
                value |= 1;
            }
        }
        return value;
    }

 
    public static BitSet extractBitSet(int bitLength, byte[] incoming, int startingBitLocation) {
        BitSet incomingBitSet = BitSet.valueOf(incoming);
        BitSet result;
        if(bitLength < 0) {
            result = incomingBitSet.get(startingBitLocation, incomingBitSet.length() - 1);
        } else {
            result = incomingBitSet.get(startingBitLocation, startingBitLocation+bitLength);
        }

        return result;
    }
    

    public static void sleepMilliseconds(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RaspoidInterruptedException("The thread was interrupted unexpectedly while sleeping", e);
        }
    }
}