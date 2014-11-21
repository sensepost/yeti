filename = utilApi.saveFileDialog(".xml")
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
controller.addOutput("Import done.")