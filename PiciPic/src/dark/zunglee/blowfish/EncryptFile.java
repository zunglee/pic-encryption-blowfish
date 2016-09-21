package dark.zunglee.blowfish;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;
  
/**
 * @author Dark
 *
 */
public class EncryptFile {  
  
    static KeyGenerator keyGenerator = null;
    static SecretKey secretKey = null;  
    static Cipher cipher = null;  
    static String masterPassword = "";
    public EncryptFile() {  
        try {  
            /** 
             * Create a Blowfish key 
             */  
        	secretKey = new SecretKeySpec(masterPassword.getBytes(), "Blowfish");
            /** 
             * Create an instance of cipher mentioning the name of algorithm 
             *     - Blowfish 
             */  
            cipher = Cipher.getInstance("Blowfish");  
        } catch (NoSuchPaddingException ex) {  
            System.out.println(ex);  
        } catch (NoSuchAlgorithmException ex) {  
            System.out.println(ex);  
        }  
    }  
  
    public static boolean createFolderIfNot(String directoryPath ,String folderName){
    	// Create Folder if not exist to store encrypted picture or folder 
		File folder = new File(directoryPath+folderName);
    //Check if folder exist
		if(!folder.exists())
			if(folder.mkdir())
	          return true;
			else return false;
		return true;
    } 
    
    public static void main(String[] args) {  
        String fileToEncrypt = "";  
        String encryptedFile = "";  
        String decryptedFile = "";  
        String directoryPath = "Z:/us/amazonInterview/";  
        String encryptedFilePath = "";
        String decryptedFilePath = "";
        
        // encrypted folder
        if(createFolderIfNot(directoryPath,"encryptedFolder")){
        	encryptedFilePath= directoryPath+"/encryptedFolder/";
        	System.out.println("Folder created ");
        }
		else System.out.println("Folder not able to create");
	

        // decrypted folder
        if(createFolderIfNot(directoryPath,"decryptedFolder")){
        	decryptedFilePath  = directoryPath+"/decryptedFolder/";
        	System.out.println("Folder created ");
        }
		else System.out.println("Folder not able to create");
       
       //  store all names of picture or file in a folder  
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();
        EncryptFile encryptFile = new EncryptFile();
        
       // loop to read each  picture or file and to encrypt it 
     for(int i=0 ; i< files.length ;i++){
            encryptedFile = files[i].getName();  
        System.out.println("Starting Encryption...");  
        encryptFile.encrypt(directoryPath + encryptedFile,    encryptedFilePath + encryptedFile);  
        System.out.println("Encryption completed...");
        }
        
        dir = new File(encryptedFilePath);
        files = dir.listFiles();
       
   /*  // loop to read each  picture or file and to decrypt it
        for(int i=0 ; i< files.length ;i++){
        System.out.println("Starting Decryption...");  
        encryptedFile = files[i].getName();
        encryptFile.decrypt(encryptedFilePath + encryptedFile,decryptedFilePath + encryptedFile);  
       System.out.println("Decryption completed...");
        }*/
    }  
  
    private void encrypt(String srcPath, String destPath) {  
        
    	File rawFile = new File(srcPath);  
        File encryptedFile = new File(destPath);  
        InputStream inStream = null;  
        OutputStream outStream = null;  
        try {  
            /** 
             * Initialize the cipher for encryption 
             */  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);  
            /** 
             * Initialize input and output streams 
             */  
            inStream = new FileInputStream(rawFile);  
            outStream = new FileOutputStream(encryptedFile);  
            byte[] buffer = new byte[1024];  
            int len;  
            while ((len = inStream.read(buffer)) > 0) {  
                outStream.write(cipher.update(buffer, 0, len));  
                outStream.flush();  
            }  
            outStream.write(cipher.doFinal());  
            inStream.close();  
            outStream.close();  
        } catch (IllegalBlockSizeException ex) {  
            System.out.println(ex);  
        } catch (BadPaddingException ex) {  
            System.out.println(ex);  
        } catch (InvalidKeyException ex) {  
            System.out.println(ex);  
        } catch (FileNotFoundException ex) {  
            System.out.println(ex);  
        } catch (IOException ex) {  
            System.out.println(ex);  
        }  
    }  
    
    /** 
     *  
     * @param srcPath 
     * @param destPath 
     * 
     * Encrypts the file in srcPath and creates a file in destPath 
     */  
   
  
    /** 
     *  
     * @param srcPath 
     * @param destPath 
     * 
     * Decrypts the file in srcPath and creates a file in destPath 
     */  
    private void decrypt(String srcPath, String destPath) {  
        File encryptedFile = new File(srcPath);  
        File decryptedFile = new File(destPath);  
        InputStream inStream = null;  
        OutputStream outStream = null;  
        try {  
            /** 
             * Initialize the cipher for decryption 
             */  
            cipher.init(Cipher.DECRYPT_MODE, secretKey);  
            /** 
             * Initialize input and output streams 
             */  
            inStream = new FileInputStream(encryptedFile);  
            outStream = new FileOutputStream(decryptedFile);  
            byte[] buffer = new byte[1024];  
            int len;  
            while ((len = inStream.read(buffer)) > 0) {  
                outStream.write(cipher.update(buffer, 0, len));  
                outStream.flush();  
            }  
            outStream.write(cipher.doFinal());  
            inStream.close();  
            outStream.close();  
        } catch (IllegalBlockSizeException ex) {  
            System.out.println(ex);  
        } catch (BadPaddingException ex) {  
            System.out.println(ex);  
        } catch (InvalidKeyException ex) {  
            System.out.println(ex);  
        } catch (FileNotFoundException ex) {  
            System.out.println(ex);  
        } catch (IOException ex) {  
            System.out.println(ex);  
        }  
    }  
}  
