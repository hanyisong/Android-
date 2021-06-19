package Utils;

/**
 * Created by han on 2021/6/11.
 */
public class StringUtil {
    static public boolean StrCheck(String str){
        boolean res = true;
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i)) && !Character.isLetter(str.charAt(i))){
                res = false;
                break;
            }
        }
        return res;
    }
}
