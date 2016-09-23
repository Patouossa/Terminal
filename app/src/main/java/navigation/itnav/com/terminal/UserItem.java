package navigation.itnav.com.terminal;

/**
 * Created by IBRAHIM on 15/09/2016.
 */

public class UserItem {
    private String text;
    private boolean hasRadioButton;
    private String radioButtonText;
    private int code;
    public UserItem(String text, boolean hasRadioButton, String radioButtonText, int code){
        this.hasRadioButton = hasRadioButton;
        this.text = text;
        this.radioButtonText = radioButtonText;
        this.code = code;
    }

    public String getText(){
        return text;
    }

    public boolean getHasRadioButton(){
        return hasRadioButton;
    }

    public String getRadioButtonText(){
        return radioButtonText;
    }

    public int getCode(){
        return code;
    }
}

