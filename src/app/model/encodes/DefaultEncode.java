package app.model.encodes;

//for completeness
public class DefaultEncode extends SkeletonEncode {

	private static DefaultEncode INSTANCE;

	private DefaultEncode() {
	};
	
	public static DefaultEncode getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultEncode();
		}
		return INSTANCE;
	}



	@Override
	protected char mapChar(char c) {
		return c;
	}

}
