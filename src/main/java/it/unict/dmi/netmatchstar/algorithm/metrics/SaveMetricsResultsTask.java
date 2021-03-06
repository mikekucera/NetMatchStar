/*
 * Copyright (c) 2015, Rosalba Giugno.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the University of Catania nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ROSALBA GIUGNO ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ROSALBA GIUGNO BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package it.unict.dmi.netmatchstar.algorithm.metrics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import it.unict.dmi.netmatchstar.utils.Common;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

/**
 * 
 * @author Fabio Rinnone
 *
 */
public class SaveMetricsResultsTask extends AbstractTask {
	
	private static boolean completedSuccessfully;

	private File file;
	private String fileName;

	private HashMap<String,ArrayList<Double>> resultsMap;
	private String queryNetName;
	private String targetNetName;

	private BufferedWriter writer = null;

	private TaskMonitor taskMonitor;
	private boolean interrupted;

	public SaveMetricsResultsTask(String queryNetName, String targetNetName,
			HashMap<String,ArrayList<Double>> resultsMap, File file) {
		this.queryNetName = queryNetName;
		this.targetNetName = targetNetName;
		this.resultsMap = resultsMap;
		this.file = file;
		//this.fileName = fileName;
		
		fileName = file.getAbsolutePath();
	}
	
	@Override
	public void run(TaskMonitor tm) throws Exception {
		taskMonitor = tm;
		
		if (taskMonitor == null) {
			throw new IllegalStateException("Task Monitor is not set.");
		}
		
		System.out.println("Saving Metrics Results...");
		taskMonitor.setProgress(-1.0);
		taskMonitor.setStatusMessage("Saving Metrics Results...");
		
		writeFile();
		
		if (interrupted) 
			return;
	}
	
	private void writeFile() throws IOException {
		//Scrittura su file temporanei
		File tempFile = File.createTempFile("tmp",null,new File("."));
	    writer = new BufferedWriter(new FileWriter(tempFile));

		writer.write("\"Query Network\",\"" + queryNetName + "\"\n");
		writer.write("\"Target Network\",\"" + targetNetName + "\"\n");
		writer.write("\n");

		writer.write("\"Network\",\"Average degree\",\"Average clustering coefficient\",\"Assortativity\"\n");

		Set<Map.Entry<String,ArrayList<Double>>> entrySet = resultsMap.entrySet();
		for (Map.Entry<String, ArrayList<Double>> entry : entrySet) {
			String networkName = entry.getKey();
			ArrayList<Double> values = entry.getValue();
			writer.write("\"" + networkName + "\",");
			Iterator<Double> iterator = values.iterator();
			while(iterator.hasNext()) {
				writer.write("\"" + iterator.next() + "\"");
				if (iterator.hasNext())
					writer.write(",");
			}
			writer.write("\n");
		}

	    writer.close();
	    tempFile.renameTo(file);
	}
	
	public static boolean isCompletedSuccessfully() {
		return completedSuccessfully;
	}
    
    public String getTitle() {
        return Common.APP_NAME;
    }

    public void halt() {
    	interrupted = true;
    }

    public void setTaskMonitor(TaskMonitor tm) throws IllegalThreadStateException {
		if(taskMonitor != null)
			throw new IllegalStateException("Task Monitor is already set.");
    	taskMonitor = tm;
    }
    
    @Override
	public void cancel() {
		//this.interrupted = true;
	}
}
