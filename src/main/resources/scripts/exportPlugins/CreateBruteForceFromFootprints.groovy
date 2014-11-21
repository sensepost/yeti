fileDir = dataApi.getConfigSetting("forwardLookup.BruteLocaction")

nameSet = new HashSet()

for (fid in dataApi.getFootprintIds()){
	for (host in dataApi.getHosts(fid)){
		nameSet.add(utilApi.getRelativeName(host))
	}	
}

controller.addOutput("Found (" + nameSet.size() +") name")

filename = utilApi.saveFileDialog("")
if (filename != ""){
	controller.addOutput("Saving data to: " + filename)
	outFile = new File(filename)
	appendFlag = false
	outStream = new FileOutputStream(outFile, appendFlag)
	writer = new FileWriter(outFile, appendFlag)
	outChannel = outStream.channel
	for (host in nameSet){
		outFile << host+"\n"
	}
}
controller.addOutput("Done writing file, size: " + outFile.text.size())