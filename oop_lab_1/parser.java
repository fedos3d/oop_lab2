import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser {

    private Pattern sections = Pattern.compile("\\s*\\[([^]]*)\\]\\s*");
    private Pattern keyValue = Pattern.compile("\\s*([^=]*)=(.*)");
    private ArrayList<String> filestorage = new ArrayList<>();
    private Map <String, Map<String, String>> sectionkeymap = new HashMap<>();

    public parser (String mem) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(mem))) {
            String line;
            while ((line = br.readLine()) != null) {
                filestorage.add(line);
            }
            if (checkiniext(mem) && checknosection() && checkwhitespaces() &&checknoequalvalue()) {
                load();
            } else {
                throw new IncorrectIniFileStrucureException("Incorrect ini file structure");
            }
            load();
        }
    }

    boolean checkiniext(String mem) { //Here we check file extenstion
            if (mem.endsWith(".ini")) {
                return true;
            } else {
                throw new NotIniFileException("File extension is not .ini");
            }
    }
    boolean checknosection() { //here we check if file contains params without sections
        String line;
        for (int i = 0; i < filestorage.size(); i++) {
            line = filestorage.get(i);
            if (!line.startsWith("[") && !line.startsWith(";")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    boolean checkwhitespaces() { //Here we check whitespaces in ini file
            String line;
            for (int i = 0; i < filestorage.size(); i++) {
                line = filestorage.get(i);
                if (!line.contains(";")) {
                    if (line.contains(" ")) {
                        return false;
                    }
                } else if (line.startsWith(";")) {
                }
                else {
                    int indofchar = line.indexOf(';');
                    if (line.substring(0, indofchar).contains(" ")) {
                        System.out.println(line.substring(0, indofchar));
                        return false;
                    }
                }
            }
            return true;
    }
    boolean checknoequalvalue () {
        String line;
        for (int i = 0; i < filestorage.size(); i++) {
            line = filestorage.get(i);
            if (!line.contains("=") && !line.contains("[") && !line.contains(";")) {
                if (!line.matches("^[a-zA-z0-9]*=$")) {
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void load () { //here we load our section, keys, values to a hashmap
            String line;
            String section = null;
            for (int i = 0; i < filestorage.size(); i++) {
                line = filestorage.get(i);
                Matcher m = this.sections.matcher(line);
                if (m.matches()) {
                    section = m.group(1).trim();
                    //System.out.println("Section: " + section); //debugging
                    if (!section.matches("^[a-zA-Z_0-9]+$")) {
                        throw new IncorrectIniFileStrucureException("Incorrect ini file structure");
                    }
                }
                else if (section != null) {
                    m = keyValue.matcher(line);
                    if (m.matches()) {
                        String key = m.group(1).trim();
                        //System.out.println("key: " + key); //debugging
                        if (!key.matches("^[a-zA-Z_0-9]+")) {
                            throw new IncorrectIniFileStrucureException("Incorrect ini file structure");
                        }
                        String value = m.group(2).trim();
                        if (value.isBlank()) {
                            throw new IncorrectIniFileStrucureException("Incorrect ini file structure");
                        }
                        //System.out.println("val: " + value); //debugging
                        if (value.contains(";")) {
                            value = value.substring(0,value.indexOf(';'));
                            if (!value.matches("^[0-9]*[.,][0-9]*$") && !value.matches("^[a-zA-Z0-9\\.]*$") && !value.matches("^[0-9]*$")) {
                                throw new IncorrectIniFileStrucureException("Incorrect ini file structure");
                            }
                            if (value.contains(" ")) {
                                throw new IncorrectIniFileStrucureException("Non valid ini file structure, param value contains whitesapces");
                            }
                        }
                        if (!value.matches("^[0-9]*[.,][0-9]*$") && !value.matches("^[a-zA-Z0-9\\.]*$") && !value.matches("^[0-9]*$")) {
                            throw new IncorrectIniFileStrucureException("Incorrect ini file structure");
                        }
                        Map<String, String> mainmap = sectionkeymap.get(section);
                        if (mainmap == null) {
                            sectionkeymap.put(section, mainmap = new HashMap<>());
                        }
                        mainmap.put(key, value);
                    }
                }
            }
    }

    public String getString (String sectionused, String key) {
        Map<String, String> mainmap;
        String a;
        try{
            mainmap = sectionkeymap.get(sectionused);
            if (mainmap == null) {
                throw new NoSuchSectionException("There is no such section");
            }
            a = mainmap.get(key);
            if (a == null) {
                throw new NoSuchKeyException("There is no such key");
            }
            return a;
        }
        catch (NullPointerException e) {
            throw new NoSuchSectionException("There is no such section");
        }
    }

    public int getInt(String sectionused, String key) {
        return Integer.parseInt(getString(sectionused, key));
    }


    public double getDouble (String sectionused, String key) {
        return Double.parseDouble(getString(sectionused, key));
    }


}
class NoSuchSectionException extends RuntimeException {
    public NoSuchSectionException(String errorMessage) {
        super(errorMessage);
    }
}
class NoSuchKeyException extends RuntimeException {
    public NoSuchKeyException(String errorMessage) {
        super(errorMessage);
    }
}
class IncorrectIniFileStrucureException extends RuntimeException {
    public IncorrectIniFileStrucureException(String errorMessage) {
        super(errorMessage);
    }
}
class NotIniFileException extends RuntimeException {
    public NotIniFileException(String errorMessage) {
        super(errorMessage);
    }
}
class WrongParsingMethod extends NumberFormatException {
    public WrongParsingMethod(String errorMessage) {
        super(errorMessage);
    }
}