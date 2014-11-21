import groovy.util.XmlParser;

def executeOnShell(String command) {
    return executeOnShell(command, new File(System.properties.'user.dir'))
}

private def executeOnShell(String command, File workingDir) {
    println command
    try {
        process = new ProcessBuilder(addShellPrefix(command))
        .directory(workingDir)
        .redirectErrorStream(true)
        .start()
        process.inputStream.eachLine {controller.addOutput(it+"\n")}
        process.waitFor();
        return process.exitValue()
    } catch (all) {
        controller.addOutput(": error!!!! "+all)
    }
}

private def addShellPrefix(String command) {
    commandArray = new String[3]
    commandArray[0] = "sh"
    commandArray[1] = "-c"
    commandArray[2] = command
    return commandArray
}

controller.addOutput("Starting the script");
filename = "/tmp/yeti_nmap_temp.xml"
nmapLocation = utilApi.getConfigValue("nmap.location");
if (nmapLocation == ""){
    nmapLocation = utilApi.showInputBox("Please enter nmap exe location")
    if (nmapLocation != ""){
        utilApi.setConfigValue("nmap.location",nmapLocation)
    }
}
commandString = utilApi.showInputBox("Enter nmap params")
commandString = commandString + " -oX " + filename
controller.addOutput("Removing old file")
executeOnShell("rm "+filename)


for (ip in dataApi.getAllIPS()){
    commandString = commandString + " " + ip
}
controller.addOutput("Executing: " + nmapLocation + " " +commandString)
executeOnShell(nmapLocation + " " +commandString)

if (filename != ""){
    controller.addOutput("Import started")
    String xml = ""
    resultsFile = new File(filename)
    resultsFile.eachLine{ xml=xml+it+"\n"}
    xml=xml.trim()
    controller.addOutput("Done reading "+filename)
    try{
        controller.addOutput(xml)
        targets = new XmlParser().parseText(xml)
        for (t in targets.host){     
            ipAddr = t.address.'@addr'.text()
            for (p in t.ports[0]){
                if (p.state[0] != null){			
                    portNumber = p.'@portid' 
                    portState = p.state[0].'@state'
                    name=""
                    version=""
                    extraInfo=""
                    product=""
                    if (p.service[0] != null){
                        name = p.service[0].'@name'
                        version = p.service[0].'@version'
                        if (version == null) version = ""
                        extraInfo = p.service[0].'@extrainfo'
                        if (extraInfo == null) extraInfo = ""
                        product = p.service[0].'@product'
                        if (product == null) product = ""
                        extraInfo = (extraInfo + " " + product).trim()
                    }                                                                                                                                             
                    dataApi.addPortToIP(ipAddr, Integer.parseInt(portNumber),portState,name,version,extraInfo)
                    controller.addOutput(ipAddr +":"+portNumber+" "+portState+" "+name+" "+version+" "+extraInfo)
                }
            }
        }
    } catch(all){
        controller.addOutput(": error: "+all)
    } 
}
controller.addOutput("Script done.")