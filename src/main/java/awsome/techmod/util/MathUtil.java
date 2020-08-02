package awsome.techmod.util;

import java.math.BigDecimal;

public class MathUtil {
	/**
	* Round to certain number of decimals
	* 
	* @param d
	* @param decimalPlace
	* @return
	*/
	public static float roundFloat(float f, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(f));
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	    return bd.floatValue();
	}
}
