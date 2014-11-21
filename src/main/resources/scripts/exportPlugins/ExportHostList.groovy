filename = utilApi.saveFileDialog("")
try{
    if (filename != ""){
	controller.addOutput("Saving data to: " + filename)
	outFile = new File(filename)
	appendFlag = false
	outStream = new FileOutputStream(outFile, appendFlag)
	writer = new FileWriter(outFile, appendFlag)
	outChannel = outStream.channel
	for (host in dataApi.getHosts()){
            controller.addOutput("exporting: " +host)
            outFile << host+"\n"
	}
    }
} catch(all){
        controller.addOutput("error: "+all)
} 
controller.addOutput("Done writing file, size: " + outFile.text.size())