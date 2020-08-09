package awsome.techmod.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
	/**
	* Round to certain number of decimals
	* 
	* @param f The float to round
	* @param decimalPlace Number of decimal places to round to
	* @return The rounded value as a float
	*/
	public static float roundFloat(float f, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(f));
	    bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
	    return bd.floatValue();
	}
}
