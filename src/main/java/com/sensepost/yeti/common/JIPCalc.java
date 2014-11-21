package com.sensepost.yeti.common;

/**
 *
 * @author Jurgens van der Merwe
 */
import java.util.ArrayList;

public class JIPCalc {

    static int baseIPnumeric;
    static int netmaskNumeric;
    static ArrayList<String> al;

    public JIPCalc(String input) {
        al = new ArrayList<>();
        if (input.contains("/255.")) {
            // when user supplied xxx.xxx.xxx/255.xxx.xxx.xxx (IP/Subnet)
            targetsSubnet(input);
            al = getAvailableIPs();
        } else if (input.contains("-")) {
            // when user supplied xxx.xxx.xxx-xxx.xxx.xxx.xxx (StartIP-EndIP)
            targetsRange(input);
        } else if (input.contains("/")) {
            // when user supplied xxx.xxx.xxx/(8|32) (IP/CIDR)
            targetsCIDR(input);
            al = getAvailableIPs();
        } else if (validateIPAddress(input)) {
            // when user supplied xxx.xxx.xxx (Single IP)
            targetsSingle(input);
        }

    }

    public static ArrayList<String> getTargets() {
        return al;
    }

    public static void targetsSubnet(String input) {
        String data[] = input.split("/");
        String startip = data[0];
        if (!validateIPAddress(startip)) {
            throw new NumberFormatException("Invalid IP address");
        }

        String netmask = data[1];
        String[] st = startip.split("\\.");
        if (st.length != 4) {
            throw new NumberFormatException("Invalid IP address: " + startip);
        }

        int i = 24;
        baseIPnumeric = 0;
        for (int n = 0; n < st.length; n++) {
            int value = Integer.parseInt(st[n]);
            if (value != (value & 0xff)) {
                throw new NumberFormatException("Invalid IP address: "
                        + startip);
            }

            baseIPnumeric += value << i;
            i -= 8;
        }

        /* Netmask */
        st = netmask.split("\\.");
        if (st.length != 4) {
            throw new NumberFormatException("Invalid netmask address: "
                    + netmask);
        }

        i = 24;
        netmaskNumeric = 0;
        if (Integer.parseInt(st[0]) < 255) {
            throw new NumberFormatException(
                    "The first byte of netmask can not be less than 255");
        }
        for (int n = 0; n < st.length; n++) {
            int value = Integer.parseInt(st[n]);
            if (value != (value & 0xff)) {
                throw new NumberFormatException("Invalid netmask address: "
                        + netmask);
            }

            netmaskNumeric += value << i;
            i -= 8;
        }

        /*
         * see if there are zeroes inside netmask, like: 1111111101111 This is
         * illegal, throw exception if encountered. Netmask should always have
         * only ones, then only zeroes, like: 11111111110000
         */
        boolean encounteredOne = false;
        int ourMaskBitPattern = 1;
        for (i = 0; i < 32; i++) {
            if ((netmaskNumeric & ourMaskBitPattern) != 0) {
                encounteredOne = true; // the bit is 1
            } else { // the bit is 0
                if (encounteredOne == true) {
                    throw new NumberFormatException("Invalid netmask: "
                            + netmask + " (bit " + (i + 1) + ")");
                }
            }

            ourMaskBitPattern = ourMaskBitPattern << 1;
        }
    }

    public static void targetsRange(String input) {

        String[] data = input.split("-");
        String startIP = data[0];
        String endIP = data[1];
        if (!(validateIPAddress(startIP) && validateIPAddress(endIP))) {
            throw new NumberFormatException("Your IP addresses are wrong please ensure they are correct");
        }

        long sIP = new Long(ipToInt(startIP));
        long eIP = new Long(ipToInt(endIP));
        if (eIP < sIP) {
            throw new NumberFormatException("End IP cannot be smaller than Start IP");
        }
        //long target = SIP;
        while (sIP <= eIP) {
            al.add(fromNumerical(sIP));
            sIP++;
        }
    }

    public static void targetsCIDR(String input) {
        String[] st = input.split("\\/");
        if (st.length != 2) {
            throw new NumberFormatException("Invalid CIDR format '"
                    + input + "', should be: xx.xx.xx.xx/xx");
        }
        String symbolicIP = st[0];
        String symbolicCIDR = st[1];
        Integer numericCIDR = new Integer(symbolicCIDR);
        if (numericCIDR > 32) {
            throw new NumberFormatException("CIDR can not be greater than 32");
        }

        /* IP */
        st = symbolicIP.split("\\.");
        if (st.length != 4) {
            throw new NumberFormatException("Invalid IP address: " + symbolicIP);
        }

        int i = 24;
        baseIPnumeric = 0;
        for (int n = 0; n < st.length; n++) {
            int value = Integer.parseInt(st[n]);
            if (value != (value & 0xff)) {
                throw new NumberFormatException("Invalid IP address: "
                        + symbolicIP);
            }

            baseIPnumeric += value << i;
            i -= 8;
        }

        /* netmask from CIDR */
        if (numericCIDR < 8) {
            throw new NumberFormatException(
                    "Netmask CIDR can not be less than 8");
        }
        netmaskNumeric = 0xffffffff;
        netmaskNumeric = netmaskNumeric << (32 - numericCIDR);
    }

    public static void targetsSingle(String input) {
        if (validateIPAddress(input)) {
            al.add(input);
        }
    }

    public static String fromNumerical(long address) {
        StringBuffer sb = new StringBuffer();
        String[] ip = new String[4];
        int a = 3;
        for (int i = 0; i < 4; i++) {
            long value = (address & 0xff);
            address = address >> 8;
            ip[a--] = Long.toString(value);
        }
        for (int i = 0; i < 4; i++) {
            sb.append(ip[i]);
            if (i != 3) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    public static Long ipToInt(String addr) {
        String[] addrArray = addr.split("\\.");

        long num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;

            num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
        }
        return num;
    }

    public static String convertNumericIpToSymbolic(Integer ip) {
        StringBuffer sb = new StringBuffer(15);
        for (int shift = 24; shift > 0; shift -= 8) {
            // process 3 bytes, from high order byte down.
            sb.append(Integer.toString((ip >>> shift) & 0xff));
            sb.append('.');
        }
        sb.append(Integer.toString(ip & 0xff));
        return sb.toString();
    }

    //Function to get targets when using CIDR
    public static ArrayList<String> getAvailableIPs() {
        ArrayList<String> result = new ArrayList<String>();
        int numberOfBits;
        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {
            if ((netmaskNumeric << numberOfBits) == 0) {
                break;
            }
        }
        Integer numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {
            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;
        }

        Integer baseIP = baseIPnumeric & netmaskNumeric;

        for (int i = 1; i < (numberOfIPs); i++) {
            Integer ourIP = baseIP + i;
            String ip = convertNumericIpToSymbolic(ourIP);
            result.add(ip);
        }
        return result;
    }

    public static boolean validateIPAddress(String input) {
        String IPAddress = input;
        if (IPAddress.startsWith("0")) {
            return false;
        }

        if (IPAddress.isEmpty()) {
            return false;
        }

        if (IPAddress.matches("\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z")) {
            return true;
        }
        return false;
    }

}
