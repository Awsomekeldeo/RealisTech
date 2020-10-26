package awsome.realistech.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MoldType {
	
	INGOT("ingot"),
	SWORD("sword"),
	PICKAXE("pickaxe"),
	SHOVEL("shovel"),
	PROPICK("propick"),
	AXE("axe");
	
	private static final MoldType[] TYPES = values();
	private String id;
	
	private static final Map<String, MoldType> NAME_LOOKUP_TABLE = Arrays.stream(TYPES).collect(Collectors.toMap(MoldType::getTypeForString, (type) -> {
		return type;
	}));
	
	
	private MoldType(String name) {
		this.id = name;
	}
	
	@Override
	public String toString() {
		return this.id;
	}
	
	public static MoldType getFromString(String s) {
		return s == null ? null : NAME_LOOKUP_TABLE.get(s);
	}
	
	public String getTypeForString() {
		return this.id;
	}
}
