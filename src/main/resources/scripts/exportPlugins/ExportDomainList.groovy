filename = utilApi.saveFileDialog("")
try{
    if (filename != ""){
	controller.addOutput("Saving data to: " + filename)
	outFile = new File(filename)
	appendFlag = false
	outStream = new FileOutputStream(outFile, appendFlag)
	writer = new FileWriter(outFile, appendFlag)
	outChannel = outStream.channel
	for (domain in dataApi.getDomains()){
            controller.addOutput("exporting: " +domain)
            outFile << "*."+domain+"\n"
	}
    }
} catch(all){
        controller.addOutput("error: "+all)
} 
controller.addOutput("Done writing file, size: " + outFile.text.size())