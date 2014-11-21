package com.sensepost.yeti.common.update;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Johan Snyman
 */
public class TldMappingUpdater {

    private final static String ATTR_REGEX = "^.+[=].+[;]$";
    private final static String COMMENT_REGEX = "^\\s*[#].*";
    private final static String NODE_END_REGEX = "\\s*[}]\\s*";
    private final static String NODE_START_REGEX = ".+[{]\\s*";
    private final static String STRING_ATTR_REGEX = "\\s*[\"].+[\"].*[=].*[\"].+[\"][;]$";
    private final static String STRING_MAP_REGEX = "[\"].+[\"].*=.*[\"].+[\"][;]$";
    private final static String STRING_NODE_START = "\\s*[\"].+[\"].*[{]\\s*";

    private final static String IP4_REGEX = "(([0-9]{1,3}).){2}([0-9]{1,3})";
    private final static String IP6_REGEX = "([0-9A-Fa-f]{1,4}:)+([0-9A-Fa-f]{1,4}){1,}";

    private final static Pattern ATTR_PATTERN = Pattern.compile(ATTR_REGEX);
    private final static Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_REGEX);
    private final static Pattern NODE_END_PATTERN = Pattern.compile(NODE_END_REGEX);
    private final static Pattern NODE_START_PATTERN = Pattern.compile(NODE_START_REGEX);
    private final static Pattern STRING_ATTR_PATTERN = Pattern.compile(STRING_ATTR_REGEX);
    private final static Pattern STRING_MAP_PATTERN = Pattern.compile(STRING_MAP_REGEX);
    private final static Pattern STRING_NODE_START_PATTERN = Pattern.compile(STRING_NODE_START);

    private final static String TLD_NODE_NAME = "whois-servers";
    private final static String IPV4_NODE_NAME = "cidr-blocks";
    private final static String IPV6_NODE_NAME = "cidr6-blocks";
    private final static String HANDLES_NODE_NAME = "handles";
    private final static String SERVER_OPTIONS_NODE_NAME = "server-options";

    private final static String CONFIG_URL = "http://savannah.gnu.org/cgi-bin/viewcvs/jwhois/jwhois/example/jwhois.conf";

    private final static String TMP_FOLDER_NAME = "tldupdate";
    private final static String TMP_FILE_NAME = "tldupdate.conf";

    private static Path folderPath;
    private static Path configFile;
    private static BufferedReader br;
    
    private static ConfigReaderNode whoisServersNode;
    private static ConfigReaderNode cidrBlocksNode;
    private static ConfigReaderNode cidr6BlocksNode;
    private static ConfigReaderNode handlesNode;
    private static ConfigReaderNode serverOptionsNode;

    private static Reader reader;
    
    public TldMappingUpdater() {
    }

//    @Override
//    public boolean update() {
//        // 0. create a tmp directory
//        if (!createFolder()) {
//
//        }
//        // 1. download the file
//        // 2. read file
//        // 3. submit to db
//        // 4. cleanup of file and folder
//        return false;
//    }
    
    public static void main(String[] args) {
        if (!createFolder()) {
            
        }
        
        if (!downloadConfigFile()) {
            
        }
        
        if (!readFile(null)) {
            
        }
    }

    private static boolean createFolder() {
        try {
            String folderString = System.getProperty("user.dir") + "/" + TMP_FOLDER_NAME;
            folderPath = Files.createDirectory(folderPath);
        } catch (IOException ioe) {
            Logger.getLogger("TldMappingUpdater.createFolder").log(Level.SEVERE,
                    "Could not update the TLD mappings", ioe);
            return false;
        }
        return folderPath != null;
    }

    private static boolean downloadConfigFile() {
        try {
            URL website = new URL(CONFIG_URL);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("information.html");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static boolean readFile(String filePath) {
        List<ConfigReaderNode> result = new ArrayList<>();

        // Setup regex
        // TODO: Change this string to the parameter passed here, filePath
        Path newFile = Paths.get("/home/leviathan/tmp/jwhois.conf");

        //Reading from file
        try {
            br = Files.newBufferedReader(
                    newFile, Charset.defaultCharset());
//            reader = new Reader();
//            reader.increment();
//            whoisServersNode = getNode(reader);
//            cidrBlocksNode = getNode(reader);
//            cidr6BlocksNode = getNode(reader);
//            handlesNode = getNode(reader);
//            serverOptionsNode = getNode(reader);
//
//            // TODO: Write a function here to check if nodes could be read,
//            // mail someone if it doesn't
//            System.out.println("******************************************************************");
//            System.out.println("whoisServersNode:");
//            System.out.println(whoisServersNode);
//            System.out.println("******************************************************************");
//            System.out.println("cidrBlocksNode:");
//            System.out.println(cidrBlocksNode);
//            System.out.println("******************************************************************");
//            System.out.println("cidr6BlocksNode:");
//            System.out.println(cidr6BlocksNode);
//            System.out.println("******************************************************************");
//            System.out.println("handlesNode:");
//            System.out.println(handlesNode);
//            System.out.println("******************************************************************");
//            System.out.println("serverOptionsNode:");
//            System.out.println(serverOptionsNode);
//            System.out.println("******************************************************************");

        } catch (IOException exception) {
            System.out.println("Error while reading file");

        }

//        return result;
        return false;
    }

    private boolean persistTldData() {
        return false;
    }

    private ConfigReaderNode getNode(Reader reader) {
        ConfigReaderNode result = new ConfigReaderNode();
        String lineFromFile = reader.read();

        // Iterate over lines until a node start is found
        Matcher matcher = NODE_START_PATTERN.matcher(lineFromFile);
        while (lineFromFile == null || lineFromFile.isEmpty() || !matcher.matches()) {
            lineFromFile = reader.incrementAndRead();
            matcher = NODE_START_PATTERN.matcher(lineFromFile);
        }

        // If the start to a node was found:
        if (matcher.start() >= 0) {
            String name = lineFromFile.substring(matcher.start(), lineFromFile.indexOf(" {"));
            result.setName(name);

            Matcher endMatcher = NODE_END_PATTERN.matcher(lineFromFile);
            while (!endMatcher.matches()) {
                // Read the next line
                lineFromFile = reader.incrementAndRead();
                endMatcher = NODE_END_PATTERN.matcher(lineFromFile);

                // Check if it is a comment, or if the line is empty
                matcher = COMMENT_PATTERN.matcher(lineFromFile);
                if (lineFromFile == null || lineFromFile.isEmpty() || matcher.matches()) {
                    continue;
                }

                //Check if it is a string attribute - has to be before attribute
                matcher = STRING_ATTR_PATTERN.matcher(lineFromFile);
                if (matcher.matches()) {
                    String[] strArr = lineFromFile.trim().split("=");
                    String attrName = strArr[0].trim();
                    attrName = attrName.substring(1, attrName.length() - 1);
                    String attrValue = strArr[1].trim();
                    attrValue = attrValue.substring(1, attrValue.length() - 2);
                    result.addToStringValueMap(attrName, attrValue);
                    continue;
                }

                // Check if it is an attribute
                // attribute
                matcher = ATTR_PATTERN.matcher(lineFromFile);
                if (matcher.matches()) {
                    String[] strArr = lineFromFile.trim().split("=");
                    String attrName = strArr[0].trim();
                    String attrValue = strArr[1].trim();
                    attrValue = attrValue.substring(0, strArr[1].length() - 2);

                    if ("type".equals(attrName)) {
                        result.setType(attrValue);
                    } else {
                        result.addToValueMap(attrName, attrValue);
                    }
                    continue;
                }

                // Check if it is a string node - has to be checked before node start
                matcher = STRING_NODE_START_PATTERN.matcher(lineFromFile);
                if (matcher.matches()) {
                    result.addStringNode(getNode(reader));
                    continue;
                }

                // Check if it is a node
                matcher = NODE_START_PATTERN.matcher(lineFromFile);
                if (matcher.matches()) {
                    result.addNode(getNode(reader));
                }
                endMatcher = NODE_END_PATTERN.matcher(lineFromFile);
            }
        }

        return result;
    }

    class Reader {

        String currentLine;

        void increment() {
            try {
                currentLine = br.readLine();
            } catch (IOException ioe) {
                System.err.println("!!!!!!!!!!!!!!!!!!!!!" + ioe.getMessage());
            }
        }

        String incrementAndRead() {
            increment();
            return currentLine;
        }

        String read() {
            return currentLine;
        }
    }

    class ConfigReaderNode {

        private String name;
        private String type;

        private final Map<String, String> valueMap;
        private final Map<String, String> stringValueMap;

        private final List<ConfigReaderNode> nodes;
        private final List<ConfigReaderNode> stringNodes;

        ConfigReaderNode() {
            valueMap = new HashMap<>();
            stringValueMap = new HashMap<>();
            nodes = new ArrayList<>();
            stringNodes = new ArrayList<>();
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        void addToValueMap(String attrName, String attrValue) {
            valueMap.put(attrName, attrValue);
        }

        void addToStringValueMap(String attrName, String attrValue) {
            stringValueMap.put(attrName, attrValue);
        }

        Map<String, String> getValueMap() {
            return valueMap;
        }

        Map<String, String> getStringValueMap() {
            return stringValueMap;
        }

        List<ConfigReaderNode> getNodes() {
            return nodes;
        }

        void addNode(ConfigReaderNode node) {
            nodes.add(node);
        }

        List<ConfigReaderNode> getStringNodes() {
            return stringNodes;
        }

        void addStringNode(ConfigReaderNode node) {
            stringNodes.add(node);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getName()).append(" {").append(System.lineSeparator());
            for (String key : valueMap.keySet()) {
                sb.append("\t").append(key).append(" = ")
                        .append(valueMap.get(key))
                        .append(System.lineSeparator());
            }

            for (String key : stringValueMap.keySet()) {
                sb.append("\t\"").append(key).append("\" = \"")
                        .append(stringValueMap.get(key)).append("\"")
                        .append(System.lineSeparator());
            }

            for (ConfigReaderNode crn : nodes) {
                sb.append(crn.toString());
            }

            for (ConfigReaderNode crn : stringNodes) {
                sb.append(crn.toString());
            }

            sb.append("}").append(System.lineSeparator());
            return sb.toString();
        }
    }
}
