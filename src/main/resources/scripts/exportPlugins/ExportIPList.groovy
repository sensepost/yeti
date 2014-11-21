filename = utilApi.saveFileDialog("")
try{
    if (filename != ""){
	controller.addOutput("Saving data to: " + filename)
	outFile = new File(filename)
	appendFlag = false
	outStream = new FileOutputStream(outFile, appendFlag)
	writer = new FileWriter(outFile, appendFlag)
	outChannel = outStream.channel
	for (ip in dataApi.getAllIPS()){
            controller.addOutput("exporting: " +ip)
            outFile << ip+"\n"
	}
    }
} catch(all){
        controller.addOutput("error: "+all)
} 
controller.addOutput("Done writing file, size: " + outFile.text.size())