package applicationTools;

public class GetInstanceBluetoothManager {

	private static IBluetoothManager INSTANCE = null;
	
	public static IBluetoothManager getInstance()
	{	
		return INSTANCE;
	}
	
	public static void setInstance(IBluetoothManager i){
		INSTANCE = i;
	}
}
