filename = utilApi.saveFileDialog("txt")
try {
    if (filename != "") {
	controller.addOutput("Saving data to: " + filename)
	outFile = new File(filename)
	appendFlag = false
	outStream = new FileOutputStream(outFile, appendFlag)
	writer = new FileWriter(outFile, appendFlag)
	outChannel = outStream.channel
	for (host in dataApi.getHosts())  {
            controller.addOutput("Exporting attributes for: " + host)
            outFile << host + ":\n"
            attrs = dataApi.getHostAttributes(host)
            if (attrs != null && attrs.size() > 0) {
                for (attr in attrs) {
                    outFile << attr + "\n"
                }
            } else {
                outFile << "No attributes found"
            }
	}
    }
} catch(all){
    controller.addOutput("Error: " + all)
} 
controller.addOutput("Done writing file, size: " + outFile.text.size())
