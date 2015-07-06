package it.univr.utils;

import it.univr.exceptions.DatabaseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * Uploader di File attraverso l'implementazione di un client FTP.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class FTPFileUploader implements Serializable, FileUploadInterface {

	/** Serial UID Version.	 */
	private static final long serialVersionUID = 8977637017965643347L;

	/** File delle properties che setta la directory di upload del file */
	private static final String configFile = "/it/univr/properties/config.properties";

	/** Properties lette dal file {@link #configFile}. */
	private Properties properties;
	
	/** File part utilizzato durante l'upload */
	private Part file;
	
	/** Byte del fiel da salvare*/
	private byte[] fileByte;
	
	/** Estensione del file da salvare */
	private String extension = "";

	/** Pathname del file da salvare */
	private String pathname;
	
	/** Nome del file da salvare */
	private String filename;

	/**
	 * Costruisco un nuovo {@code FTPFileUploader}}
	 */
	public FTPFileUploader(){
		properties = new Properties();

		try {
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(configFile));
		} catch (IOException e) {
			throw new DatabaseException();
		}
	}
	
	public Part getFile() { return file; }

	public void setFile(Part file) { this.file = file; }
	
	public String getFilename() { return filename; }
	
	public void upload() {
		if (file != null) {
			System.out.println("call upload...");
			System.out.println("content-type:{0}" + file.getContentType());
			System.out.println("filename:{0}" + file.getName());
			System.out.println("submitted filename:{0}"
					+ file.getSubmittedFileName());
			System.out.println("size:{0}" + file.getSize());

			String filename = file.getSubmittedFileName();
			String[] estensione = filename.split("\\.");
			this.pathname = properties.getProperty("images.path");
			
			this.extension = estensione[estensione.length - 1];

			this.fileByte = new byte[(int) file.getSize()];

			try {
				file.getInputStream().read(fileByte);
			} catch (IOException e) {
				e.printStackTrace();
			}

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Uploaded!"));
		}
	}
	
	public void save(String nomefile) {

		//Creo il nome del file salvato su disco
		filename = nomefile + "." + this.extension;
				
		FileOutputStream fos = null;
		
		File fileToSave = new File(pathname + filename);
		
		try {
			fos = new FileOutputStream(fileToSave);
			fos.write(this.fileByte);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.saveToRemoteResources(fileToSave);
		
        //Cancello il file dalla directory temporanea
        //fileToSave.delete();
        
	}
	
	/**
	 * Salvo il file caricato nel repository remoto
	 * @param fileToSave file da salvare ne repository remoto
	 */
	private void saveToRemoteResources(File fileToSave){
		
		String ftpUrl = properties.getProperty("images.ftpserver");
    	
    	String host = ftpUrl.split(":")[0];
    	int port = Integer.parseInt(ftpUrl.split(":")[1]);
    	
    	System.out.println("ftphost: "+host);
    	System.out.println("ftpport: "+port);
    	FTPClient ftpClient = new FTPClient();
    	
    	try {
    		 
            ftpClient.connect(host, port);
            ftpClient.login("", "");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(pathname + filename);
            
            
            
            String firstRemoteFile = filename;
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            System.out.println("Start uploading first file");
            
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            } 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	public String normalizeUploadFileName(List<String> nameparts){
		String res = "";

		char[] target = { '@', '#', '.', ',', '>', '<', '-', '_', '|', '/',
				'\\', '\'', '\"', '!', '?', '=', 'ò', 'à', 'ù', 'ì', 'è', '+',
				'-', '*', '^', '°', '§', 'ç', 'é', '£', '$', '%', '&', '(',
				')', ';', ':' };
		
		for (String s : nameparts) {
			if (s != null) {
				char[] temp = s.toLowerCase().toCharArray();

				for (int i = 0; i < temp.length; i++)
					for (int j = 0; j < target.length; j++)
						if (temp[i] == target[j])
							temp[i] = ' ';

				res += new String(temp) + "_";
			}

		}

		return res.substring(0, res.length()-1).replace(" ", "");
	}

	
}
