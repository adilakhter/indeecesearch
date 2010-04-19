package indeece;

import java.util.Set;

public class VectModel extends Model {

	private boolean	highIdfOpt	= false;
	
	public VectModel(Index index, boolean highIdfOpt)
	{
		super(index);
		this.highIdfOpt = highIdfOpt;
	}
	
	@Override
	public Set<Result> search(String rawQuery) 
	{
		String query = index.preprocess(rawQuery);
		return null;
	}

}
